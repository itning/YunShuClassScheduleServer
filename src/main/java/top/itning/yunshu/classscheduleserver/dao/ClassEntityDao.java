package top.itning.yunshu.classscheduleserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.yunshu.classscheduleserver.entity.ClassEntity;
import top.itning.yunshu.classscheduleserver.entity.Profession;

import java.util.List;

public interface ClassEntityDao extends JpaRepository<ClassEntity, String> {
    /**
     * 根据专业获取所有班级
     *
     * @param profession 专业
     * @return 班级集合
     */
    List<ClassEntity> findByProfession(Profession profession);

    boolean existsByName(String name);

    ClassEntity getByName(String name);
}
