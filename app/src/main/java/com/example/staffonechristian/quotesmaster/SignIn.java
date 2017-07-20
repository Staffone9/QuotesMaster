package com.example.staffonechristian.quotesmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SignIn extends AppCompatActivity {

    private SignInButton googleButton;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private static String TAG="Main_Activity";
    private FirebaseAuth.AuthStateListener mAuthListener;
    boolean flag;
    UserList userListVar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userListVar = new UserList();
        mAuth = FirebaseAuth.getInstance();

        googleButton = (SignInButton) findViewById(R.id.sign_in_button);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null)
                {
//                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                    startActivity(intent);
                    FirebaseUser userOne = firebaseAuth.getCurrentUser();
                }
            }
        };
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this,
                new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), "Error", Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
        flag=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        flag=true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void SignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            CreateUser();
                              Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                              startActivity(intent);
                            Toast.makeText(getApplicationContext(),"Welcome "+user.getDisplayName(), Toast.LENGTH_SHORT).show();



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Authentication failed", Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }
                });
    }

    private void CreateUser() {


        DatabaseReference referenceWriteOne = FirebaseDatabase.getInstance().getReference();

        referenceWriteOne.child("Quote").child("UserData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = mAuth.getCurrentUser().getEmail();
                for (DataSnapshot  data: dataSnapshot.getChildren()) {
                    System.out.println("----->data :"+data.toString());
                    flag=true;
//                    if(data.child(email).exists())
//                    {
//                        Toast.makeText(getApplicationContext(),"User Exist", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(getApplicationContext(),"User does not Exist", Toast.LENGTH_SHORT).show();
//                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        if(flag){
            DatabaseReference referenceWrite = FirebaseDatabase.getInstance().getReference();
            String email = mAuth.getCurrentUser().getEmail();
            String uid = mAuth.getCurrentUser().getUid();
            DatabaseReference drWrite = referenceWrite.child("Quote").child("users").child(uid);
                            UserData userDataObject = new UserData(email);
                            drWrite.setValue(userDataObject);

        }else {

        }



    }

}
