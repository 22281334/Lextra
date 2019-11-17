package comp5216.sydney.edu.au.lextra.Sidebar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import comp5216.sydney.edu.au.lextra.Bookmark.BookMarkActivity;
import comp5216.sydney.edu.au.lextra.Login.UserDB;
import comp5216.sydney.edu.au.lextra.Login.UserDao;
import comp5216.sydney.edu.au.lextra.MainActivity;
import comp5216.sydney.edu.au.lextra.R;
import comp5216.sydney.edu.au.lextra.UserSetting.UserSettingActivity;

public class SideBarActivity extends AppCompatActivity {

    UserDB db;
    UserDao usersDao;
    String email;
    String semester;
    String avatar_path = "";
    ImageView  imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.side_bar);
//        隐藏appbar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);//防止5.x以后半透明影响效果，使用这种透明方式


               // this.getSupportActionBar().hide();

        db = UserDB.getDatabase(this.getApplication().getApplicationContext());
        usersDao = db.userDao();

        email = userEmail();

        try {
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    avatar_path = usersDao.selectUserAvatar(email);
                    return null;
                }
            }.execute().get();
        } catch (Exception ex){
            Log.e("selectPasswordFromUsers",ex.getStackTrace().toString());
        }

        setContentView(R.layout.side_bar);
        imageView1 = findViewById(R.id.sidebar_avatar);

        getCurrentAvatar();

    }


    public void onSettingsClick (View v){
        Intent intent = new Intent(SideBarActivity.this, UserSettingActivity.class);
        if (intent != null){
            intent.putExtra("email",email);
            startActivity(intent);
            finish();
        }
    }

    public void onBookMarkClick (View v){
        Intent intent = new Intent(SideBarActivity.this, BookMarkActivity.class);
        intent.putExtra("email",email);
        intent.putExtra("semester",semester);
        startActivity(intent);
        finish();
    }

    public void onLogoutClick(View v){
        Intent intent = new Intent(SideBarActivity.this, MainActivity.class);
        startActivity(intent);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("account", "0");
        editor.commit();
        finish();
    }

    public void getCurrentAvatar(){
        // New User without customized avatar_path
        // Using the default user profile image
        if(avatar_path==null || avatar_path.equals("")){
            Resources res = getResources();
            imageView1.setImageDrawable(res.getDrawable(R.drawable.default_user_pic));
        } else {
            Bitmap b = null;
            try {
                File f = new File(avatar_path, "profile.png");
                b = BitmapFactory.decodeStream(new FileInputStream(f));
                imageView1.setImageBitmap(b);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    private String userEmail(){
        SharedPreferences preferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        return preferences.getString("email", "");
    }


}
