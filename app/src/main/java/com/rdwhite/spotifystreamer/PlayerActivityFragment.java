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
public class PlayerActivityFragment extends Fragment {

    private final String LOG_TAG = PlayerActivityFragment.class.getSimpleName();

    private ArrayList<TrackSearchResult> trackSearchResultArrayList;
    private int transportIndex;

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
        bundle.putParcelableArrayList("playerActivityTracks", trackSearchResultArrayList);
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
        transportCurrentPositionTextView = (TextView) rootView.findViewById(R.id.player_transport_current_position);
        transportTrackLengthTextView = (TextView) rootView.findViewById(R.id.player_transport_track_length);
        previousImageButton = (ImageButton) rootView.findViewById(R.id.player_button_previous);
        playPauseImageButton = (ImageButton) rootView.findViewById(R.id.player_button_play);
        nextImageButton = (ImageButton) rootView.findViewById(R.id.player_button_next);

        Intent intent = getActivity().getIntent();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            trackSearchResultArrayList = savedInstanceState.getParcelableArrayList("playerActivityTracks");
        } else {
            trackSearchResultArrayList = new ArrayList<TrackSearchResult>();
        }
        // TODO: Continue here....
        Intent intent = getActivity().getIntent();
        if (savedInstanceState == null && intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String spotifyId = intent.getStringExtra(Intent.EXTRA_TEXT);
            FetchTrackResultTask fetchTrackResultTask = new FetchTrackResultTask();
            fetchTrackResultTask.execute(spotifyId);
        }
    }

    public ArrayList<TrackSearchResult> getTrackSearchResultArrayList() {
        return trackSearchResultArrayList;
    }

    public void setTrackSearchResultArrayList(ArrayList<TrackSearchResult> trackSearchResultArrayList) {
        this.trackSearchResultArrayList = trackSearchResultArrayList;
    }

    public int getTransportIndex() {
        return transportIndex;
    }

    public void setTransportIndex(int transportIndex) {
        this.transportIndex = transportIndex;
    }

    public TextView getArtistNameTextView() {
        return artistNameTextView;
    }

    public void setArtistNameTextView(TextView artistNameTextView) {
        this.artistNameTextView = artistNameTextView;
    }

    public TextView getAlbumTitleTextView() {
        return albumTitleTextView;
    }

    public void setAlbumTitleTextView(TextView albumTitleTextView) {
        this.albumTitleTextView = albumTitleTextView;
    }

    public ImageView getAlbumCoverImageView() {
        return albumCoverImageView;
    }

    public void setAlbumCoverImageView(ImageView albumCoverImageView) {
        this.albumCoverImageView = albumCoverImageView;
    }

    public TextView getTrackTitleTextView() {
        return trackTitleTextView;
    }

    public void setTrackTitleTextView(TextView trackTitleTextView) {
        this.trackTitleTextView = trackTitleTextView;
    }

    public SeekBar getTransportSeekBar() {
        return transportSeekBar;
    }

    public void setTransportSeekBar(SeekBar transportSeekBar) {
        this.transportSeekBar = transportSeekBar;
    }

    public TextView getTransportCurrentPositionTextView() {
        return transportCurrentPositionTextView;
    }

    public void setTransportCurrentPositionTextView(TextView transportCurrentPositionTextView) {
        this.transportCurrentPositionTextView = transportCurrentPositionTextView;
    }

    public TextView getTransportTrackLengthTextView() {
        return transportTrackLengthTextView;
    }

    public void setTransportTrackLengthTextView(TextView transportTrackLengthTextView) {
        this.transportTrackLengthTextView = transportTrackLengthTextView;
    }

    public ImageButton getPreviousImageButton() {
        return previousImageButton;
    }

    public void setPreviousImageButton(ImageButton previousImageButton) {
        this.previousImageButton = previousImageButton;
    }

    public ImageButton getPlayPauseImageButton() {
        return playPauseImageButton;
    }

    public void setPlayPauseImageButton(ImageButton playPauseImageButton) {
        this.playPauseImageButton = playPauseImageButton;
    }

    public ImageButton getNextImageButton() {
        return nextImageButton;
    }

    public void setNextImageButton(ImageButton nextImageButton) {
        this.nextImageButton = nextImageButton;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }


}
