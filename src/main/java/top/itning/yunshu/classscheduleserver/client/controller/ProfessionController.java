package top.itning.yunshu.classscheduleserver.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.itning.yunshu.classscheduleserver.exception.NoSuchIdException;
import top.itning.yunshu.classscheduleserver.service.ProfessionService;

/**
 * 专业
 *
 * @author itning
 */
@Controller
@RequestMapping("/server")
public class ProfessionController {
    private final ProfessionService professionService;

    @Autowired
    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @PostMapping("/profession/add")
    @ResponseBody
    public int addProfession(@RequestParam String name) {
        professionService.addProfession(name);
        return 200;
    }

    @PostMapping("/profession/del")
    @ResponseBody
    public int delProfession(@RequestParam String id) {
        try {
            professionService.delProfession(id);
        } catch (NoSuchIdException e) {
            return 404;
        }
        return 200;
    }

    @PostMapping("/profession/modify")
    @ResponseBody
    public int modifyProfession(@RequestParam String name, @RequestParam String id) {
        try {
            professionService.modifyProfession(id, name);
        } catch (NoSuchIdException e) {
            return 404;
        }
        return 200;
    }
}
