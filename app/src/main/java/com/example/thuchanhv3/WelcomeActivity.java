package com.example.thuchanhv3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    private TextView tvname,tvclass,tvaddress,tvcontact;
    private Button btnlogout,btnedit;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        preferenceHelper = new PreferenceHelper(this);
        tvclass = (TextView) findViewById(R.id.tvclass);
        tvname = (TextView) findViewById(R.id.tvname);
        tvaddress = findViewById(R.id.tvaddress);
        tvcontact = findViewById(R.id.tvcontact);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        btnedit = findViewById(R.id.btnedit);

        tvname.setText("Welcome "+preferenceHelper.getName());
        tvclass.setText("Your class is "+preferenceHelper.getClass1());
        tvaddress.setText("Your address is "+preferenceHelper.getAddress());
        tvcontact.setText("Your contact is "+preferenceHelper.getContact());
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.putIsLogin(false);
                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WelcomeActivity.this,Edit_Activity1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });
    }
}
