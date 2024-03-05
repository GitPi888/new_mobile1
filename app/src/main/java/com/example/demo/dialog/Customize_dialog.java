package com.example.demo.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.demo.R;
import com.example.demo.activities.LoginActivity;

public class Customize_dialog extends DialogFragment {
    private static final String TAG = "Customize_dialog";
    Button btn_Cancel,btn_Confirm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.custome_dialog, container, false);
        btn_Cancel=root.findViewById(R.id.btn_dialog_Cancel);
        btn_Confirm= root.findViewById(R.id.btn_dialog_Confirm);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: closing dialog");
                getDialog().dismiss();
            }
        });
        btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });
        return root ;
    }
}
