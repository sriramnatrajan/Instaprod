package com.sriram.instapro;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by windev on 5/23/2016.
 */
public class InfoActivity extends Activity {
    ImageView btnInfo, btnBack,imgInfo;
    TextView tvDesc;
    Button btnFeedback;
    ImageView ivFb,ivTwiter,ivWeb,ivLinkedin;
    private static StringBuilder appVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        tvDesc=findViewById(R.id.tvDesc);
        btnFeedback=findViewById(R.id.btnFeedback);
        ivFb =  findViewById(R.id.ivFb);
        ivTwiter = findViewById(R.id.ivTwiter);
         ivLinkedin=findViewById(R.id.ivLinkedin);





        try {
            PackageInfo appInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            appVersion = new StringBuilder(appInfo.versionName);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ivFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.sriram.instapro")));

            }
        });

        ivTwiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/sriram_natrajan"));
                startActivity(intent);
            }
        });



        ivLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/sriram_natrajan/"));
                startActivity(intent);
            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder devBrand = new StringBuilder(Build.BRAND);
                StringBuilder devOEM = new StringBuilder(Build.MANUFACTURER);
                StringBuilder devModel = new StringBuilder(Build.MODEL);

                StringBuilder deviceInfo;

                if (devBrand.toString().equals(devOEM.toString())) {

                    deviceInfo = new StringBuilder(devBrand.toString() + " " + devModel.toString());
                } else {

                    deviceInfo = new StringBuilder(devBrand.toString() + " " + devOEM.toString() + " " + devModel.toString());
                }

                StringBuilder osVersion = new StringBuilder("" + Build.VERSION.RELEASE);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.text_feedback_mail_id)});
                intent.putExtra(Intent.EXTRA_SUBJECT, (getString(R.string.app_name)+" - InstaPro"));
                intent.putExtra(Intent.EXTRA_TEXT, ("App Version : " + appVersion.toString() + "\nDevice : " + deviceInfo.toString() + "\nAndroid : " + osVersion.toString() + "\n<Insert feedback below>\n\n"));
                startActivity(Intent.createChooser(intent, "Feedback"));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}



