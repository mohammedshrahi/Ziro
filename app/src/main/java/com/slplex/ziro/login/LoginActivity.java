package com.slplex.ziro.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.slplex.ziro.MainActivity;
import com.slplex.ziro.R;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.LoginView {
    String TAG = "LoginActivityDebug";
    int APP_REQUEST_CODE = 999;
    Button login;
    EditText name;
    View body;
    ProgressBar progressBar;
    LoginPresenter loginPresenter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        name=findViewById(R.id.name);
        body=findViewById(R.id.body);
        progressBar=findViewById(R.id.progress);
        LoginPresenter loginPresenter=new LoginPresenter(this,this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().equals(""))
                {
                   Toast.makeText(getApplicationContext(),"Add your name please",Toast.LENGTH_SHORT).show();;
                   return;
                }
                body.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                phoneLogin();
            }
        });
    }

    public void phoneLogin() {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: ");
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                Log.i(TAG, "onActivityResult: " + loginResult.getError().toString());
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                    Log.i(TAG, "onActivityResult: ");
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
                Log.i(TAG, "onActivityResult: success");
                getUserClient("momo");
            }

            // Surface the result to your user in an appropriate way.
            Toast.makeText(
                    this,
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    void getUserClient(String name) {
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                // Get Account Kit ID
                String accountKitId = account.getId();
                Log.i(TAG, "onSuccess: " + accountKitId);

                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                if (phoneNumber != null) {
                    String phoneNumberString = phoneNumber.toString();
                    Log.i(TAG, "onSuccess: " + phoneNumberString);
                }

                // Get email
                String email = account.getEmail();

                if (phoneNumber != null) {
                    String phoneNumberString = phoneNumber.toString();
                    register(name, phoneNumberString);

                }

            }

            @Override
            public void onError(final AccountKitError error) {
                // Handle Error
                Log.i(TAG, "onError: " + error);
            }
        });

    }

    void register(String name, String phone) {
        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        if(loginPresenter==null)loginPresenter=new LoginPresenter(this,this);
        loginPresenter.login(user);

    }

    @Override
    public void showBody() {

    }

    @Override
    public void hideBody() {

    }

    @Override
    public void showProgess() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void login(User user) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("name",user.name);
        i.putExtra("phone",user.phone);
        startActivity(i);
        finish();
    }

    @Override
    public void haveUser(User user) {
        if(user!=null)
        {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("name",user.name);
            i.putExtra("phone",user.phone);
            startActivity(i);
            finish();
        }

    }
}
