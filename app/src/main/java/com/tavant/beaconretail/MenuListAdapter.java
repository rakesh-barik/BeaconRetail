package com.tavant.beaconretail;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tavant.beaconretail.model.DrawerMenuItem;

import java.util.List;

/**
 * Created by rakesh.barik on 10-03-2015.
 */
public class MenuListAdapter extends ArrayAdapter<DrawerMenuItem> {

    private Activity activity;
    private List<DrawerMenuItem> values;

    public MenuListAdapter(Activity activity, List<DrawerMenuItem> objects) {
        super(activity, R.layout.menu_row, objects);
        this.activity = activity;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        DrawerMenuItem drawerMenuItem = getItem(position);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.menu_row,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.image_thumbnail);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.tv_menu_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
            viewHolder.textView.setText(drawerMenuItem.getTitle());
            viewHolder.imageView.setImageResource(drawerMenuItem.getImageId());

        return convertView;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
