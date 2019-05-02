package com.example.asm_server;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.asm_server.model.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
        private Context context;
        private List<User> users;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.context=context;
        this.users=objects;
    }
    @Override
    public View getView(final  int pos, View convertview, ViewGroup parent){
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.one_item_listview,parent,false);
        TextView tvId=rowView.findViewById(R.id.tvId);
        TextView tvName=rowView.findViewById(R.id.tvName);
        TextView tvEmail=rowView.findViewById(R.id.tvEmail);
        TextView tvNumberPhone=rowView.findViewById(R.id.tvNumberPhone);
        TextView tvCity=rowView.findViewById(R.id.tvCity);
        tvId.setText(String.format("_id: %s",users.get(pos).getId()));
        tvName.setText(String.format("fullName:%s", users.get(pos).getName()));
        tvEmail.setText(String.format("email:%s", users.get(pos).getEmail()));
        tvNumberPhone.setText(String.format("mobile:%s", users.get(pos).getPhoneNumber()));
        tvCity.setText(String.format("city:%s", users.get(pos).getCity()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,UserActivity.class);
                intent.putExtra("_id", String.valueOf(users.get(pos).getId()));
                intent.putExtra("fullName",String.valueOf(users.get(pos).getName()));
                intent.putExtra("email",String.valueOf(users.get(pos).getEmail()));
                intent.putExtra("mobile", String.valueOf(users.get(pos).getPhoneNumber()));
                intent.putExtra("city", String.valueOf(users.get(pos).getCity()));
                context.startActivity(intent);
            }
        });



    return  rowView;
    }
}
