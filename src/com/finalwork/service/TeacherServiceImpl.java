package com.finalwork.service;

import com.finalwork.factory.UserFactory;
import com.finalwork.mapper.TeacherMapper;
import com.finalwork.po.PageData;
import com.finalwork.po.User;
import com.finalwork.po.VacateVO;
import com.finalwork.utils.format.DataFormat;
import com.finalwork.utils.format.Pagination;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService,UserService, InitializingBean {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public User findUserById(String id) {
        return teacherMapper.findUserById(id);
    }

    @Override
    public Integer getAuditCount(String userId) {
        return teacherMapper.getAuditCount(userId);
    }

    @Override
    public Integer updatePasswordById(String userId, String newPwd) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("newPwd",newPwd);
        return teacherMapper.updatePwd(map);
    }

    @Override
    public List<VacateVO> findVacate(String id, Integer status) {
        Map<String, Object> map = DataFormat.wrapData(id,status);
        List<VacateVO> vacateVOList = DataFormat.formatList(teacherMapper.findVacateByStatus(map));
        if(status == 0 && !vacateVOList.isEmpty()) {
            //当查询未审核的请假时，标记为已读
            List<String> vacateIds = new ArrayList<>();
            for (VacateVO vacateVO : vacateVOList) {
                vacateIds.add(vacateVO.getVacate().getVacate_id());
            }
            map.put("status", 1);
            map.put("vacateIds", vacateIds);
            teacherMapper.updateTeacherVacate(map);
        }
        return vacateVOList;
    }

    @Override
    public List<VacateVO> findVacateWithPagination(String id, Integer status, Integer selectedPage, Integer pageSize) {
        return null;
    }

    @Override
    public PageData findVacateWithLogicPagination(String id, Integer status, Integer pageSize) {
        List<VacateVO> vacateList = DataFormat.formatList(teacherMapper.findVacateByStatus(DataFormat.wrapData(id,status)));
        if(status == 0 && !vacateList.isEmpty()) {
            //当查询未审核的请假时，标记为已读
            List<String> vacateIds = new ArrayList<>();
            for (VacateVO vacateVO : vacateList) {
                vacateIds.add(vacateVO.getVacate().getVacate_id());
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId",id);
            map.put("status", 1);
            map.put("vacateIds", vacateIds);
            teacherMapper.updateTeacherVacate(map);
        }
        HashMap<Integer,List<VacateVO>> vacateMap = Pagination.getLogicPagination(vacateList,pageSize);
        PageData pageData = new PageData();
        pageData.setDataCount(vacateList.size());
        pageData.setVacateMap(vacateMap);
        return pageData;
    }

    @Override
    public List<VacateVO> searchVacateByTerm(String userId, String term) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("term",term);
        return teacherMapper.searchVacateByTerm(map);
    }

    @Override
    public List<VacateVO> searchVacateByPeriod(String userId, String startTime, String endTime) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        return teacherMapper.searchVacateByPeriod(map);
    }

    @Override
    public List<VacateVO> searchVacateByType(String userId, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("type",type);
        return teacherMapper.searchVacateByType(map);
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
        List<VacateVO> vacateList = DataFormat.formatList(teacherMapper.searchVacate(condition));
        PageData pageData = new PageData();
        pageData.setDataCount(vacateList.size());
        pageData.setVacateMap(Pagination.getLogicPagination(vacateList,pageSize));
        return pageData;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UserFactory.register("3",this);
    }
}
