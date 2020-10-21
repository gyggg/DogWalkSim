package com.kou.dogwalksim.dog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class ItemList {
    public static ItemList instence;
    public Map<Item, Double> map;
    public int sum = 0;
    public Random rnd = new Random(System.currentTimeMillis());
    private ItemList(){
        map = new TreeMap<>();
        addItem("風船", 10);
        addItem("婚約指輪",  1);
        addItem("サッカーボール", 5);
        addItem("バラの花",  10);
        addItem("ハンチング",  1);
        addItem("ピザ", 5);
        addItem("木の枝", 10);
        addItem("ビーチボール", 5);
        addItem("ペットボトル", 10);
        addItem("フープ", 1);
        addItem("鉄のボルト", 10);
        addItem("ネコ草", 10);
        addItem("歯みがきガム", 10);
    }

    static public ItemList getInstance(){
        if(instence == null) {
            instence = new ItemList();
        }
        return instence;
    }

    public void addItem(String name, String describe, int weight) {
        sum = sum + weight;
        Item item = new Item(name,describe, weight);
        map.put(item, (double)weight / (double)sum);
        for(Map.Entry<Item, Double> entry : map.entrySet()) {
            double dw = (double)entry.getKey().getWeight() / (double)sum;
            map.put(entry.getKey(), dw);
        }
    }

    public void addItem(String name, int weight) {
        addItem(name, "", weight);
    }

    public Item rollItem() {
        TreeMap<Double, Item> treeMap = new TreeMap();
        double r = rnd.nextDouble();
        for(Map.Entry<Item, Double> entry : map.entrySet()) {
            treeMap.put(entry.getValue(), entry.getKey());
        }
        for(Map.Entry<Double, Item> entry : treeMap.entrySet()) {
            if(r < entry.getKey()){
                return entry.getValue();
            }
        }
        return new Item("バグ", "", 0);
    }
}
