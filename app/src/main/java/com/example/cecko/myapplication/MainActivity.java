package com.example.cecko.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private ArrayList<SpotifyModel> mData;
    private ServiceConnection connection;
    private Intent spotifyServiceIntent;

    public static final String SONG_NAME = "song_name";
    public static final String ACTION = "action";
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_FAST_FORWARD = "action_fast_forward";
    public static final String ACTION_FAST_BACKWARD = "action_fast_backward";

    @Override
    public void onClickStartSong(String songName) {
        Toast.makeText(this, "onCustomerClick listener", Toast.LENGTH_SHORT).show();
        Log.d(this.getClass().getSimpleName(), String.valueOf(this.getResources().getIdentifier(songName, "raw", this
                .getPackageName())));
    }

    @Override
    public void onPlay(String songName) {
        onPlayerAction(ACTION_PLAY, songName);
    }

    @Override
    public void onPause(String songName) {
        onPlayerAction(ACTION_PAUSE, songName);
    }

    @Override
    public void onFastForward(String songName) {
        onPlayerAction(ACTION_FAST_FORWARD, songName);
    }

    @Override
    public void onFastBackward(String songName) {
        onPlayerAction(ACTION_FAST_BACKWARD, songName);
    }

    public void onPlayerAction(String action, String songName) {
        spotifyServiceIntent = new Intent(this, SpotifyService.class);
        spotifyServiceIntent.putExtra(SONG_NAME, songName);
        spotifyServiceIntent.putExtra(ACTION, action);
        bindService(spotifyServiceIntent, connection, Context.BIND_AUTO_CREATE);
        startService(spotifyServiceIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mData = fillData();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(false);

        RecyclerView.Adapter mAdapter = new RecycleViewAdapter(mData, this);
        mRecyclerView.setAdapter(mAdapter);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                SpotifyService.FirstServiceBinder serviceToOperate = (SpotifyService.FirstServiceBinder) service;
                serviceToOperate.getService().setServiceCallback(MainActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Toast.makeText(MainActivity.this, "Service is out of the game", Toast.LENGTH_SHORT).show();
            }
        };

        spotifyServiceIntent = new Intent(this, SpotifyService.class);
        bindService(spotifyServiceIntent, connection, Context.BIND_AUTO_CREATE);
        startService(spotifyServiceIntent);
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        stopService(spotifyServiceIntent);
        super.onDestroy();
    }

    private ArrayList<SpotifyModel> fillData() {
        mData = new ArrayList<>();
        int counter = 1;
        SpotifyModel model = new SpotifyModel(counter++, "Crazy", "Aerosmith - Crazy", "aerosmith_crazy");
        mData.add(model);

        model = new SpotifyModel(counter++, "Cryin", "Aerosmith - Cryin", "aerosmith_cryin");
        mData.add(model);

        model = new SpotifyModel(counter++, "I Dont Wanna Miss A Thing", "Aerosmith - I Dont Wanna Miss A Thing",
                "aerosmith_i_dont_wanna_miss_a_thing");
        mData.add(model);

        model = new SpotifyModel(counter++, "Forever Young", "Alphaville - Forever Young", "alphaville_forever_young");
        mData.add(model);

        model = new SpotifyModel(counter++, "Respect", "Aretha Franklin - Respect", "aretha_franklin_respect");
        mData.add(model);

        model = new SpotifyModel(counter++, "Back In Black", "AC/DC - Back In Black", "ac_dc_back_in_black");
        mData.add(model);

        model = new SpotifyModel(counter++, "Big Gun", "AC/DC - Big Gun", "ac_dc_big_gun");
        mData.add(model);

        model = new SpotifyModel(counter++, "Highway To Hell", "AC/DC - Highway To Hell", "ac_dc_highway_to_hell");
        mData.add(model);

        model = new SpotifyModel(counter++, "The Jack", "AC/DC - The Jack", "ac_dc_the_jack");
        mData.add(model);

        model = new SpotifyModel(counter, "Thunderstruck", "AC/DC - Thunderstruck", "ac_dc_thunderstruck");
        mData.add(model);
        return mData;
    }
}
