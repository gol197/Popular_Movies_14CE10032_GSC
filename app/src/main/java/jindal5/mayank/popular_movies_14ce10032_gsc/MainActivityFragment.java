package jindal5.mayank.popular_movies_14ce10032_gsc;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.GridView;

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
    public ImageAdapter imag_adap;
    public String movieJsonStr;
    public ArrayList<String > tit_arr = new ArrayList<String>();
    public ArrayList<String > rel_dat_arr = new ArrayList<String>();
    public ArrayList<String > url1_arr = new ArrayList<String>();
    public ArrayList<String > over_arr = new ArrayList<String>();
    public ArrayList<String > id_arr = new ArrayList<String>();
    public String put_ext_tit;
    public String rel_date_ext;
 public  String url_for_pos_ex;
    public String over_ex;
    public String id_ex;
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
            //SharedPreferences share = PreferenceManager.getDefaultSharedPreferences();
          //  String order = share.getString()
            getimage imageTask = new getimage();
            imageTask.execute("popularity.desc");
            return true;
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
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] tit1 = tit_arr.toArray(new String[tit_arr.size()]);
                String[] rel_date = rel_dat_arr.toArray(new String[rel_dat_arr.size()]);
                String[] url_for_pos = url1_arr.toArray(new String[url1_arr.size()]);
                String[] over = url1_arr.toArray(new String[over_arr.size()]);
                String[] id = id_arr.toArray(new String[over_arr.size()]);
                Log.v("mayank",tit1[3]);
                try{
                    put_ext_tit = tit1[i];
                    rel_date_ext = rel_date[i];
                    url_for_pos_ex = url_for_pos[i];
                    over_ex = over[i];
                    id_ex =id[i];
                    Intent intent = new Intent(getActivity(),det_mov.class);
                    //.putExtra(Intent.EXTRA_TEXT,put_ext_tit);
                    Bundle extras = new Bundle();
                    extras.putString("title",put_ext_tit);
                    extras.putString("rel_date",rel_date_ext);
                    extras.putString("url_pos",url_for_pos_ex);
                    extras.putString("over_ex",over_ex);
                    extras.putString("id_ex", id_ex);
                    extras.putInt("posit",i);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
                catch (ArrayIndexOutOfBoundsException p){
                    Log.e("mayank","reg",p);
                }
            }
        });

        return rootview;
    }

    public class getimage extends AsyncTask<String, String, String[]> {
        private final String LOG_TAG = getimage.class.getSimpleName();
        public int count;
        String[] pos_path;
        String[]  mov_id;
        String[] tit;
        String[] rev;
        String[] rel_dat;
        String[] url1;
        String[] over;
        String[] id;
        public ImageAdapter getad(){
            return imag_adap;
        }


        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;

            BufferedReader reader = null;
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

                JSONObject movieJson = new JSONObject(movieJsonStr);
                JSONArray movieJsonarray = movieJson.getJSONArray("results");
                 count = movieJsonarray.length();
                pos_path = new String[count];
                mov_id = new String[count];
                tit = new String[count];
                rev = new String[count];
                rel_dat = new String[count];
                url1 = new String[count];
                over = new String[count];
                id = new String[count];
                for(int i=0;i<count;i++){
                    JSONObject sin_movie = movieJsonarray.getJSONObject(i);
                    pos_path[i] = sin_movie.getString("poster_path");
                    over[i] = sin_movie.getString("overview");
                    Uri.Builder url_for_poster = new Uri.Builder();
                    String qw = "t";
                    String as = "p";
                    Uri.Builder url_build =  url_for_poster.scheme("http").authority("image.tmdb.org").appendPath(qw).appendPath(as).appendPath("w500").appendEncodedPath(pos_path[i]);
                     url1[i] = url_build.toString();

                   // mov_id[i] =  sin_movie.getString("id");
                   tit[i] = sin_movie.getString("title");
                    //rev[i] = sin_movie.getString("review");
                    rel_dat[i] = sin_movie.getString("release_date");
                    id[i] = sin_movie.getString("id");
                    publishProgress((String)(tit[i]),(String)(rel_dat[i]),(String)(url1[i]),(String)(over[i]),(String)(id[i]));
                    Log.v(LOG_TAG, "Forecast string: " + id[i]);

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
        protected void onProgressUpdate(String... values) {

                tit_arr.add(values[0]);
            rel_dat_arr.add(values[1]);
            url1_arr.add(values[2]);
            over_arr.add(values[3]);
            id_arr.add(values[4]);
        }

        @Override
        protected void onPostExecute(String[] result) {

            ArrayList<String> uriPaths = imag_adap.getUriList();
            uriPaths.clear();
           // ImageView[] im_vi = new ImageView[count];

            for(int j=0;j<count;j++) {

               String  pos_sin_path = pos_path[j];
                String id_mov = id[j];
                Uri.Builder url_for_poster = new Uri.Builder();
                Uri.Builder url_for_rev = new Uri.Builder();
                String qw = "t";
                String as = "p";
              Uri.Builder url_build =  url_for_poster.scheme("http").authority("image.tmdb.org").appendPath(qw).appendPath(as).appendPath("w500").appendEncodedPath(pos_sin_path);
                String url = url_build.toString();
                Uri.Builder url_build_rev = url_for_rev.scheme("http").encodedAuthority("api.themoviedb.org/3/movie").appendPath(id_mov).appendPath("reviews");

             // try {
               //   String url_fin = URLEncoder.encode(url, "UTF-8");

                  uriPaths.add(url);
               // mov_title.add(tit[j]);
                //mov_id_arr.add(id);
             //   mov_tit_arr.add(title);
             // }
              //catch (UnsupportedEncodingException e){

              //}
                //url_build.clearQuery();

                //Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(im_vi[j]);
                //imag_adap.getItem(j) = im_vi[j];\

            }
            imag_adap.notifyDataSetChanged();
        }
    }
}


