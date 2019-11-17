package comp5216.sydney.edu.au.lextra.BuildDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comp5216.sydney.edu.au.lextra.Map.MapActivity;
import comp5216.sydney.edu.au.lextra.R;
import comp5216.sydney.edu.au.lextra.uos.LectureSession;
import comp5216.sydney.edu.au.lextra.uos.UoS;

public class BuildingDetail extends AppCompatActivity {

    TextView buildingNameTextView;

    ListView uosListView;

    ArrayList<UoS> buildingLectures;
    UosAdapter uosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_details);

        //Get the data from building list or marker.
        String buildingName = getIntent().getStringExtra("buildingName");

        buildingNameTextView = findViewById(R.id.buildingDetaillNameTextView);
        buildingNameTextView.setText(buildingName);

        buildingLectures = new ArrayList<>();
        uosAdapter = new UosAdapter(this, buildingLectures, buildingName);

        // Find UoS in the building
        for (UoS uos : MapActivity.courses) {
            for (LectureSession lecture : uos.getLectures()) {
                if (lecture.getLocation() == null) continue;
                if (lecture.getLocation().equals(buildingName)) {
                    buildingLectures.add(uos);
                    break;
                }
            }
        }

        uosListView = findViewById(R.id.buildingNameListView);
        uosListView.setAdapter(uosAdapter);

        uosListView.setOnItemClickListener((adapterView, view, i, l) -> {
            UoS clickedUoS = (UoS) uosAdapter.getItem(i);
            Intent intent = new Intent(BuildingDetail.this, UosDetail.class);
            intent.putExtra("hexString", Integer.toHexString(clickedUoS.hashCode()));
            startActivity(intent);
        });

    }
}
