package com.finalwork.controller;

import com.finalwork.po.ResMessage;
import com.finalwork.po.VacateVO;
import com.finalwork.service.EduService;
import com.finalwork.utils.excel.ExcelUtils;
import com.finalwork.utils.format.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edu")
public class EduController {

    @Autowired
    private EduService eduService;

    @GetMapping("/verified")
    public List<VacateVO> verifyVacate(@RequestParam String userId,
                                       @RequestParam String vacate_id,
                                       @RequestParam String identity,
                                       @RequestParam Integer upStatus) {
        Integer num = eduService.verifyVacate(userId, vacate_id, upStatus);
        return eduService.findVacate(userId, 0);
    }

    @PostMapping("/file/upload")
    public ResMessage<Integer> uploadFile(@RequestParam MultipartFile file,
                                          @RequestParam String userId,
                                          HttpServletRequest request) {
        ResMessage<Integer> response = new ResMessage<>();
        String path = ExcelUtils.getFullDir(file, userId, request, "/upload");
        System.out.println(path);
        Integer num = eduService.saveFile(file, path);
        if (num > 0) {
            response.setStatus(ResMessage.RES_SUCCESS);
            response.setData(num);
        }
        return response;
    }

    @GetMapping("/analysis")
    public Map<String, Integer> dataAnalyze(@RequestParam String userId) {
        HashMap<String,Integer> map = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            String type = DataFormat.switchType(i);
            Integer count = eduService.searchVacateByType(userId,DataFormat.switchType(i)).size();
            if (i == 2) {
                type = type.substring(2);
            }
            map.put(type, count);
        }
        return map;
    }
}
