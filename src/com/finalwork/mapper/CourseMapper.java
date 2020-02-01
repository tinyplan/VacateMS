package com.finalwork.mapper;

import java.util.List;

public interface CourseMapper {
    List<String> findTeacherIdById(List<String> courseList);
}
