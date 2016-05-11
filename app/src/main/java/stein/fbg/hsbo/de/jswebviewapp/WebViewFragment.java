package stein.fbg.hsbo.de.jswebviewapp;


import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        WebView webView = (WebView) view.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new WebAppInterface(this.getContext()), "Android");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        //webView.loadUrl(URL);
        //webView.loadUrl("file:///android_asset/test.html");
        webView.loadUrl("file:///android_asset/esri.html");

        //String html = readHtml("esri.html");
        //webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
        return view;
    }

    private String readHtml(String fileName) {
        AssetManager am = getContext().getAssets();
        String out = "";
        BufferedReader in = null;
        try {
            InputStream is = am.open(fileName);
            InputStreamReader isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            String str;
            while ((str = in.readLine()) != null) {
                out += str;
            }
        } catch (MalformedURLException e) {

        } catch (IOException e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return out;
    }



}
