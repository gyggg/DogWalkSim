package com.kou.dogwalksim.ui.home;

import android.os.AsyncTask;

public class WalkTask extends AsyncTask {

    private Listener listener;
    private boolean continueFlag = true;
    private int disTime = 100;

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        while(continueFlag) {
            publishProgress();
            try {
                Thread.sleep(disTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    interface Listener {
        void onRefreshed();
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        listener.onRefreshed();
    }

    public void pause() {
        continueFlag = false;
    }

    public void resume() {
        continueFlag = true;
    }

    public int getDisTime() {
        return disTime;
    }

    public void setDisTime(int disTime) {
        this.disTime = disTime;
    }
}
