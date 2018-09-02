package top.itning.yunshu.classscheduleserver.service;

import top.itning.yunshu.classscheduleserver.entity.AppUpdate;

public interface AppUpdateService {
    AppUpdate getAppUpdateInfo();

    void modifyAppUpdateInfo(int versionCode, String versionName, String versionDesc, String downloadUrl);
}
