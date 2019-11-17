package comp5216.sydney.edu.au.lextra.BuildDetails;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import comp5216.sydney.edu.au.lextra.MainActivity;
import comp5216.sydney.edu.au.lextra.Map.MapActivity;
import comp5216.sydney.edu.au.lextra.R;
import comp5216.sydney.edu.au.lextra.uos.LectureSession;
import comp5216.sydney.edu.au.lextra.uos.UoS;

public class UosDetail extends AppCompatActivity {

    TextView uosCodeTextView;
    TextView uosNameTextView;
    ListView buildingListView;

    ArrayList<LectureSession> lectures;
    BuildingAdaptor buildingAdaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uos_detail);

        String hexString = getIntent().getStringExtra("hexString");

        UoS clickedUoS = null;
        for (UoS uos : MapActivity.courses) {
            if (Integer.toHexString(uos.hashCode()).equals(hexString)) {
                clickedUoS = uos;
            }
        }

        uosCodeTextView = findViewById(R.id.uos_detail_uos_code);
        uosCodeTextView.setText(clickedUoS.getUosCode());

        uosNameTextView = findViewById(R.id.uos_detail_uos_name);
        uosNameTextView.setText(clickedUoS.getUosName());

        lectures = clickedUoS.getLectures();

        HashMap<String, ArrayList<LectureSession>> locationLecturesMap = new HashMap<>();
        for (LectureSession lecture : lectures) {
            String location = lecture.getLocation();
            if (locationLecturesMap.containsKey(location)) {
                locationLecturesMap.get(location).add(lecture);
            } else {
                ArrayList<LectureSession> lectures = new ArrayList<>();
                lectures.add(lecture);
                locationLecturesMap.put(location, lectures);
            }
        }
        ArrayList<ArrayList<LectureSession>> arrayListOfLectures = new ArrayList<>();
        ArrayList<String> buildings = new ArrayList<>();
        for (String location : locationLecturesMap.keySet()) {
            ArrayList<LectureSession> lectures = new ArrayList<>(locationLecturesMap.get(location));
            arrayListOfLectures.add(lectures);
            buildings.add(location);
        }
        buildingAdaptor = new BuildingAdaptor(UosDetail.this, arrayListOfLectures, buildings);

        buildingListView = findViewById(R.id.uos_detail_building_list);
        buildingListView.setAdapter(buildingAdaptor);
    }

}
