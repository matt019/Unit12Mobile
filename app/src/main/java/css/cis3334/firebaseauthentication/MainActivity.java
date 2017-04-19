package css.cis3334.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static css.cis3334.firebaseauthentication.R.id.text;
import static java.lang.reflect.Modifier.FINAL;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    final String TAG = "";
    final String Text = "Authentication failed";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);
        mAuth = FirebaseAuth.getInstance();



        /*
        This method creates a button that allows the user to sign in
         */
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Log.d("CIS3334", "normal login "); // main login
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        /*
        This method creates a button that allows the user to create an account
         */
        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CIS3334", "Create Account "); // button to create an account
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        /*
        This method creates a button that allows the user to sign in to Google
         */
        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CIS3334", "Google login "); //button to login to google
                googleSignIn();
            }
        });

        /*
        This method creates a button that allows the user to sign out
         */
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CIS3334", "Logging out - signOut "); //button to logout
                signOut();
            }
        });


        /*
        This method logged when a user was signed in or out
         */
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

            }
        };




    }

    /*
        This method works at the beginning of an event
         */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /*
        This method stops an event
         */
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /*
        This method allows the user to sign in
         */
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       // Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful()); // Generic status message

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            textViewStatus.setText(Text);
                            //Toast.makeText(MainActivity.this, "Authentication failed.",
                                    //Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    /*
       This method adds functionality to the sign in button
        */
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful()); //Sign in with email

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "signInWithEmail", task.getException()); //Sign in with email
                            textViewStatus.setText(Text);
                            //Toast.makeText(MainActivity.this, "Authentication failed.",
                                    //Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    /*
       This method lets the user sign out
        */
    private void signOut () {
        mAuth.signOut();

    }

    /*
       This method allows the user sign into google
        */
    private void googleSignIn() {

    }




}