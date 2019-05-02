package com.example.asm_server;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_server.model.User;
import com.example.asm_server.remote.APIUtils;
import com.example.asm_server.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    UserService userService;
    EditText edtId, edtName,edtEmail,edtMobile,edtCity;
    Button btnBack,btnSave,btnDelete;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_acti);
        edtId = findViewById(R.id.edtId);
        edtName=findViewById(R.id.edtName);
        edtEmail=findViewById(R.id.edtEmail);
        edtMobile=findViewById(R.id.edtMobile);
        edtCity=findViewById(R.id.edtCity);
        btnBack=findViewById(R.id.btnBack);
        btnSave=findViewById(R.id.btnSave);
        btnDelete=findViewById(R.id.btnDelete);


        userService = APIUtils.getUserService();

        Bundle extras=getIntent().getExtras();
        final String id=extras.getString("_id" );
        String name=extras.getString("fullName");
        String email=extras.getString("email");
        String mobile=extras.getString("mobile");
        String city=extras.getString("city");

        edtId.setText(id);
        edtName.setText(name);
        edtEmail.setText(email);
        edtMobile.setText(mobile);
        edtCity.setText(city);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user=new User();
//                String name=edtName.getText().toString();
//                String email=edtEmail.getText().toString();
//                String mobile=edtMobile.getText().toString();
//                String city=edtCity.getText().toString();
//                User s=new User(name,email,mobile,city);
                user.setId(edtId.getText().toString());
                user.setName(edtName.getText().toString());
                user.setEmail(edtEmail.getText().toString());
                user.setPhoneNumber(edtMobile.getText().toString());
                user.setCity(edtCity.getText().toString());
                    updateUser(id,user);
                    Intent i =new Intent(UserActivity.this,MainActivity.class);
                    startActivity(i);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(id);
                Intent i =new Intent(UserActivity.this,MainActivity.class);
                startActivity(i);
            }
        });


    }

    public void updateUser(String id,User user){
        Call<User> call=userService.updateUser(id,user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(UserActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                    Log.e("ERR", response.message() );
                }
                if(!response.isSuccessful()){
                    Toast.makeText(UserActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ERR", t.getMessage() );
            }
        });
    }
    public void deleteUser(String id){
        Call<User> call=userService.deleteUser(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(UserActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ERR", t.getMessage() );
            }
        });
    }
}
