package com.kou.dogwalksim.ui.settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.kou.dogwalksim.MainActivity;
import com.kou.dogwalksim.R;
import com.kou.dogwalksim.dog.State;

public class SettingsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final Button btn_reset = root.findViewById(R.id.btn_reset);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                State.resetState(getContext(), MainActivity.state);
            }
        });
        builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setMessage("すべての記録を消したいですか？（記録が消されたら、元の状態に戻れないですよ）");

        btn_reset.setOnClickListener(v -> {
            dialog.show();
        });
        return root;
    }
}
