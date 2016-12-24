package artem.sborets.com.flowtifysamplework.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import artem.sborets.com.flowtifysamplework.R;
import artem.sborets.com.flowtifysamplework.view.FragmentInteractionInterface;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment {

    @BindView(R.id.loginInput)
    TextView loginInput;
    @BindView(R.id.passwordInput)
    TextView passwordInput;
    @BindView(R.id.submitButton)
    Button submitButton;

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginInput.getText().toString();
                String password =  passwordInput.getText().toString();
                FragmentInteractionInterface interactionInterface = (FragmentInteractionInterface) getContext();
                interactionInterface.onLogin(login, password);
            }
        });

        return view;
    }

}
