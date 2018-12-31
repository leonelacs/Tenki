package com.leonelacs.tenki;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChiikiAPIO {
    private static final String URL= "http://v.juhe.cn/weather/citys?key=6690cb86309398c0f2e54670d4725605";
    public List<Chiiki> chiikiIchiran = new ArrayList<>();

    public ChiikiAPIO() {
        String receive = NIO.get(URL);
        if (receive != null) {
            JSONObject obj = JSONObject.parseObject(receive);
            String resultCode = obj.getString("resultcode");
            if (resultCode != null && resultCode.equals("200")) {
                String result = obj.getString("result");
                JSONArray arr = JSONArray.parseArray(result);
                for (Object o: arr) {
                    JSONObject jsonChiiki = JSONObject.parseObject(o.toString());
                    String id = jsonChiiki.getString("id");
                    String province = jsonChiiki.getString("province");
                    String city = jsonChiiki.getString("city");
                    String district = jsonChiiki.getString("district");
                    Chiiki tmp = new Chiiki(id, province, city, district);
                    chiikiIchiran.add(tmp);
                }
            }
        }
    }
}
