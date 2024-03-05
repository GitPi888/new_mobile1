package com.example.demo.dialog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.demo.Model.UserDatabase;
import com.example.demo.R;
import com.example.demo.activities.LoginActivity;

public class Dialog_Delete extends DialogFragment {
    private static final String TAG = "Dialog_Delete";
    Button btn_Delete,btn_Cancel;
    LinearLayout linearLayout;
    TextView txt1,txt2;
    UserDatabase db ;
    String email_value;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_delete, container, false);
        btn_Cancel=root.findViewById(R.id.btn_dialog_Cancel1);
        btn_Delete=root.findViewById(R.id.btn_dialog_Delete);
        linearLayout=root.findViewById(R.id.layout_dialog_top);
        txt1=root.findViewById(R.id.txt_View1);
        txt2=root.findViewById(R.id.txt_View2);
        db=new UserDatabase(getContext());
        email_value = this.getArguments().getString("key");
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: closing dialog");
                getDialog().dismiss();
            }
        });
        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    linearLayout.setBackgroundColor(000000);
                    txt1.setText("Are you sure to your account will be deleted all information?");
                    txt1.setTextColor(Color.RED);
                    btn_Cancel.setEnabled(false);
                    btn_Cancel.setText(" ");
                    txt2.setText("This is your email:"+email_value);
                    txt2.setTextColor(Color.RED);
                    btn_Delete.setText("Confirm");
                    btn_Delete.setTextColor(getResources().getColor(R.color.txt_btn_dialog));
                    btn_Delete.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.bnt_background_dialog));
                btn_Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       boolean rs= db.DeleteUser(email_value);
                       if(rs){
                           Intent i = new Intent(getActivity(), LoginActivity.class);
                           startActivity(i);
                       }
                       else{
                           Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        });
        return root;
    }
}
