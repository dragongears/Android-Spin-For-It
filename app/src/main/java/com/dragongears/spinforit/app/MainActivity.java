package com.dragongears.spinforit.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

    private ImageView pointerImageView;
    private View.OnClickListener pointerTapListener;
    private long mSpinDuration;
    private float mSpinRevolutions;
    private boolean mFirstCreate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        InitializeMainActivity();
    }

    private void InitializeMainActivity() {
        // Get the pointer ImageView resource
        pointerImageView = (ImageView)findViewById(R.id.imageView);

        // Define and attach listeners
        pointerTapListener = new View.OnClickListener()  {
            public void onClick(View v) {
                StartSpinner();
            }
        };
        pointerImageView.setOnClickListener(pointerTapListener);

        // Read in saved settings
        changeBasedOnSettings();

        if (mFirstCreate) {
            mFirstCreate = false;
            StartSpinner();
        }
    }

    public void StartSpinner() {
        float end = (float)Math.floor(Math.random() * 360);

        RotateAnimation rotateAnim = new RotateAnimation(0f, mSpinRevolutions + end, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setInterpolator(new DecelerateInterpolator());
        rotateAnim.setRepeatCount(0);
        rotateAnim.setDuration(mSpinDuration);
        rotateAnim.setFillAfter(true);
        pointerImageView.startAnimation(rotateAnim);
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
        String defaultValue;
        int resId;

        long duration[] = {1000, 5000, 10000, 20000};   // Time
        float revolutions[] = {720f, 3600f, 7200f, 14400f}; // Number of revolutions

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Resources resources = getResources();

        // Pointer image
        defaultValue = resources.getString(R.string.pointer_style_default);
        resId = resources.getIdentifier(sharedPrefs.getString("pref_pointer_style", defaultValue), "drawable", getPackageName());
        pointerImageView.setImageResource(resId);

        // Spin duration
        defaultValue = resources.getString(R.string.spin_duration_default);
        int index = Integer.parseInt(sharedPrefs.getString("pref_spin_duration", defaultValue));
        mSpinDuration = duration[index];
        mSpinRevolutions = revolutions[index];

        // Background color
        defaultValue = resources.getString(R.string.background_color_default);
        resId = resources.getIdentifier(sharedPrefs.getString("pref_background_color", defaultValue), "color", getPackageName());
        getWindow().getDecorView().setBackgroundColor(resources.getColor(resId));

        // Auto spin
        mFirstCreate = mFirstCreate && sharedPrefs.getBoolean("pref_spin_auto", true);
    }
}

// TODO: Deal with changes to settings better
// TODO: Better about screen
