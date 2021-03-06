package com.egnify.University_demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.egnify.University_demo.Activities.Vice_c_main;
import com.egnify.University_demo.Activities.new_home_activity;
import com.egnify.University_demo.Activities.new_home_hod;
import com.egnify.University_demo.Activities.notifcations;
import com.egnify.University_demo.Network.AppController;
import com.egnify.University_demo.gcm.GCMIntentService;
import com.egnify.University_demo.helper.SQLiteHandler_emp_info;
import com.egnify.University_demo.helper.SessionManager;
import com.egnify.University_demo.model.app_url;
import com.egnify.University_demo.pojos.User;
import com.egnify.University_demo.utils.Config;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements View.OnTouchListener {


    // Database Name
    private static final String DATABASE_NAME = "android_api";
    // private String TAG = LoginActivity.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    // Login table name
    private static final String TABLE_LOGIN = "login";
    private static final String TAG = "GCM MESSAGE";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    // UI referencesjava.lang.String.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private SQLiteHandler_emp_info db;
    private SessionManager session;
    private String id_type;
    private String token;

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppController.getInstance().getPrefManager().getUser() != null) {
            startActivity(new Intent(this, notifcations.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        //gcm registration

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    token = intent.getStringExtra("token");

                    Toast.makeText(getApplicationContext(), "GCM registration token: " + token, Toast.LENGTH_LONG).show();

                }
            }
        };

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler_emp_info(getApplicationContext());
        if (session.isLoggedIn()) {
            HashMap<String, String> user = db.getcontactDetails();
            String e_id = user.get("e_id");
            String admin_rights = user.get("admin_rights");
            if (e_id.equals("1")) {
                Intent intent = new Intent(LoginActivity.this, Vice_c_main.class);
                intent.putExtra("click", "0");
                intent.putExtra("type", admin_rights);
                intent.putExtra("id", Integer.valueOf(e_id));
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                finish();
            } else if (e_id.equals("2")) {
                Intent intent = new Intent(LoginActivity.this, new_home_hod.class);
                intent.putExtra("click", "0");
                intent.putExtra("type", admin_rights);
                intent.putExtra("id", Integer.valueOf(e_id));
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, new_home_activity.class);
                intent.putExtra("click", "0");
                intent.putExtra("type", admin_rights);
                intent.putExtra("id", Integer.valueOf(e_id));
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                finish();
            }


        }
        // Mint.initAndStartSession(LoginActivity.this, "34116d9d");
        //  Mint.initAndStartSession(LoginActivity.this, "a3d4e3a8");
        //Mint.initAndStartSession(LoginActivity.this, "1d3aaa2c");

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();

                if (!(email.trim().length() > 0)) {
                    mEmailView.setError(getString(R.string.error_field_required));
                    mEmailView.requestFocus();
                } else if (!(password.trim().length() > 3)) {
                    // Prompt User to enter credentials
                    mPasswordView.setError(getString(R.string.error_invalid_password));
                    mPasswordView.requestFocus();
                } else {
                    //dummy_attempt();
                    attemptLogin();
                }


            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        if (checkPlayServices()) {
            registerGCM();
        }
    }

    private void dummy_attempt() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        session.setLogin(true);
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        if (email.equals("1")) {
            String e_id = "1";
            String emp_name = "Kirshna";
            String designation = "VC";
            String mobile = "1234567";
            String email_s = "vc@gmail.com";
            String admin_rights = "1";
            db.addcontactinfo(e_id, emp_name, designation, mobile, email_s, admin_rights);
            Intent intent = new Intent(LoginActivity.this, Vice_c_main.class);
            intent.putExtra("type", "1");
            intent.putExtra("click", "0");
            intent.putExtra("id", 1);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            finish();
        } else if (email.equals("2")) {
            String e_id = "2";
            String emp_name = "Mohan Reddy";
            String designation = "Faculty";
            String mobile = "1234567";
            String email_s = "hod@gmail.com";
            String admin_rights = "2";
            db.addcontactinfo(e_id, emp_name, designation, mobile, email_s, admin_rights);
            Intent intent = new Intent(LoginActivity.this, new_home_hod.class);
            intent.putExtra("type", "2");
            intent.putExtra("click", "0");
            intent.putExtra("id", 2);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            finish();
        } else {

            String e_id = "3";
            String emp_name = "Srinivas Reddy";
            String designation = "Faculty";
            String mobile = "1234567";
            String email_s = "faculty@gmail.com";
            String admin_rights = "3";
            db.addcontactinfo(e_id, emp_name, designation, mobile, email_s, admin_rights);
            Intent intent = new Intent(LoginActivity.this, new_home_activity.class);
            intent.putExtra("type", "3");
            intent.putExtra("id", 3);
            intent.putExtra("click", "0");
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            finish();
        }

    }

    private void attemptLogin() {


        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid email address.
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the User login attempt.
            showProgress(true);
            login(email, password);
            // userlogin(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void login(final String email, final String name) {


        StringRequest strReq = new StringRequest(Request.Method.POST,
                app_url.LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error flag
                    if (obj.getBoolean("error") == false) {
                        // user successfully logged in

                        JSONObject userObj = obj.getJSONObject("user");
                        User user = new User(userObj.getString("user_id"),
                                userObj.getString("name"),
                                userObj.getString("email"));

                        // storing user in shared preferences
                        AppController.getInstance().getPrefManager().storeUser(user);

                        // start main activity
                        startActivity(new Intent(getApplicationContext(), notifcations.class));
                        finish();

                    } else {
                        // login error - simply toast the message
                        Toast.makeText(getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                // params.put("token", token);


                Log.e(TAG, "params: " + params.toString());
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void userlogin(String email, String password) {
        final String mEmail;
        final String mPassword;
        mEmail = email;
        mPassword = password;
        String tag_string_req = "req_login";

        showProgress(true);
        String url = app_url.URL_LOGIN_new + mEmail + "/" + mPassword;
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("LOGIN", "Login Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    int sc = jObj.getInt("success");
                    // Check for error node in json
                    if (sc == 1) {
                        // User successfully logged in
                        // Create login session
                        session.setLogin(true);
                        JSONObject user = jObj.getJSONObject("User");
                        String e_id = user.getString("e_id");
                        String emp_name = user.getString("emp_name");
                        String designation = user.getString("designation");
                        String mobile = user.getString("mobile");
                        String email = user.getString("email");
                        String admin_rights = user.getString("admin_rights");


                        db.addcontactinfo(e_id, emp_name, designation, mobile, email, admin_rights);

                        Bundle args = new Bundle();

                        Intent intent = new Intent(LoginActivity.this, new_home_activity.class);
                        intent.putExtra("click", "0");
                        intent.putExtra("type", admin_rights);
                        intent.putExtra("id", Integer.valueOf(e_id));
                        startActivity(intent);
                        finish();


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        if (errorMsg.equals("Password Incorrect !")) {
                            mPasswordView.setError(errorMsg);
                            mPasswordView.requestFocus();
                        } else {
                            mEmailView.setError(errorMsg);
                            mEmailView.requestFocus();
                        }


                        showProgress(false);

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOGIN", "Login Error: " + error.getMessage());

                mEmailView.setError(getString(R.string.error_invalid_email));
                mEmailView.requestFocus();
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                showProgress(false);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    // starting the service to register with GCM
    private void registerGCM() {
        Intent intent = new Intent(this, GCMIntentService.class);
        intent.putExtra("key", "register");
        startService(intent);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported. Google Play Services not installed!");
                Toast.makeText(getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));
    }
}

