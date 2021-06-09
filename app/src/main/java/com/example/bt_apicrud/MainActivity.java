package com.example.bt_apicrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtName, txtEmail, txtAge;
    private RadioButton rbtnMale, rbtnFemale;
    private Button btnSave;
    private List<Student> students = new ArrayList<>();


    private String url = "https://60afb2eee6d11e00174f4e37.mockapi.io/student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtAge = findViewById(R.id.txtAge);
        rbtnMale = findViewById(R.id.rbtnMale);
        rbtnFemale = findViewById(R.id.rbtnFemale);
        btnSave = findViewById(R.id.btnSave);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GetArrayJson(url);
    }

    private void GetArrayJson(String url) {
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject o = (JSONObject) response.getJSONObject(i);
                                        Student student = new Student(o.getInt("id"), o.getString("fullname"), o.getString("email"), o.getString("gender"), o.getInt("age"));

                                        students.add(student);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                StudentAdapter adapter = new StudentAdapter(getApplicationContext(), students);
                                recyclerView.setAdapter(adapter);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by get Json Array!", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
