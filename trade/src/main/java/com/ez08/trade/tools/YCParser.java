package com.ez08.trade.tools;

import android.net.Uri;

import com.ez08.trade.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class YCParser {

    public static Map<String, String> parseObject(String json) throws JSONException {
        Map<String, String> map = new HashMap<>();
        Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + json);
        Set<String> pn = uri.getQueryParameterNames();
        for (Iterator it = pn.iterator(); it.hasNext(); ) {
            String key = it.next().toString();
            if ("TBL_OUT".equals(key)) {
                String out = uri.getQueryParameter(key);
                String[] split = out.split(";");
                String[] title = split[0].split(",");
                if(split.length > 1) {
                    String[] var = split[1].split(",", -1);
                    for (int i = 0; i < title.length; i++) {
                        map.put(title[i], var[i]);
                    }
                }
            }
        }

        return map;
    }

    public static List<Map<String, String>> parseArray(String json) throws JSONException {
        List<Map<String, String>> list = new ArrayList<>();
        Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + json);
        Set<String> pn = uri.getQueryParameterNames();
        for (Iterator it = pn.iterator(); it.hasNext(); ) {
            String key = it.next().toString();
            if ("TBL_OUT".equals(key)) {
                String out = uri.getQueryParameter(key);
                String[] split = out.split(";");
                String[] title = split[0].split(",");
                for (int i = 1; i < split.length; i++) {
                    String item = split[i];
                    String[] items = item.split(",");
                    Map<String, String> map = new HashMap<>();
                    for (int j = 0; j < items.length; j++) {
                        map.put(title[j], items[j]);
                    }
                    list.add(map);
                }
            }
        }

        return list;
    }
}
