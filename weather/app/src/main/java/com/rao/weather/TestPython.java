package com.rao.weather;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class TestPython {
    private String name;
    private List<String> data;

    public TestPython(String n){
        this.name = n;
        data = new ArrayList<String>();
    }

    public void setData(String el){
        this.data.add(el);
    }

    public void print(){
        for (String it: data) {
            Log.d("Java Bean - "+this.name,it);
        }
    }
}
