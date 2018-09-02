package top.itning.yunshu.classscheduleserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.yunshu.classscheduleserver.entity.User;

public interface UserDao extends JpaRepository<User, String> {
}
