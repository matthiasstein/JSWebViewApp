package stein.fbg.hsbo.de.jswebviewapp;

/**
 * Created by Mattes on 25.12.2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FeatureListAdapter extends BaseAdapter {
    Context context;
    List<Feature> featureList;

    FeatureListAdapter(Context context, List<Feature> featureList) {
        this.context = context;
        this.featureList = featureList;
    }

    @Override
    public int getCount() {
        return featureList.size();
    }

    @Override
    public Object getItem(int position) {
        return featureList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return featureList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.feature_list_item, null);
        }

        TextView txtName = (TextView) convertView.findViewById(R.id.name);
        TextView txtType = (TextView) convertView.findViewById(R.id.type);
        ImageView typeImage=(ImageView) convertView.findViewById(R.id.typeImage);

        Feature feature = featureList.get(position);
        // set the title
        txtName.setText(feature.getName());
        txtType.setText(feature.getType());
        switch (feature.getType()){
            case "Apotheke":
                typeImage.setImageResource(R.drawable.apotheke);
                break;
            case "Baecker":
                typeImage.setImageResource(R.drawable.baecker);
                break;
            case "Friseur":
                typeImage.setImageResource(R.drawable.friseur);
                break;
            case "Lebensmittel":
                typeImage.setImageResource(R.drawable.lebensmittel);
                break;
            case "Trinkhalle":
                typeImage.setImageResource(R.drawable.trinkhalle);
                break;
            default:
                typeImage.setImageResource(R.drawable.shopping);
                break;
        }

        return convertView;
    }
}
