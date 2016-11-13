package za.co.dladle.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prady on 11/13/2016.
 */
public class ResponseUtil {

    public static Map<String, Object> response(String status, Object data, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("Status", status);
        map.put("Data", data);
        map.put("Message", message);
        return map;
    }
}
