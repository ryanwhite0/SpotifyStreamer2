package com.rdwhite.spotifystreamer.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 752632 on 7/7/2015.
 */
public class ArtistSearchResult implements Parcelable {
    private String artistName;
    private String spotifyId;
    private String artistImageUrl;

    public ArtistSearchResult(String artistName, String artistImageUrl, String spotifyId) {
        this.artistName = artistName;
        this.artistImageUrl = artistImageUrl;
        this.spotifyId = spotifyId;
    }

    public ArtistSearchResult() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artistName);
        dest.writeString(spotifyId);
        dest.writeString(artistImageUrl);
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistImageUrl() {
        return artistImageUrl;
    }

    public void setArtistImageUrl(String artistImageUrl) {
        this.artistImageUrl = artistImageUrl;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }
}
