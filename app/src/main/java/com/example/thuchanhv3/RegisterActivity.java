package com.example.thuchanhv3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private EditText etname, etclass, etaddress, etcontact, etusername, etpassword;
    private Button btnregister;
    private TextView tvlogin;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        preferenceHelper = new PreferenceHelper(this);
        if(preferenceHelper.getIsLogin()){
            Intent intent = new Intent(RegisterActivity.this,WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }
        etname = findViewById(R.id.etname);
        etclass = findViewById(R.id.etclass);
        etaddress = findViewById(R.id.etaddress);
        etcontact = findViewById(R.id.etcontact);
        etusername = findViewById(R.id.etusername);
        etpassword = findViewById(R.id.etpassword);

        btnregister = findViewById(R.id.btnregister);
        tvlogin = findViewById(R.id.tvlogin);
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerMe();
            }
        });
    }

    private void registerMe() {
        final String name = etname.getText().toString();
        final String classRoom = etclass.getText().toString();
        final String address = etaddress.getText().toString();
        final String contact = etcontact.getText().toString();
        final String username = etusername.getText().toString();
        final String password = etpassword.getText().toString();
        final int admin_id = 0;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegisterInterface.REGIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RegisterInterface api = retrofit.create(RegisterInterface.class);

        Call<String> call = api.getUserRegi(name,classRoom,address,contact,username,password,admin_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        parseRegData(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void parseRegData(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        if (jsonObject.optString("status").equals("true")){

            saveInfo(response);

            Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this,WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }else {

            try {
                Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        }
    }

    private void saveInfo(String response) {
        preferenceHelper.putIsLogin(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {

                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.putName(dataobj.getString("name"));
                    preferenceHelper.putClass(dataobj.getString("class"));
                    preferenceHelper.putAddress(dataobj.getString("address"));
                    preferenceHelper.putContact(dataobj.getString("contact"));
                    preferenceHelper.putAdmin_ID(dataobj.getString("admin_id"));
                    preferenceHelper.putUsername(dataobj.getString("user_name"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
