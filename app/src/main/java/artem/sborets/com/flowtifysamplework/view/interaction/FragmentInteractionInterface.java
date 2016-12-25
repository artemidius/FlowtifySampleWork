package artem.sborets.com.flowtifysamplework.view.interaction;


import java.util.List;

import artem.sborets.com.flowtifysamplework.model.Department;

public interface FragmentInteractionInterface {
    void onLogin(String login, String password);
    void onLogout();
    void loginResponse(String response);
    void departmentsResponse(String response, List<Department> departmentsList);

}
