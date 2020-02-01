package com.finalwork.mapper;

import com.finalwork.po.Grade;
import com.finalwork.po.Tutor;
import com.finalwork.po.VacateVO;

import java.util.List;
import java.util.Map;

public interface TutorMapper {

    Integer getAuditCount(String userId);

    Integer updatePwd(Map map);

    Tutor findTutorById(String id);

    List<Grade> findGradeById(String id);

    Integer verifyVacate();

    List<VacateVO> searchVacateByTerm(Map map);

    List<VacateVO> searchVacateByPeriod(Map map);

    List<VacateVO> searchVacateByType(Map map);

    List<VacateVO> searchVacate(Map map);
}
