package com.rdwhite.spotifystreamer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.SeekBar;

import com.rdwhite.spotifystreamer.objects.TrackSearchResult;

import java.io.IOException;
import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by 752632 on 8/24/2015.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener{

    // private static final String ACTION_PLAY = "com.rdwhite.spotifystreamer.action.PLAY";
    private static final String LOG_TAG = MusicService.class.getSimpleName();
    private static final int TRACK_COMPLETED = 0;

    public static final String PLAY = "com.rdwhite.spotifystreamer.PLAY";
    public static final String PAUSE = "com.rdwhite.spotifystreamer.PAUSE";
    public static final String NEXT = "com.rdwhite.spotifystreamer.NEXT";
    public static final String PREVIOUS = "com.rdwhite.spotifystreamer.PREVIOUS";
    public static final String RESUME = "com.rdwhite.spotifystreamer.RESUME";
    public static final String TRANSPORT = "com.rdwhite.spotifystreamer.TRANSPORT";
    public static final String TRANSPORT_POSITION = "TRANSPORT_POSITION";



    private MediaPlayer mediaPlayer = null;
    private ResultReceiver resultReceiver = null;
    private final IBinder musicBinder = new MusicIBinder();
    private int trackIndex;
    private ArrayList<TrackSearchResult> trackSearchResultArrayList;
    private boolean playing = false;


    private ArrayList<TrackSearchResult> trackSearchResultArrayList;

    @Override
    public int onStartCommand(Intent intent, int flags, int trackIndex) {
        String action = intent.getAction();

        switch (action) {
            case PLAY: play(intent);
                break;
            case PAUSE: pause();
                break;
            case NEXT:
                break;
            case PREVIOUS:
                break;
            case RESUME: resume();
                break;
        }
    }

    public void play(Context context, int transportPosition) {
        playing = false;
        Intent intent = new Intent(context, MusicService.class);
        intent.setAction(PLAY);
        intent.putExtra(TRANSPORT_POSITION, transportPosition);
        context.startService(intent);
    }

    public void play(Intent intent) {
        stop();

        trackIndex = intent.getIntExtra(TRANSPORT_POSITION, 0);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        try {
            mediaPlayer.setDataSource(trackSearchResultArrayList.get(trackIndex).getPreviewUrl());
            mediaPlayer.prepareAsync();
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "IO Exception on play.");
        }
    }

    public void stop() {
        if(mediaPlayer != null) {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.setOnPreparedListener(null);
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void pause() {
        if(mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }
    }

    public void resume() {
        if(mediaPlayer != null) {
            if(!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        trackIndex = 0;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(playing) {
            resultReceiver.send(TRACK_COMPLETED, null);
            playing = false;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        mediaPlayer.seekTo(completed);
        completed = 0;
        playing = true;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(LOG_TAG, "Music Service has encountered an error");
        mediaPlayer.reset();
        return false;
    }

    @Override
    public void onDestroy() {
        if(mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public int getTrackIndex() {
        return trackIndex;
    }

    public void setTrackIndex(int trackIndex) {
        this.trackIndex = trackIndex;
    }

    public ArrayList<TrackSearchResult> getTrackSearchResultArrayList() {
        return trackSearchResultArrayList;
    }

    public ResultReceiver getResultReceiver() {
        return resultReceiver;
    }

    public void setResultReceiver(ResultReceiver resultReceiver) {
        this.resultReceiver = resultReceiver;
    }

    public void setTrackSearchResultArrayList(ArrayList<TrackSearchResult> trackSearchResultArrayList) {
        this.trackSearchResultArrayList = trackSearchResultArrayList;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getPausedIndex() {
        return pausedIndex;
    }

    public void setPausedIndex(int pausedIndex) {
        this.pausedIndex = pausedIndex;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public class MusicIBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public void seek(int milli) {
        mediaPlayer.seekTo(milli);
    }
}
