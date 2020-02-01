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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TutorServiceImpl implements TutorService, UserService, InitializingBean {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TutorMapper tutorMapper;

    @Autowired
    private VacateMapper vacateMapper;

    @Override
    public List<VacateVO> findVacate(String id, Integer status) {
        HashMap<String,Object> map = DataFormat.wrapData(id,status);
        return DataFormat.formatList(vacateMapper.findTutorVacateWithStatus(map));
    }

    @Override
    public List<VacateVO> findVacateWithPagination(String id, Integer status, Integer selectedPage, Integer pageSize) {
        return null;
    }

    @Override
    public PageData findVacateWithLogicPagination(String id, Integer status, Integer pageSize) {
        List<VacateVO> vacateList = DataFormat.formatList(vacateMapper.findTutorVacateWithStatus(DataFormat.wrapData(id,status)));
        HashMap<Integer,List<VacateVO>> vacateMap = Pagination.getLogicPagination(vacateList,pageSize);
        PageData pageData = new PageData();
        pageData.setDataCount(vacateList.size());
        pageData.setVacateMap(vacateMap);
        return pageData;
    }

    @Override
    public Integer getAuditCount(String userId) {
        return tutorMapper.getAuditCount(userId);
    }

    @Override
    public User findUserById(String id) {
        return tutorMapper.findTutorById(id);
    }

    @Override
    public Integer updatePasswordById(String userId, String newPwd) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("newPwd",newPwd);
        return tutorMapper.updatePwd(map);
    }

    /**
     * 审批状态改变
     * @param userId 辅导员ID
     * @param vacate_id 请假UUID
     * @param upStatus 要更新的状态
     *
     * 操作流程: upStatus == 1 ==>更新总表(step=2)
     *                         ==>更新辅导员分表(status=1)
     *                         ==>向教务分表插入数据
     *                    ==-1 ==>更新学生分表(status=-1)
     *                         ==>更新辅导员分表(status=-1)
     */
    @Override
    public Integer verifyVacate(String userId, String vacate_id, Integer upStatus) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("vacate_id",vacate_id);
        map.put("status",upStatus);
        if(upStatus == 1){
            map.put("step",2);
            vacateMapper.updateStepById(map);
            vacateMapper.updateTutorStatusById(map);
            //向教务分表插入数据
            String edu_id = tutorMapper.findTutorById(userId).getEdu_id();
            EduVacate eduVacate = new EduVacate(edu_id,vacate_id);
            return vacateMapper.addEduVacate(eduVacate);
        }else{
            vacateMapper.updateStuStatusById(map);
            return vacateMapper.updateTutorStatusById(map);
        }
    }

    @Override
    public Integer deleteVacateById(String id) {
        return vacateMapper.deleteTutorVacateById(id);
    }

    @Override
    public List<VacateVO> searchVacateByTerm(String userId, String term) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("term",term);
        return tutorMapper.searchVacateByTerm(map);
    }

    @Override
    public List<VacateVO> searchVacateByPeriod(String userId, String startTime, String endTime) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        return tutorMapper.searchVacateByPeriod(map);
    }

    @Override
    public List<VacateVO> searchVacateByType(String userId, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("type",type);
        return tutorMapper.searchVacateByType(map);
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
        List<VacateVO> vacateList = DataFormat.formatList(tutorMapper.searchVacate(condition));
        PageData pageData = new PageData();
        pageData.setDataCount(vacateList.size());
        pageData.setVacateMap(Pagination.getLogicPagination(vacateList,pageSize));
        return pageData;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UserFactory.register("1",this);
    }
}
