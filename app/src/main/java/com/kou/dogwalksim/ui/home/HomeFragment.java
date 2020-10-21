package com.kou.dogwalksim.ui.home;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kou.dogwalksim.MainActivity;
import com.kou.dogwalksim.R;
import com.kou.dogwalksim.dog.Dog;
import com.kou.dogwalksim.dog.Item;
import com.kou.dogwalksim.dog.ItemList;
import com.kou.dogwalksim.dog.State;

public class HomeFragment extends Fragment implements WalkTask.Listener, LocationListener, CompoundButton.OnCheckedChangeListener {

    static private WalkTask walkTask;
    private HomeViewModel homeViewModel;
    private LocationManager locationManager;
    static private Dog dog;
    private double mySpeed;
    private TextView tvDog;
    private TextView tvDogSpeed;
    private Switch swRun;
    private SeekBar sbCheat;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(walkTask == null) {
            walkTask = new WalkTask();
            walkTask.setListener(this);
        }
        if(dog == null) {
            dog = new Dog(MainActivity.state);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getDog().setValue(dog);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        tvDogSpeed = root.findViewById(R.id.tv_dog_speed);
        final TextView tvMySpeed = root.findViewById(R.id.tv_speed);
        final Button btnGo = root.findViewById(R.id.btn_go);
        final TextView tvDistance = root.findViewById(R.id.tv_distance);
        final Button btnGotcha = root.findViewById(R.id.btn_gotcha);
        sbCheat = root.findViewById(R.id.sb_cheat);
        sbCheat.setMax(10);
        sbCheat.setProgress(0);

        if(dog.isPaused()) {
            btnGo.setText("GO!");
        } else {
            btnGo.setText("Pause..");
        }
        btnGo.setOnClickListener(v -> {
            if(dog.isPaused()) {
                dog.setPaused(false);
                btnGo.setText("Pause..");
            } else {
                dog.setPaused(true);
                btnGo.setText("GO!");
            }
        });
        btnGotcha.setOnClickListener(v -> {
            Item item = ItemList.getInstance().rollItem();
            MainActivity.state.getBag().addItem(item);
        });
        tvDog = root.findViewById(R.id.tv_dog);
        swRun = root.findViewById(R.id.sw_run);
        swRun.setOnCheckedChangeListener(this);
        swRun.setChecked(MainActivity.state.isRun());
        homeViewModel.getDog().observe(getViewLifecycleOwner(), dog -> {
            tvDogSpeed.setText(String.format("⬆　%.2f km/h", dog.getSpeed() * 3.6));
            tvMySpeed.setText(String.format("スピード：%.2f km/h", dog.getMyspeed() * 3.6));
            tvDistance.setText(String.format("距　　離：%.2f m", dog.getState().getDistance()));
        });
        homeViewModel.getDog().postValue(dog);


        if (ActivityCompat.checkSelfPermission(this.getContext()
                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, (float) 0, this);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            walkTask.execute();
        }catch (Exception e) {
            walkTask.pause();
            walkTask = new WalkTask();
            walkTask.setListener(this);
            walkTask.execute();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        State.saveState(this.getContext(), MainActivity.state);
        //locationManager.removeUpdates(this);
    }

    @Override
    public void onRefreshed() {
        dog.refresh(dog.getState().getDistance() + (dog.getSpeed() - mySpeed) * (walkTask.getDisTime() / 1000.0));
        dog.getState().setTotalDistance(dog.getState().getTotalDistance() + (mySpeed) * (walkTask.getDisTime() / 1000.0));
        tvDog.setY((float) (960 - (dog.getState().getDistance()  * 30)));
        if (tvDog.getY() < -200) {
            tvDog.setY(-200);
        }
        homeViewModel.getDog().postValue(dog);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location.hasSpeed()) {
            mySpeed = location.getSpeed() * (sbCheat.getProgress() + 1);
        } else {
            mySpeed = mySpeed;
        }
        dog.setMyspeed(mySpeed);
        Log.i("Speed", String.format("%f m/s", location.getSpeed()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        MainActivity.state.setRun(isChecked);
    }
}
