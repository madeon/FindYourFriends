package com.example.mathias.findyourfriends.Helpers;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by mathi on 21-03-2018.
 */

public class ToastMaker {

    public ToastMaker() {

    }

    public void createToast(Activity activity, String text) {
        Toast.makeText(activity, text,
                Toast.LENGTH_LONG).show();
    }


}
