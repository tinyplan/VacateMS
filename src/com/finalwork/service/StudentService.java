package com.finalwork.service;

import com.finalwork.po.*;

import java.text.ParseException;
import java.util.List;

public interface StudentService {
    String findIdByName(String stuName);
    Integer addVacate(Vacate vacate);
    List<Course> findCoursesByStuId(String stuId,String startDate,String endDate) throws ParseException;
    List<Course> findCourseByCourseId(String courseIds);
    Integer updateTrashStatus(String vacate_id);
    List<VacateVO> findTrash(String userId);
    List<VacateVO> reCommitVacate(String userId,String vacate_id);
}
