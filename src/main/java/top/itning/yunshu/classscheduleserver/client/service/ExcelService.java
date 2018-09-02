package top.itning.yunshu.classscheduleserver.client.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelService {
    void upFile(MultipartFile file) throws IOException;
}
