package comp5216.sydney.edu.au.lextra.UserSetting;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import comp5216.sydney.edu.au.lextra.Login.UserDB;
import comp5216.sydney.edu.au.lextra.Login.UserDao;
import comp5216.sydney.edu.au.lextra.R;
import comp5216.sydney.edu.au.lextra.Sidebar.SideBarActivity;

public class UserSettingActivity extends AppCompatActivity {

    EditText old_password;
    EditText new_password;
    EditText confirm_password;
    ImageView avatar;

    UserDB db;
    UserDao usersDao;
    String email;
    String password;
    String avatar_path;
    Boolean avatarChanged = false;

    Bitmap resized;
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_user_setting);

        db = UserDB.getDatabase(this.getApplication().getApplicationContext());
        usersDao = db.userDao();

        avatar = findViewById(R.id.imageViewSetting);
        old_password = (EditText) findViewById(R.id.old_password);
        new_password = (EditText) findViewById(R.id.new_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);


        /**
         * Obtain user's email
         * */
        email = userEmail();

        getCurrentAvatar(email);
    }



    public void getCurrentAvatar(final String input_email){
        try {
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    avatar_path = usersDao.selectUserAvatar(input_email);
                    return null;
                }
            }.execute().get();
        } catch (Exception ex){
            Log.e("selectPasswordFromUsers",ex.getStackTrace().toString());
        }

        // New User without customized avatar
        // Using the default user profile image
        if(avatar_path==null || avatar_path.equals("")){
            Resources res = getResources();
            avatar.setImageDrawable(res.getDrawable(R.drawable.default_user_pic));
        } else {
            Bitmap b = null;
            try {
                File f = new File(avatar_path, "profile.png");
                b = BitmapFactory.decodeStream(new FileInputStream(f));
                avatar.setImageBitmap(b);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

    }

    public void onChangeAvatar(View v){
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

                resized = Bitmap.createScaledBitmap(bitmap, (int)(225), (int)(255), true);

                avatar.setImageBitmap(resized);
                avatarChanged = true;
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void onSubmitClick(View v){

        String oldPassword = old_password.getText().toString();
        String newPassword= new_password.getText().toString();
        String confirmPassword = confirm_password.getText().toString();
        Boolean passwordChanged = false;

        Log.i("Old, new, confirm",oldPassword+", "+newPassword+", "+confirmPassword);

        if(oldPassword.equals("") && newPassword.equals("") && confirmPassword.equals("")){
            // do nothing
        } else {
            if(verifyPassword(oldPassword)){
                if (newPassword.equals(confirmPassword)){

                    final String tmp = newPassword;

                    new AsyncTask<Void, Void, Void>(){
                        @Override
                        protected Void doInBackground(Void... voids){
                            usersDao.updatePassword(email, tmp);
                            Log.i("SaveItems","Complete");
                            return null;
                        }
                    }.execute();

                    passwordChanged = true;
                } else {
                    Log.i("Not the same detected","new and confirm"+newPassword+" and "+confirmPassword);

                    alertNotMatch();
                }
            } else {
                alertWrongPassword();
            }
        }

        if(avatarChanged){
            final String path = saveToInternalStorage(resized);
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids){
                    Log.i("Avatar is",path);
                    usersDao.updateUserAvatar(email,path);
                    Log.i("SaveItems","Complete");
                    return null;
                }
            }.execute();
        }

        if(passwordChanged || avatarChanged){
            final Intent intent = new Intent(UserSettingActivity.this, SideBarActivity.class);
            startActivity(intent);
            finish();

        } else {
//            Toast.makeText(getApplicationContext(), "Nothing is submitted",
//                    Toast.LENGTH_LONG).show();
        }

    }

    public Boolean verifyPassword(String old_password){
        try {
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    password = usersDao.selectUserPassword(email);
                    return null;
                }
            }.execute().get();
        } catch (Exception ex){
            Log.e("selectPasswordFromUsers",ex.getStackTrace().toString());
        }

        Log.i("Original password!!!!",password);

        if (old_password.equals(password)){
            return true;
        } else {
            return false;
        }
    }

    public void alertWrongPassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserSettingActivity.this);
        builder.setTitle("Wrong password")
                .setMessage("The old password you have entered is incorrect")
                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        old_password.getText().clear();
                        new_password.getText().clear();
                        confirm_password.getText().clear();
                    }
                });
        builder.create().show();
    }

    public void alertNotMatch(){

        AlertDialog.Builder builder = new AlertDialog.Builder(UserSettingActivity.this);
        builder.setTitle("Confirm password")
                .setMessage("The new password and confirm password do not matched")
                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        old_password.getText().clear();
                        new_password.getText().clear();
                        confirm_password.getText().clear();
                    }
                });
        builder.create().show();
    }

    public String saveToInternalStorage(Bitmap bitmap){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, "profile.png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private String userEmail(){
        SharedPreferences preferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        return preferences.getString("email", "");
    }
}
