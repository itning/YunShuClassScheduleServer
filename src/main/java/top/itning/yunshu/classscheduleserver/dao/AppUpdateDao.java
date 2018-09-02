package top.itning.yunshu.classscheduleserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.yunshu.classscheduleserver.entity.AppUpdate;

public interface AppUpdateDao extends JpaRepository<AppUpdate, Integer> {
}
