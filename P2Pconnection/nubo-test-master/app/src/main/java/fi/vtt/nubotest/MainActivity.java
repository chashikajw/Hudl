/*
 * (C) Copyright 2016 VTT (http://www.vtt.fi)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package fi.vtt.nubotest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import fi.vtt.nubomedia.kurentoroomclientandroid.KurentoRoomAPI;
import fi.vtt.nubomedia.kurentoroomclientandroid.RoomError;
import fi.vtt.nubomedia.kurentoroomclientandroid.RoomListener;
import fi.vtt.nubomedia.kurentoroomclientandroid.RoomNotification;
import fi.vtt.nubomedia.kurentoroomclientandroid.RoomResponse;
import fi.vtt.nubomedia.utilitiesandroid.LooperExecutor;
import fi.vtt.nubotest.util.Constants;

public class MainActivity extends Activity implements RoomListener {
    private String username, roomname;
    private String TAG = "MainActivity";
    private LooperExecutor executor;
    private static KurentoRoomAPI kurentoRoomAPI;
    private int roomId=0;
    private EditText mTextMessageET;
    private TextView mUsernameTV, mTextMessageTV;
    private String wsUri;
    public static Map<String, Boolean> userPublishList = new HashMap<>();
    Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();

        setContentView(R.layout.activity_main);
        this.mUsernameTV = (TextView) findViewById(R.id.main_username);
        this.mTextMessageTV = (TextView) findViewById(R.id.message_textview);
        this.mTextMessageET = (EditText) findViewById(R.id.main_text_message);
        this.mTextMessageTV.setText("");
        executor = new LooperExecutor();
        executor.requestStart();
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Constants.SERVER_ADDRESS_SET_BY_USER = mSharedPreferences.getString(Constants.SERVER_NAME, Constants.DEFAULT_SERVER);
        wsUri = mSharedPreferences.getString(Constants.SERVER_NAME, Constants.DEFAULT_SERVER);
        //wsUri = "https://192.168.1.5:8443";

        kurentoRoomAPI = new KurentoRoomAPI(executor, wsUri, this);
        mHandler = new Handler();

        this.username     = mSharedPreferences.getString(Constants.USER_NAME, "");
        this.roomname     = mSharedPreferences.getString(Constants.ROOM_NAME, "");
        Log.i(TAG, "username: "+this.username);
        Log.i(TAG, "roomname: "+this.roomname);

        // Load test certificate from assets
        CertificateFactory cf;
        try {
            cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = new BufferedInputStream(context.getAssets().open("kurento_room_base64.cer"));
            Certificate ca = cf.generateCertificate(caInput);
            kurentoRoomAPI.addTrustedCertificate("ca", ca);

        } catch (CertificateException|IOException e) {
            e.printStackTrace();

        }
        kurentoRoomAPI.useSelfSignedCertificate(true);

        if (kurentoRoomAPI.isWebSocketConnected()){
            mTextMessageTV.setText("connected");

        }
        else{
            //mTextMessageTV.setText("not connected");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!kurentoRoomAPI.isWebSocketConnected()) {
            this.mUsernameTV.setText(getString(R.string.room_connecting, username, roomname));
            Log.i(TAG, "Connecting to room at " + wsUri);
            kurentoRoomAPI.connectWebSocket();
        }
    }

    private void showFinishingError(String title, String message) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { finish(); }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        if (kurentoRoomAPI.isWebSocketConnected()) {
            kurentoRoomAPI.sendLeaveRoom(roomId);
            kurentoRoomAPI.disconnectWebSocket();
        }
        executor.requestStop();
        super.onDestroy();
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
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            kurentoRoomAPI.sendLeaveRoom(roomId);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void joinRoom () {
        Constants.id++;
        roomId = Constants.id;
        Log.i(TAG, "Joinroom: User: "+this.username+", Room: "+this.roomname+" id:"+roomId);
        if (kurentoRoomAPI.isWebSocketConnected()) {
            kurentoRoomAPI.sendJoinRoom(this.username, this.roomname, true, roomId);
        }
    }

    private Runnable clearMessageView = new Runnable() {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextMessageTV.setText("");
                }
            });
        }
    };

    /**
     * Take the user to a video screen. USER_NAME is a required field.
     * @param view button that is clicked to trigger toVideo
     */
    public void makeCall(View view){
        Intent intent = new Intent(MainActivity.this, PeerVideoActivity.class);
        intent.putExtra(Constants.USER_NAME, username);
        startActivity(intent);
    }

    public void showToast(String string) {
        try {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, string, duration);
            toast.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    public void sendTextMessage(View view){
        String message=mTextMessageET.getText().toString();
        if(message.length()>0) {
            Log.d("SendMessage: ", this.roomname + ", " + this.username + ", " + message);
            mTextMessageET.setText("");
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            if(kurentoRoomAPI.isWebSocketConnected()){
                Log.i(TAG, "sendMessage");
                kurentoRoomAPI.sendMessage(this.roomname, this.username, message, Constants.id++);
            }
        }
        else {
            showToast("There is no message!");
        }
    }*/

    private void logAndToast(String message) {
        Log.i(TAG, message);
        showToast(message);
    }

    @Override
    public void onRoomResponse(RoomResponse response) {
        // joinRoom response
        if (response.getMethod()==KurentoRoomAPI.Method.JOIN_ROOM) {
            userPublishList = new HashMap<>(response.getUsers());
        }
    }

    @Override
    public void onRoomError(RoomError error) {
        Log.wtf(TAG, error.toString());
        if(error.getCode() == 104) {
            showFinishingError("Room error", "Username already taken");
        }
    }

    @Override
    public void onRoomNotification(RoomNotification notification) {
        Log.i(TAG, notification.toString());
        Map<String, Object> map = notification.getParams();

        // Somebody wrote a message to other users in the room
        if(notification.getMethod().equals(RoomListener.METHOD_SEND_MESSAGE)) {
            final String user = map.get("user").toString();
            final String message = map.get("message").toString();
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextMessageTV.setText(getString(R.string.room_text_message, user, message));
                    mHandler.removeCallbacks(clearMessageView);
                    mHandler.postDelayed(clearMessageView, 5000);
                }
            });
        }

        // Somebody left the room
        else if(notification.getMethod().equals(RoomListener.METHOD_PARTICIPANT_LEFT)) {
            final String user = map.get("name").toString();
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    mTextMessageTV.setText(getString(R.string.participant_left, user));
                    mHandler.removeCallbacks(clearMessageView);
                    mHandler.postDelayed(clearMessageView, 3000);
                }
            });
        }

        // Somebody joined the room
        else if(notification.getMethod().equals(RoomListener.METHOD_PARTICIPANT_JOINED)) {
            final String user = map.get("id").toString();
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextMessageTV.setText(getString(R.string.participant_joined, user));
                    mHandler.removeCallbacks(clearMessageView);
                    mHandler.postDelayed(clearMessageView, 3000);
                }
            });
        }

        // Somebody in the room published their video
        else if(notification.getMethod().equals(RoomListener.METHOD_PARTICIPANT_PUBLISHED)) {
            final String user = map.get("id").toString();
            userPublishList.put(user, true);
            Log.i(TAG, "I'm " + username + " DERP: Other peer published already:" + notification.toString());
        }

    }

    @Override
    public void onRoomConnected() {
        if (kurentoRoomAPI.isWebSocketConnected()) {
            joinRoom();
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mUsernameTV.setText(getString(R.string.room_title, username, roomname));
                }
            });
        }
    }

    @Override
    public void onRoomDisconnected() {
        showFinishingError("Disconnected", "You have been disconnected from room.");
    }

    public static KurentoRoomAPI getKurentoRoomAPIInstance(){
        return kurentoRoomAPI;
    }
}
