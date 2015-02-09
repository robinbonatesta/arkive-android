package com.robinbonatesta.arkive;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class CardsActivity extends ActionBarActivity {

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Session.restoreSession(this, null, null, bundle);
        session = Session.getActiveSession();

    }

    private ArrayList<String> getFriendIds(){

        new Request(

                session,
                "me/friends",
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    @Override
                    public void onCompleted(Response response) {

                        try{
                            GraphObject go = response.getGraphObject();
                            JSONObject jso = go.getInnerJSONObject();
                            JSONArray arr = jso.getJSONArray("data");

                            for(int i = 0; i < arr.length(); i++){

                                JSONObject json_obj = arr.getJSONObject(i);
                                String id = json_obj.getString("id");
                                Log.i("CARDS,", id);
                            }

                        }catch (Throwable t){

                            t.printStackTrace();

                        }

                    }
                }

        ).executeAsync();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cards, menu);
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
}
