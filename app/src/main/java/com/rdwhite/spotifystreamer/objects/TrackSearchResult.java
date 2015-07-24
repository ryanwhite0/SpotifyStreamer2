package com.rdwhite.spotifystreamer.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 752632 on 7/7/2015.
 */
public class TrackSearchResult implements Parcelable {
    private String trackTitle;
    private String trackAlbum;
    private String albumImageSmallUrl;
    private String albumImageLargeUrl;
    private String previewUrl;

    public TrackSearchResult(String trackTitle, String trackAlbum, String albumImageSmallUrl,
                             String albumImageLargeUrl, String previewUrl) {
        this.trackTitle = trackTitle;
        this.trackAlbum = trackAlbum;
        this.albumImageSmallUrl = albumImageSmallUrl;
        this.albumImageLargeUrl = albumImageLargeUrl;
        this.previewUrl = previewUrl;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trackTitle);
        dest.writeString(trackAlbum);
        dest.writeString(albumImageSmallUrl);
        dest.writeString(albumImageLargeUrl);
        dest.writeString(previewUrl);
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }

    public String getTrackAlbum() {
        return trackAlbum;
    }

    public void setTrackAlbum(String trackAlbum) {
        this.trackAlbum = trackAlbum;
    }

    public String getAlbumImageSmallUrl() {
        return albumImageSmallUrl;
    }

    public void setAlbumImageSmallUrl(String albumImageSmallUrl) {
        this.albumImageSmallUrl = albumImageSmallUrl;
    }

    public String getAlbumImageLargeUrl() {
        return albumImageLargeUrl;
    }

    public void setAlbumImageLargeUrl(String albumImageLargeUrl) {
        this.albumImageLargeUrl = albumImageLargeUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}
