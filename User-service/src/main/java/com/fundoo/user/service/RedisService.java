package com.fundoo.user.service;

import com.fundoo.user.model.User;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisService {

    private static Jedis jedis = new Jedis();

    private static String userdata="user";

    public static void setToken(String email, User user) {
        Gson gson = new Gson();
        String toJson = gson.toJson(user);
        jedis.hset(email,userdata,toJson);
    }

    public static User getToken(String user_id) {
        String json = jedis.hget(user_id, userdata);
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        return user;
    }

}

