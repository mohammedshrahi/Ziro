package com.slplex.ziro.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
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

import androidx.appcompat.app.AppCompatActivity;


public class AccountKitLogin extends AppCompatActivity {
//    String TAG="loginDebug";
//    String type;
//    public static final String TYPE_RESPONSE="type_response";
//    public static final String PASSWORD="PASSWORD";
//    public static final String REGISTER="REGISTER";
//    public static final String NAME="REGISTER";
//    public static final String PHONE="REGISTER";
//    public static final String REGISTER_PASSWORD="REGISTER";
//
//
//    int APP_REQUEST_CODE = 99;
//    @BindView(R.id.progressBar)
//    ProgressBar progress;
//    boolean phoneLogged=false;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_accountkit_login);
//        ButterKnife.bind(this);
//        type=getIntent().getStringExtra(TYPE_RESPONSE);
//
//
//        progress.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if(!phoneLogged)
//                {
//                    phoneLogged=true;
//                    phoneLogin();
//                }
//            }
//        });
//
//    }
//
//    public void phoneLogin() {
//        final Intent intent = new Intent(this, AccountKitActivity.class);
//        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
//                new AccountKitConfiguration.AccountKitConfigurationBuilder(
//                        LoginType.PHONE,
//                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
//        // ... perform additional configuration ...
//        intent.putExtra(
//                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
//                configurationBuilder.build());
//        startActivityForResult(intent, APP_REQUEST_CODE);
//    }
//    @Override
//    protected void onActivityResult(
//            final int requestCode,
//            final int resultCode,
//            final Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.i(TAG, "onActivityResult: ");
//        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
//            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
//            String toastMessage;
//            if (loginResult.getError() != null) {
//                toastMessage = loginResult.getError().getErrorType().getMessage();
//                Log.i(TAG, "onActivityResult: "+loginResult.getError().toString());
//            } else if (loginResult.wasCancelled()) {
//                toastMessage = "Login Cancelled";
//            } else {
//                if (loginResult.getAccessToken() != null) {
//                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
//                    Log.i(TAG, "onActivityResult: ");
//                } else {
//                    toastMessage = String.format(
//                            "Success:%s...",
//                            loginResult.getAuthorizationCode().substring(0,10));
//                }
//
//                // If you have an authorization code, retrieve it from
//                // loginResult.getAuthorizationCode()
//                // and pass it to your server and exchange it for an access token.
//
//                // Success! Start your next activity...
//                Log.i(TAG, "onActivityResult: success");
//                getUserClient();
//            }
//
//            // Surface the result to your user in an appropriate way.
//            Toast.makeText(
//                    this,
//                    toastMessage,
//                    Toast.LENGTH_LONG)
//                    .show();
//        }
//    }
//    public void emailLogin(final View view) {
//        final Intent intent = new Intent(this, AccountKitActivity.class);
//        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
//                new AccountKitConfiguration.AccountKitConfigurationBuilder(
//                        LoginType.EMAIL,
//                        AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
//        // ... perform additional configuration ...
//        intent.putExtra(
//                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
//                configurationBuilder.build());
//        startActivityForResult(intent, APP_REQUEST_CODE);
//    }
//    void getUserClient()
//    {
//        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
//            @Override
//            public void onSuccess(final Account account) {
//                // Get Account Kit ID
//                String accountKitId = account.getId();
//                Log.i(TAG, "onSuccess: "+accountKitId);
//
//                // Get phone number
//                PhoneNumber phoneNumber = account.getPhoneNumber();
//                if (phoneNumber != null) {
//                    String phoneNumberString = phoneNumber.toString();
//                    Log.i(TAG, "onSuccess: "+phoneNumberString);
//                }
//
//                // Get email
//                String email = account.getEmail();
//                if(type.equals(PASSWORD))
//                {
//                    String phoneNumberString = phoneNumber.toString();
//
//                    passwordFinish(accountKitId,phoneNumberString);
//                }
//                else
//                {
//                    if (phoneNumber != null) {
//                        String phoneNumberString = phoneNumber.toString();
//                        String name = getIntent().getStringExtra(NAME);
//                        String password=getIntent().getStringExtra(PASSWORD);
//                        register(name,password,phoneNumberString,accountKitId);
//
//                    }
//                }
//            }
//
//            @Override
//            public void onError(final AccountKitError error) {
//                // Handle Error
//                Log.i(TAG, "onError: "+error);
//            }
//        });
//
//    }
//    void passwordFinish(final String id, String number ) {
//        CheckMobileExist checkMobileExist = new CheckMobileExist(this);
//        checkMobileExist.setListner(new Listner() {
//            @Override
//            public void onSuccess(Object o) {
//                MobileExistResponse mobileExistResponse =(MobileExistResponse)o;
//                Log.i(TAG, "onSuccess: "+mobileExistResponse.getCode());
//                if(!mobileExistResponse.isSuccess()) {
//                    Intent i = new Intent(getApplicationContext(), NewPasswordActivity.class);
//                    i.putExtra(NewPasswordActivity.USER_ID, id);
//                    startActivity(i);
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(),mobileExistResponse.getStatus(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
//        checkMobileExist.check(number);
//    }
//    void registerFinish()
//    {
//        UserDetailsBehave userDetailsBehave = new UserDetailsBehave(this);
//        userDetailsBehave.setListner(new Listner() {
//            @Override
//            public void onSuccess(Object o) {
//               new LoadingBehave(AccountKitLogin.this).changeLanguage(AccountKitLogin.this);
//                Log.i(TAG, "onSuccess: ");
//            }
//
//            @Override
//            public void onError() {
//                Log.i(TAG, "onError: ");
//                UtilsToast.showErrorToast(getApplicationContext());
//            }
//        });
//        userDetailsBehave.getUserDetails();
//    }
//    void register(String name, String password, String phone, String id)
//    {
//        Log.i(TAG, "register: "+id);
//        Log.i(TAG, "register: "+phone);
//        RegisterRequst r = new RegisterRequst();
//        r.setName(name);
//        r.setPassword(password);
//        r.setPhone(phone);
//        r.setSocial_id(id);
//        Log.i(TAG, "test: ");
//        APIInterface apiInterface = Singleton.getInstance("",getApplicationContext()).getApiInterface("","",getApplicationContext());
//        Call<RegisterResponse> call= apiInterface.register(r);
//        call.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                Log.i(TAG, "onResponse: "+response.code());
//                if(response.code()==200)
//                {
//
//                    if(response.body().isSuccess())
//                    {
//                    }
//                    else
//                    {
//                        Log.i(TAG,response.body().getError());
//
//                    }
//                    new Access(getApplicationContext()).saveAccessToken(response.body().getToken());
//                    registerFinish();
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                Log.i(TAG, "onFailure: "+t.getMessage());
//                t.printStackTrace();
//            }
//        });
//
//    }
}
