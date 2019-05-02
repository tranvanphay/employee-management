package com.example.asm_server;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asm_server.model.User;
import com.example.asm_server.remote.APIUtils;
import com.example.asm_server.remote.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
FloatingActionButton btnAddUser;
ListView listView;
UserService userService;
List<User> list =new ArrayList<User>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddUser=findViewById(R.id.flb);
        listView=findViewById(R.id.listView);
        userService= APIUtils.getUserService();
        getUserList();

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_add);
                final EditText edtName,edtEmail,edtMobile,edtCity;
                Button btnAdd;
                ImageView btnCancel;
                edtName=dialog.findViewById(R.id.edtAddName);
                edtEmail=dialog.findViewById(R.id.edtAddEmail);
                edtMobile=dialog.findViewById(R.id.edtAddMobile);
                edtCity=dialog.findViewById(R.id.edtAddCity);
                btnAdd=dialog.findViewById(R.id.btnAddEmployee);
                btnCancel=dialog.findViewById(R.id.imvCancel);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar=Calendar.getInstance();
                        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                        String strDate = mdformat.format(calendar.getTime());

                        User user=new User();
                        user.setId(strDate);
                        user.setName(edtName.getText().toString());
                        user.setEmail(edtEmail.getText().toString());
                        user.setPhoneNumber(edtMobile.getText().toString());
                        user.setCity(edtCity.getText().toString());
                        addUser(user);
                        dialog.dismiss();
                        getUserList();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }
    public void getUserList(){
        Call<List<User>> call=userService.getUser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    list=response.body();
                    listView.setAdapter(new UserAdapter(MainActivity.this,R.layout.one_item_listview,list));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
    public void addUser(User user){
        Call<User> call=userService.addUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ERR", t.getMessage() );
            }
        });
    }
}
