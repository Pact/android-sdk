package io.catalyze.android.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.base.Optional;

import io.catalyze.sdk.android.Catalyze;
import io.catalyze.sdk.android.CatalyzeRequest;
import io.catalyze.sdk.android.User;
import io.catalyze.sdk.android.UserRequest;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MyApplication mMyApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMyApplication = (MyApplication) getApplication();
        mMyApplication.getRequestQueue().start();

        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Catalyze.INSTANCE.login("2", "catalyzeexample"));
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Catalyze.INSTANCE.logout(MainActivity.this);
            }
        });
    }


    // TODO instead of defining the intent-receiver in manifest & tying it to the activity,
    // register one in code & intercept the redirect to extract data.
    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "Got it");
        final Optional<User> userOptional = Catalyze.INSTANCE.finalizeLogin(this, intent);
        if (userOptional.isPresent()) {
            Log.d(TAG, "in");
            final User user = userOptional.get();
            UserRequest.Builder builder = new UserRequest.Builder(this);
            CatalyzeRequest request = builder.setMethod(CatalyzeRequest.Method.RETRIEVE).setContent
                    (user).setErrorListener
                    (new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }).setListener(new Response.Listener<User>() {
                @Override
                public void onResponse(User response) {
                    Log.d(TAG, response.toString(2));
                    fillUserView(response);
                }
            }).build();

            Log.d(TAG, "gonna request");
            mMyApplication.getRequestQueue().add(request);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void fillUserView(User user) {
        ((TextView) findViewById(R.id.UserView)).setText(user.toString(2));
    }

}
