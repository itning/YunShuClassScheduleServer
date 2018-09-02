package top.itning.yunshu.classscheduleserver.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.itning.yunshu.classscheduleserver.client.service.ExcelService;
import top.itning.yunshu.classscheduleserver.entity.AppUpdate;
import top.itning.yunshu.classscheduleserver.entity.ClassEntity;
import top.itning.yunshu.classscheduleserver.entity.ClassSchedule;
import top.itning.yunshu.classscheduleserver.exception.NoSuchIdException;
import top.itning.yunshu.classscheduleserver.service.AppUpdateService;
import top.itning.yunshu.classscheduleserver.service.ClassEntityService;
import top.itning.yunshu.classscheduleserver.service.ClassScheduleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 框架控制器
 *
 * @author itning
 */
@Controller
public class FrameController {
    private final ExcelService excelService;

    private final ClassScheduleService classScheduleService;

    private final ClassEntityService classEntityService;

    private final AppUpdateService appUpdateService;

    @Autowired
    public FrameController(ClassScheduleService classScheduleService, ClassEntityService classEntityService, ExcelService excelService, AppUpdateService appUpdateService) {
        this.classScheduleService = classScheduleService;
        this.classEntityService = classEntityService;
        this.excelService = excelService;
        this.appUpdateService = appUpdateService;
    }

    @GetMapping("/")
    public String re() {
        return "redirect:/server";
    }


    @GetMapping("/server")
    public String index() {
        return "index";
    }

    @GetMapping("/server/profession")
    public String profession(Model model) {
        model.addAttribute("professionList", classScheduleService.getProfessions());
        return "profession_manager";
    }

    @GetMapping("/server/class")
    public String clazz(Model model, @RequestParam String id) {
        try {
            model.addAttribute("professionId", id);
            model.addAttribute("classList", classEntityService.getAllClass(id));
        } catch (NoSuchIdException e) {
            model.addAttribute("professionId", -1);
            model.addAttribute("classList", new ArrayList<ClassEntity>());
        }
        return "class_manager";
    }

    @GetMapping("/server/schedule")
    public String schedule(Model model, @RequestParam String id) {
        List<ClassSchedule> classScheduleList;
        try {
            model.addAttribute("classid", id);
            classScheduleList = classScheduleService.getClassScheduleByClassId(id);
        } catch (NoSuchIdException e) {
            model.addAttribute("classid", -1);
            classScheduleList = new ArrayList<>();
        }
        model.addAttribute("scheduleList", classScheduleList);
        return "schedule_manager";
    }

    @PostMapping("/server/file")
    public String file(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "redirect:index";
        }
        excelService.upFile(file);
        return "redirect:server/profession";
    }

    @GetMapping("/server/update")
    public String update(Model model) {
        model.addAttribute("appUpdate", appUpdateService.getAppUpdateInfo());
        return "app_update";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/update.json")
    @ResponseBody
    public AppUpdate updateJson() {
        return appUpdateService.getAppUpdateInfo();
    }

    @PostMapping("/server/update")
    public String update(@RequestParam int versionCode, @RequestParam String versionName, @RequestParam String versionDesc, @RequestParam String downloadUrl) {
        appUpdateService.modifyAppUpdateInfo(versionCode, versionName, versionDesc, downloadUrl);
        return "redirect:server/update";
    }
}
