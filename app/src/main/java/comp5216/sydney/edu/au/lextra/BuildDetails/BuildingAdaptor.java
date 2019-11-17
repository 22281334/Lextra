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

import static comp5216.sydney.edu.au.lextra.util.Util.setListViewHeightBasedOnChildren;

public class BuildingAdaptor extends BaseAdapter {

    private Context context;
    private ArrayList<ArrayList<LectureSession>> arrayListOfLectures;
    private ArrayList<String> buildings;

    public BuildingAdaptor(Context context, ArrayList<ArrayList<LectureSession>> arrayListOfLectures, ArrayList<String> buildings) {
        this.context = context;
        this.arrayListOfLectures = arrayListOfLectures;
        this.buildings = buildings;
    }

    public void setArrayListOfLectures(ArrayList<ArrayList<LectureSession>> arrayListOfLectures) {
        this.arrayListOfLectures = arrayListOfLectures;
    }

    @Override
    public int getCount() {
        return arrayListOfLectures.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return arrayListOfLectures.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ArrayList<LectureSession> lecturesInBuilding = arrayListOfLectures.get(position);
        String building = buildings.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.adapter_location_in_uos_detail, null);
        }

        ListView buildingsListView = convertView.findViewById(R.id.uos_detail_lecture_list);
        LectureAdapter lectureAdapter = new LectureAdapter(context, lecturesInBuilding);
        buildingsListView.setAdapter(lectureAdapter);

        setListViewHeightBasedOnChildren(buildingsListView);

        TextView locationTextView = convertView.findViewById(R.id.uos_detail_location);
        locationTextView.setText(building);

        return convertView;
    }

}
