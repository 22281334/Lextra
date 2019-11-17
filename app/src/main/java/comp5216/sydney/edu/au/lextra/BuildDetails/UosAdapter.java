package comp5216.sydney.edu.au.lextra.BuildDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comp5216.sydney.edu.au.lextra.R;
import comp5216.sydney.edu.au.lextra.uos.LectureSession;
import comp5216.sydney.edu.au.lextra.uos.UoS;

import static comp5216.sydney.edu.au.lextra.util.Util.setListViewHeightBasedOnChildren;

public class UosAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UoS> units;
    private String location;

    public UosAdapter(Context context, ArrayList<UoS> units, String location) {
        this.context = context;
        this.units = units;
        this.location = location;
    }

    public void setUnits(ArrayList<UoS> units) {
        this.units = units;
    }

    @Override
    public int getCount() {
        return units.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return units.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UoS uos = units.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.adapter_uos_on_list, null);
        }

        ArrayList<LectureSession> lectures = uos.getLectures();
        ArrayList<LectureSession> toRemove = new ArrayList<>();
        for (LectureSession lecture : lectures) {
            if (!lecture.getLocation().equals(location)) {
                toRemove.add(lecture);
            }
        }

        lectures.removeAll(toRemove);

        ListView lectureListView = convertView.findViewById(R.id.lecture_list);
        LectureAdapter lectureAdapter = new LectureAdapter(context, lectures);
        lectureListView.setAdapter(lectureAdapter);

        setListViewHeightBasedOnChildren(lectureListView);

        TextView uosCodeTextView = convertView.findViewById(R.id.uos_code);
        uosCodeTextView.setText(uos.getUosCode());

        TextView uosNameTextView = convertView.findViewById(R.id.uos_name);
        uosNameTextView.setText(uos.getUosName());

        lectureListView.setFocusable(false);
        uosCodeTextView.setFocusable(false);
        uosNameTextView.setFocusable(false);
        lectureListView.setClickable(false);
        uosCodeTextView.setClickable(false);
        uosNameTextView.setClickable(false);

        return convertView;
    }

}
