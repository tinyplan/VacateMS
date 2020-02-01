package com.finalwork.mapper;

import com.finalwork.po.Edu;
import com.finalwork.po.VacateVO;

import java.util.List;
import java.util.Map;

public interface EduMapper {

    Integer getAuditCount(String userId);

    Integer updatePwd(Map map);

    Edu findEduById(String id);

    List<VacateVO> searchVacateByTerm(Map map);

    List<VacateVO> searchVacateByPeriod(Map map);

    List<VacateVO> searchVacateByType(Map map);

    List<VacateVO> searchVacate(Map map);

    Integer importTutor(List list);
}
