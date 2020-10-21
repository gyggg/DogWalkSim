package com.kou.dogwalksim.dog;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Item implements Comparable, Serializable {
    private String name;
    private String discribe;
    private int weight;

    public Item(String name, String discribe, int weight) {
        this.name = name;
        this.discribe = discribe;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscribe() {
        return discribe;
    }

    public void setDiscribe(String discribe) {
        this.discribe = discribe;
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
