package pl.migibud.blog.util;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    private ResponseUtil(){
        throw new UnsupportedOperationException();
    }

    public static  <T> Map<String, String> entityDeleteResponse(Class<T> clazz, Long id, HttpStatus status) {
        Map<String,String> message = new HashMap<>();
        message.put(String.format("Deleted %s with id:",clazz.getName()), id.toString());
        message.put("Timestamp:", LocalDateTime.now().toString());
        message.put("Status:", status.toString());
        return message;
    }
}
