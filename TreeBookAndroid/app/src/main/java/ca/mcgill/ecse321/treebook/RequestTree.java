package ca.mcgill.ecse321.treebook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import cz.msebera.android.httpclient.Header;

public class RequestTree extends AppCompatActivity {

    private String error="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_tree);
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
    }

    //Request a tree
    public void requestTree(View v) {

        RequestParams rp = new RequestParams();

        //Set up username
        TextView tv = (TextView) findViewById(R.id.newuser_name);
        String username = tv.getText().toString();

        //Set up password
        tv = (TextView) findViewById(R.id.newuser_pass);
        String password = tv.getText().toString();

        //Set up longitude
        tv = (TextView) findViewById(R.id.newtree_longitude);
        String longitude = tv.getText().toString();

        //Set up latitude

        tv = (TextView) findViewById(R.id.newtree_latitude);
        String latitude = tv.getText().toString(); // THIS MIGHT BE WRONG

        //Fill in requestParameters.
        NumberFormat formatter = new DecimalFormat("00");
        rp.add("latitude",latitude);
        rp.add("longitude",longitude);
        rp.add("username",username);
        rp.add("password",password);

        //Call the POST
        HttpUtils.post("trees/requestTreeOnAndroid", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
             error += "Successfully cut a tree!";
             refreshErrorMessage();
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
