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
        if (convertView == null) {
            if (position == 0) {
                convertView = inflater.inflate(R.layout.menu_header, null);
                viewHolder = new ViewHolder();
                viewHolder.profileImageView = (ImageView) convertView.findViewById(R.id.profile_image);
                viewHolder.txtUserName = (TextView) convertView.findViewById(R.id.user_name);
                viewHolder.txtMailId = (TextView) convertView.findViewById(R.id.email_id);
            } else {
                convertView = inflater.inflate(R.layout.menu_row, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_thumbnail);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_menu_item);
            }
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            viewHolder.profileImageView.setImageResource(R.drawable.profile_icon);
            viewHolder.txtUserName.setText("Tavant");
            viewHolder.txtMailId.setText("tav1blr@gmail.com");
        } else {
            viewHolder.textView.setText(drawerMenuItem.getTitle());
            viewHolder.imageView.setImageResource(drawerMenuItem.getImageId());
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
        ImageView profileImageView;
        TextView txtUserName;
        TextView txtMailId;
    }
}
