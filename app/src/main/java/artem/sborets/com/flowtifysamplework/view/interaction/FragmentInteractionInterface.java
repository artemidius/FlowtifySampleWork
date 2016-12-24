package artem.sborets.com.flowtifysamplework.view.interaction;


import java.util.List;

import artem.sborets.com.flowtifysamplework.model.Department;
import artem.sborets.com.flowtifysamplework.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FragmentInteractionInterface {
    void onLogin(String login, String password);
}
