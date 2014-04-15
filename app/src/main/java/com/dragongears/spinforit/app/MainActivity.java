package com.dragongears.spinforit.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {
    SharedPreferences preferences;

    private ImageView spinnerImage;
    private View.OnClickListener spinnerTapListener;
    private long mSpinDuration;
    private float mSpinRevolutions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        InitializeMainActivity();
    }

    private void InitializeMainActivity() {
        spinnerImage = (ImageView) findViewById(R.id.imageView);

        // Define and attach listeners
        spinnerTapListener = new View.OnClickListener()  {
            public void onClick(View v) {
                StartSpinner();
            }
        };
        spinnerImage.setOnClickListener(spinnerTapListener);
        changeBasedOnSettings();
//        StartSpinner();
    }

    public void StartSpinner() {
        spinnerImage = (ImageView) this.findViewById(R.id.imageView);
        float end = (float)Math.floor(Math.random() * 360);

        RotateAnimation rotateAnim = new RotateAnimation(0f, mSpinRevolutions + end, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setInterpolator(new DecelerateInterpolator());
        rotateAnim.setRepeatCount(0);
        rotateAnim.setDuration(mSpinDuration);
        rotateAnim.setFillAfter(true);
        spinnerImage.startAnimation(rotateAnim);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeBasedOnSettings() {
        long duration[] = {1000, 5000, 10000, 20000};
        float rotations[] = {720f, 3600f, 7200f, 14400f};

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String defaultValue = getResources().getString(R.string.pointer_style_default);
        int resId = getResources().getIdentifier(sharedPrefs.getString("pref_pointer_style", defaultValue), "drawable", getPackageName());
        spinnerImage = (ImageView) this.findViewById(R.id.imageView);
        spinnerImage.setImageResource(resId);

        defaultValue = getResources().getString(R.string.spin_duration_default);
        int index = Integer.parseInt(sharedPrefs.getString("pref_spin_duration", defaultValue));
        mSpinDuration = duration[index];
        mSpinRevolutions = rotations[index];

        defaultValue = getResources().getString(R.string.background_color_default);
        resId = getResources().getIdentifier(sharedPrefs.getString("pref_background_color", defaultValue), "color", getPackageName());
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(resId));
    }
}

// TODO: Splash screen
// TODO: Deal with changes to settings better
// TODO: Background color
// TODO: Spin on start
