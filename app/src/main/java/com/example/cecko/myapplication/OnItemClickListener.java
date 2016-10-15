package com.example.cecko.myapplication;

/**
 * Created by CECKO on 15.10.2016 г..
 */

interface OnItemClickListener {

    void onClickStartSong(String songName);

    void onPlay(String songName);

    void onPause(String songName);

    void onFastForward(String songName);

    void onFastBackward(String songName);
}
