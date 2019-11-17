package comp5216.sydney.edu.au.lextra.BuildDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import comp5216.sydney.edu.au.lextra.R;
import comp5216.sydney.edu.au.lextra.uos.LectureSession;

public class LectureAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<LectureSession> lectures;

    public LectureAdapter(Context context, ArrayList<LectureSession> lectures) {
        this.context = context;
        this.lectures = lectures;
    }

    @Override
    public int getCount() {
        return lectures.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return lectures.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LectureSession lectureSession = lectures.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.adapter_lecture_sessions, null);
        }

        TextView lectureTimeTextView = convertView.findViewById(R.id.lecture_session_time);
        String str = lectureSession.getWeekday();
        str += " ";
        str += lectureSession.getTime();
        lectureTimeTextView.setText(str);
        TextView lectureWeeksTextView = convertView.findViewById(R.id.lecture_session_weeks);
        lectureWeeksTextView.setText(lectureSession.getWeeks());

        lectureTimeTextView.setFocusable(false);
        lectureTimeTextView.setClickable(false);
        lectureWeeksTextView.setFocusable(false);
        lectureWeeksTextView.setClickable(false);

        return convertView;
    }
}
