package pl.migibud.blog.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonParserUtil {
    private JsonParserUtil(){
    }

    public static <T> String asJsonString(final T t) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
