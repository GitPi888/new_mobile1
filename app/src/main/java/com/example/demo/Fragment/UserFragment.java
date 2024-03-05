package com.example.demo.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.Model.Table;
import com.example.demo.Model.UserDatabase;
import com.example.demo.Model.UserModel;
import com.example.demo.R;
import com.example.demo.activities.LoginActivity;
import com.example.demo.activities.Updatepassword;
import com.example.demo.dialog.Customize_dialog;
import com.example.demo.dialog.Dialog_Delete;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    private static final String TAG="UserFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String email_user,password_user;
    TextView txt_email,txt_name,update_password,txt_Delete;
    Button btn_logout;
    RelativeLayout relativeLayout;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        email_user=this.getArguments().getString("email");
        password_user=this.getArguments().getString("pass");

    }
    public ArrayList<UserModel> getData(String email_user){
        String[] rs = new String[1];
        rs[0]=email_user;
        ArrayList<UserModel> ds = new ArrayList<>();
        UserDatabase db = new UserDatabase(requireContext());
        SQLiteDatabase sq = db.getReadableDatabase();
        Cursor c = sq.rawQuery("Select*from USER where EMAIL=?",rs);
        if(c.moveToFirst()){
            UserModel userModel = new UserModel();
            userModel.setName(c.getString(1));
            userModel.setEmail(c.getString(2));
            ds.add(userModel);
        }
        return  ds;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        relativeLayout=root.findViewById(R.id.layout_UserName);
        txt_email = root.findViewById(R.id.fragment_Email);
        txt_name = root.findViewById(R.id.fragment_Username);
        txt_Delete=root.findViewById(R.id.txt_Delete_Account);
        update_password=root.findViewById(R.id.txt_Update_Password);
        btn_logout=root.findViewById(R.id.btn_Logout);
        setClick();
        showData();
        return root;
    }
    private void showData(){
        ArrayList<UserModel> ds = new ArrayList<>();
        ds=getData(email_user);
        txt_email.setText(ds.get(0).getEmail());
        txt_name.setText((ds.get(0).getName()));
    }
    public void setClick(){
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: opening dialog");
                Customize_dialog cs = new Customize_dialog();
                cs.show(getChildFragmentManager(),"Customize_dialog");
            }
        });
        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Updatepassword.class);
                intent.putExtra("key_email",email_user);
                intent.putExtra("key_password",password_user);
                startActivity(intent);
            }
        });
        txt_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: opening dialog");
                Bundle args = new Bundle();
                args.putString("key",email_user);
                DialogFragment da = new Dialog_Delete();
                da.setArguments(args);
                da.show(getChildFragmentManager(),"Dialog_Delete");
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}