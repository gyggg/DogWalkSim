package com.kou.dogwalksim.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kou.dogwalksim.dog.Dog;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Dog> dog;

    public HomeViewModel() {
        dog = new MutableLiveData<>();
    }

    public MutableLiveData<Dog> getDog() {
        return dog;
    }

    public void setDog(MutableLiveData<Dog> dog) {
        this.dog = dog;
    }
}