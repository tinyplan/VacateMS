package com.finalwork.controller;

import com.finalwork.factory.UserFactory;
import com.finalwork.po.*;
import com.finalwork.service.StudentService;
import com.finalwork.service.TutorService;
import com.finalwork.service.UserService;
import com.finalwork.utils.format.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService stuService;

    @Autowired
    private TutorService tutorService;

    /**
     * 提交请假申请
     * @param stuName 学生姓名
     * @param submitTime 提交时间
     * @param startDate 请假开始时间
     * @param endDate 请假结束时间
     * @param typeIndex 请假类型
     * @param result 请假原因(请假类型为其他,此项必须填写)
     * @return 暂定为状态信息
     */
    @RequestMapping(value = "/addVacate",method = RequestMethod.GET)
    public ResMessage addVacate(@RequestParam String stuName,
                                @RequestParam String submitTime,
                                @RequestParam String startDate,
                                @RequestParam String endDate,
                                @RequestParam Integer typeIndex,
                                @RequestParam String result,
                                @RequestParam String courses) throws ParseException {
        /**
         * Controller中考虑
         * 判断数据的合法性
         *  若不合法，返回表示错误的状态码
         *  合法，则将转换后的数据传入service
         *  uuid由service生成
         */
        ResMessage<Integer> response = new ResMessage<>();

        //判断学生是否存在
        String id = stuService.findIdByName(DataFormat.reEncode(stuName));
        if(id == null){
            response.setMessage("查无此人,请确认姓名填写正确!");
            return response;
        }

        //判断时间是否合法
        int interval = DataFormat.calInterval(startDate,endDate);
        if(interval <= 0){
            response.setMessage("请假时间冲突,请重新选择!");
            return response;
        }

        //判断为类型3时,是否填写原因
        if(typeIndex.equals(3) && result.equals("")){
            response.setMessage("原因未填写,请填写!");
            return response;
        }

        //数据审核通过,开始封装数据
        Vacate vacate = new Vacate();
        vacate.setStu_id(id);
        vacate.setTerm(DataFormat.calTerm(submitTime));
        vacate.setSubmit_time(String.valueOf(DataFormat.parseToStamp("yyyy-MM-dd HH:mm:ss",submitTime.replaceAll("/","-"))));
        vacate.setStart_time(String.valueOf(DataFormat.parseToStamp(startDate)));
        vacate.setEnd_time(String.valueOf(DataFormat.parseToStamp(endDate)));
        vacate.setPeriod(DataFormat.calInterval(startDate,endDate));
        vacate.setType(DataFormat.switchType(typeIndex));
        vacate.setResult(DataFormat.reEncode(result));
        vacate.setCourse_list(courses);

        int num = stuService.addVacate(vacate);

        if(num > 0){
            response.setStatus(ResMessage.RES_SUCCESS);
            response.setMessage("提交成功");
            response.setData(num);
        }else{
            response.setMessage("提交失败,未知原因");
        }

        return response;
    }

    /**
     * 查找学生的选课列表
     * @param stuId 学号
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    @RequestMapping(value = "/findCourse",method = RequestMethod.GET)
    public List<Course> findCourse(@RequestParam String stuId,
                                   @RequestParam String startDate,
                                   @RequestParam String endDate) throws ParseException {
        return stuService.findCoursesByStuId(stuId,startDate,endDate);
    }

    @GetMapping("/findCourseName")
    public ResMessage<List<Course>> findCourseName(@RequestParam String course){
        ResMessage<List<Course>> response = new ResMessage<>();
        List<Course> courses = stuService.findCourseByCourseId(course);
        if(courses != null){
            response.setStatus(ResMessage.RES_SUCCESS);
            response.setData(courses);
        }else{
            response.setMessage("未查询到课程");
        }
        return response;
    }

    @GetMapping("/trash/add")
    public List<VacateVO> addTrash(@RequestParam String userId,
                                    @RequestParam String vacate_id){
        /*
         * 删除辅导员表中的数据（vacate_id）
         * 学生表（status = -2）
         * 总表（step = -2）
         */
        tutorService.deleteVacateById(vacate_id);
        stuService.updateTrashStatus(vacate_id);
        //返回新的数据
        UserService service = UserFactory.getServiceByIdentity("0");
        return service.findVacate(userId,0);
    }

    @GetMapping("/trash/add/web")
    public PageData addTrashWeb(@RequestParam String userId,
                                @RequestParam String vacate_id,
                                @RequestParam Integer pageSize){
        /*
         * 删除辅导员表中的数据（vacate_id）
         * 学生表（status = -2）
         * 总表（step = -2）
         */
        tutorService.deleteVacateById(vacate_id);
        stuService.updateTrashStatus(vacate_id);
        //返回新的数据
        UserService service = UserFactory.getServiceByIdentity("0");
        return service.findVacateWithLogicPagination(userId,0,pageSize);
    }

    @GetMapping("/trash/find")
    public List<VacateVO> findTrash (@RequestParam String userId){
        /*
         * 查学生表（status = -2）
         */
        return stuService.findTrash(userId);
    }

    @GetMapping("/trash/reSubmit")
    public List<VacateVO> reCommitVacate(@RequestParam String userId,
                                  @RequestParam String vacate_id){
        return stuService.reCommitVacate(userId,vacate_id);
    }
}
