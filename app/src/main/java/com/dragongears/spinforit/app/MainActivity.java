package com.dragongears.spinforit.app;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private float mSpinDuration;
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
        rotateAnim.setDuration((long)mSpinDuration);
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
        float duration[] = {1000, 5000, 10000, 20000};
        float rotations[] = {720, 3600, 7200, 14400};

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        int resId = getResources().getIdentifier(sharedPrefs.getString("pref_pointer_style", "hand_512"), "drawable", getPackageName());
        spinnerImage = (ImageView) this.findViewById(R.id.imageView);
        spinnerImage.setImageResource(resId);

        int index = Integer.parseInt(sharedPrefs.getString("pref_spin_duration", "1"));
        mSpinDuration = duration[index];
        mSpinRevolutions = rotations[index];
    }
}

// TODO: Splash screen
// TODO: Deal with changes to settings better
// TODO: Move spin duration arrays to resources
// TODO: Multiple resource files for each pointer
// TODO: Duration preference resource as int ???
