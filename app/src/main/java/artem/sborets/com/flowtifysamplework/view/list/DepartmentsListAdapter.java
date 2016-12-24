package artem.sborets.com.flowtifysamplework.view.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import artem.sborets.com.flowtifysamplework.R;
import artem.sborets.com.flowtifysamplework.model.Department;

public class DepartmentsListAdapter extends RecyclerView.Adapter<DepartmentViewHolder> {

    private List<Department> mDataset = new ArrayList<>();

    public DepartmentsListAdapter(List<Department> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DepartmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_department, parent, false);
        return  new DepartmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DepartmentViewHolder holder, int position) {
        holder.setData(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
