package com.example.thuchanhv3;

//import android.app.AlertDialog;

//import android.app.DownloadManager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText etname, ethobby, etusername, etpassword;
    private Button btnregister;
    private TextView tvlogin;
    private FloatingActionButton fatlogout;

    ListView listView;
    MyAdapter adapter;
    private PreferenceHelper preferenceHelper;

    public static ArrayList<Employee> employeeArrayList = new ArrayList<>();
    String url = "http://192.168.43.36:8081/app_android/retrieve.php";
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceHelper = new PreferenceHelper(this);

        //list data
        listView = findViewById(R.id.myListView);
        fatlogout = findViewById(R.id.fatlogout);
        adapter = new MyAdapter(this, employeeArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                CharSequence[] dialogItem = {"View Data","Edit Data","Delete Data"};
                builder.setTitle(employeeArrayList.get(position).getName());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtra("position",position));
                                break;

                            case 1:
                                startActivity(new Intent(getApplicationContext(),Edit_Activity.class).putExtra("position",position));
                                break;

                            case 2:

                                deleteData(employeeArrayList.get(position).getId());

                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        retrieveData();
        //end list data

        //
//        preferenceHelper = new PreferenceHelper(this);
//        if(preferenceHelper.getIsLogin()){
//            Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            this.finish();
//        }
//        etname = findViewById(R.id.etname);
//        ethobby = findViewById(R.id.ethobby);
//        etusername = findViewById(R.id.etusername);
//        etpassword = findViewById(R.id.etpassword);
//
//        btnregister = findViewById(R.id.btnregister);
//        tvlogin = findViewById(R.id.tvlogin);
//        tvlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//                startActivity(intent);
//                MainActivity.this.finish();
//            }
//        });
//        btnregister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                registerMe();
//            }
//        });
        fatlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceHelper.putIsLogin(false);
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

    private void deleteData(String id) {
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.36:8081/app_android/delete.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("Data Deleted")){
                            Toast.makeText(MainActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                            retrieveData();

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void retrieveData(){

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        employeeArrayList.clear();
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(sucess.equals("1")){


                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String name = object.getString("name");
//                                    String email = object.getString("email");
                                    String contact = object.getString("contact");
                                    String address = object.getString("address");
                                    String Class = object.getString("class");
                                    String username = object.getString("username");
                                    String password = object.getString("password");
                                    String admin_id = object.getString("admin_id");

                                    employee = new Employee(id,name,contact,address,username,password,Class,admin_id);
                                    employeeArrayList.add(employee);
                                    adapter.notifyDataSetChanged();



                                }



                            }




                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }






                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);




    }
    public void btn_add_activity(View view){
        startActivity(new Intent(getApplicationContext(),Add_Data_Activity.class));
    }
    public void btnOutput(View view){
        preferenceHelper.putIsLogin(false);
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        MainActivity.this.finish();
//        startActivity(new Intent(getApplicationContext(),Add_Data_Activity.class));
    }
//    private void registerMe() {
//        final String name = etname.getText().toString();
//        final String hobby = ethobby.getText().toString();
//        final String username = etusername.getText().toString();
//        final String password = etpassword.getText().toString();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(RegisterInterface.REGIURL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .build();
//
//        RegisterInterface api = retrofit.create(RegisterInterface.class);
//
//        Call<String> call = api.getUserRegi(name,hobby,username,password);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
//                Log.i("Responsestring", response.body().toString());
//                //Toast.makeText()
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        Log.i("onSuccess", response.body().toString());
//
//                        String jsonresponse = response.body().toString();
//                        parseRegData(jsonresponse);
//
//                    } else {
//                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void parseRegData(String response) {
//        JSONObject jsonObject = new JSONObject();
//        if (jsonObject.optString("status").equals("true")){
//
//            saveInfo(response);
//
//            Toast.makeText(MainActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            this.finish();
//        }else {
//
//            try {
//                Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//            } catch (JSONException jsonException) {
//                jsonException.printStackTrace();
//            }
//        }
//    }
//
//    private void saveInfo(String response) {
//        preferenceHelper.putIsLogin(true);
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            if (jsonObject.getString("status").equals("true")) {
//                JSONArray dataArray = jsonObject.getJSONArray("data");
//                for (int i = 0; i < dataArray.length(); i++) {
//
//                    JSONObject dataobj = dataArray.getJSONObject(i);
//                    preferenceHelper.putName(dataobj.getString("name"));
//                    preferenceHelper.putHobby(dataobj.getString("hobby"));
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}