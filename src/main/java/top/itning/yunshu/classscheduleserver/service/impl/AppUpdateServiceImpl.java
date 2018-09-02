package top.itning.yunshu.classscheduleserver.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.yunshu.classscheduleserver.dao.AppUpdateDao;
import top.itning.yunshu.classscheduleserver.entity.AppUpdate;
import top.itning.yunshu.classscheduleserver.service.AppUpdateService;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class AppUpdateServiceImpl implements AppUpdateService {
    private final AppUpdateDao appUpdateDao;

    @Autowired
    public AppUpdateServiceImpl(AppUpdateDao appUpdateDao) {
        this.appUpdateDao = appUpdateDao;
    }

    @Override
    public AppUpdate getAppUpdateInfo() {
        if (appUpdateDao.findAll().isEmpty()) {
            AppUpdate appUpdate = new AppUpdate();
            appUpdate.setVersionCode(0);
            appUpdate.setVersionName("");
            appUpdate.setVersionDesc("");
            appUpdate.setDownloadUrl("");
            appUpdateDao.saveAndFlush(appUpdate);
        }
        return appUpdateDao.findAll().get(0);
    }

    @Override
    public void modifyAppUpdateInfo(int versionCode, String versionName, String versionDesc, String downloadUrl) {
        AppUpdate appUpdate = new AppUpdate();
        appUpdate.setVersionCode(versionCode);
        appUpdate.setVersionName(versionName);
        appUpdate.setVersionDesc(versionDesc);
        appUpdate.setDownloadUrl(downloadUrl);
        appUpdateDao.deleteAll();
        appUpdateDao.save(appUpdate);
    }
}
