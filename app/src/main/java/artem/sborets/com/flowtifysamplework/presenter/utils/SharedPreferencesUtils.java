package artem.sborets.com.flowtifysamplework.presenter.utils;


import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static artem.sborets.com.flowtifysamplework.presenter.utils.Properties.PASSWORD;
import static artem.sborets.com.flowtifysamplework.presenter.utils.Properties.SHARED_PREFERENCES;
import static artem.sborets.com.flowtifysamplework.presenter.utils.Properties.USERNAME;

public class SharedPreferencesUtils {

    protected Context context;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesUtils(Context context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    private void addToSharedPrefs(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getFromSharedPrefsString(String key) {
        return sharedpreferences.getString(key, "");
    }

    public void writeCredentials(String login, String password) {
        addToSharedPrefs(USERNAME, login);
        addToSharedPrefs(PASSWORD, password);
    }

    public void clearCredentials() {
        addToSharedPrefs(USERNAME, "");
        addToSharedPrefs(PASSWORD, "");
    }

}