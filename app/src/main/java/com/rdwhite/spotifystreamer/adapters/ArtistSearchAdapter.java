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
import com.rdwhite.spotifystreamer.objects.ArtistSearchResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 752632 on 7/7/2015.
 */
public class ArtistSearchAdapter extends ArrayAdapter<ArtistSearchResult> {
    private static final String LOG_TAG = ArtistSearchAdapter.class.getSimpleName();

    public ArtistSearchAdapter(Activity context, List<ArtistSearchResult> artistSearchResults) {
        super(context, 0, artistSearchResults);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        ArtistSearchResult artistSearchResult = getItem(position);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artist, parent, false);

        if(!Util.isStringNullOrEmpty(artistSearchResult.getArtistImageUrl())) {
            Picasso.with(getContext()).load(artistSearchResult.getArtistImageUrl()).into(
                    (ImageView) rootView.findViewById(R.id.list_item_artist_image_view));
        }

        TextView artistName = (TextView) rootView.findViewById(R.id.list_item_artist_text_view);
        artistName.setText(artistSearchResult.getArtistName());

        return rootView;
    }
}
