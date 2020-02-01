package com.finalwork.mapper;

import com.finalwork.po.*;

import java.util.List;
import java.util.Map;

public interface VacateMapper {
    Integer addVacate(Vacate vacate);

    Integer addStuVacate(StuVacate stuVacate);

    Integer addTutorVacate(TutorVacate tutorVacate);

    Integer addEduVacate(EduVacate eduVacate);

    Vacate findOneVacateById(String id);

    @Deprecated
    List<StuVacate> findStuAudit(String stuId);

    @Deprecated
    List<Vacate> findVacateByUUID(List<String> ids);

    @Deprecated
    List<VacateVO> findVacateInfo(List<VacateVO> vacates);

    /**
     * @param map 用户id
     *            请假状态
     */
    List<VacateVO> findStuVacateWithStatus(Map map);

    /**
     * 应查询辅导员分表中的status，
     * 读取的数据应是学生分表中的status
     */
    List<VacateVO> findTutorVacateWithStatus(Map map);

    List<VacateVO> findEduVacateWithStatus(Map map);

    Integer updateStepById(Map map);

    Integer updateTutorStatusById(Map map);

    Integer updateStuStatusById(Map map);

    Integer updateEduStatusById(Map map);

    Integer deleteTutorVacateById(String id);

    List<VacateVO> findTrash(String id);

    Integer addTeacherVacate(List<TeacherVacate> list);
}
