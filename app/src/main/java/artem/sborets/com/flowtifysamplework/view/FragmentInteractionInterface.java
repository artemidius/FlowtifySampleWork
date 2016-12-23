package artem.sborets.com.flowtifysamplework.view;


import java.util.List;

import artem.sborets.com.flowtifysamplework.model.Department;
import artem.sborets.com.flowtifysamplework.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FragmentInteractionInterface {
    @GET("department/list/backend")
    Call<List<Department>> getDepartments();

    @POST("account/login")
    Call<User> basicLogin();
}
