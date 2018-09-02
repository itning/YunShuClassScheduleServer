package top.itning.yunshu.classscheduleserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.itning.yunshu.classscheduleserver.entity.ClassEntity;
import top.itning.yunshu.classscheduleserver.entity.ClassSchedule;
import top.itning.yunshu.classscheduleserver.entity.Profession;
import top.itning.yunshu.classscheduleserver.exception.NoSuchIdException;
import top.itning.yunshu.classscheduleserver.service.ClassEntityService;
import top.itning.yunshu.classscheduleserver.service.ClassScheduleService;

import java.util.ArrayList;
import java.util.List;


/**
 * @author itning
 */
@RestController
@RequestMapping("/classSchedule")
public class ClassScheduleController {
    private final ClassEntityService classEntityService;

    private final ClassScheduleService classScheduleService;

    @Autowired
    public ClassScheduleController(ClassEntityService classEntityService, ClassScheduleService classScheduleService) {
        this.classEntityService = classEntityService;
        this.classScheduleService = classScheduleService;
    }

    /**
     * 获取专业信息
     *
     * @return 专业集合
     */
    @GetMapping("/profession")
    public List<Profession> getProfession() {
        return classScheduleService.getProfessions();
    }

    /**
     * 获取班级信息
     *
     * @param professionId 专业ID
     * @return 班级信息集合
     */
    @GetMapping("/class")
    public List<ClassEntity> getClass(@RequestParam String professionId) {
        List<ClassEntity> classEntityList;
        try {
            classEntityList = classEntityService.getAllClass(professionId);
        } catch (NoSuchIdException e) {
            classEntityList = new ArrayList<>();
        }
        return classEntityList;
    }

    /***
     * 检查课程表版本
     * @param classId 班级ID
     * @return 版本号
     */
    @GetMapping("/checkVersion")
    public String checkVersion(@RequestParam String classId) {
        System.out.println("classSchedule-checkVersion");
        return classEntityService.getClassVersion(classId);
    }

    /**
     * 下载课程表
     *
     * @param classId 班级ID
     * @return 课程表集合
     */
    @GetMapping("/download")
    public List<ClassSchedule> download(@RequestParam String classId) {
        System.out.println("download-->" + classId);
        List<ClassSchedule> classScheduleList;
        try {
            classScheduleList = classScheduleService.getClassScheduleByClassId(classId);
        } catch (NoSuchIdException e) {
            classScheduleList = new ArrayList<>();
        }
        return classScheduleList;
    }
}
