package com.finalwork.service;

import com.finalwork.factory.UserFactory;
import com.finalwork.mapper.StudentMapper;
import com.finalwork.mapper.TutorMapper;
import com.finalwork.mapper.VacateMapper;
import com.finalwork.po.*;
import com.finalwork.utils.format.DataFormat;
import com.finalwork.utils.format.Pagination;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

@Service
@Transactional
public class StudentServiceImpl implements StudentService, UserService, InitializingBean {

    @Autowired
    private StudentMapper stuMapper;

    @Autowired
    private VacateMapper vacateMapper;

    @Override
    public Integer getAuditCount(String userId) {
        return null;
    }

    @Override
    public String findIdByName(String stuName) {
        return stuMapper.findIdByName(stuName);
    }

    @Override
    public User findUserById(String id) {
        return stuMapper.findStudentById(id);
    }

    @Override
    public Integer updatePasswordById(String userId, String newPwd) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("newPwd",newPwd);
        return stuMapper.updatePwd(map);
    }

    /**
     * 提交请假申请 =>总表
     *              =>学生分表
     *              =>辅导员分表
     * @param vacate 请假对象
     */
    @Override
    public Integer addVacate(Vacate vacate) {
        String id = UUID.randomUUID().toString();
        vacate.setVacate_id(id);
        StuVacate stuVacate = new StuVacate();
        stuVacate.setStu_id(vacate.getStu_id());
        stuVacate.setVacate_id(id);
        TutorVacate tutorVacate = new TutorVacate();
        tutorVacate.setTutor_id(stuMapper.findTutorById(vacate.getStu_id()));
        tutorVacate.setVacate_id(id);
        //由于外键的关系，应首先添加总表中的数据
        vacateMapper.addVacate(vacate);
        vacateMapper.addTutorVacate(tutorVacate);
        return vacateMapper.addStuVacate(stuVacate);
    }

    /**
     * 查询课程列表
     * @param stuId 学生
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 筛选后的课程列表
     * 2019-12-9 : 增加需求=>根据请假的时间选出课程
     * 2019-12-10 : 修改需求=>添加容错措施
     */
    @Override
    public List<Course> findCoursesByStuId(String stuId, String startDate, String endDate) throws ParseException {
        //先查询学生的选课信息
        List<String> courseIds = Arrays.asList(stuMapper.findCoursesByStuId(stuId).split(","));
        Map<String,Object> map = new HashMap<>();
        map.put("start", !startDate.equals("") ?DataFormat.calWeek(startDate):"");
        map.put("end",!endDate.equals("")?DataFormat.calWeek(endDate):"");
        map.put("courses",courseIds);
        List<Course> courseList = stuMapper.findCoursesByWeek(map);
        return courseList;
    }

    /**
     * 查询审核中
     * @param id 学生ID
     * @param status 请假状态
     * @return VacateVO列表
     */
    @Override
    public List<VacateVO> findVacate(String id, Integer status) {
        HashMap<String,Object> map = DataFormat.wrapData(id,status);
        List<VacateVO> results = DataFormat.formatList(vacateMapper.findStuVacateWithStatus(map));
        return results;
    }

    @Override
    public List<VacateVO> findVacateWithPagination(String id, Integer status, Integer selectedPage, Integer pageSize) {
        return null;
    }

    @Override
    public PageData findVacateWithLogicPagination(String id, Integer status, Integer pageSize) {
        List<VacateVO> vacateList = DataFormat.formatList(vacateMapper.findStuVacateWithStatus(DataFormat.wrapData(id,status)));
        HashMap<Integer,List<VacateVO>> vacateMap = Pagination.getLogicPagination(vacateList,pageSize);
        PageData pageData = new PageData();
        pageData.setDataCount(vacateList.size());
        pageData.setVacateMap(vacateMap);
        return pageData;
    }

    @Override
    public List<Course> findCourseByCourseId(String courseIds) {
        List<Course> courses = stuMapper.findCoursesByIds(Arrays.asList(courseIds.split(",")));
        return courses;
    }

    @Override
    public Integer updateTrashStatus(String vacate_id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("vacate_id",vacate_id);
        map.put("status",-2);
        map.put("step",-2);
        vacateMapper.updateStuStatusById(map);
        return vacateMapper.updateStepById(map);
    }

    @Override
    public List<VacateVO> findTrash(String userId) {
        return DataFormat.formatList(vacateMapper.findTrash(userId));
    }

    @Override
    public List<VacateVO> reCommitVacate(String userId, String vacate_id) {
        /**
         * 修改总表vacate_id(step = 1)
         * 修改学生表vacate_id(status = 0)
         * 插入辅导员分表
         */
        HashMap<String,Object> map = new HashMap<>();
        map.put("vacate_id",vacate_id);
        map.put("step",1);
        map.put("status",0);
        vacateMapper.updateStepById(map);
        vacateMapper.updateStuStatusById(map);
        TutorVacate tutorVacate = new TutorVacate();
        tutorVacate.setVacate_id(vacate_id);
        tutorVacate.setTutor_id(stuMapper.findTutorById(userId));
        vacateMapper.addTutorVacate(tutorVacate);
        return findTrash(userId);
    }

    @Override
    public List<VacateVO> searchVacateByTerm(String userId, String term) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("term",term);
        return stuMapper.searchVacateByTerm(map);
    }

    @Override
    public List<VacateVO> searchVacateByPeriod(String userId, String startTime, String endTime) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        return stuMapper.searchVacateByPeriod(map);
    }

    @Override
    public List<VacateVO> searchVacateByType(String userId, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("type",type);
        return stuMapper.searchVacateByType(map);
    }

    @Override
    public PageData searchVacate(String userId, String inputIndex, String selectedValue, Integer pageSize) throws ParseException {
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("userId",userId);
        if("0".equals(inputIndex)){
            //学期查询
            condition.put("term",selectedValue);
        }else if("1".equals(inputIndex)){
            //日期查询
            String[] date = selectedValue.split(",");
            condition.put("start",DataFormat.parseToStamp(date[0]));
            condition.put("end",DataFormat.parseToStamp(date[1]));
        }else{
            //类型查询
            condition.put("type",DataFormat.switchType(Integer.parseInt(selectedValue)));
        }
        List<VacateVO> vacateList = DataFormat.formatList(stuMapper.searchVacate(condition));
        PageData pageData = new PageData();
        pageData.setDataCount(vacateList.size());
        pageData.setVacateMap(Pagination.getLogicPagination(vacateList,pageSize));
        return pageData;
    }

    /**
     * 在UserFactory中注册
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        UserFactory.register("0",this);
    }
}
