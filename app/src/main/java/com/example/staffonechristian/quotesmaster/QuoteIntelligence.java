package com.example.staffonechristian.quotesmaster;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by staffonechristian on 2017-07-23.
 */

public class QuoteIntelligence {

    String key;
    QuoteData quoteData;
    static boolean flag=false;


    public void LikesAdd(String tempKey,String tempCategory)
    {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference updateRef =  databaseReference.child("Quote").child(tempCategory);
        UserData.userLikedQuotes.add(tempKey);
        updateRef.orderByChild("key").equalTo(tempKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // System.out.println("------>DatasnapShot"+dataSnapshot.toString());

                for (DataSnapshot dataSnapShotOne:dataSnapshot.getChildren()
                     ) {
                    int likes = dataSnapShotOne.child("quoteLikes").getValue(Integer.class);
                    if(QuotesAdapter.heartState==true)
                    {
                        dataSnapShotOne.child("quoteLikes").getRef().setValue(++likes);
                    }else{
                        dataSnapShotOne.child("quoteLikes").getRef().setValue(--likes);
                    }

                    System.out.println("------>likessss"+likes);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public static void EndUserLikeUpdate(){
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRefLikes =  databaseReference.child("Quote").child("users");

        userRefLikes.orderByChild("userEmailID").equalTo(auth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshotfor : dataSnapshot.getChildren())
                {
                    dataSnapshotfor.child("userLikedQuotes").getRef().setValue(UserData.userLikedQuotes);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void UserViewsAdd(){
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference updateRef =  databaseReference.child("Quote").child("users");

        updateRef.orderByChild("userEmailID").equalTo(auth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshotfor : dataSnapshot.getChildren())
                {
                   dataSnapshotfor.child("userViewedQuotes").getRef().setValue(UserData.userViewedQuotes);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public  boolean UserGet(){
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        final SignIn signIn = new SignIn();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference updateRef =  databaseReference.child("Quote").child("users");

        updateRef.orderByChild("userEmailID").equalTo(auth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshotfor : dataSnapshot.getChildren())
                {
                    if(dataSnapshotfor !=  null)
                    {
                        UserData userData= dataSnapshotfor.getValue(UserData.class);
                        flag = true;




                    }else {
                        signIn.CreateUser();
                        flag=true;
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println("Flag--->"+flag);
        return flag;
    }


}
