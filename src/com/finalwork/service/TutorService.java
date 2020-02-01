package com.finalwork.service;

import com.finalwork.po.VacateVO;

import java.util.List;

public interface TutorService {
    Integer verifyVacate(String userId, String vacate_id, Integer upStatus);
    Integer deleteVacateById(String id);
    List<VacateVO> findVacate(String id, Integer status);
}
