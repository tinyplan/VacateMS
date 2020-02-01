package com.finalwork.service;

import com.finalwork.po.VacateVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EduService {
    Integer verifyVacate(String userId, String vacate_id, Integer upStatus);
    List<VacateVO> findVacate(String id, Integer status);
    Integer saveFile(MultipartFile file, String path);
    List<VacateVO> searchVacateByType(String userId,String type);
}
