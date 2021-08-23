package com.globomed.newsfragments;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    private List<Article> articleList;


    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 0;
    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articleList = new ArrayList<>();


        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Add Fragments
        pagerAdapter.addFragment(new BreakingNews(), "Breaking");
        pagerAdapter.addFragment(new TechFragment(), "Tech");
        pagerAdapter.addFragment(new BusinessFragment(), "Business");
        pagerAdapter.addFragment(new SportsFragment(), "Sports");


        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        createNotificationChannel();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notify_menu_item){

            Intent notifyIntent = new Intent(this, NotifyReceiver.class);

            PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(
                    this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long repeatInterval = AlarmManager.INTERVAL_HOUR;
            long triggerTime = SystemClock.elapsedRealtime();

            if (alarmManager != null){
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerTime, repeatInterval, notifyPendingIntent);
            }

            Toast.makeText(getApplicationContext(), "Notify Me", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    //Creates a Notification channel, for OREO and higher
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(){

        //create notification manager object
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Check SDK version
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            //Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel(
                    PRIMARY_CHANNEL_ID, "Breaking News Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setDescription("Sends notification about breaking news");

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}