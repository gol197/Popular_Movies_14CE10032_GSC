package jindal5.mayank.popular_movies_14ce10032_gsc;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private ImageAdapter imag_adap;

    public MainActivityFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            getimage imageTask = new getimage();
            imageTask.execute("popularity.desc");
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        GridView grid = (GridView) rootview.findViewById(R.id.gridView);
        imag_adap = new ImageAdapter(getActivity());
        grid.setAdapter(imag_adap);
        return rootview;
    }

    public class getimage extends AsyncTask<String, Void, String[]> {
        private final String LOG_TAG = getimage.class.getSimpleName();
        public int count;
        String[] pos_path;


        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;
            try {
                final String movie_url_str = "http://api.themoviedb.org/3/discover/movie?sort_by="+ params[0] +"&api_key=8d7a48043ba1d3348181e2b6615cedc7";
                URL movie_url = new URL(movie_url_str);
                urlConnection = (HttpURLConnection) movie_url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                movieJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Forecast string: " + movieJsonStr);
                JSONObject movieJson = new JSONObject(movieJsonStr);
                JSONArray movieJsonarray = movieJson.getJSONArray("results");
                 count = movieJsonarray.length();
                pos_path = new String[count];
                for(int i=0;i<count;i++){
                    JSONObject sin_movie = movieJsonarray.getJSONObject(i);
                    pos_path[i] = sin_movie.getString("poster_path");
                    Log.v(LOG_TAG, "Forecast string: " + pos_path[i]);
                }
            } catch (IOException | JSONException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return pos_path;
            }
        @Override
        protected void onPostExecute(String[] result) {

            ArrayList<String> uriPaths = imag_adap.getUriList();
            uriPaths.clear();
            ImageView[] im_vi = new ImageView[count];

            for(int j=0;j<count;j++) {

               String  pos_sin_path = pos_path[j];
                Uri.Builder url_for_poster = new Uri.Builder();
                String qw = "t";
                String as = "p";
              Uri.Builder url_build =  url_for_poster.scheme("http").authority("image.tmdb.org").appendPath(qw).appendPath(as).appendPath("w500").appendEncodedPath(pos_sin_path);


                String url = url_build.toString();

             // try {
               //   String url_fin = URLEncoder.encode(url, "UTF-8");
                  Log.v(LOG_TAG, "Forecast string: " + url);
                  uriPaths.add(url);
             // }
              //catch (UnsupportedEncodingException e){

              //}
                //url_build.clearQuery();

                //Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(im_vi[j]);
                //imag_adap.getItem(j) = im_vi[j];

            }
            imag_adap.notifyDataSetChanged();
        }
    }
}


