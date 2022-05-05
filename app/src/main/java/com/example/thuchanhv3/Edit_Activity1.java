package com.example.thuchanhv3;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Edit_Activity1 extends AppCompatActivity {
    EditText edId, edName, edContact, edEmail, edAddress,edClass, edAdmin_id, edUsername, edPassword;
    private PreferenceHelper preferenceHelper;
    LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit1);
        preferenceHelper = new PreferenceHelper(this);

        edId = findViewById(R.id.ed_id1);
        edName = findViewById(R.id.ed_name1);
        edContact = findViewById(R.id.ed_contact1);
        edPassword = findViewById(R.id.ed_password2);
        edClass = findViewById(R.id.ed_class1);
        edAddress = findViewById(R.id.ed_address1);
        edUsername = findViewById(R.id.ed_username1);
        edAdmin_id = findViewById(R.id.ed_adminid1);

        Log.i(TAG,preferenceHelper.getId().toString());
        edId.setText(preferenceHelper.getId());
        edName.setText(preferenceHelper.getName());
        edClass.setText(preferenceHelper.getClass1());
        edAddress.setText(preferenceHelper.getAddress());
        edContact.setText(preferenceHelper.getContact());
        edUsername.setText(preferenceHelper.getUsername());
        edPassword.setText(preferenceHelper.getPassword());
        edAdmin_id.setText(preferenceHelper.getAdmin_ID());
    }

    public void btnUpdateData1(View view) {

        final String name = edName.getText().toString();
//        final String email = edEmail.getText().toString();
        final String contact = edContact.getText().toString();
        final String address = edAddress.getText().toString();
        final String id = edId.getText().toString();
        final String username = edUsername.getText().toString();
        final String Class  = edClass.getText().toString();
        final String password = edPassword.getText().toString();
        final String admin_id = edAdmin_id.getText().toString();





        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.36:8081/app_android/update.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String jsonresponse) {

                        Toast.makeText(Edit_Activity1.this, jsonresponse, Toast.LENGTH_SHORT).show();
                        Log.i(TAG,jsonresponse);
//                        saveInfo(jsonresponse);
//                        parseLoginData(jsonresponse);
                        loginUser();
                        startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Edit_Activity1.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i(TAG,error.getMessage());
                progressDialog.dismiss();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();

                params.put("id",id);
                params.put("name",name);
//                params.put("email",email);
                params.put("contact",contact);
                params.put("address",address);
                params.put("class",Class);
                params.put("user_name",username);
                params.put("password",password);
                params.put("admin_id",admin_id);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Edit_Activity1.this);
        requestQueue.add(request);





    }
    private void loginUser() {
//        final String username = etUname.getText().toString().trim();
//        final String password = etPass.getText().toString().trim();
        final String username = edUsername.getText().toString().trim();
        final String password = edPassword.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginInterface.LOGINURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        LoginInterface api = retrofit.create(LoginInterface.class);
        Call<String> call = api.getUserLogin(username,password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
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
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")){
                saveInfo(response);

//                Toast.makeText(Edit_Activity1.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Edit_Activity1.this,WelcomeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                this.finish();
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
