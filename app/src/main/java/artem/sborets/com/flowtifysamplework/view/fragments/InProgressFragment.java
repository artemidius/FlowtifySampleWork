package artem.sborets.com.flowtifysamplework.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import artem.sborets.com.flowtifysamplework.R;


public class InProgressFragment extends Fragment {

    public InProgressFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_in_progress, container, false);
    }
}
