package comp5216.sydney.edu.au.lextra.Login;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import comp5216.sydney.edu.au.lextra.Map.MapActivity;
import comp5216.sydney.edu.au.lextra.R;

public class WelcomActivity extends AppCompatActivity {
    ImageView background;
    Button Next;
    int times=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        //Initialize variables
        background=findViewById(R.id.imageView);
        background.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.image1));
        Next=findViewById(R.id.Next);
        //Disable the button prevent false touches, let user read
        disable_button();
    }
//On click of next button
    public void onNext(View view){
        disable_button();
        Log.i("i",times+"");
        switch (times){
            case 0:
                background.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.image2));
                break;
            case 1:
                background.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.image3));
                break;
            case 2:
                background.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.image4));
                break;
        }

        if (times==2){
            Next.setText("Start");
        }
        if(times==3){
            Intent intent = new Intent(WelcomActivity.this, MapActivity.class);
            startActivity(intent);
            WelcomActivity.this.finish();
        }
        times++;
    }
//Disable button for user to read
    private void disable_button(){
        Next.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Next.setEnabled(true);
            }
        },3000);
    }
}
