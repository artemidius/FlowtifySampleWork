package artem.sborets.com.flowtifysamplework.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import artem.sborets.com.flowtifysamplework.R;
import artem.sborets.com.flowtifysamplework.model.Department;
import artem.sborets.com.flowtifysamplework.model.User;
import artem.sborets.com.flowtifysamplework.presenter.rest.ApiClient;
import artem.sborets.com.flowtifysamplework.presenter.rest.ApiInterface;
import artem.sborets.com.flowtifysamplework.utils.NetworkUtils;
import artem.sborets.com.flowtifysamplework.view.DepartmentsListFragment;
import artem.sborets.com.flowtifysamplework.view.FragmentInteractionInterface;
import artem.sborets.com.flowtifysamplework.view.LoginFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FragmentInteractionInterface {

    Context mContext;
    LoginFragment loginFragment = new LoginFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        addFragment(loginFragment, false);
    }

    private void addFragment(Fragment fragment, boolean backstack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(backstack)fragmentManager.beginTransaction().replace(R.id.activity_main, fragment).addToBackStack(null).commit();
        else fragmentManager.beginTransaction().replace(R.id.activity_main, fragment).commit();
    }

    private void checkLogin(final String login, final String password) {
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class, login, password);
        Call<User> call = apiInterface.basicLogin();
        call.enqueue(new Callback<User >() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    getDepartments(login, password);
                } else {
                    showDialog(mContext, getString(R.string.dialog_title_no_auth), getString(R.string.dialog_message_no_auth));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showDialog(mContext, getString(R.string.dialog_title_server_failed), getString(R.string.dialog_message_server_failed));
            }
        });
    }

    private void getDepartments(String login, String password) {
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class, login, password);
        Call<List<Department>> call = apiInterface.getDepartments();
        call.enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                if (response.isSuccessful()) {
                    DepartmentsListFragment departmentsListFragment = new DepartmentsListFragment();
                    departmentsListFragment.setData(response.body());
                    addFragment(departmentsListFragment, false);
                } else {
                    if(response.errorBody() != null) showDialog(mContext, getString(R.string.dialog_title_error), response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                showDialog(mContext, getString(R.string.dialog_title_server_failed), getString(R.string.dialog_message_server_failed));
            }
        });
    }

    @Override
    public void onLogin(String login, String password) {
        if(NetworkUtils.networkIsAvailable(this)) checkLogin(login, password);
        else showDialog(this, getString(R.string.dialog_title_no_network), getString(R.string.dialog_message_no_network));
    }

    public static void showDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
