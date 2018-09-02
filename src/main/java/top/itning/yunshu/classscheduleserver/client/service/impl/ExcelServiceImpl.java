package top.itning.yunshu.classscheduleserver.client.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.itning.yunshu.classscheduleserver.client.service.ExcelService;
import top.itning.yunshu.classscheduleserver.dao.ClassEntityDao;
import top.itning.yunshu.classscheduleserver.dao.ClassScheduleDao;
import top.itning.yunshu.classscheduleserver.dao.ProfessionDao;
import top.itning.yunshu.classscheduleserver.entity.ClassEntity;
import top.itning.yunshu.classscheduleserver.entity.ClassSchedule;
import top.itning.yunshu.classscheduleserver.entity.Profession;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;

@Service
@Transactional(rollbackOn = Exception.class)
public class ExcelServiceImpl implements ExcelService {
    private static final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);
    /**
     * xlsx mime type
     */
    private static final String MIME_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    /**
     * xls mime type
     */
    private static final String MIME_XLS = "application/vnd.ms-excel";

    private final ClassScheduleDao classScheduleDao;

    private final ClassEntityDao classEntityDao;

    private final ProfessionDao professionDao;

    @Autowired
    public ExcelServiceImpl(ClassScheduleDao classScheduleDao, ClassEntityDao classEntityDao, ProfessionDao professionDao) {
        this.classScheduleDao = classScheduleDao;
        this.classEntityDao = classEntityDao;
        this.professionDao = professionDao;
    }

    @Override
    public void upFile(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        Workbook workbook;
        if (MIME_XLSX.equals(contentType)) {
            logger.debug("创建xlsx类型");
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (MIME_XLS.equals(contentType)) {
            logger.debug("创建xls类型");
            workbook = new HSSFWorkbook(file.getInputStream());
        } else {
            logger.warn("文件类型不正确:" + contentType);
            throw new IllegalArgumentException("文件类型不正确:" + contentType);
        }
        logger.debug("获取到的工作表个数->" + workbook.getNumberOfSheets());
        logger.debug("获取第" + (2 + 1) + "个工作表");
        Sheet sheetAt = workbook.getSheetAt(2);
        logger.debug("名:" + sheetAt.getSheetName());
        logger.debug("获取到最后的行数->" + sheetAt.getLastRowNum());
        for (int i = 2; i <= sheetAt.getLastRowNum(); i++) {
            logger.debug("开始获取第" + i + "行");
            Row row = sheetAt.getRow(i);
            for (int j = 1; j < 25; j++) {
                //-->
                int select = j;
                //-->
                String className = sheetAt.getRow(1).getCell(select + 1).getStringCellValue();
                String cellValue = getCellValue(row, select + 1);
                if (cellValue != null) {
                    String[] info = cellValue.trim().split("\n");
                    if (info.length == 3) {
                        System.out.print("星期" + analysisWeek(i - 1));
                        System.out.print(" 第" + analysisSection(row) + "节");
                        System.out.println(" 班级:" + className + " 课程:" + analysisName(info[0].trim()) + " 地点:" + info[1].trim() + " 教师:" + info[2].trim());
                        startSave(i, row, className, info);
                    } else {
                        logger.error(Arrays.toString(info));
                    }
                }
            }
        }
    }

    private void startSave(int i, Row row, String className, String[] info) {
        //判断专业存在
        if (!professionDao.existsByName(analysisProfession(className))) {
            Profession profession = new Profession();
            profession.setName(analysisProfession(className));
            professionDao.saveAndFlush(profession);
        }
        Profession profession = professionDao.getByName(analysisProfession(className));
        //判断班级存在
        if (!classEntityDao.existsByName(className)) {
            ClassEntity classEntity = new ClassEntity();
            classEntity.setName(className);
            classEntity.setVersion("1");
            classEntity.setProfession(profession);
            classEntityDao.saveAndFlush(classEntity);
        }
        ClassEntity classEntity = classEntityDao.getByName(className);
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setWeek(analysisWeek(i - 2));
        classSchedule.setSection(analysisSection(row));
        classSchedule.setName(analysisName(info[0].trim()));
        classSchedule.setLocation(info[1].trim());
        classSchedule.setTeacher(info[2].trim());
        classSchedule.setByClass(classEntity);
        classScheduleDao.save(classSchedule);
    }

    private String analysisProfession(String className) {
        if (className.startsWith("计科")) {
            char c = className.charAt(3);
            if (c == '7') {
                return "17计算机科学与技术";
            }
        } else {
            char c = className.charAt(3);
            if (c == '7') {
                return "17软件工程";
            }
        }
        throw new IllegalArgumentException("className:" + className);
    }

    private String analysisName(String s) {
        int i = s.indexOf("（");
        return s.substring(0, i);
    }

    private int analysisSection(Row row) {
        String value = getCellValue(row, 1);
        switch (value) {
            case "1-2节": {
                return 1;
            }
            case "3-4节": {
                return 2;
            }
            case "5-6节": {
                return 3;
            }
            case "7-8节": {
                return 4;
            }
            case "9-10节": {
                return 5;
            }
            default:
                throw new IllegalArgumentException("value:" + value);
        }
    }

    private int analysisWeek(int row) {
        if (row < 6) {
            return 1;
        } else if (row > 23 && row <= 24) {
            return row / 5 + 2;
        } else if (row > 24) {
            return row / 5 + 1;
        } else {
            return row / 5 + 1;
        }
    }

    /**
     * 获取单元格中的数据
     *
     * @param row     行
     * @param cellNum 单元格号
     * @return 该单元格的数据
     */
    private String getCellValue(Row row, int cellNum) {
        String stringCellValue = null;
        Cell cell;
        if ((cell = row.getCell(cellNum)) != null) {
            try {
                stringCellValue = cell.getStringCellValue();
            } catch (IllegalStateException e) {
                logger.info("CellNum->" + cellNum + "<-尝试获取String类型数据失败");
                try {
                    logger.info("CellNum->" + cellNum + "<-尝试获取Double类型数据");
                    stringCellValue = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e1) {
                    try {
                        logger.info("CellNum->" + cellNum + "<-尝试获取Boolean类型数据");
                        stringCellValue = String.valueOf(cell.getBooleanCellValue());
                    } catch (IllegalStateException e2) {
                        try {
                            logger.info("CellNum->" + cellNum + "<-尝试获取Date类型数据");
                            stringCellValue = String.valueOf(cell.getDateCellValue());
                        } catch (IllegalStateException e3) {
                            logger.warn("CellNum->" + cellNum + "<-未知类型数据->" + e3.getMessage());
                        }
                    }
                }
            } finally {
                logger.debug("getCellValue::第" + cellNum + "格获取到的数据为->" + stringCellValue);
            }
        } else {
            logger.debug("getCellValue::第" + cellNum + "格为空");
        }
        return stringCellValue;
    }
}
