package com.finalwork.service;

import com.finalwork.factory.UserFactory;
import com.finalwork.mapper.CourseMapper;
import com.finalwork.mapper.EduMapper;
import com.finalwork.mapper.VacateMapper;
import com.finalwork.po.*;
import com.finalwork.utils.excel.ExcelUtils;
import com.finalwork.utils.format.DataFormat;
import com.finalwork.utils.format.Pagination;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service
@Transactional
public class EduServiceImpl implements EduService, UserService, InitializingBean {

    @Autowired
    private EduMapper eduMapper;

    @Autowired
    private VacateMapper vacateMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public User findUserById(String id) {
        return eduMapper.findEduById(id);
    }

    @Override
    public Integer getAuditCount(String userId) {
        return eduMapper.getAuditCount(userId);
    }

    @Override
    public Integer updatePasswordById(String userId, String newPwd) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("newPwd",newPwd);
        return eduMapper.updatePwd(map);
    }

    @Override
    public List<VacateVO> findVacate(String id, Integer status) {
        HashMap<String,Object> map = DataFormat.wrapData(id,status);
        return DataFormat.formatList(vacateMapper.findEduVacateWithStatus(map));
    }

    @Override
    public List<VacateVO> findVacateWithPagination(String id, Integer status, Integer selectedPage, Integer pageSize) {
        return null;
    }

    @Override
    public PageData findVacateWithLogicPagination(String id, Integer status, Integer pageSize) {
        List<VacateVO> vacateList = DataFormat.formatList(vacateMapper.findEduVacateWithStatus(DataFormat.wrapData(id,status)));
        HashMap<Integer,List<VacateVO>> vacateMap = Pagination.getLogicPagination(vacateList,pageSize);
        PageData pageData = new PageData();
        pageData.setDataCount(vacateList.size());
        pageData.setVacateMap(vacateMap);
        return pageData;
    }

    /**
     * 教务通过流程
     * upStatus ==>1 ==>总表（step = 3）
     *               ==>学生表（status = 1）
     *               ==>教务表（status = 1）
     *               ==>查询课程信息，向老师表添加数据
     *          ==>-1 ==>学生表（status = -1）
     *                ==>教务表（status = -1）
     */
    @Override
    public Integer verifyVacate(String userId, String vacate_id, Integer upStatus) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("vacate_id",vacate_id);
        map.put("status",upStatus);
        if(upStatus == 1){
            map.put("step",3);
            vacateMapper.updateStepById(map);
            vacateMapper.updateStuStatusById(map);
            vacateMapper.updateEduStatusById(map);
            //查询课程信息
            Vacate vacate = vacateMapper.findOneVacateById(vacate_id);
            List<String> courseList = Arrays.asList(vacate.getCourse_list().split(","));
            List<String> teacherIdList = courseMapper.findTeacherIdById(courseList);
            //向老师表添加数据
            List<TeacherVacate> teacherVacateList = new ArrayList<>();
            for(String id:teacherIdList){
                teacherVacateList.add(new TeacherVacate(id,vacate_id));
            }
            return vacateMapper.addTeacherVacate(teacherVacateList);
        }else{
            vacateMapper.updateStuStatusById(map);
            return vacateMapper.updateEduStatusById(map);
        }
    }

    @Override
    public List<VacateVO> searchVacateByTerm(String userId, String term) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("term",term);
        return eduMapper.searchVacateByTerm(map);
    }

    @Override
    public List<VacateVO> searchVacateByPeriod(String userId, String startTime, String endTime) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        return eduMapper.searchVacateByPeriod(map);
    }

    @Override
    public List<VacateVO> searchVacateByType(String userId, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("type",type);
        return eduMapper.searchVacateByType(map);
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
        List<VacateVO> vacateList = DataFormat.formatList(eduMapper.searchVacate(condition));
        PageData pageData = new PageData();
        pageData.setDataCount(vacateList.size());
        pageData.setVacateMap(Pagination.getLogicPagination(vacateList,pageSize));
        return pageData;
    }

    @Override
    public Integer saveFile(MultipartFile file, String path) {
        List<Tutor> tutorList = null;
        try {
            file.transferTo(new File(path));
            tutorList = ExcelUtils.readExcel(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eduMapper.importTutor(tutorList);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UserFactory.register("2",this);
    }
}
