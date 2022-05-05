package com.example.thuchanhv3;

import static android.content.ContentValues.TAG;

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
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText etUname, etPass;
    private PreferenceHelper preferenceHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferenceHelper = new PreferenceHelper(this);
        etUname = findViewById(R.id.edtusername);
        etPass = findViewById(R.id.edtpassword);

        Button btnlogin = findViewById(R.id.btnlogin);
        TextView tvreg = findViewById(R.id.tvregister);

        tvreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        final String username = etUname.getText().toString().trim();
        final String password = etPass.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginInterface.LOGINURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        LoginInterface api = retrofit.create(LoginInterface.class);
        Call<String> call = api.getUserLogin(username,password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                if (response.isSuccessful()) {
                    if (response.body()!= null){
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        parseLoginData(jsonresponse);
                    } else{
                        Log.i("onEmptyResponse", "Returned empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    private void parseLoginData(String response) {

//        var id = preferenceHelper.getAdmin_ID().toString().trim();
//        String admin_id = preferenceHelper.getAdmin_ID().toString();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")){
                saveInfo(response);
                int admin_id = Integer.parseInt(preferenceHelper.getAdmin_ID());

                Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                Log.i(TAG,"hello" + String.valueOf(admin_id));
                if (admin_id == 1){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();
                }else{
                    Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();
                }

            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

    private void saveInfo(String response) {
        preferenceHelper.putIsLogin(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                Log.i(TAG,dataArray.toString());
                for (int i = 0; i < dataArray.length(); i++) {

                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.putID(dataobj.getString("id"));
                    preferenceHelper.putName(dataobj.getString("name"));
                    preferenceHelper.putClass(dataobj.getString("class"));
                    preferenceHelper.putAddress(dataobj.getString("address"));
                    preferenceHelper.putContact(dataobj.getString("contact"));
                    preferenceHelper.putUsername(dataobj.getString("user_name"));
                    preferenceHelper.putPassword(dataobj.getString("password"));
                    preferenceHelper.putAdmin_ID(dataobj.getString("admin_id"));
                }
            }
            Log.e(TAG,preferenceHelper.getId().toString());
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

    }
}
