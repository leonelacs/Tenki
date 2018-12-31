package com.leonelacs.tenki;

import java.util.ArrayList;
import java.util.List;

public class Emojis {
    List<TenkiEmoji> emojis = new ArrayList<>();
    public Emojis() {
        emojis.add(new TenkiEmoji("00", "晴", "☀"));
        emojis.add(new TenkiEmoji("01", "多云", "⛅"));
        emojis.add(new TenkiEmoji("02", "阴", "☁"));
        emojis.add(new TenkiEmoji("03", "阵雨", "🌦"));
        emojis.add(new TenkiEmoji("04", "雷阵雨", "🌦⚡"));
        emojis.add(new TenkiEmoji("05", "雷阵雨伴有冰雹", "🌦⚡⚪"));
        emojis.add(new TenkiEmoji("06", "雨夹雪", "🌧❄"));
        emojis.add(new TenkiEmoji("07", "小雨", "🌧"));
        emojis.add(new TenkiEmoji("08", "中雨", "🌧🌧"));
        emojis.add(new TenkiEmoji("09", "大雨", "🌧🌧🌧"));
        emojis.add(new TenkiEmoji("10", "暴雨", "🌧❗"));
        emojis.add(new TenkiEmoji("11", "大暴雨", "🌧❗❗"));
        emojis.add(new TenkiEmoji("12", "特大暴雨", "🌧🈲"));
        emojis.add(new TenkiEmoji("13", "阵雪", "❄"));
        emojis.add(new TenkiEmoji("14", "小雪", "❄"));
        emojis.add(new TenkiEmoji("15", "中雪", "❄❄"));
        emojis.add(new TenkiEmoji("16", "大雪", "❄❄❄"));
        emojis.add(new TenkiEmoji("17", "暴雪", "❄❗"));
        emojis.add(new TenkiEmoji("18", "雾", "🌫"));
        emojis.add(new TenkiEmoji("19", "冻雨", "〰"));
        emojis.add(new TenkiEmoji("20", "沙尘暴", "💲"));
        emojis.add(new TenkiEmoji("21", "小雨-中雨", "🌧🌧"));
        emojis.add(new TenkiEmoji("22", "中雨-大雨", "🌧🌧🌧"));
        emojis.add(new TenkiEmoji("23", "大雨-暴雨", "🌧❗"));
        emojis.add(new TenkiEmoji("24", "暴雨-大暴雨", "🌧❗❗"));
        emojis.add(new TenkiEmoji("25", "大暴雨-特大暴雨", "🌧🈲"));
        emojis.add(new TenkiEmoji("26", "小雪-中雪", "❄❄"));
        emojis.add(new TenkiEmoji("27", "中雪-大雪", "❄❄❄"));
        emojis.add(new TenkiEmoji("28", "大雪-暴雪", "❄❗"));
        emojis.add(new TenkiEmoji("29", "浮尘", "💲➖"));
        emojis.add(new TenkiEmoji("30", "扬沙", "💲🔼"));
        emojis.add(new TenkiEmoji("31", "强沙尘暴", "💲❗"));
        emojis.add(new TenkiEmoji("53", "霾", "♾"));
    }

    String getEmojiByCode(String code) {
        for (TenkiEmoji e: emojis) {
            if (e.code.equals(code)) {
                return e.emoji;
            }
        }
        return "";
    }
}
