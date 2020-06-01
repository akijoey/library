package com.akijoey.library.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResultUtil {

    public Map<String, Object> createResult(Integer status, String message) {
        return new HashMap<>(){{
            put("status", status);
            put("message", message);
        }};
    }

    public Map<String, Object> createResult(Integer status, String message, Object data) {
        return new HashMap<>(){{
            put("status", status);
            put("message", message);
            put("data", data);
        }};
    }
}
