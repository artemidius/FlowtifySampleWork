package artem.sborets.com.flowtifysamplework.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import artem.sborets.com.flowtifysamplework.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentsListFragment extends Fragment {


    public DepartmentsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_departments_list, container, false);
    }

}
