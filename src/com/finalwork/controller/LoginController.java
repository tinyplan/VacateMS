package com.finalwork.controller;

import com.finalwork.factory.UserFactory;
import com.finalwork.po.ResMessage;
import com.finalwork.po.User;
import com.finalwork.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @GetMapping("/login")
    @ResponseBody
    public ResMessage<User> login(@RequestParam String userId, @RequestParam String password, @RequestParam String identity){
        ResMessage<User> response = new ResMessage<>();
        UserService service = UserFactory.getServiceByIdentity(identity);
        User user = service.findUserById(userId);
        if(user != null){
            if(password != null && password.equals(user.getPassword())){
                response.setStatus("success");
                response.setData(user);
                return response;
            }
        }
        return response;
    }
}
