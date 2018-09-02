package top.itning.yunshu.classscheduleserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.yunshu.classscheduleserver.dao.ClassEntityDao;
import top.itning.yunshu.classscheduleserver.dao.ClassScheduleDao;
import top.itning.yunshu.classscheduleserver.dao.ProfessionDao;
import top.itning.yunshu.classscheduleserver.entity.ClassEntity;
import top.itning.yunshu.classscheduleserver.entity.ClassSchedule;
import top.itning.yunshu.classscheduleserver.entity.Profession;
import top.itning.yunshu.classscheduleserver.exception.NoSuchIdException;
import top.itning.yunshu.classscheduleserver.service.ClassScheduleService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 课程表实现
 *
 * @author itning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class ClassScheduleServiceImpl implements ClassScheduleService {
    private final ClassScheduleDao classScheduleDao;

    private final ClassEntityDao classEntityDao;

    private final ProfessionDao professionDao;

    @Autowired
    public ClassScheduleServiceImpl(ClassScheduleDao classScheduleDao, ClassEntityDao classEntityDao, ProfessionDao professionDao) {
        this.classScheduleDao = classScheduleDao;
        this.classEntityDao = classEntityDao;
        this.professionDao = professionDao;
    }


    @Override
    public List<ClassSchedule> getClassScheduleByClassId(String id) throws NoSuchIdException {
        ClassEntity classEntity = classEntityDao.findById(id).orElseThrow(() -> new NoSuchIdException("ID不存在->" + id));
        return classScheduleDao.findByByClass(classEntity);
    }

    @Override
    public List<Profession> getProfessions() {
        return professionDao.findAll();
    }
}
