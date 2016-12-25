package artem.sborets.com.flowtifysamplework.presenter.rest;

import android.content.Context;

import java.util.List;

import artem.sborets.com.flowtifysamplework.model.Department;
import artem.sborets.com.flowtifysamplework.model.User;
import artem.sborets.com.flowtifysamplework.presenter.utils.Properties;
import artem.sborets.com.flowtifysamplework.presenter.utils.SharedPreferencesUtils;
import artem.sborets.com.flowtifysamplework.view.interaction.FragmentInteractionInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiService {

    private SharedPreferencesUtils sharedPreferencesUtils;
    private FragmentInteractionInterface fragmentInteractionInterface;

    public ApiService(Context context, SharedPreferencesUtils sharedPreferencesUtils) {
        this.sharedPreferencesUtils = sharedPreferencesUtils;
        fragmentInteractionInterface = (FragmentInteractionInterface) context;
    }

    public void checkLogin(final String login, final String password) {
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class, login, password);
        Call<User> call = apiInterface.basicLogin();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    sharedPreferencesUtils.writeCredentials(login, password);
                    fragmentInteractionInterface.loginResponse(Properties.Responses.LOGIN_OK);
                } else {
                    fragmentInteractionInterface.loginResponse(Properties.Responses.LOGIN_DENIED);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                fragmentInteractionInterface.loginResponse(Properties.Responses.LOGIN_SERVER_FAIL);
            }
        });
    }

    public void getDepartments(String login, String password) {
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class, login, password);
        Call<List<Department>> call = apiInterface.getDepartments();
        call.enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                if (response.isSuccessful()) {
                    fragmentInteractionInterface.departmentsResponse(Properties.Responses.DEPARTMENTS_OK, response.body());
                } else {
                    fragmentInteractionInterface.departmentsResponse(Properties.Responses.DEPARTMENTS_ERROR, null);
                }
            }
            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                fragmentInteractionInterface.departmentsResponse(Properties.Responses.DEPARTMENTS_FAIL, null);
            }
        });
    }
}