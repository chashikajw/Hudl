package hudlmo.interfaces.registerPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hudlmo.interfaces.loginpage.R;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.provider.CalendarContract;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.volley.AuthFailureError;
import android.volley.Request;
import android.volley.RequestQueue;
import android.volley.Response;
import android.volley.VolleyError;
import android.volley.toolbox.StringRequest;
import android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.emailTxt);
        username = (EditText) findViewById(R.id.usernamrTxt);
        password = (EditText) findViewById(R.id.passwordTxt);
        name = (EditText) findViewById(R.id.nameTxt);
    }

    private void registerUser() {
        String nameT = name.getText().toString().trim();
        String emailT = email.getText().toString().trim();
        String usernameT = username.getText().toString().trim();
        String passwordT = password.getText().toString().trim();

        progressDialog.setMessage("Registering user");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("email",email);
                params.put("password",password);
                return params;


            }
        };

        RequestHandelr.getInstance(this).addToRequestQueue(stringRequest);



    }


}
