package stein.fbg.hsbo.de.jswebviewapp;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

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
    public void handleJsonFeatures(String [] features) {
        Toast.makeText(mContext, features[0], Toast.LENGTH_SHORT).show();
    }
}
