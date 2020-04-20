package com.akijoey.library.util;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil {

    public static Map<String, Object> createResult(Integer status, String message, Object data) {
        return new HashMap<>(){{
            put("status", status);
            put("message", message);
            put("data", data);
        }};
    }
}
