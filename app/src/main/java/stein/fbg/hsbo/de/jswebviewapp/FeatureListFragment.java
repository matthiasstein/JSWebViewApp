package stein.fbg.hsbo.de.jswebviewapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class FeatureListFragment extends ListFragment {

    private FeatureListAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new FeatureListAdapter(getActivity(), MainActivity.featureList);
        setEmptyText(getString(R.string.no_data));
        setListAdapter(mAdapter);
    }

    public void refresh() {
        mAdapter.notifyDataSetChanged();
    }
}
