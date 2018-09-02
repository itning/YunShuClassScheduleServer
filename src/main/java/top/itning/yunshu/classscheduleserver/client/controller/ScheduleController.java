package top.itning.yunshu.classscheduleserver.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.itning.yunshu.classscheduleserver.exception.NoSuchIdException;
import top.itning.yunshu.classscheduleserver.service.ClassEntityService;

@Controller
@RequestMapping("/server")
public class ScheduleController {
    private final ClassEntityService classEntityService;

    @Autowired
    public ScheduleController(ClassEntityService classEntityService) {
        this.classEntityService = classEntityService;
    }

    @PostMapping("/schedule/add")
    @ResponseBody
    public int add(@RequestParam String name, @RequestParam String section, @RequestParam String week, @RequestParam String classId) {
        String[] splitArray = name.split("@");
        if (splitArray.length != 3) {
            return 400;
        }
        try {
            classEntityService.addSchedule(splitArray[0], week, section, splitArray[1], splitArray[2], classId);
        } catch (NoSuchIdException e) {
            return 404;
        }
        return 200;
    }

    @PostMapping("/schedule/delAndModify")
    @ResponseBody
    public int delAndModify(@RequestParam String name, @RequestParam String id) {
        if ("".equals(name) || " ".equals(name)) {
            try {
                classEntityService.delSchedule(id);
            } catch (NoSuchIdException e) {
                return 404;
            }
            return 200;
        } else {
            String[] splitArray = name.split("@");
            if (splitArray.length != 3) {
                return 400;
            }
            try {
                classEntityService.modifySchedule(id, splitArray[0], splitArray[1], splitArray[2]);
            } catch (NoSuchIdException e) {
                return 404;
            }
            return 200;
        }
    }

}
