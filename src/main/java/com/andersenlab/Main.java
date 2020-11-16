package com.andersenlab;

import com.andersenlab.hashmap.CustomMap;


public class Main {
    public static void main(String[] args) {
        CustomMap<String, String> customMap = new CustomMap<>();
        customMap.put("Вова", "б");
        customMap.put("Костя", "ко");
        customMap.put("Костя", "RH");
        customMap.put("Паша", "ло");
        customMap.put("Аня", "кова");
        customMap.put("Лида", "ли");

        System.out.println(customMap);

        customMap.remove("Лида");
    }
}
