package com.example.thuchanhv3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class Edit_Activity extends AppCompatActivity {
    EditText edId, edName, edContact, edEmail, edAddress,edClass, edAdmin_id, edUsername, edPassword;
    private PreferenceHelper preferenceHelper;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        preferenceHelper = new PreferenceHelper(this);

        edId = findViewById(R.id.ed_id);
        edName = findViewById(R.id.ed_name);
        edContact = findViewById(R.id.ed_contact);
        edPassword = findViewById(R.id.ed_password);
        edClass = findViewById(R.id.ed_class);
        edAddress = findViewById(R.id.ed_address);
        edUsername = findViewById(R.id.ed_username);
        edAdmin_id =findViewById(R.id.ed_adminid);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        edId.setText(MainActivity.employeeArrayList.get(position).getId());
        edName.setText(MainActivity.employeeArrayList.get(position).getName());
//        edEmail.setText(MainActivity.employeeArrayList.get(position).getEmail());
        edContact.setText(MainActivity.employeeArrayList.get(position).getContact());
        edAddress.setText(MainActivity.employeeArrayList.get(position).getAddress());
        edUsername.setText(MainActivity.employeeArrayList.get(position).getUser_name());
        edPassword.setText(MainActivity.employeeArrayList.get(position).getPassword());
        edClass.setText(MainActivity.employeeArrayList.get(position).getClassRoom());
        edAdmin_id.setText(MainActivity.employeeArrayList.get(position).getAdmin_id());


//        Log.i(TAG,preferenceHelper.getId().toString());
//        edId.setText(preferenceHelper.getId());
//        edName.setText(preferenceHelper.getName());
//        edClass.setText(preferenceHelper.getClass1());
//        edAddress.setText(preferenceHelper.getAddress());
//        edContact.setText(preferenceHelper.getContact());
//        edUsername.setText(preferenceHelper.getUsername());
//        edPassword.setText(preferenceHelper.getPassword());


    }
    public void btn_updateData(View view) {

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
                    public void onResponse(String response) {

                        Toast.makeText(Edit_Activity.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Edit_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Edit_Activity.this);
        requestQueue.add(request);





    }
}
