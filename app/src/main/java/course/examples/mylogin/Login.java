package course.examples.mylogin;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etusername);
        final EditText etPassword = (EditText) findViewById(R.id.etpassword);
        final Button bLogin = (Button) findViewById(R.id.lbutton);
        final TextView registerLink = (TextView) findViewById(R.id.textView);


        registerLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                Intent registerIntent = new Intent(Login.this, RegisterActivity.class);
                Login.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v) {
                   final String username = etUsername.getText().toString();
                   final String password = etPassword.getText().toString();
                   Response.Listener<String> responseListener = new Response.Listener<String>(){
                       public void onResponse(String response){
                           try{
                               JSONObject jsonResponse = new JSONObject(response);
                               boolean success = jsonResponse.getBoolean("success");
                               if(success){
                                   String name = jsonResponse.getString("name");
                                   int age = jsonResponse.getInt("age");

                                   Intent intent = new Intent(Login.this, user.class);
                                   intent.putExtra("name", name);
                                   intent.putExtra("username", username);
                                   intent.putExtra("age", age);
                                   Login.this.startActivity(intent);

                               }else{
                                   AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                   builder.setMessage("Login Failed")
                                           .setNegativeButton("Retry", null)
                                           .create()
                                           .show();
                                   }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
                   };

                   LoginRequest loginRequest = new LoginRequest(username, password,responseListener);
                   RequestQueue queue = Volley.newRequestQueue(Login.this);
                   queue.add(loginRequest);
               }
        }
        );
    }
}
