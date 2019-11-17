package comp5216.sydney.edu.au.lextra.Bookmark;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import comp5216.sydney.edu.au.lextra.Login.UserDB;
import comp5216.sydney.edu.au.lextra.Login.UserDao;
import comp5216.sydney.edu.au.lextra.R;
import comp5216.sydney.edu.au.lextra.uos.UoS;
import comp5216.sydney.edu.au.lextra.uos.UosParser;


public class BookMarkActivity extends AppCompatActivity implements Serializable {

    public ArrayList<UoS> courses;
    ListView listView;

    BookMarkAdapter adapter;

    UserDB db;
    UserDao userDao;
    String email;
    String semester;
    String bookmark;

    ArrayList<UoS> uos;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_bookmark);


        db = UserDB.getDatabase(this.getApplication().getApplicationContext());
        userDao = db.userDao();

        email = getIntent().getStringExtra("email");
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

        readBookmarkFromDB();

        displayBookmark();

        setAdapter();

        setupListViewListener();
    }

    private void displayBookmark(){
        if(bookmark !=null && bookmark.length()>0){
            String[] units = bookmark.split(",");
            Log.d("bookmark","bookmark has "+units[0]);
            for (String unit : units){
                for(int i=0; i<courses.size();i++){
                    UoS searchCourse = courses.get(i);
                    if(searchCourse.getUosCode().equals(unit) && searchCourse.getSession().contains(semester)){
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
    }

    private void setAdapter(){
        adapter = new BookMarkAdapter(this ,uos);
        listView = (ListView) findViewById(R.id.bookmark_listView);
        listView.setAdapter(adapter);
    }

    private void setupListViewListener(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long id) {
                Log.i("BookMarkActivity","Long Clicked item "+pos);
                AlertDialog.Builder builder = new AlertDialog.Builder(BookMarkActivity.this);
                builder.setTitle("Delete a bookmark")
                        .setMessage("Do you want to delete this bookmark?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ArrayList<Integer> tmp = new ArrayList<>();
                                String remove_unit = uos.get(pos).getUosCode();
                                for(int j=0;j<uos.size();j++){
                                    if(uos.get(j).getUosCode().equals(remove_unit)){
                                        tmp.add(j);
                                    }
                                }
                                if(tmp.size()>0){
                                    for(int k=tmp.size()-1; k>=0; k--){
                                        uos.remove(k);
                                    }
                                }
                                adapter.notifyDataSetChanged(); //Notify listView adapter to
                                // update changes
                                saveItemsToDB();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //User cancelled the dialog
                                //Nothing happens
                            }
                        });
                builder.create().show();

                return true;
            }
        });
    }

    private void readBookmarkFromDB(){
        try{
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids){
                    bookmark = userDao.selectBookmark(email);
                    return null;
                }
            }.execute().get();
        }catch (Exception ex){
            Log.e("readItemsFromDatabase",ex.getStackTrace().toString());
        }
    }

    private void saveItemsToDB(){
        String new_bookmark = "";
        if(uos.size()>0){
            for(UoS course: uos){
                new_bookmark+=course.getUosCode()+",";
            }
            new_bookmark = new_bookmark.substring(0, new_bookmark.length() - 1);
        }
        final String tmp = new_bookmark;
        try{
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids){
                    userDao.updateBookmark(email,tmp);
                    return null;
                }
            }.execute().get();
        }catch (Exception ex){
            Log.e("readItemsFromDatabase",ex.getStackTrace().toString());
        }
    }
}
