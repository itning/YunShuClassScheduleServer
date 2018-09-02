package top.itning.yunshu.classscheduleserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.yunshu.classscheduleserver.entity.Profession;

/**
 * 专业DAO
 *
 * @author itning
 */
public interface ProfessionDao extends JpaRepository<Profession, String> {
    boolean existsByName(String name);

    Profession getByName(String name);
}
