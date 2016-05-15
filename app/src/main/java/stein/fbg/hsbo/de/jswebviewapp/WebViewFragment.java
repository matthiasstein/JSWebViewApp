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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {


    int SELECTIONMODE = 0;
    WebView webView;
    View view;

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_web_view, container, false);
        webView = (WebView) view.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new WebAppInterface(this.getContext()), "Android");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        //webView.loadUrl("file:///android_asset/test.html");
        webView.loadUrl("file:///android_asset/esri.html");
        loadSpinner();
        Button selectButon=(Button)view.findViewById(R.id.selectButton);
        selectButon.setOnClickListener(this);
        //String html = readHtml("esri.html");
        //webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
        return view;
    }


    public void changeBasemap(String basemap) {
        webView.loadUrl("javascript:changeBasemap('" + basemap + "')");
    }

    public void selectFeatures(String mode){
        webView.loadUrl("javascript:activateTool('" + mode + "')");
    }

    private void loadSpinner() {
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_selection);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.array_selection_mode, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //SetOnItemSelectedListener
        spinner.setOnItemSelectedListener(this);
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


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        SELECTIONMODE = pos;
        String item = (String) parent.getItemAtPosition(pos);
        String toastContent = getString(R.string.selection_mode_changed) + " " + item;
        Toast toast = Toast.makeText(view.getContext(), toastContent, Toast.LENGTH_LONG);
        toast.show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selectButton:
                chooseSelection();
                break;
            default:
                break;
        }
    }

    private void chooseSelection(){
        switch (SELECTIONMODE) {
            case 0:
                selectFeatures("FREEHAND_POLYGON");
                break;
            case 1:
                selectFeatures("RECTANGLE");
                break;
            case 2:
                selectFeatures("CIRCLE");
                break;
            default:
                selectFeatures("FREEHAND_POLYGON");
        }
    }
}
