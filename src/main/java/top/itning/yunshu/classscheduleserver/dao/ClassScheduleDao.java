package top.itning.yunshu.classscheduleserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.yunshu.classscheduleserver.entity.ClassEntity;
import top.itning.yunshu.classscheduleserver.entity.ClassSchedule;

import java.util.List;

/**
 * 课程表dao
 *
 * @author itning
 */
public interface ClassScheduleDao extends JpaRepository<ClassSchedule, String> {
    /**
     * 根据本机查询所有课程
     *
     * @param byClass 班级
     * @return 课程集合
     */
    List<ClassSchedule> findByByClass(ClassEntity byClass);
}
