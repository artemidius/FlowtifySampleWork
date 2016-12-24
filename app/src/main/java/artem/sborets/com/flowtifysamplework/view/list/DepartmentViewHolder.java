package artem.sborets.com.flowtifysamplework.view.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import artem.sborets.com.flowtifysamplework.R;
import artem.sborets.com.flowtifysamplework.model.Department;
import butterknife.BindView;
import butterknife.ButterKnife;



public class DepartmentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.departmentName)
    TextView departmentName;
    @BindView(R.id.departmentAdress)
    TextView departmentAdress;


    public DepartmentViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

    public void setData(Department department) {
        departmentName.setText(department.getName());
        departmentAdress.setText(department.getAdress());
    }


}