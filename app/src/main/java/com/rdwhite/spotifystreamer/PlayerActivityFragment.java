package com.rdwhite.spotifystreamer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rdwhite.spotifystreamer.objects.TrackSearchResult;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerActivityFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    private final String LOG_TAG = PlayerActivityFragment.class.getSimpleName();

    private ArrayList<TrackSearchResult> trackSearchResultArrayList;
    private int trackIndex;

    private TextView artistNameTextView;
    private TextView albumTitleTextView;
    private ImageView albumCoverImageView;
    private TextView trackTitleTextView;
    private SeekBar transportSeekBar;
    private TextView transportCurrentPositionTextView;
    private TextView transportTrackLengthTextView;
    private ImageButton previousImageButton;
    private ImageButton playPauseImageButton;
    private ImageButton nextImageButton;

    private MediaPlayer mediaPlayer;

    public PlayerActivityFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList(Constants.PLAYER_TRACKS_LIST, trackSearchResultArrayList);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player, container, false);

        artistNameTextView = (TextView) rootView.findViewById(R.id.player_artist_name);
        albumTitleTextView = (TextView) rootView.findViewById(R.id.player_album_title);
        albumCoverImageView = (ImageView) rootView.findViewById(R.id.player_album_cover);
        trackTitleTextView = (TextView) rootView.findViewById(R.id.player_track_title);
        transportSeekBar = (SeekBar) rootView.findViewById(R.id.player_transport);
        transportSeekBar.setOnClickListener(this);
        transportCurrentPositionTextView = (TextView) rootView.findViewById(R.id.player_transport_current_position);
        transportTrackLengthTextView = (TextView) rootView.findViewById(R.id.player_transport_track_length);
        previousImageButton = (ImageButton) rootView.findViewById(R.id.player_button_previous);
        previousImageButton.setOnClickListener(this);
        playPauseImageButton = (ImageButton) rootView.findViewById(R.id.player_button_play);
        playPauseImageButton.setOnClickListener(this);
        nextImageButton = (ImageButton) rootView.findViewById(R.id.player_button_next);
        nextImageButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            trackSearchResultArrayList = savedInstanceState.getParcelableArrayList(Constants.PLAYER_TRACKS_LIST);
        } else {
            trackSearchResultArrayList = new ArrayList<TrackSearchResult>();
        }

        Intent intent = getActivity().getIntent();
        if (savedInstanceState == null && intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            trackIndex = Integer.parseInt(intent.getStringExtra(Intent.EXTRA_TEXT));
            Bundle bundle = intent.getExtras();
            trackSearchResultArrayList = bundle.getParcelableArrayList(Constants.PLAYER_TRACKS_LIST);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.player_button_play:
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    // TODO: Change Button Image
                    //playPauseImageButton.setImageResource(R.drawable.btn_play);
                }
                else {
                    mediaPlayer.start();
                    // TODO: Change Button Image
                }
                break;
            case R.id.player_button_next:
                if(trackIndex + 1 >= trackSearchResultArrayList.size()) {
                    trackIndex = 0;
                }
                else {
                    trackIndex++;
                }
                // TODO: Update GUI, Play track.
                break;
            case R.id.player_button_previous:
                if(trackIndex == 0) {
                    trackIndex = trackSearchResultArrayList.size() - 1;
                }
                else {
                    trackIndex--;
                }
                // TODO: Update GUI, Play Track.
                break;


        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
