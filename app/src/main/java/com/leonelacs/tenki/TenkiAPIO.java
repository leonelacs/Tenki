package com.leonelacs.tenki;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TenkiAPIO {
    private static final String URL_BY_CITYNAME = "http://v.juhe.cn/weather/index?key=6690cb86309398c0f2e54670d4725605&format=2&cityname=";
    private static final String URL_BY_COORDINATE = "http://v.juhe.cn/weather/geo?key=6690cb86309398c0f2e54670d4725605&format=2&";
    private static final String URL_AQI = "http://web.juhe.cn:8080/environment/air/pm?key=c779b3e9680227e637793e104bd33c26&city=";
    public List<Chiiki> chiikiIchiran = new ArrayList<>();

    public TenkiAPIO() {
    }

    public TenkiInfo refreshByCityName(String cityName, String bigCity) {
        TenkiInfo tenki = new TenkiInfo();
        String url = URL_BY_CITYNAME + cityName;
        String receive = null;
        String receive_a = null;

        OkHttpClient clientTenki = new OkHttpClient();
        Request requestTenki = new Request.Builder().url(url).build();
        try {
            Response responseTenki = clientTenki.newCall(requestTenki).execute();
            receive = responseTenki.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

//        String receive = NIO.get(url);
        if (receive != null) {
            JSONObject obj = JSONObject.parseObject(receive);
            String resultCode = obj.getString("resultcode");
            if (resultCode != null && resultCode.equals("200")) {
                String result = obj.getString("result");
                JSONObject objResult = JSONObject.parseObject(result);

                String strSk = objResult.getString("sk");
                JSONObject objSk = JSONObject.parseObject(strSk);
                String temp = objSk.getString("temp");
                tenki.imaOndo = Integer.parseInt(temp);
                tenki.mokuzenTemperature = "🌡"+temp+"℃　";
                String wind_direction = objSk.getString("wind_direction");
                String wind_strength = objSk.getString("wind_strength");
                String humidity = objSk.getString("humidity");
                String wind_and_humidity = "🌬 " + wind_direction + " " + wind_strength + "　|　💧 " + humidity;
                tenki.mokuzenWindAndHumidity = wind_and_humidity;
                String time = objSk.getString("time");
                tenki.tenkiUpdateTime = "更新时间　" + time;
                String strToday = objResult.getString("today");
                JSONObject objToday = JSONObject.parseObject(strToday);
                String temperature = objToday.getString("temperature");
                tenki.todayTemperature = "今日温度　" + temperature;
                String weather = objToday.getString("weather");
                String strWeatherId = objToday.getString("weather_id");
                JSONObject objWeatherId = JSONObject.parseObject(strWeatherId);
                String fa = objWeatherId.getString("fa");
                String fb = objWeatherId.getString("fb");
                String wind = objToday.getString("wind");
                String dressing_index = objToday.getString("dressing_index");
                String today_tenki = weather + "　" + wind + "　" + dressing_index;
                tenki.todayTenki = today_tenki;
                Emojis emojis = new Emojis();
                if (fa.equals(fb)) {
                    tenki.mokuzenTenkiEmoji = emojis.getEmojiByCode(fa);
                }
                else {
                    tenki.mokuzenTenkiEmoji = emojis.getEmojiByCode(fa) + "/" + emojis.getEmojiByCode(fb);
                }
                String dressing_advice = objToday.getString("dressing_advice");
                tenki.todayClothes = dressing_advice;

                String strFuture = objResult.getString("future");
                JSONArray arrFuture = JSONArray.parseArray(strFuture);
                JSONObject obj1 = arrFuture.getJSONObject(0);
                JSONObject obj2 = arrFuture.getJSONObject(1);
                JSONObject obj3 = arrFuture.getJSONObject(2);
                JSONObject obj4 = arrFuture.getJSONObject(3);
                JSONObject obj5 = arrFuture.getJSONObject(4);
                JSONObject obj6 = arrFuture.getJSONObject(5);
                String y1Temp = obj1.getString("temperature");
                String y2Temp = obj2.getString("temperature");
                String y3Temp = obj3.getString("temperature");
                String y4Temp = obj4.getString("temperature");
                String y5Temp = obj5.getString("temperature");
                String y6Temp = obj6.getString("temperature");
                tenki.youbi1Temp = y1Temp;
                tenki.youbi2Temp = y2Temp;
                tenki.youbi3Temp = y3Temp;
                tenki.youbi4Temp = y4Temp;
                tenki.youbi5Temp = y5Temp;
                tenki.youbi6Temp = y6Temp;
                String y1Weather = obj1.getString("weather");
                String y2Weather = obj2.getString("weather");
                String y3Weather = obj3.getString("weather");
                String y4Weather = obj4.getString("weather");
                String y5Weather = obj5.getString("weather");
                String y6Weather = obj6.getString("weather");
                String y1Wind = obj1.getString("wind");
                String y2Wind = obj2.getString("wind");
                String y3Wind = obj3.getString("wind");
                String y4Wind = obj4.getString("wind");
                String y5Wind = obj5.getString("wind");
                String y6Wind = obj6.getString("wind");
                tenki.youbi1Tenki = y1Weather + " " + y1Wind;
                tenki.youbi2Tenki = y2Weather + " " + y2Wind;
                tenki.youbi3Tenki = y3Weather + " " + y3Wind;
                tenki.youbi4Tenki = y4Weather + " " + y4Wind;
                tenki.youbi5Tenki = y5Weather + " " + y5Wind;
                tenki.youbi6Tenki = y6Weather + " " + y6Wind;
                String y1Week = obj1.getString("week");
                String y2Week = obj2.getString("week");
                String y3Week = obj3.getString("week");
                String y4Week = obj4.getString("week");
                String y5Week = obj5.getString("week");
                String y6Week = obj6.getString("week");
                tenki.youbi1Youbi = y1Week;
                tenki.youbi2Youbi = y2Week;
                tenki.youbi3Youbi = y3Week;
                tenki.youbi4Youbi = y4Week;
                tenki.youbi5Youbi = y5Week;
                tenki.youbi6Youbi = y6Week;
            }
        }

        String url_a = URL_AQI + bigCity;

        OkHttpClient clientAQI = new OkHttpClient();
        Request requestAQI = new Request.Builder().url(url_a).build();
        try {
            Response responseAQI = clientAQI.newCall(requestAQI).execute();
            receive_a = responseAQI.body().string();
        }
        catch (Exception e) {}

//        String receive_a = NIO.get(url_a);
        if (receive_a != null) {
            JSONObject obj = JSONObject.parseObject(receive_a);
            String resultCode = obj.getString("resultcode");
            if (resultCode != null && resultCode.equals("200")) {
                String result = obj.getString("result");
                JSONArray arrResult = JSONArray.parseArray(result);
                JSONObject objInResult = arrResult.getJSONObject(0);
//                JSONObject objInResult = JSONObject.parseObject(result);
                String aqi = objInResult.getString("AQI");
                tenki.mokuzenAQIValue = aqi;
                String quality = objInResult.getString("quality");
                tenki.mokuzenAQIWord = quality;
                String PM2_5 = objInResult.getString("PM2.5");
                tenki.pm25Value = PM2_5;
                String PM10 = objInResult.getString("PM10");
                tenki.pm10Value = PM10;
                String CO = objInResult.getString("CO");
                tenki.coValue = CO;
                String NO2 = objInResult.getString("NO2");
                tenki.no2Value = NO2;
                String O3 = objInResult.getString("O3");
                tenki.o3Value = O3;
                String SO2 = objInResult.getString("SO2");
                tenki.so2Value = SO2;
                String time = objInResult.getString("time");
                tenki.aqiUpdateTime = time;
            }
            else {
                tenki.mokuzenAQIValue = "-";
                tenki.mokuzenAQIWord = "-";
                tenki.pm25Value = "-";
                tenki.pm10Value = "-";
                tenki.coValue = "-";
                tenki.no2Value = "-";
                tenki.o3Value = "-";
                tenki.so2Value = "-";
                tenki.aqiUpdateTime = "-";
            }
        }
        return tenki;
    }

    public TenkiInfo refreshByCoordinate(String lon, String lat) {
        TenkiInfo tenki = new TenkiInfo();
        String url = URL_BY_COORDINATE + "lon=" + lon + "&lat=" + lat;
        String receive = null;
        String receive_a = null;

        OkHttpClient clientTenki = new OkHttpClient();
        Request requestTenki = new Request.Builder().url(url).build();
        try {
            Response responseTenki = clientTenki.newCall(requestTenki).execute();
            receive = responseTenki.body().string();
        }
        catch (Exception e) {}
        String city = "notfound";
        if (receive != null) {
            JSONObject obj = JSONObject.parseObject(receive);
            String resultCode = obj.getString("resultcode");
            if (resultCode != null && resultCode.equals("200")) {
                String result = obj.getString("result");
                JSONObject objResult = JSONObject.parseObject(result);

                String strSk = objResult.getString("sk");
                JSONObject objSk = JSONObject.parseObject(strSk);
                String temp = objSk.getString("temp");
                tenki.mokuzenTemperature = "🌡"+temp+"℃　";
                String wind_direction = objSk.getString("wind_direction");
                String wind_strength = objSk.getString("wind_strength");
                String humidity = objSk.getString("humidity");
                String wind_and_humidity = "🌬 " + wind_direction + " " + wind_strength + "　|　💧 " + humidity;
                tenki.mokuzenWindAndHumidity = wind_and_humidity;
                String time = objSk.getString("time");
                tenki.tenkiUpdateTime = "更新时间　" + time;
                String strToday = objResult.getString("today");
                JSONObject objToday = JSONObject.parseObject(strToday);
                String temperature = objToday.getString("temperature");
                tenki.todayTemperature = "今日温度　" + temperature;
                String weather = objToday.getString("weather");
                String strWeatherId = objToday.getString("weather_id");
                JSONObject objWeatherId = JSONObject.parseObject(strWeatherId);
                String fa = objWeatherId.getString("fa");
                String fb = objWeatherId.getString("fb");
                String wind = objToday.getString("wind");
                String dressing_index = objToday.getString("dressing_index");
                String today_tenki = weather + "　" + wind + "　" + dressing_index;
                tenki.todayTenki = today_tenki;
                Emojis emojis = new Emojis();
                if (fa.equals(fb)) {
                    tenki.mokuzenTenkiEmoji = emojis.getEmojiByCode(fa);
                }
                else {
                    tenki.mokuzenTenkiEmoji = emojis.getEmojiByCode(fa) + "　/　" + emojis.getEmojiByCode(fb);
                }
                String dressing_advice = objToday.getString("dressing_advice");
                tenki.todayClothes = dressing_advice;
                city = objToday.getString("city");

                String strFuture = objResult.getString("future");
                JSONArray arrFuture = JSONArray.parseArray(strFuture);
                JSONObject obj1 = arrFuture.getJSONObject(0);
                JSONObject obj2 = arrFuture.getJSONObject(1);
                JSONObject obj3 = arrFuture.getJSONObject(2);
                JSONObject obj4 = arrFuture.getJSONObject(3);
                JSONObject obj5 = arrFuture.getJSONObject(4);
                JSONObject obj6 = arrFuture.getJSONObject(5);
                String y1Temp = obj1.getString("temperature");
                String y2Temp = obj2.getString("temperature");
                String y3Temp = obj3.getString("temperature");
                String y4Temp = obj4.getString("temperature");
                String y5Temp = obj5.getString("temperature");
                String y6Temp = obj6.getString("temperature");
                tenki.youbi1Temp = y1Temp;
                tenki.youbi2Temp = y2Temp;
                tenki.youbi3Temp = y3Temp;
                tenki.youbi4Temp = y4Temp;
                tenki.youbi5Temp = y5Temp;
                tenki.youbi6Temp = y6Temp;
                String y1Weather = obj1.getString("weather");
                String y2Weather = obj2.getString("weather");
                String y3Weather = obj3.getString("weather");
                String y4Weather = obj4.getString("weather");
                String y5Weather = obj5.getString("weather");
                String y6Weather = obj6.getString("weather");
                String y1Wind = obj1.getString("wind");
                String y2Wind = obj2.getString("wind");
                String y3Wind = obj3.getString("wind");
                String y4Wind = obj4.getString("wind");
                String y5Wind = obj5.getString("wind");
                String y6Wind = obj6.getString("wind");
                tenki.youbi1Tenki = y1Weather + " " + y1Wind;
                tenki.youbi2Tenki = y2Weather + " " + y2Wind;
                tenki.youbi3Tenki = y3Weather + " " + y3Wind;
                tenki.youbi4Tenki = y4Weather + " " + y4Wind;
                tenki.youbi5Tenki = y5Weather + " " + y5Wind;
                tenki.youbi6Tenki = y6Weather + " " + y6Wind;
                String y1Week = obj1.getString("week");
                String y2Week = obj2.getString("week");
                String y3Week = obj3.getString("week");
                String y4Week = obj4.getString("week");
                String y5Week = obj5.getString("week");
                String y6Week = obj6.getString("week");
                tenki.youbi1Youbi = y1Week;
                tenki.youbi2Youbi = y2Week;
                tenki.youbi3Youbi = y3Week;
                tenki.youbi4Youbi = y4Week;
                tenki.youbi5Youbi = y5Week;
                tenki.youbi6Youbi = y6Week;
            }
        }

        String url_a = URL_AQI + city;

        OkHttpClient clientAQI = new OkHttpClient();
        Request requestAQI = new Request.Builder().url(url_a).build();
        try {
            Response responseAQI = clientAQI.newCall(requestAQI).execute();
            receive_a = responseAQI.body().string();
        }
        catch (Exception e) {}
        if (receive_a != null) {
            JSONObject obj = JSONObject.parseObject(receive);
            String resultCode = obj.getString("resultcode");
            if (resultCode != null && resultCode.equals("200")) {
                String result = obj.getString("result");
                JSONArray arrResult = JSONArray.parseArray(result);
                JSONObject objInResult = arrResult.getJSONObject(0);
                String aqi = objInResult.getString("aqi");
                tenki.mokuzenAQIValue = aqi;
                String quality = objInResult.getString("quality");
                tenki.mokuzenAQIWord = quality;
                String PM2_5 = objInResult.getString("PM2.5");
                tenki.pm25Value = PM2_5;
                String PM10 = objInResult.getString("PM10");
                tenki.pm10Value = PM10;
                String CO = objInResult.getString("CO");
                tenki.coValue = CO;
                String NO2 = objInResult.getString("NO2");
                tenki.no2Value = NO2;
                String O3 = objInResult.getString("O3");
                tenki.o3Value = O3;
                String SO2 = objInResult.getString("SO2");
                tenki.so2Value = SO2;
                String time = objInResult.getString("time");
                tenki.aqiUpdateTime = time;
            }
            else {
                tenki.mokuzenAQIValue = "-";
                tenki.mokuzenAQIWord = "-";
                tenki.pm25Value = "-";
                tenki.pm10Value = "-";
                tenki.coValue = "-";
                tenki.no2Value = "-";
                tenki.o3Value = "-";
                tenki.so2Value = "-";
                tenki.aqiUpdateTime = "-";
            }
        }
        return tenki;
    }
}
