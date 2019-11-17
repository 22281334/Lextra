package comp5216.sydney.edu.au.lextra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import comp5216.sydney.edu.au.lextra.Login.Sign_upActivity;
import comp5216.sydney.edu.au.lextra.Login.User;
import comp5216.sydney.edu.au.lextra.Login.UserDB;
import comp5216.sydney.edu.au.lextra.Login.UserDao;
import comp5216.sydney.edu.au.lextra.Map.MapActivity;

public class MainActivity extends AppCompatActivity {

    EditText Username;
    EditText Password;
    String username;
    String password;
    UserDB db;
    UserDao userDao;
    List<User> users;
    ProgressBar Loading;
    String toast_message;
    boolean check_results=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize variables
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Loading=findViewById(R.id.loading);
        db = UserDB.getDatabase(this.getApplication().getApplicationContext());
        userDao = db.userDao();
        //Check user login state
        Check_Login();
    }
    //Load and check login state
    private void Check_Login(){
        SharedPreferences preferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        String account = preferences.getString("account", "");
        //1 means still online
        if(account.equals("1")){
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    }
    // Go to sign up page
    public void onSign_up(View view) {
        Intent intent = new Intent(MainActivity.this, Sign_upActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }
    //On click of Sign in button
    public void onSign_in(View view) {
        username = Username.getText().toString();
        password = Password.getText().toString();
        Loading.setVisibility(View.VISIBLE);
        Check();
    }

    // Check if the name and password match the data in database
    private void Check() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    users= userDao.listAll();
                    Log.i("i", users.toString());
                    if (users != null & users.size() > 0) {
                        for (User item : users) {
                            if (item.email.equals(username)) {
                                if (item.password.equals(password)) {
                                    toast_message="Sign in successfully";
                                    check_results = true;
                                } else {
                                    toast_message="Password not found";
                                    check_results = false;
                                }
                            } else {
                                toast_message= "username not found";
                                check_results = false;
                            }
                        }
                    }
                    Log.i("i", users.toString());
                    return null;
                }

                @Override
                protected void onPostExecute(Void voids){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Loading.setVisibility(View.INVISIBLE);
                        }
                    },1000);
                    Toast.makeText(getApplicationContext(),toast_message , Toast.LENGTH_LONG).show();
                    if (check_results) {
                        //Save email of current user
                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", username);
                        editor.commit();
                        //Go to main page
                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                }
            }.execute();

        } catch (Exception ex) {
            Log.i("readItemsFromDatabase", ex.getStackTrace().toString());
        }

    }
}

