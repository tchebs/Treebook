package ca.mcgill.ecse321.treebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.EntityUtils;

public class LoginActivity extends AppCompatActivity {

    private String error = null;

    //Define your views
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        refreshErrorMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Add a user. I THINK ITS FINISHED.
    public void loginToAccount(View v) {
        error = "";

        final RequestParams rp = new RequestParams();

        //Set up username
        TextView tv = (TextView) findViewById(R.id.newuser_name);
        String name = tv.getText().toString();


        //Set up password
        tv = (TextView) findViewById(R.id.newuser_pass);
        String password = tv.getText().toString();

        rp.put("name",name);
        rp.put("password",password);

        HttpUtils.post("loginOnAndroid/", rp, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject loginSuccess) {


                try {
                    String loginSuccessful = loginSuccess.getString("loginSuccess");
                    if (loginSuccessful.equals("true"))
                        //TODO: Save cookie and move on to main activity!
                    ((TextView) findViewById(R.id.newuser_name)).setText(loginSuccessful);
                    else
                        ((TextView) findViewById(R.id.newuser_name)).setText("lolno!");
                    //
                   // refreshErrorMessage();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });

    }

    //Brings us to AddTreePage!
    public void goCreateUserPage(View v) {
        startActivity(new Intent(this, CreateUserPage.class));
    }


    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }


}