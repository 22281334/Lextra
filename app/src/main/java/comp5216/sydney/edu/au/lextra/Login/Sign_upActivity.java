package comp5216.sydney.edu.au.lextra.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comp5216.sydney.edu.au.lextra.R;

public class Sign_upActivity extends AppCompatActivity {

    UserDB db;
    UserDao userDao;
    EditText username;
    EditText password;
    EditText c_password;
    String name;
    String pass;
    boolean validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Initialize variables
        db = UserDB.getDatabase(this.getApplication().getApplicationContext());
        userDao = db.userDao();
        username = findViewById(R.id.username_2);
        password = findViewById(R.id.password_2);
        c_password = findViewById(R.id.c_password);
    }
    //On click of Sign up button & Save the account to database
    public void onSave(View view) {
        validation=false;
        name= username.getText().toString();
        pass= password.getText().toString();
        String c_pass = c_password.getText().toString();
        Toast.makeText(getApplicationContext(), Massage(name, pass, c_pass), Toast.LENGTH_LONG).show();
        if (validation) {
            new AsyncTask<Void,Void,Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    User user_data = new User(name, pass, "","COMP5216");
                    userDao.insert(user_data);
                    return null;
                }
                @Override
                protected void onPostExecute(Void voids){
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", name);
                    editor.commit();
                    Intent intent = new Intent(Sign_upActivity.this, WelcomActivity.class);
                    startActivity(intent);
                    Sign_upActivity.this.finish();
                }
            }.execute();

        }
    }

//Validate input format
    private int Validation(String Name, String Pass, String C_pass) {
        Pattern pattern1 =
                Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher matcher1 = pattern1.matcher(Name);
        Pattern pattern2 =
                Pattern.compile("((?=.*\\d)(?=.*\\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,16}$");
        Matcher matcher2 = pattern2.matcher(Pass);
        if (Name.length() == 0) {
            return 1;
        } else {
            if (!matcher1.matches()) {
                return 2;
            } else {
                if (Pass.length() == 0) {
                    return 3;
                } else {
                    if (!matcher2.matches()) {
                        return 4;
                    } else {
                        if (!C_pass.equals(Pass)) {
                            return 5;
                        } else {
                            validation=true;
                            return 6;
                        }
                    }
                }
            }
        }

    }
//Alert message
    private String Massage(String Name, String Pass, String C_pass) {
        int cases = Validation(Name, Pass, C_pass);
        switch (cases) {
            case 1:
                return "Please input Email";
            case 2:
                return "Email format wrong";
            case 3:
                return "Password cannot be empty";
            case 4:
                return "The password should be 8-16, least 1 number, 1 letter and 1 symbol;";
            case 5:
                return "The password entered the second time is different from the first";
            case 6:
                return "Sign up successfully";
            default:
                return "Unknown error";
        }
    }
}
