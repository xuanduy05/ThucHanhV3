package com.example.thuchanhv3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Add_Data_Activity extends AppCompatActivity {
//    EditText txtName, txtEmail, txtContact, txtAddress;
    TextInputEditText txtName,txtContact, txtAddress,txtClass,txtUser_name,txtPassword,txtAdmin_id;
//    InputType txtName,txtContact, txtAddress;
    Button btn_insert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        txtName = findViewById(R.id.edtname);

//        txtName = findViewById(R.id.edtname);
//        txtEmail = findViewById(R.id.edtemail);
        txtClass = findViewById(R.id.edtclass);
        txtUser_name = findViewById(R.id.edtusername);
        txtContact = findViewById(R.id.edtcontact);
        txtAddress = findViewById(R.id.edtaddress);
        txtPassword = findViewById(R.id.edtpassword);
        txtAdmin_id = findViewById(R.id.edtadminid);
        btn_insert = findViewById(R.id.btnInsert);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
    }

    private void insertData() {
        final String name = txtName.getText().toString().trim();
        final String classRoom = txtClass.getText().toString().trim();
//        final String email = txtEmail.getText().toString().trim();
        final String contact = txtContact.getText().toString().trim();
        final String address = txtAddress.getText().toString().trim();
        final String user_name = txtUser_name.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();
        final String admin_id = txtAdmin_id.getText().toString().trim();



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }
//        else if (email.isEmpty()) {
//            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
//            return;
//        }
        else if (contact.isEmpty()) {
            Toast.makeText(this, "Enter Contact", Toast.LENGTH_SHORT).show();
            return;
        } else if (address.isEmpty()) {
            Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.36:8081/app_android/insert.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("Data Inserted")) {
                                Toast.makeText(Add_Data_Activity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                // chuyen doi activity
                                Intent intent = new Intent(Add_Data_Activity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                Add_Data_Activity.this.finish();

                            } else {
                                Toast.makeText(Add_Data_Activity.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_Data_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("name", name);
                    params.put("class",classRoom);
//                    params.put("email", email);
                    params.put("contact", contact);
                    params.put("address", address);
                    params.put("user_name",user_name);
                    params.put("password",password);
                    params.put("admin_id",admin_id);


                    return params;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(Add_Data_Activity.this);
            requestQueue.add(request);


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
