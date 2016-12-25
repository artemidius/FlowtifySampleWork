package artem.sborets.com.flowtifysamplework.view.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import artem.sborets.com.flowtifysamplework.R;
import artem.sborets.com.flowtifysamplework.model.Department;
import artem.sborets.com.flowtifysamplework.presenter.rest.ApiService;
import artem.sborets.com.flowtifysamplework.presenter.utils.NetworkUtils;
import artem.sborets.com.flowtifysamplework.presenter.utils.Properties;
import artem.sborets.com.flowtifysamplework.presenter.utils.SharedPreferencesUtils;
import artem.sborets.com.flowtifysamplework.view.fragments.DepartmentsListFragment;
import artem.sborets.com.flowtifysamplework.view.fragments.InProgressFragment;
import artem.sborets.com.flowtifysamplework.view.fragments.LoginFragment;
import artem.sborets.com.flowtifysamplework.view.interaction.FragmentInteractionInterface;

import static artem.sborets.com.flowtifysamplework.presenter.utils.Properties.PASSWORD;
import static artem.sborets.com.flowtifysamplework.presenter.utils.Properties.USERNAME;

public class MainActivity extends AppCompatActivity implements FragmentInteractionInterface {

    Context mContext;
    LoginFragment loginFragment = new LoginFragment();
    InProgressFragment inProgressFragment = new InProgressFragment();
    SharedPreferencesUtils sharedPreferencesUtils;
    ApiService apiService;
    boolean loginPrechecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
        apiService = new ApiService(this, sharedPreferencesUtils);
        addFragment(inProgressFragment, false);
        attemptConnection();
    }

    //when activity starts it checks if there is a connection
    //if true it attempts to log in with credentials saved to shared preferences
    private void attemptConnection() {
        if (NetworkUtils.networkIsAvailable(this))
            apiService.checkLogin(sharedPreferencesUtils.getFromSharedPrefsString(USERNAME), sharedPreferencesUtils.getFromSharedPrefsString(PASSWORD));
        else {
            showDialog(this, getString(R.string.dialog_title_no_network), getString(R.string.dialog_message_no_connection_short), dialogListener);
        }
    }

    //method shows fragment passed in parameters
    private void addFragment(Fragment fragment, boolean backstack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (backstack)
            fragmentManager.beginTransaction().replace(R.id.activity_main, fragment).addToBackStack(null).commitAllowingStateLoss();
        else
            fragmentManager.beginTransaction().replace(R.id.activity_main, fragment).commitAllowingStateLoss();
    }

    //this method is called when user taps *LOG IN* button
    @Override
    public void onLogin(String login, String password) {

        if (NetworkUtils.networkIsAvailable(this)) {
            addFragment(inProgressFragment, false);
            apiService.checkLogin(login, password);
        } else
            showDialog(this, getString(R.string.dialog_title_no_network), getString(R.string.dialog_message_no_network), null);
    }

    //this method is called when user taps *LOG OUT* button
    @Override
    public void onLogout() {
        sharedPreferencesUtils.clearCredentials();
        addFragment(loginFragment, false);
    }

    //result of backend request
    @Override
    public void loginResponse(String response) {
        switch (response) {
            case Properties.Responses.LOGIN_OK:
                apiService.getDepartments(sharedPreferencesUtils.getFromSharedPrefsString(USERNAME), sharedPreferencesUtils.getFromSharedPrefsString(PASSWORD));
                break;
            case Properties.Responses.LOGIN_DENIED:
                addFragment(loginFragment, false);

                //This method is called on activity start and also after user tries to log in
                //we use *loginPrechecked* variable to define current situation
                //when we fail`to login on activity start we show login fragment
                if (loginPrechecked)
                    showDialog(mContext, getString(R.string.dialog_title_no_auth), getString(R.string.dialog_message_no_auth), null);
                else loginPrechecked = true;
                break;
            case Properties.Responses.LOGIN_SERVER_FAIL:
                showDialog(mContext, getString(R.string.dialog_title_server_failed), getString(R.string.dialog_message_server_failed), dialogListener);
                break;
        }
    }

    //result of backend request
    @Override
    public void departmentsResponse(String response, List<Department> departmentsList) {
        switch (response) {
            case Properties.Responses.DEPARTMENTS_OK:
                DepartmentsListFragment departmentsListFragment = new DepartmentsListFragment();
                if (departmentsList != null) departmentsListFragment.setData(departmentsList);
                addFragment(departmentsListFragment, false);
                break;
            case Properties.Responses.DEPARTMENTS_ERROR:
                addFragment(loginFragment, false);
                break;
            case Properties.Responses.DEPARTMENTS_FAIL:
                addFragment(loginFragment, false);
                loginPrechecked = false;
                showDialog(mContext, getString(R.string.dialog_title_server_failed), getString(R.string.dialog_message_server_failed), dialogListener);
                break;
        }
    }

    public static void showDialog(Context context, String title, String message, DialogInterface.OnClickListener dialogListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton(android.R.string.ok, dialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            attemptConnection();
        }
    };
}