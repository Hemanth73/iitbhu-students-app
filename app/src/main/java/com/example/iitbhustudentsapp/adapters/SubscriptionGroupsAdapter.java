package com.example.iitbhustudentsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iitbhustudentsapp.R;

public class SubscriptionGroupsAdapter extends BaseAdapter {
    Context context;
    String[] groupNames;

    public SubscriptionGroupsAdapter(Context context, String[] groupNames) {
        this.groupNames = groupNames;
        this.context = context;
    }

    @Override
    public int getCount() {
        return groupNames.length;
    }

    @Override
    public Object getItem(int position) {
        return groupNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String groupName = (String) getItem(position);
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.subscribe_item, parent, false);
            holder = new ViewHolder();
            holder.groupTextView = convertView.findViewById(R.id.subscribe_textview);
            holder.subscribeSwitch = convertView.findViewById(R.id.subscribe_switch);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.groupTextView.setText(groupName);
        holder.subscribeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String message = (isChecked ? "Dummy Subscribed to " : "Dummy Unsubscribed to ") + groupName;
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;

    }

    static class ViewHolder {
        TextView groupTextView;
        Switch subscribeSwitch;
    }
}
