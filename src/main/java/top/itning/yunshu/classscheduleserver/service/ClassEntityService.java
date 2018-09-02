package top.itning.yunshu.classscheduleserver.service;

import top.itning.yunshu.classscheduleserver.entity.ClassEntity;
import top.itning.yunshu.classscheduleserver.exception.NoSuchIdException;

import java.util.List;

public interface ClassEntityService {
    /**
     * 获取所有班级
     *
     * @param professionId 专业ID
     * @return 班级集合
     * @throws NoSuchIdException 专业ID不存在
     */
    List<ClassEntity> getAllClass(String professionId) throws NoSuchIdException;

    /**
     * 获取课程版本
     *
     * @param id 班级ID
     * @return 版本号
     */
    String getClassVersion(String id);

    void addClass(String name, String professionId) throws NoSuchIdException;

    void delClass(String id) throws NoSuchIdException;

    void modifyClass(String id, String name, String version) throws NoSuchIdException;

    void addSchedule(String name, String week, String section, String location, String teacher, String classId) throws NullPointerException, NoSuchIdException;

    void delSchedule(String id) throws NoSuchIdException;

    void modifySchedule(String id, String name, String location, String teacher) throws NoSuchIdException;
}
