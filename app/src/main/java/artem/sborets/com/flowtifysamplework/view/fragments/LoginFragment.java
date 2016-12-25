package artem.sborets.com.flowtifysamplework.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import artem.sborets.com.flowtifysamplework.R;
import artem.sborets.com.flowtifysamplework.view.interaction.FragmentInteractionInterface;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment {

    @BindView(R.id.loginInput)
    TextView loginInput;
    @BindView(R.id.passwordInput)
    TextView passwordInput;
    @BindView(R.id.submitButton)
    TextView submitButton;

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
                hideKeyboard();
                FragmentInteractionInterface interactionInterface = (FragmentInteractionInterface) getContext();
                interactionInterface.onLogin(login, password);
            }

            private void hideKeyboard() {
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        return view;
    }
}