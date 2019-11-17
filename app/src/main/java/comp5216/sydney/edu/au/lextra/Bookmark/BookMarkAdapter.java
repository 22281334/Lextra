package comp5216.sydney.edu.au.lextra.Bookmark;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import comp5216.sydney.edu.au.lextra.R;
import comp5216.sydney.edu.au.lextra.uos.UoS;

public class BookMarkAdapter extends ArrayAdapter<UoS> {
    public BookMarkAdapter(Context context, ArrayList<UoS> courses){
        super(context,0,courses);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_bookmark,
                    parent,false);
        }

        UoS uos = getItem(position);

        TextView uosCode = view.findViewById(R.id.bookmark_uosCode);
        TextView uosName = view.findViewById(R.id.bookmark_uosName);
        TextView uosLocation = view.findViewById(R.id.bookmark_uosLocation);
        TextView uosTime = view.findViewById(R.id.bookmark_uosTime);
        TextView weekDay = view.findViewById(R.id.bookmark_weekDay);
        TextView weeks = view.findViewById(R.id.bookmark_weeks);

        Log.d("UoS",uos.getUosCode()+", "+uos.getUosName());

        uosCode.setText(uos.getUosCode());
        uosName.setText(uos.getUosName());
        uosLocation.setText("Location: "+uos.getLecture().getLocation());
        uosTime.setText(uos.getLecture().getTime());
        weekDay.setText(uos.getLecture().getWeekday());
        weeks.setText(uos.getLecture().getWeeks());

        return view;
    }
}
