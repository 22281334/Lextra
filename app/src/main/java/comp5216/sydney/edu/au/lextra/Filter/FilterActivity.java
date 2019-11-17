package comp5216.sydney.edu.au.lextra.Filter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.lextra.R;

public class FilterActivity extends AppCompatActivity {

    Intent data = new Intent();
    List<String> prefixes = new ArrayList<>();
    List<String> faculties = new ArrayList<>();

    /**
     * The filter activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_filter);

//        隐藏appbar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);//防止5.x以后半透明影响效果，使用这种透明方式

        onFacultySpinner();
        onPrefixSpinner();
    }

    private void onPrefixSpinner() {
//        Getting array to bind in Spinner
        List<String> list = new ArrayList<>();
        list.add("Prefix ONE");
        list.add("Prefix TWO");

        final List<KeyPairBoolData> listArray0 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray0.add(h);
        }

        /**
         * Search MultiSelection Spinner (With Search/Filter Functionality)
         *
         *  Using MultiSpinnerSearch class
         */
        MultiSpinnerSearch searchMultiSpinnerUnlimited = (MultiSpinnerSearch) findViewById(R.id.prefixSpinner);

        searchMultiSpinnerUnlimited.setItems(listArray0, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                prefixes.clear();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i("INFO", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        prefixes.add(items.get(i).getName());
                    }
                }
            }
        });
    }

    private void onFacultySpinner() {
        /**
         * Getting array to bind in Spinner
         */
        List<String> list = new ArrayList<>();
        list.add("Faculty ONE");
        list.add("Faculty TWO");

        final List<KeyPairBoolData> listArray0 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray0.add(h);
        }

        /**
         * Search MultiSelection Spinner (With Search/Filter Functionality)
         *
         *  Using MultiSpinnerSearch class
         */
        MultiSpinnerSearch searchMultiSpinnerUnlimited = (MultiSpinnerSearch) findViewById(R.id.facultySpinner);

        searchMultiSpinnerUnlimited.setItems(listArray0, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                faculties.clear();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i("INFO", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        faculties.add(items.get(i).getName());
                    }
                }
            }
        });
    }

    public void onSubmitClick(View view) {
        String[] prefix_array = prefixes.toArray(new String[prefixes.size()]);
        data.putExtra("prefixes", prefix_array);
        String[] faculty_array = faculties.toArray(new String[faculties.size()]);
        data.putExtra("faculties", faculty_array);

        setResult(0, data);
        finish();
    }
}
