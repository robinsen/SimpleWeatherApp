package com.rao.weather;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherScrawl {
    static final String TAG = "PythonOnAndroid";
    private String pm;
    private String pm10;
    private String aqi;
    private String aqilevel;
    private String aqiname;
    private String degree;
    private String weather;
    private String weatherShort;
    private String weatherDirection;
    private String weatherCode;
    private String windPower;
    private String city;
    private String province;
    private double windDeg;
    private double windSpeed;
    private long update_time;
    private List<HashMap<String,String>> dataList = new ArrayList<>();
    private List<HashMap<String,String>> hourList = new ArrayList<>();

    //'degree': '29', 'update_time': '20210402120000', 'weather': '多云', 'weather_code': '01', 'weather_short': '多云', 'wind_direction': '东南风', 'wind_power': '3'

    // 调用python代码
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean scrawlWeather(Python py){
        //Python py = Python.getInstance();
        // 调用Python函数 将返回的Python中的dic转为Java的map
        PyObject objMap = py.getModule("weather").callAttr("getTodayWeather");
        Map<PyObject,PyObject> tempMap = objMap.asMap();
        Log.d(TAG,"get weather info= "+tempMap.values().toString());

        //今天天气
        if (tempMap.containsKey("0")) {
            PyObject todayObj = tempMap.get("0");
            Map<PyObject, PyObject> todayObjMap = todayObj.asMap();
            for (int i=0; i<todayObjMap.size();i++) {
                if (!todayObjMap.containsKey(String.valueOf(i)))
                    continue;
                if (i>23)
                    break;
                Map<PyObject, PyObject> curMap = todayObjMap.get(String.valueOf(i)).asMap();
                HashMap<String,String> map = new HashMap<String,String>();
                if (curMap.containsKey("degree"))
                    map.put("degree",curMap.get("degree").toString());
                if (curMap.containsKey("updatetime"))
                    map.put("updatetime",curMap.get("updatetime").toString());
                if (curMap.containsKey("weather_code"))
                    map.put("weather_code",curMap.get("weather_code").toString());
                if (curMap.containsKey("weather_short"))
                    map.put("weather_short",curMap.get("weather_short").toString());
                if (curMap.containsKey("wind_power"))
                    map.put("wind_power",curMap.get("wind_power").toString());
                if (curMap.containsKey("wind_direction"))
                    map.put("wind_direction",curMap.get("wind_direction").toString());
                hourList.add(map);
            }
        }

        if (hourList.size()>0) {
            HashMap<String,String> map = hourList.get(0);
            this.degree = map.get("degree");
            this.weather = map.get("weather_short");
        }

        //今天天气质量
        if (tempMap.containsKey("province")) {
            this.province = tempMap.get("province").toString();
        }
        if (tempMap.containsKey("city")) {
            this.city = tempMap.get("city").toString();
        }

        //今天天气质量
        if (tempMap.containsKey("air")) {
            PyObject airObj = tempMap.get("air");
            Map<PyObject, PyObject> airObjMap = airObj.asMap();
            if (airObj.containsKey("pm2.5"))
                pm = airObj.get("pm2.5").toString();
            if (airObj.containsKey("pm10"))
                pm10 = airObj.get("pm10").toString();
            if (airObj.containsKey("aqi"))
                aqi = airObj.get("aqi").toString();
            if (airObj.containsKey("aqi_level"))
                aqilevel = airObj.get("aqi_level").toString();
            if (airObj.containsKey("aqi_name"))
                aqiname = airObj.get("aqi_name").toString();
        }

        //未来7天
        for (int i=1; i<tempMap.size(); i++) {
            if (!tempMap.containsKey(String.valueOf(i)))
                continue;
            PyObject obj = tempMap.get(String.valueOf(i));
            Map<PyObject,PyObject> data = obj.asMap();
            HashMap<String,String> map = new HashMap<String,String>();
            if (data.containsKey("day_weather"))
                map.put("day_weather",data.get("day_weather").toString());
            if (data.containsKey("day_weather_code"))
                map.put("day_weather_code",data.get("day_weather_code").toString());
            if (data.containsKey("day_weather_short"))
                map.put("day_weather_short",data.get("day_weather_short").toString());
            if (data.containsKey("day_wind_direction"))
                map.put("day_wind_direction",data.get("day_wind_direction").toString());
            if (data.containsKey("day_wind_direction_code"))
                map.put("day_wind_direction_code",data.get("day_wind_direction_code").toString());
            if (data.containsKey("max_degree"))
                map.put("max_degree",data.get("max_degree").toString());
            if (data.containsKey("min_degree"))
                map.put("min_degree",data.get("min_degree").toString());
            if (data.containsKey("time"))
                map.put("time",data.get("time").toString());
            dataList.add(map);
        }

        return true;
    }

    public String getDegree() { return this.degree; }
    public String getWindPower() { return this.windPower; }
    public String getWeather() { return this.weather; }
    public String getCity() { return this.city; }
    public String getProvince() { return this.province; }

    public List<HashMap<String,String>> getSevenDayWeather() { return this.dataList; }
    public List<HashMap<String,String>> getTodayHourWeather() { return this.hourList; }
}
