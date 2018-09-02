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
import top.itning.yunshu.classscheduleserver.service.ClassEntityService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 课程信息服务实现
 *
 * @author itning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class ClassEntityServiceImpl implements ClassEntityService {
    private final ClassEntityDao classEntityDao;

    private final ProfessionDao professionDao;

    private final ClassScheduleDao classScheduleDao;

    @Autowired
    public ClassEntityServiceImpl(ClassEntityDao classEntityDao, ProfessionDao professionDao, ClassScheduleDao classScheduleDao) {
        this.classEntityDao = classEntityDao;
        this.professionDao = professionDao;
        this.classScheduleDao = classScheduleDao;
    }

    @Override
    public List<ClassEntity> getAllClass(String professionId) throws NoSuchIdException {
        Profession profession = professionDao.findById(professionId).orElseThrow(() -> new NoSuchIdException("不存在的ID->" + professionId));
        return classEntityDao.findByProfession(profession);
    }

    @Override
    public String getClassVersion(String id) {
        Optional<ClassEntity> classEntityOptional = classEntityDao.findById(id);
        if (!classEntityOptional.isPresent()) {
            return "";
        }
        return classEntityOptional.get().getVersion();
    }

    @Override
    public void addClass(String name, String professionId) throws NoSuchIdException {
        Optional<Profession> professionOptional = professionDao.findById(professionId);
        if (!professionOptional.isPresent()) {
            throw new NoSuchIdException("ID" + professionId + "不存在");
        }
        ClassEntity classEntity = new ClassEntity();
        classEntity.setName(name);
        classEntity.setVersion("1");
        classEntity.setProfession(professionOptional.get());
        classEntityDao.save(classEntity);
    }

    @Override
    public void delClass(String id) throws NoSuchIdException {
        Optional<ClassEntity> classEntityOptional = classEntityDao.findById(id);
        if (!classEntityOptional.isPresent()) {
            throw new NoSuchIdException("ID:" + id + "不存在");
        }
        classEntityDao.delete(classEntityOptional.get());
    }

    @Override
    public void modifyClass(String id, String name, String version) throws NoSuchIdException {
        Optional<ClassEntity> classEntityOptional = classEntityDao.findById(id);
        if (!classEntityOptional.isPresent()) {
            throw new NoSuchIdException("ID:" + id + "不存在");
        }
        ClassEntity classEntity = classEntityOptional.get();
        classEntity.setName(name);
        classEntity.setVersion(version);
        classEntityDao.save(classEntity);
    }

    @Override
    public void addSchedule(String name, String week, String section, String location, String teacher, String classId) throws NullPointerException, NoSuchIdException {
        Optional<ClassEntity> classEntityOptional = classEntityDao.findById(classId);
        if (!classEntityOptional.isPresent()) {
            throw new NoSuchIdException("id:" + classId + "不存在");
        }
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setWeek(Integer.parseInt(week));
        classSchedule.setSection(Integer.parseInt(section));
        classSchedule.setName(name);
        classSchedule.setLocation(location);
        classSchedule.setTeacher(teacher);
        classSchedule.setByClass(classEntityOptional.get());
        classScheduleDao.saveAndFlush(classSchedule);
    }

    @Override
    public void delSchedule(String id) throws NoSuchIdException {
        Optional<ClassSchedule> classScheduleOptional = classScheduleDao.findById(id);
        if (!classScheduleOptional.isPresent()) {
            throw new NoSuchIdException("id:" + id + "不存在");
        }
        classScheduleDao.delete(classScheduleOptional.get());
    }

    @Override
    public void modifySchedule(String id, String name, String location, String teacher) throws NoSuchIdException {
        Optional<ClassSchedule> classScheduleOptional = classScheduleDao.findById(id);
        if (!classScheduleOptional.isPresent()) {
            throw new NoSuchIdException("id:" + id + "不存在");
        }
        ClassSchedule classSchedule = classScheduleOptional.get();
        classSchedule.setName(name);
        classSchedule.setLocation(location);
        classSchedule.setTeacher(teacher);
        classScheduleDao.saveAndFlush(classSchedule);
    }
}
