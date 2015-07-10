package jindal5.mayank.popular_movies_14ce10032_gsc;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jindal5.mayank.popular_movies_14ce10032_gsc.Data.MovieDbHelper;

public class ImageAdapter extends BaseAdapter {
    private String drawablePrefix;
    private Context mContext;
    private ArrayList<String> mThumbUris;
    private ArrayList<String> mov_id_arr;
    private ArrayList<String> mov_tit_arr;
    // private Bitmap[] bit_arr;
    private ArrayList<Bitmap> bitmap_arr;
    private ArrayList<byte[]> byte_arr_arr;
    private MovieDbHelper mdb;
    private Cursor cup;

    public ImageAdapter(Context c) {

        mContext = c;
        String packName=mContext.getPackageName();
        drawablePrefix="android.resource://" +packName+ "/";
        ArrayList<String> mov_id_arr =new ArrayList<>();
        ArrayList<String> mov_tit_arr=new ArrayList<>();
        mov_tit_arr.add("asdf");
        mov_tit_arr.add("asdf");
        mov_tit_arr.add("asdf");
        mov_tit_arr.add("asdf");
        mov_tit_arr.add("asdf");
        mov_tit_arr.add("asdf");

        ArrayList<String> uriPaths=new ArrayList<>();// place your drawables.
        //ArrayList<String> uri
        uriPaths.add(drawablePrefix+ R.drawable.sample_0);
         uriPaths.add(drawablePrefix+ R.drawable.sample_0);
         uriPaths.add(drawablePrefix+ R.drawable.sample_0);
         uriPaths.add(drawablePrefix+ R.drawable.sample_0);
         uriPaths.add(drawablePrefix+ R.drawable.sample_0);
         uriPaths.add(drawablePrefix+ R.drawable.sample_0);


        mThumbUris=uriPaths;
    }

    public int getCount() {
        return mThumbUris.size();
    }

    public Object getItem(int position) {
        return mThumbUris.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(400, 400));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Uri imgUri=Uri.parse(mThumbUris.get(position));

        Picasso.with(mContext)
                .load(imgUri)
                .placeholder(R.drawable.sample_0)
                .centerCrop()
                .resize(400, 400)
                .into(imageView);



        //imageView.setImageResource(mThumbIds[position]);
        // imageView.setAdjustViewBounds(true);
        return imageView;
    }

    public ArrayList<String> getUriList(){
        return mThumbUris;
    }
    public ArrayList<String> getidlist(){
        return mov_id_arr;
    }
    public ArrayList<String> gettitlist(){
        return mov_tit_arr;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
}