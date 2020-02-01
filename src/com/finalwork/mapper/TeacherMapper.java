package com.finalwork.mapper;

import com.finalwork.po.User;
import com.finalwork.po.VacateVO;

import java.util.List;
import java.util.Map;

public interface TeacherMapper {

    Integer getAuditCount(String userId);

    Integer updatePwd(Map map);

    User findUserById(String id);

    List<VacateVO> findVacateByStatus(Map map);

    Integer updateTeacherVacate(Map map);

    List<VacateVO> searchVacateByTerm(Map map);

    List<VacateVO> searchVacateByPeriod(Map map);

    List<VacateVO> searchVacateByType(Map map);

    List<VacateVO> searchVacate(Map map);
}
