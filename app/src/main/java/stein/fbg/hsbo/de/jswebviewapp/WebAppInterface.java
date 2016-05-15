package stein.fbg.hsbo.de.jswebviewapp;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mattes on 01.05.2016.
 */
public class WebAppInterface {
    Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles the JSON representation of features
     */
    @JavascriptInterface
    public void handleJsonFeatures(String[] features) {
        ArrayList featureList = MainActivity.featureList;
        featureList.clear();
        for (String s : features) {
            Feature feature = new Feature(s);
            featureList.add(feature);
        }
        Toast.makeText(mContext, features.length + " " + mContext.getString(R.string.selected), Toast.LENGTH_SHORT).show();
    }
}
