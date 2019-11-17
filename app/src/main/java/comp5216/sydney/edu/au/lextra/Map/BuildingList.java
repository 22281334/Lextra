package comp5216.sydney.edu.au.lextra.Map;

import androidx.appcompat.app.AppCompatActivity;

public class BuildingList extends AppCompatActivity {

    private String buildingName;
    private String distance;

    public BuildingList(String buildingName, String distance) {
        this.buildingName = buildingName;
        this.distance = distance;

    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getDistance() {
        return distance;
    }


    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}
