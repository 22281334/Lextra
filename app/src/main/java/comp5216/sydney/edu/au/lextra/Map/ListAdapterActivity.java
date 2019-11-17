package comp5216.sydney.edu.au.lextra.Map;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

import comp5216.sydney.edu.au.lextra.R;

public class ListAdapterActivity extends BaseAdapter {


    private LinkedList<BuildingList> linkedListData;
    private Context myContext;

    public ListAdapterActivity(LinkedList<BuildingList> linkedListData, Context myContext) {
        this.linkedListData = linkedListData;
        this.myContext = myContext;
    }

    @Override
    public int getCount() {
        return linkedListData.size();
    }

    @Override
    public Object getItem(int position) {
        return linkedListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(myContext).inflate(R.layout.activity_list_adapter, parent, false);
        TextView txt_buildingName = (TextView) convertView.findViewById(R.id.buildingNameText);
        TextView txt_distanceFromMe = (TextView) convertView.findViewById(R.id.distanceText);


        txt_buildingName.setText(linkedListData.get(position).getBuildingName());
        txt_distanceFromMe.setText(linkedListData.get(position).getDistance());

        return convertView;
    }

}
