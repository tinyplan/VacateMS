package com.finalwork.controller;


import com.finalwork.factory.UserFactory;
import com.finalwork.po.PageData;
import com.finalwork.po.ResMessage;
import com.finalwork.po.User;
import com.finalwork.po.VacateVO;
import com.finalwork.service.UserService;
import com.finalwork.utils.format.DataFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/vacate")
public class VacateController {

    @GetMapping("/find")
    public ResMessage<List<VacateVO>> findVacate(@RequestParam String userId,
                                                 @RequestParam String identity,
                                                 @RequestParam Integer status) {
        ResMessage<List<VacateVO>> response = new ResMessage<>();
        UserService service = UserFactory.getServiceByIdentity(identity);
        List<VacateVO> vacates = service.findVacate(userId, status);
        if (vacates != null) {
            response.setStatus(ResMessage.RES_SUCCESS);
            response.setData(vacates);
        }
        return response;
    }

    @GetMapping("/find/web")
    public ResMessage findVacateWithPagination(@RequestParam String userId,
                                               @RequestParam String identity,
                                               @RequestParam Integer status,
                                               @RequestParam Integer pageSize){
        ResMessage<PageData> response = new ResMessage<>();
        UserService service = UserFactory.getServiceByIdentity(identity);
        PageData pageData = service.findVacateWithLogicPagination(userId,status,pageSize);
        response.setData(pageData);
        return response;
    }

    @GetMapping("/search/term")
    public ResMessage<List<VacateVO>> searchVacateByTerm(@RequestParam String userId,
                                                         @RequestParam String identity,
                                                         @RequestParam String term) {
        ResMessage<List<VacateVO>> response = new ResMessage<>();
        UserService service = UserFactory.getServiceByIdentity(identity);
        List<VacateVO> vacates = DataFormat.formatList(service.searchVacateByTerm(userId, term));
        response.setData(vacates);
        return response;
    }

    @GetMapping("/search/period")
    public ResMessage<List<VacateVO>> searchVacateByPeriod(@RequestParam String userId,
                                                           @RequestParam String identity,
                                                           @RequestParam String startTime,
                                                           @RequestParam String endTime) throws ParseException {
        ResMessage<List<VacateVO>> response = new ResMessage<>();
        UserService service = UserFactory.getServiceByIdentity(identity);
        startTime = String.valueOf(DataFormat.parseToStamp(startTime));
        endTime = String.valueOf(DataFormat.parseToStamp(endTime));
        List<VacateVO> vacates = DataFormat.formatList(service.searchVacateByPeriod(userId, startTime,endTime));
        response.setData(vacates);
        return response;
    }

    @GetMapping("/search/type")
    public ResMessage<List<VacateVO>> searchVacateByType(@RequestParam String userId,
                                                         @RequestParam String identity,
                                                         @RequestParam String type) {
        ResMessage<List<VacateVO>> response = new ResMessage<>();
        type = DataFormat.reEncode(type);
        UserService service = UserFactory.getServiceByIdentity(identity);
        List<VacateVO> vacates =
                DataFormat.formatList(service.searchVacateByType(userId, type));
        response.setData(vacates);
        return response;
    }

    @GetMapping("/search/web")
    public ResMessage searchVacate(@RequestParam String userId,
                                   @RequestParam String identity,
                                   @RequestParam String inputIndex,
                                   @RequestParam String selectedValue,
                                   @RequestParam Integer pageSize) throws ParseException {
        ResMessage<PageData> response = new ResMessage<>();
        UserService service = UserFactory.getServiceByIdentity(identity);
        response.setData(service.searchVacate(userId, inputIndex, selectedValue, pageSize));
        return response;
    }

    @GetMapping("/edit/pwd")
    public ResMessage<User> editUserPassword(@RequestParam String userId,
                                             @RequestParam String identity,
                                             @RequestParam String newPwd){
        ResMessage<User> response = new ResMessage<>();
        UserService service = UserFactory.getServiceByIdentity(identity);
        Integer num = service.updatePasswordById(userId, newPwd);
        if(num >= 0){
            User user = service.findUserById(userId);
            response.setData(user);
            response.setStatus(ResMessage.RES_SUCCESS);
            response.setMessage("修改成功");
        }else{
            response.setMessage("修改失败");
        }
        return response;
    }

    @GetMapping("/count")
    public Integer getCount(@RequestParam String userId,
                            @RequestParam String identity){
        UserService service = UserFactory.getServiceByIdentity(identity);
        return service.getAuditCount(userId);
    }

}
