package jindal5.mayank.popular_movies_14ce10032_gsc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class det_mov_revFragment extends Fragment {
    public  ArrayList<String> revi = new ArrayList<String>();

    public det_mov_revFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_det_mov_rev, container, false);
        Intent in = getActivity().getIntent();

        if(in != null){
            Bundle b = in.getExtras();
            try {
                revi = b.getStringArrayList("rev_list");
                String[]  revi_arr = revi.toArray(new String[revi.size()]);
             // String[]  revi_arr = revi.toArray(new String[revi.size()]);
               // Log.e("mayank1",revi_arr[1]);
              //  if (revi == null) {
               // Log.e("mayank1",revi_arr[1]);

                   ArrayAdapter rev_lis_adapter = new ArrayAdapter<String>(getActivity(), R.layout.mov_rev_lis,R.id.mov_rev_lis_text,revi_arr);
                    ListView lis_rev = (ListView) rootview.findViewById(R.id.list_rev);
                    lis_rev.setAdapter(rev_lis_adapter);
               // }
            }
            catch (NullPointerException e){
                Log.e("Mayank","NULL POINTER");
            }

        }
        return rootview;
    }

}
