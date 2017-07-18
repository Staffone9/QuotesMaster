package com.example.staffonechristian.quotesmaster;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by staffonechristian on 2017-07-16.
 */

public class CheckUser {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public void CheckUserInDatabase(){
        mAuth.addAuthStateListener(mAuthListener);


    }
}
