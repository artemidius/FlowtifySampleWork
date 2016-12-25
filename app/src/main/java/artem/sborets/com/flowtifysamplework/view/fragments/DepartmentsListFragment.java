package artem.sborets.com.flowtifysamplework.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import artem.sborets.com.flowtifysamplework.R;
import artem.sborets.com.flowtifysamplework.model.Department;
import artem.sborets.com.flowtifysamplework.view.interaction.FragmentInteractionInterface;
import artem.sborets.com.flowtifysamplework.view.list.DepartmentsListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DepartmentsListFragment extends Fragment {

    @BindView(R.id.departmentsList)
    RecyclerView departmentsList;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    List<Department> mData = new ArrayList<>();


    public DepartmentsListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.departments));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_departments_list, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);

        departmentsList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        departmentsList.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new DepartmentsListAdapter(mData);
        departmentsList.setAdapter(mAdapter);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:
                FragmentInteractionInterface interactionInterface = (FragmentInteractionInterface) getContext();
                interactionInterface.onLogout();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_departments, menu);
    }

    public void setData (List<Department> list) {
        mData = list;
    }

}