package comp5216.sydney.edu.au.lextra.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import comp5216.sydney.edu.au.lextra.Bookmark.MessageActivity;
import comp5216.sydney.edu.au.lextra.BuildDetails.BuildingDetail;
import comp5216.sydney.edu.au.lextra.Filter.FilterActivity;
import comp5216.sydney.edu.au.lextra.R;
import comp5216.sydney.edu.au.lextra.Sidebar.SideBarActivity;
import comp5216.sydney.edu.au.lextra.uos.LectureSession;
import comp5216.sydney.edu.au.lextra.uos.UoS;
import comp5216.sydney.edu.au.lextra.uos.UosParser;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    //    Request code
    public final int FILTER_REQUEST_CODE = 647;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int INTERVAL = 100000;
    private static final int FAST_INTERVAL = 100000;
    private static final float DEFAULT_ZOOM = 15f;

    private Boolean myLocationPermissionsGranted = false;
    private GoogleMap myMap;
    private FusedLocationProviderClient myFusedLocationProviderClient;

    //widgets
    private AutoCompleteTextView mySearchText;
    LatLng myLatLng;
    private List<BuildingList> buildingListData = null;
    private ListAdapterActivity myAdapter = null;
    private ListView listView;
    public static ArrayList<UoS> courses = new ArrayList<>();
    private ArrayList<String> autocompleteStringList = new ArrayList<>();
    String userInput = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mySearchText = findViewById(R.id.input_search);
        listView = (ListView) findViewById(R.id.lstView);

        // User Login - Stone
        Save_login();
        Getemail();

        getLocationPermission(); // get location permission
        init(); // init map
        InputStream uos_file = getResources().openRawResource(R.raw.uos_info); // read course file
        try {
            courses = UosParser.readUoSFromCSV(uos_file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> list = new ArrayList<String>();// list for get course location
        for (int i = 0; i < courses.size(); i++) {
            list.add(courses.get(i).getLectures().get(0).getLocation() + "\n");
        }

        setupListViewListener();// list view listener

        ArrayAdapter<String> searchAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, autocompleteStringList);
        mySearchText.setAdapter(searchAdapter);

    }

    /**
     * Get email
     * by Stone
     */
    private String Getemail() {
        SharedPreferences preferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        return preferences.getString("email", "");
    }

    /**
     * Save Login
     * by Stone
     */
    private void Save_login() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("account", "1");
        editor.commit();
    }

    /**
     * Set up build list
     * request UoS list
     */
    private void setBuildingAdapterList(ArrayList<UoS> courses) {
        BuildingLocation buildingLocation = new BuildingLocation(myMap);

        ArrayList<String> buildStrList = new ArrayList<>();
        buildingListData = new LinkedList<BuildingList>();
        for (int i = 0; i < courses.size(); i++) {
            for (int k = 0; k < courses.get(i).getLectures().size(); k++) {
                String buildStr = courses.get(i).getLectures().get(k).getLocation();
                if (buildStr != null) {
                    String splitArray = buildStr.split("\\.")[0].trim();
                    //splitArray = splitArray.split(",")[0].trim();
                    buildStrList.add(splitArray);
                }
            }
        }

        buildStrList = distinct(buildStrList);

        for (String str : buildStrList) {
            BuildingList buildingList = new BuildingList(str, (int) getDistance(myLatLng, buildingLocation.findLocation(str)) + " m");
            buildingListData.add(buildingList);
        }
        myAdapter = new ListAdapterActivity((LinkedList<BuildingList>) buildingListData, this);
        listView.setAdapter(myAdapter);
    }


    /**
     * Distinct
     *
     * @return String of ArrayList
     */
    private ArrayList<String> distinct(ArrayList<String> strList) {
        Set<String> set = new HashSet<>(strList);
        strList.clear();
        strList.addAll(set);
        return strList;
    }


    /**
     * get distance between two location
     */
    private float getDistance(LatLng latLng1, LatLng latLng2) {
        if (latLng1 != null && latLng2 != null) {
            Location location1 = new Location("A");
            location1.setLatitude(latLng1.latitude);
            location1.setLongitude(latLng1.longitude);

            Location location2 = new Location("B");
            location2.setLatitude(latLng2.latitude);
            location2.setLongitude(latLng2.longitude);
            return location1.distanceTo(location2);
        }
        return -1;
    }


    /**
     * Init map
     */
    private void init() {
        mySearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    ArrayList<UoS> searchResult = search();
                    setBuildingAdapterList(searchResult);
                    myMap.clear();

                    BuildingLocation buildingLocation = new BuildingLocation(myMap, searchResult);
                    for (int i = 0; i < searchResult.size(); i++) {
                        buildingLocation.setMarker(searchResult.get(i).getLectures().get(0).getLocation().split("\\.")[0].trim());
                    }
                }
                return false;
            }
        });
    }

    /**
     * Search UoS code, course information, location
     *
     * @return ArrayList UoS
     */
    private ArrayList<UoS> search() {
        String searchString = mySearchText.getText().toString();
        userInput = searchString;

        if (!searchString.equals("")) {
            ArrayList<UoS> searchedCourses = new ArrayList<>();
            for (int i = 0; i < courses.size(); i++) {
                if (searchString.equals(courses.get(i).getUosCode()) ||
                        searchString.equals(courses.get(i).getUosName()) ||
                        searchString.equals(courses.get(i).getLectures().get(0).getLocation().split("\\.")[0].trim())) {
                    searchedCourses.add(courses.get(i));
                }
            }
            return searchedCourses;
        }
        return courses;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        myMap = googleMap;
        if (myLocationPermissionsGranted) {
            getDeviceLocation();
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            myMap.setMyLocationEnabled(true);
            myMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();

        }
        BuildingLocation buildingLocation = new BuildingLocation(myMap);
        buildingLocation.setMarker();
        myMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                String buildingName = marker.getTitle();
                Intent intent = new Intent(MapActivity.this, BuildingDetail.class);
                if (intent != null) {
                    intent.putExtra("buildingName", buildingName);
                }
                startActivity(intent);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        myLocationPermissionsGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            myLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    myLocationPermissionsGranted = true;
                    // initialize out map
                    initMap();
                }
            }
        }

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    /**
     * Get my device location
     */
    private void getDeviceLocation() {
        myFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (myLocationPermissionsGranted) {
                Task location = myFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            myLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            moveCamera(myLatLng, DEFAULT_ZOOM, "My Location");
                        }
                        setBuildingAdapterList(courses);
                        createLocationRequest();
                        addAutoCompleteText(courses);

                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("ERROR", "SecurityException" + e.getMessage());
        }
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FAST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    /**
     * add course information for Auto complete
     */
    private void addAutoCompleteText(ArrayList<UoS> courses) {
        for (int i = 0; i < courses.size(); i++) {
            autocompleteStringList.add(courses.get(i).getLectures().get(0).getLocation().split("\\.")[0].trim());
            autocompleteStringList.add(courses.get(i).getUosCode());
            autocompleteStringList.add(courses.get(i).getUosName());
        }
        autocompleteStringList = distinct(autocompleteStringList);
    }

    /**
     * move camera to a location
     */
    private void moveCamera(LatLng latLng, float zoom, String title) {
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            myMap.addMarker(options);
        }
    }

    /**
     * Get Location permission
     */
    private void getLocationPermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                myLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permission,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permission,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    /**
     * Set up list view listener
     * Start new activity putExtra data
     */
    private void setupListViewListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BuildingList buildingList = (BuildingList) myAdapter.getItem(position);
                String buildingName = buildingList.getBuildingName();
                Intent intent = new Intent(MapActivity.this, BuildingDetail.class);
                if (intent != null) {
                    intent.putExtra("buildingName", buildingName);
                }
                startActivity(intent);
            }
        });
    }


    /**
     * check google map works.
     */
    public boolean checkGoogle() {
        int abli = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(this);
        if (abli == ConnectionResult.SUCCESS) {
            System.out.println("Google play services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance()
                .isUserResolvableError(abli)) {
            System.out.println("error but can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, abli, Integer.parseInt("error"));
            return true;
        } else {
            return true;
        }
    }


    /**
     * Filter
     * by Richard
     */
    public void onFilterBtnClick(View view) {
        Log.i("INFO", "FILTER CLICKED");
        Intent intent = new Intent(this, FilterActivity.class);
        startActivityForResult(intent, FILTER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILTER_REQUEST_CODE) {
            String[] prefixes = data.getExtras().getStringArray("prefixes");
            String[] faculties = data.getExtras().getStringArray("faculties");
            for (String str : prefixes) {
                Log.i("INFO", "PREFIX: " + str);
            }
            for (String str : faculties) {
                Log.i("INFO", "Faculty: " + str);
            }
        }
    }

    /**
     * Side bar
     * by Richard
     */
    public void onSidebarBtnClick(View view) {
        Log.i("INFO", "SIDEBAR CLICKED");
        Intent intent = new Intent(this, SideBarActivity.class);
        startActivity(intent);
    }

    public void onMessage(View view) {
        userInput= String.valueOf(mySearchText.getText());
        Intent intent = new Intent(MapActivity.this, MessageActivity.class);
        intent.putExtra("Course", userInput);
        startActivity(intent);
    }

}
