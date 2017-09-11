package com.jmm.client.http;

import java.util.HashMap;
import java.util.Map;


public class HttpParams {

    private Map<String, String> getMap() {
        return new HashMap<>();
    }

    private Map<String, String> sign(Map<String, String> params) {
        params.put("", "");
        return params;
    }
}
