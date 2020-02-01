package com.finalwork.mapper;

import com.finalwork.po.*;

import java.util.List;
import java.util.Map;

public interface StudentMapper {

    Integer updatePwd(Map map);

    String findIdByName(String stuName);

    Student findStudentById(String id);

    String findCoursesByStuId(String id);

    List<Course> findCoursesByIds(List<String> courseIds);

    //更改的功能
    //根据请假的时间选出时间
    List<Course> findCoursesByWeek(Map map);

    List<Student> findStudentByCourse(List<Grade> gradeList);

    String findTutorById(String id);

    List<VacateVO> searchVacateByTerm(Map map);

    List<VacateVO> searchVacateByPeriod(Map map);

    List<VacateVO> searchVacateByType(Map map);

    List<VacateVO> searchVacate(Map map);
}
