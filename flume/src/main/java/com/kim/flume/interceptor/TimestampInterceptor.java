package com.kim.flume.interceptor;

import com.alibaba.fastjson2.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class TimestampInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        Map<String, String> headers = event.getHeaders();
        String log = new String(event.getBody(), StandardCharsets.UTF_8);
        try {
            JSONObject jsonObject = JSONObject.parseObject(log);
            String ts = jsonObject.getString("ts");
            headers.put("timestamp", ts);
            return event;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        list.forEach(event -> {
            if (intercept(event) == null) {
                list.remove(event);
            }
        });
        return list;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder {
        @Override
        public Interceptor build() {
            return new TimestampInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
