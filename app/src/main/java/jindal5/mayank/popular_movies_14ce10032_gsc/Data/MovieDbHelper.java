package jindal5.mayank.popular_movies_14ce10032_gsc.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by user on 7/4/2015.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
    private Context context;
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "movie.db";
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
           String create_mov_table = "CREATE TABLE" + " MOVIE" + "(" +
                                    "mov_id"  +  " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                   "mov_pos"  +  " BLOB);";
sqLiteDatabase.execSQL(create_mov_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "MOVIE");

        onCreate(sqLiteDatabase);
    }
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public boolean addEntry( byte[] image) throws SQLiteException {

        ContentValues cv = new  ContentValues();
        cv.put("mov_pos",   image);
        SQLiteDatabase db = this.getWritableDatabase();


        try {
            db.insertOrThrow("MOVIE", null, cv );
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Cursor getEntry(String[] columns){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("MOVIE",columns,null,null,null,null,null);
    }
}
