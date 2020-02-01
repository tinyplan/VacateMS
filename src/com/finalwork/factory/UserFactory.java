package com.finalwork.factory;

import com.finalwork.service.UserService;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class UserFactory {
    private static Map<String, UserService> services = new HashMap<>();

    public static UserService getServiceByIdentity(String identity){
        return services.get(identity);
    }

    public static void register(String identity,UserService service){
        Assert.notNull(identity,"identity can not be null");
        services.put(identity,service);
    }
}
