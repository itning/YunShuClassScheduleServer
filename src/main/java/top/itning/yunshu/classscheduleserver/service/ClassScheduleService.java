package top.itning.yunshu.classscheduleserver.service;

import top.itning.yunshu.classscheduleserver.entity.ClassSchedule;
import top.itning.yunshu.classscheduleserver.entity.Profession;
import top.itning.yunshu.classscheduleserver.exception.NoSuchIdException;

import java.util.List;

/**
 * 课程表服务
 *
 * @author itning
 */
public interface ClassScheduleService {
    /**
     * 根据班级获取该班级所有课程
     *
     * @param id 班级ID
     * @return 课程集合
     * @throws NoSuchIdException 班级ID不存在
     */
    List<ClassSchedule> getClassScheduleByClassId(String id) throws NoSuchIdException;

    /**
     * 获取所有专业
     *
     * @return 专业集合
     */
    List<Profession> getProfessions();
}
