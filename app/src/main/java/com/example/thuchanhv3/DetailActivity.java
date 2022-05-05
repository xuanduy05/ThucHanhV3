package com.example.thuchanhv3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    TextView tvid,tvname,tvemail,tvcontact,tvaddress,tvuser_name,tvpassword,tvclass,tvadmin_id;
    int position;
    String admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvid = findViewById(R.id.txtid);
        tvname = findViewById(R.id.txtname);
        tvuser_name = findViewById(R.id.txtuser_name);
        tvclass = findViewById(R.id.txtclass);
//        tvemail = findViewById(R.id.txtemail);
        tvcontact = findViewById(R.id.txtcontact);
        tvaddress = findViewById(R.id.txtaddress);
        tvpassword = findViewById(R.id.txtpassword);
        tvadmin_id = findViewById(R.id.txtadmin_id);

        if (MainActivity.employeeArrayList.get(position).getAdmin_id().equals("1")){
            admin = "true";
        }else
        {
            admin = "false";
        }

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        tvid.setText("ID: "+MainActivity.employeeArrayList.get(position).getId());
        tvname.setText("Name: "+MainActivity.employeeArrayList.get(position).getName());
//        tvemail.setText("Email: "+MainActivity.employeeArrayList.get(position).getEmail());
        tvuser_name.setText("User Name: "+MainActivity.employeeArrayList.get(position).getUser_name());
        tvclass.setText("Class: "+MainActivity.employeeArrayList.get(position).getClassRoom());
        tvcontact.setText("Contact: "+MainActivity.employeeArrayList.get(position).getContact());
        tvaddress.setText("Address: "+MainActivity.employeeArrayList.get(position).getAddress());
        tvpassword.setText("Password: "+MainActivity.employeeArrayList.get(position).getPassword());
        tvadmin_id.setText("Admin: "+admin);
    }
}
