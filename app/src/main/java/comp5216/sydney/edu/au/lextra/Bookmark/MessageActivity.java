package comp5216.sydney.edu.au.lextra.Bookmark;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


import comp5216.sydney.edu.au.lextra.R;
import comp5216.sydney.edu.au.lextra.uos.UoS;
import comp5216.sydney.edu.au.lextra.uos.UosParser;

public class MessageActivity extends AppCompatActivity {

    public ArrayList<UoS> courses;
    ListView listView;

    BookMarkAdapter adapter;

    String semester;
    String Couresname;

    ArrayList<UoS> uos;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        Couresname=intent.getStringExtra("Course");

        semester = "S2C";

        uos = new ArrayList<>();

        InputStream uos_file = getResources().openRawResource(R.raw.uos_info);
        courses = new ArrayList<>();
        try {
            courses = UosParser.readUoSFromCSV(uos_file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("Receive UOS",courses.size()+"");

        displayBookmark();

        setAdapter();

        adapter.notifyDataSetChanged();

    }
    //Display course message
    private void displayBookmark(){
        if(Couresname !=null && Couresname.length()>0){
            String unit = Couresname;
            for(int i=0; i<courses.size();i++){
                UoS searchCourse = courses.get(i);
                if((searchCourse.getUosCode().equals(unit)|| searchCourse.getUosName().equals(unit)) && searchCourse.getSession().contains(semester) ){
                    for(int j=0 ; j<searchCourse.getLectures().size();j++){

                        Log.d("Bookmark","Lecture Size is "+searchCourse.getLectures().size());

                        UoS u = new UoS(searchCourse.getUosCode(),searchCourse.getUosName(),
                                searchCourse.getLectures().get(j));
                        Log.d("Bookmark","bookmark lecture session "+u.getUosCode()+", "+u.getLecture().getLocation());
                        uos.add(u);
                    }
                }
            }
        }
    }
//Set list Adapter
    private void setAdapter(){
        adapter = new BookMarkAdapter(this ,uos);
        listView = (ListView) findViewById(R.id.Message_listView);
        listView.setAdapter(adapter);
    }
}
