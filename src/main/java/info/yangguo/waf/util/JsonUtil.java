package info.yangguo.waf.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;

public class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static JsonFactory jsonFactory = new JsonFactory();

    /**
     * 将json发序列化为对象
     *
     * @param jsonAsString
     * @param pojoClass
     */
    public static <T> Object fromJson(String jsonAsString, Class<T> pojoClass) {
        Object result = null;
        try {
            result = objectMapper.readValue(jsonAsString, pojoClass);
        } catch (Exception e) {
            logger.error("JSON[{}]反序列化失败\n{}", jsonAsString, ExceptionUtils.getFullStackTrace(e));
        }
        return result;
    }

    /**
     * 将json发序列化为对象
     *
     * @param jsonAsString
     * @param reference
     */
    public static <T> T fromJson(String jsonAsString, TypeReference<T> reference) {
        T result = null;
        try {
            result = objectMapper.readValue(jsonAsString, reference);
        } catch (Exception e) {
            logger.error("JSON[{}]反序列化失败\n{}", jsonAsString, ExceptionUtils.getFullStackTrace(e));
        }
        return result;
    }

    /**
     * 将对象转化为json
     *
     * @param pojo
     * @param prettyPrint
     */
    public static String toJson(Object pojo, boolean prettyPrint) {
        String result = null;
        try {
            StringWriter sw = new StringWriter();
            JsonGenerator jg = jsonFactory.createGenerator(sw);
            if (prettyPrint) {
                jg.useDefaultPrettyPrinter();
            }
            objectMapper.writeValue(jg, pojo);
            result = sw.toString();
        } catch (Exception e) {
            logger.error("[{}]序列化成JSON失败\n{}", pojo, ExceptionUtils.getFullStackTrace(e));
        }
        return result;
    }
}
