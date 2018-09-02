package top.itning.yunshu.classscheduleserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.yunshu.classscheduleserver.dao.ProfessionDao;
import top.itning.yunshu.classscheduleserver.entity.Profession;
import top.itning.yunshu.classscheduleserver.exception.NoSuchIdException;
import top.itning.yunshu.classscheduleserver.service.ProfessionService;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class ProfessionServiceImpl implements ProfessionService {
    private final ProfessionDao professionDao;

    @Autowired
    public ProfessionServiceImpl(ProfessionDao professionDao) {
        this.professionDao = professionDao;
    }

    @Override
    public void addProfession(String name) {
        Profession profession = new Profession();
        profession.setName(name);
        professionDao.save(profession);
    }

    @Override
    public void modifyProfession(String id, String name) throws NoSuchIdException {
        Optional<Profession> optionalProfession = professionDao.findById(id);
        if (!optionalProfession.isPresent()) {
            throw new NoSuchIdException("id:" + id + "不存在");
        }
        Profession profession = optionalProfession.get();
        profession.setName(name);
        professionDao.save(profession);
    }

    @Override
    public void delProfession(String id) throws NoSuchIdException {
        if (id == null || "".equals(id)) {
            throw new NoSuchIdException("id not exist");
        }
        professionDao.deleteById(id);
    }
}
