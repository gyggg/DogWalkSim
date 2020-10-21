package com.kou.dogwalksim.dog;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class State implements Serializable {
    final public static int RUN = 1;
    final public static int WALK = 0;
    private String name = "ワンちゃん";
    private int love = 0;
    private int charactor = WALK;
    private double totalDistance = 0.0;
    private double distance = 0.0;
    private double lucky = 0.1;
    private Date date = new Date(System.currentTimeMillis());
    private Bag bag = new Bag();
    public static String pName = "dog";
    public static String pKey = "state";


    private State() {
        init();
    }


    public State init() {
        name = "ワンちゃん";
        love = 0;
        charactor = WALK;
        totalDistance = 0.0;
        distance = 0.0;
        lucky = 0.1;
        date = new Date(System.currentTimeMillis());
        bag = new Bag();
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public int getCharactor() {
        return charactor;
    }

    public void setCharactor(int charactor) {
        this.charactor = charactor;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public double getLucky() {
        return lucky;
    }

    public void setLucky(double lucky) {
        this.lucky = lucky;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public static void saveState(Context context, State state) {
        SharedPreferences.Editor editor = context.getSharedPreferences(pName, Context.MODE_PRIVATE).edit();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(state);
            String temp = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            editor.putString(pKey, temp);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static State loadState(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(pName, context.MODE_PRIVATE);
        String temp = sharedPreferences.getString(pKey, "");
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        State state = new State();
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            state = (State) ois.readObject();
        } catch (IOException e) {
        } catch (ClassNotFoundException e1) {

        }
        return state;
    }


    public static State resetState(Context context, State state) {
        state.init();
        saveState(context, state);
        return state;
    }

    public String getAge() {
        Date date = new Date(System.currentTimeMillis());
        long diff = date.getTime() - this.date.getTime();
        int days = (int) (diff / (24 * 60 * 60 * 1000)) + 1;
        double year = (double)days / 365.0;
        return String.format("%.1f　才", year);
    }

    public void setRun(boolean run) {
        if(run) {
            this.charactor = RUN;
        } else {
            this.charactor = WALK;
        }
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isRun() {
        if(charactor == RUN){
            return true;
        }
        return false;
    }
}
