package com.example.iitbhustudentsapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.iitbhustudentsapp.R;
import com.example.iitbhustudentsapp.adapters.SubscriptionGroupsAdapter;

import java.util.Arrays;

public class SubscriptionActivity extends AppCompatActivity {

    ListView subscriptionsListView;
    String[] subscriptionItemNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        subscriptionItemNames = getResources().getStringArray(R.array.subscription_groups);
        Arrays.sort(subscriptionItemNames);

        subscriptionsListView = findViewById(R.id.subscriptions_list_view);
        SubscriptionGroupsAdapter mAdapter = new SubscriptionGroupsAdapter(this, subscriptionItemNames);
        subscriptionsListView.setAdapter(mAdapter);
    }

}
