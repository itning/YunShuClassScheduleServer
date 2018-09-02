package top.itning.yunshu.classscheduleserver.service;

import top.itning.yunshu.classscheduleserver.exception.NoSuchIdException;

/**
 * 专业服务
 *
 * @author itning
 */
public interface ProfessionService {
    void addProfession(String name);

    void modifyProfession(String id, String name) throws NoSuchIdException;

    void delProfession(String id) throws NoSuchIdException;
}
