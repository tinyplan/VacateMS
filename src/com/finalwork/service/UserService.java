package com.finalwork.service;

import com.finalwork.po.PageData;
import com.finalwork.po.User;
import com.finalwork.po.VacateVO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface UserService {
    Integer getAuditCount(String userId);
    User findUserById(String id);
    Integer updatePasswordById(String userId,String newPwd);
    List<VacateVO> findVacate(String id, Integer status);
    //物理分页(待实现)
    List<VacateVO> findVacateWithPagination(String id, Integer status, Integer selectedPage, Integer pageSize);
    //逻辑分页
    PageData findVacateWithLogicPagination(String id, Integer status, Integer pageSize);
    List<VacateVO> searchVacateByTerm(String userId,String term);
    List<VacateVO> searchVacateByPeriod(String userId,String startTime,String endTime);
    List<VacateVO> searchVacateByType(String userId,String type);

    PageData searchVacate(String userId, String inputIndex, String selectedValue, Integer pageSize) throws ParseException;
}
