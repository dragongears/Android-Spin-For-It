package com.dragongears.spinforit.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutBox {
    static String VersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "Unknown";
        }
    }

    public static void Show(Activity callingActivity) {
        //Use a Spannable to allow for links highlighting
        SpannableString versionText = new SpannableString("Version " + VersionName(callingActivity));
        SpannableString aboutText = new SpannableString(callingActivity.getString(R.string.about));

        //Generate views to pass to AlertDialog.Builder and to set the text
        View about;
        TextView tvVersion;
        TextView tvAbout;

        //Inflate the custom view
        LayoutInflater inflater = callingActivity.getLayoutInflater();
        about = inflater.inflate(R.layout.about_box, (ViewGroup) callingActivity.findViewById(R.id.aboutView));
        tvVersion = (TextView) about.findViewById(R.id.versionText);
        tvAbout = (TextView) about.findViewById(R.id.aboutText);

        //Set the about text
        tvVersion.setText(versionText);
        tvAbout.setText(aboutText);

        // Now Linkify the text
        Linkify.addLinks(tvAbout, Linkify.ALL);

        //Build and show the dialog
        new AlertDialog.Builder(callingActivity)
                .setTitle("About " + callingActivity.getString(R.string.app_name))
                .setCancelable(true)
                .setIcon(R.drawable.ic_launcher)
                .setPositiveButton("OK", null)
                .setView(about)
                .show();    //Builder method returns allow for method chaining
    }
}