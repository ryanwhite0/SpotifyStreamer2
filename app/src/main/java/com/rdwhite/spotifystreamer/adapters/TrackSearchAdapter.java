package com.rdwhite.spotifystreamer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdwhite.spotifystreamer.R;
import com.rdwhite.spotifystreamer.Util;
import com.rdwhite.spotifystreamer.objects.TrackSearchResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 752632 on 7/13/2015.
 */
public class TrackSearchAdapter extends ArrayAdapter<TrackSearchResult>  {
    private static final String LOG_TAG = TrackSearchAdapter.class.getSimpleName();

    public TrackSearchAdapter(Activity context, List<TrackSearchResult> trackSearchResults) {
        super(context, 0, trackSearchResults);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        TrackSearchResult trackSearchResult = getItem(position);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_track, parent, false);

        if(!Util.isStringNullOrEmpty(trackSearchResult.getAlbumImageSmallUrl())) {
            Picasso.with(getContext()).load(trackSearchResult.getAlbumImageSmallUrl()).into(
                    (ImageView) rootView.findViewById(R.id.list_item_track_image_view));
        }

        TextView songTitle = (TextView) rootView.findViewById(R.id.list_item_track_song_title);
        songTitle.setText(trackSearchResult.getTrackTitle());

        TextView albumTitle = (TextView) rootView.findViewById(R.id.list_item_track_album_title);
        albumTitle.setText(trackSearchResult.getTrackAlbum());

        return rootView;
    }
}
