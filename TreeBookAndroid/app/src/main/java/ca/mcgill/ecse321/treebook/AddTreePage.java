package ca.mcgill.ecse321.treebook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import cz.msebera.android.httpclient.Header;

public class AddTreePage extends AppCompatActivity {

    private String error = null;
    private String[] statusEnums = new String[]{"Planted", "Diseased", "ToBeCutDown","CutDown"};
    private ArrayAdapter<String> statusAdapter;

    private String[] landUseEnums = new String[]{"Residential", "Insitutional", "Park","Municipal"};
    private ArrayAdapter<String> landUseAdapter;

    private String[] toBeChoppedList = new String[]{"false","true"};
    private ArrayAdapter<String> toBeChoppedAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tree_page);
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

        //Initialize Status Dropdown menu.
        Spinner statusSpinner = (Spinner) findViewById(R.id.statusspinner);
        statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusEnums);
        statusSpinner.setAdapter(statusAdapter);

        //Initialize LandUse Dropdown menu.
        Spinner landUseSpinner = (Spinner) findViewById(R.id.landusespinner);
        landUseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, landUseEnums);
        landUseSpinner.setAdapter(landUseAdapter);

        //Initialize toBeChopped menu
        Spinner toBeChoppedSpinner = (Spinner) findViewById(R.id.tobechoppedspinner);
        toBeChoppedAdapter = new ArrayAdapter<> (this, android.R.layout.simple_spinner_dropdown_item, toBeChoppedList);
        toBeChoppedSpinner.setAdapter(toBeChoppedAdapter);

        //refreshErrorMessage();
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

    //Add a tree
    public void addTree(View v) {

        RequestParams rp = new RequestParams();

        //Set up username
        TextView tv = (TextView) findViewById(R.id.newuser_name);
        String username = tv.getText().toString();

        //Set up password
        tv = (TextView) findViewById(R.id.newuser_pass);
        String password = tv.getText().toString();

        //Set up string species
        tv = (TextView) findViewById(R.id.newtree_species);
        String species = tv.getText().toString();

        //Set up boolean toBeChopped

        Spinner tobechoppedspinner = (Spinner) findViewById(R.id.tobechoppedspinner);
        String toBeChopped = tobechoppedspinner.getSelectedItem().toString();

        //Set up stringStatus

        Spinner statusspinner = (Spinner) findViewById(R.id.statusspinner);
        String stringStatus = statusspinner.getSelectedItem().toString();

        //Set up int height
        tv = (TextView) findViewById(R.id.newtree_height);
        String height = tv.getText().toString(); // THIS MIGHT BE WRONG

        //Set up int diameterOfCanopy
        tv = (TextView) findViewById(R.id.newtree_diameterOfCanopy);
        String diameterOfCanopy = tv.getText().toString(); // THIS MIGHT BE WRONG

        //Set up string municipality
        tv = (TextView) findViewById(R.id.newtree_municipality);
        String municipality = tv.getText().toString();

        //Set up string picOfTree
        tv = (TextView) findViewById(R.id.newtree_diameterOfTrunk);
        String diameterOfTrunk = tv.getText().toString();

        //Set up string stringLandUse
        Spinner landusespinner = (Spinner) findViewById(R.id.landusespinner);
        String stringLandUse = landusespinner.getSelectedItem().toString();

        //Set up date whenPlanted
        tv = (TextView) findViewById(R.id.newtree_whenPlanted);
        String text = tv.getText().toString();
        String[] comps = text.split("-");

        int yearWhenPlanted = Integer.parseInt(comps[2]);
        int monthWhenPlanted = Integer.parseInt(comps[1]);
        int dayWhenPlanted = Integer.parseInt(comps[0]);

        //set up int reports
        String reports = "0";

        //Set up date whenCutDown
        tv = (TextView) findViewById(R.id.newtree_whenPlanted);
        text = tv.getText().toString();
        comps = text.split("-");

        int yearWhenCutDown = Integer.parseInt(comps[2]);
        int monthWhenCutDown = Integer.parseInt(comps[1]);
        int dayWhenCutDown = Integer.parseInt(comps[0]);

        //Set up lastSurvey (make it today!!(It will get defaulted in plant tree.)
        tv = (TextView) findViewById(R.id.newtree_whenPlanted);
        text = tv.getText().toString();
        comps = text.split("-");

        int yearLastSurvey = Integer.parseInt(comps[2]);
        int monthLastSurvey = Integer.parseInt(comps[1]);
        int dayLastSurvey = Integer.parseInt(comps[0]);

        //Set up int longitude

        tv = (TextView) findViewById(R.id.newtree_longitude);
        String longitude = tv.getText().toString();

        //Set up int latitude

        tv = (TextView) findViewById(R.id.newtree_latitude);
        String latitude = tv.getText().toString();

        //Set up int monetaryValue // default it to 0
        String monetaryValue = "0";

        //Set up int BiodiversityIndex  defaulted to 0 since will be calculated.
        String BiodiversityIndex = "0";

        //Fill in requestParameters.
        NumberFormat formatter = new DecimalFormat("00");


        rp.add("species", species);
        rp.add("toBeChopped",toBeChopped);
        rp.add("stringStatus",stringStatus);
        rp.add("height",height);
        rp.add("diameterOfCanopy",diameterOfCanopy);
        rp.add("municipality",municipality);
        rp.add("diameterOfTrunk",diameterOfTrunk);
        rp.add("stringLandUse",stringLandUse);
        rp.add("whenPlanted", yearWhenPlanted + "-" + formatter.format(monthWhenPlanted) + "-" + formatter.format(dayWhenPlanted));
        rp.add("reports",reports);
        rp.add("whenCutDown", yearWhenCutDown + "-" + formatter.format(monthWhenCutDown) + "-" + formatter.format(dayWhenCutDown));
        rp.add("lastSurvey", yearLastSurvey + "-" + formatter.format(monthLastSurvey) + "-" + formatter.format(dayLastSurvey));
        rp.add("longitude",longitude);
        rp.add("latitude",latitude);
        rp.add("username",username);
        rp.add("password",password);
        rp.add("monetaryValue",monetaryValue);
        rp.add("BiodiversityIndex",BiodiversityIndex);

        //Call the POST
        HttpUtils.post("trees/newTreeOnAndroid", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                error="Successfully planted a tree!";
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
    private Bundle getTimeFromLabel(String text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split(":");
        int hour = 12;
        int minute = 0;

        if (comps.length == 2) {
            hour = Integer.parseInt(comps[0]);
            minute = Integer.parseInt(comps[1]);
        }

        rtn.putInt("hour", hour);
        rtn.putInt("minute", minute);

        return rtn;
    }

    private Bundle getDateFromLabel(String text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;

        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month-1);
        rtn.putInt("year", year);

        return rtn;
    }

    public void showTimePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getTimeFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());

        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setTime(int id, int h, int m) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d:%02d", h, m));
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
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
