package com.kou.dogwalksim.dog;

import java.util.TreeMap;

public class Bag extends TreeMap<Item, Integer>{

    public Bag() {
        super();
        this.addItem(new Item("犬のメダル", "", 0));
    }

    public Bag addItem(Item item) {
        if(super.containsKey(item)) {
            int num = this.get(item);
            this.put(item, num + 1);
        } else {
            this.put(item, 1);
        }
        return this;
    }
}
