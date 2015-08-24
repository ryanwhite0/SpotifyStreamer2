package com.rdwhite.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rdwhite.spotifystreamer.adapters.TrackSearchAdapter;
import com.rdwhite.spotifystreamer.objects.TrackSearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class TrackSearchActivityFragment extends Fragment {

    private TrackSearchAdapter trackSearchAdapter;
    private ArrayList<TrackSearchResult> trackSearchResults;

    public TrackSearchActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            trackSearchResults = savedInstanceState.getParcelableArrayList(Constants.TRACK_SEARCH_RESULTS);
        } else {
            trackSearchResults = new ArrayList<TrackSearchResult>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        trackSearchAdapter = new TrackSearchAdapter(getActivity(), trackSearchResults);

        View rootView = inflater.inflate(R.layout.fragment_track_search, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.track_search_list_view);
        listView.setAdapter(trackSearchAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(trackSearchAdapter.getPosition(trackSearchAdapter.getItem(position))));
                Bundle bundle = intent.getExtras();
                bundle.putParcelableArrayList(Constants.PLAYER_TRACKS_LIST, trackSearchResults);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Intent intent = getActivity().getIntent();
        if (savedInstanceState == null && intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String spotifyId = intent.getStringExtra(Intent.EXTRA_TEXT);
            FetchTrackResultTask fetchTrackResultTask = new FetchTrackResultTask();
            fetchTrackResultTask.execute(spotifyId);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.TRACK_SEARCH_RESULTS, trackSearchResults);
        super.onSaveInstanceState(outState);
    }

    public class FetchTrackResultTask extends AsyncTask<String, Void, Tracks> {
        private final String LOG_TAG = FetchTrackResultTask.class.getSimpleName();

        @Override
        protected Tracks doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String searchString = params[0];

            Tracks results = null;

            if (!searchString.isEmpty()) {
                try {
                    SpotifyApi api = new SpotifyApi();
                    SpotifyService spotifyService = api.getService();
                    Map<String, Object> map = new HashMap<>();
                    map.put("country", "US");
                    results = spotifyService.getArtistTopTrack(searchString, map);
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
            return results;
        }

        @Override
        protected void onPostExecute(Tracks tracks) {
            trackSearchAdapter.clear();
            if (tracks != null) {
                for (Track track : tracks.tracks) {
                    String smallImageUrl = "";
                    String largeImageUrl = "";
                    if (track.album.images != null) {
                        if (track.album.images.size() > 1) {
                            // set image to the second to last (medium) image
                            smallImageUrl = track.album.images.get(track.album.images.size() - 2).url;
                            largeImageUrl = track.album.images.get(0).url;
                        } else {
                            // Only one image size available
                            smallImageUrl = track.album.images.size() == 1 ? track.album.images.get(0).url : "";
                            largeImageUrl = smallImageUrl;
                        }
                    }
                    trackSearchAdapter.add(new TrackSearchResult(track.name, track.album.name,
                            track.artists.get(0).name, smallImageUrl,
                            largeImageUrl, track.preview_url));
                }
            }
            if (trackSearchAdapter.isEmpty()) {
                Toast.makeText(getActivity(), R.string.no_results_tracks_toast, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
