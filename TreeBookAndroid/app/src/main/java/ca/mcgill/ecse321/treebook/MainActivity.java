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

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    //Define your views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    //Brings us to AddTreePage!
    public void goCreateUserPage(View v) {
        startActivity(new Intent(this, CreateUserPage.class));
    }

    //Brings us to the edit tree page!
    public void goEditTreePage(View v) {startActivity(new Intent(this, EditTreeActivity.class));}

    /*
    //Brings us to the login page!
    public void goLoginPage(View v) { startActivity(new Intent( this, LoginActivity.class));}
*/
    
    //Brings us to AddTreePage!
    public void goAddTreePage(View v) {
        startActivity(new Intent(this, AddTreePage.class));
    }

    //Brings us to CutTree!
    public void goCutTreePage (View v) {
        startActivity(new Intent(this, CutTreeActivity.class));
    }

    //Brings us to ViewTreesPage!
/*    public void goViewTreesPage (View v) {
        startActivity(new Intent(this, ViewTreesPage.class));
    }
*/
    //Brings us to Request Tree!
    public void goRequestTree (View v) {
        startActivity(new Intent(this, RequestTree.class));
    }


}