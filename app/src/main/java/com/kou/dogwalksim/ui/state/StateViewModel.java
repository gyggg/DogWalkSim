package com.kou.dogwalksim.ui.state;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kou.dogwalksim.MainActivity;
import com.kou.dogwalksim.dog.State;

public class StateViewModel extends ViewModel {

    private MutableLiveData<State> mState;

    public StateViewModel() {
        mState = new MutableLiveData<>();
        mState.setValue(MainActivity.state);
    }

    public MutableLiveData<State> getmState() {
        return mState;
    }

    public void setmState(MutableLiveData<State> mState) {
        this.mState = mState;
    }
}