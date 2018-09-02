package top.itning.yunshu.classscheduleserver.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.itning.yunshu.classscheduleserver.exception.NoSuchIdException;
import top.itning.yunshu.classscheduleserver.service.ClassEntityService;

/**
 * @author itning
 */
@Controller
@RequestMapping("/server")
public class ClassController {
    private final ClassEntityService classEntityService;

    @Autowired
    public ClassController(ClassEntityService classEntityService) {
        this.classEntityService = classEntityService;
    }

    @PostMapping("/class/add")
    @ResponseBody
    public int addClass(@RequestParam String name, @RequestParam String professionId) {
        try {
            classEntityService.addClass(name, professionId);
        } catch (NoSuchIdException e) {
            return 404;
        }
        return 200;
    }

    @PostMapping("/class/del")
    @ResponseBody
    public int delClass(@RequestParam String id) {
        try {
            classEntityService.delClass(id);
        } catch (NoSuchIdException e) {
            return 404;
        }
        return 200;
    }

    @PostMapping("/class/modify")
    @ResponseBody
    public int modifyClass(@RequestParam String id, @RequestParam String name, @RequestParam String version) {
        try {
            classEntityService.modifyClass(id, name, version);
        } catch (NoSuchIdException e) {
            return 404;
        }
        return 200;
    }
}
