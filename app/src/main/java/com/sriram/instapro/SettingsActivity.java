package com.sriram.instapro;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreference()).commit();

    }

    public static class MainPreference extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);
            Preference preference = findPreference("key_feedback");
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    sendFeedback(getActivity());
                    return true;
                }
            });

            Preference preference1 = findPreference("key_request");
            preference1.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    sendRequest(getActivity());
                    return true;
                }
            });

            Preference preference2 = findPreference("key_about");
            preference2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    getAbout(getActivity());
                    return true;
                }
            });
        }

        public   void getAbout(Context context) {
            Intent mIntent=new Intent(context, InfoActivity.class);
            startActivity(mIntent);

        }

        public  static void sendRequest(Context context) {
            String body = null;
            try {
                body = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
                body = "\n\nApp Version : 1.0.0\n\n";


            }catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sriramnatrajan10@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT,"Request Feature from Insta Pro");
            intent.putExtra(Intent.EXTRA_TEXT, body);
            context.startActivity(Intent.createChooser(intent,"Send to"));
        }
        public static void sendFeedback(Context context) {
            String body = null;
            try {
                body = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
                body = "\n\n-----------------------------\nPlease don't remove this information\n Device OS : Android \n " +
                        "Device OS Version : " + Build.VERSION.RELEASE + "\n App Version : 1.0.0" + "\n Device Brand : " +
                        Build.BRAND + "\n Device Model : " + Build.MODEL + "\n Device Manufacture : " + Build.MANUFACTURER
                        + "\n\n";
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sriramnatrajan10@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback from Instagram Downloader Pro");
            intent.putExtra(Intent.EXTRA_TEXT, body);
            context.startActivity(Intent.createChooser(intent, "Send to"));
        }
    }
}
