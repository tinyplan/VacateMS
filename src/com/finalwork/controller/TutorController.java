package com.finalwork.controller;

import com.finalwork.po.ResMessage;
import com.finalwork.po.VacateVO;
import com.finalwork.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;


    @GetMapping("/verified")
    @ResponseBody
    public List<VacateVO> verifyVacate(@RequestParam String userId,
                                       @RequestParam String vacate_id,
                                       @RequestParam String identity,
                                       @RequestParam Integer upStatus){
        Integer num = tutorService.verifyVacate(userId,vacate_id,upStatus);
        return tutorService.findVacate(userId,0);
    }
}
