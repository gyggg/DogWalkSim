package com.kou.dogwalksim.dog;

import java.util.Random;

public class Dog {
    static private State state;
    private double speed;
    private double myspeed;
    private int sentiment;
    private int refreshTimes = 0;
    private int refreshBound = 10;
    private boolean paused = true;

    public static Random random = new Random(System.currentTimeMillis());

    public Dog(State state) {
        this.state = state;
        refresh(0);
    }

    private Dog() {
        refresh(0);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getSentiment() {
        return sentiment;
    }

    public void setSentiment(int sentiment) {
        this.sentiment = sentiment;
    }

    public int getRefreshTimes() {
        return refreshTimes;
    }

    public void setRefreshTimes(int refreshTimes) {
        this.refreshTimes = refreshTimes;
    }

    public int getRefreshBound() {
        return refreshBound;
    }

    public void setRefreshBound(int refreshBound) {
        this.refreshBound = refreshBound;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public double getMyspeed() {
        return myspeed;
    }

    public void setMyspeed(double myspeed) {
        this.myspeed = myspeed;
    }

    public void refresh(double distence) {
        double r = random.nextDouble();
        state.setDistance(distence);
        if(isPaused()){
            speed = 0;
            return;
        }
        else if (state.getCharactor() == State.WALK) {
            if (distence > 100) {
                this.sentiment = 0;
            } else {
                this.sentiment = (int) (10 - (distence / 10));
            }
            if (refreshTimes <= 0) {
                if (this.sentiment == 0) {
                    this.speed = (random.nextDouble() + 0.01) * 1 / 3;
                } else {
                    this.speed = (random.nextDouble() + sentiment / 3.0 + random.nextInt((1 + sentiment / 5)) + 0.01) * 2 / 9;
                }
                this.refreshBound = random.nextInt(50) + 20;
                refreshTimes = (refreshTimes + 1) % refreshBound;
            } else {
                refreshTimes = (refreshTimes + 1) % refreshBound;
            }
        } else {
            if (distence > 240) {
                this.sentiment = 0;
            } else {
                this.sentiment = (int) (30 - (distence / 8));
            }
            if (refreshTimes == 0) {
                if (this.sentiment == 0) {
                    this.speed = (random.nextDouble() + 0.01) * 1 / 3;
                } else {
                    this.speed = (random.nextDouble() + sentiment * sentiment / 50.0 + random.nextInt((1 + sentiment / 10)) + 0.01) * 2 / 9;
                }
                this.refreshBound = random.nextInt(50) + 30;
                refreshTimes = (refreshTimes + 1) % refreshBound;
            } else {
                refreshTimes = (refreshTimes + 1) % refreshBound;
            }
        }
    }


};
