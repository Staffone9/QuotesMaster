package com.example.staffonechristian.quotesmaster;

import android.content.Intent;
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
    int likes;
    static boolean flag = false;
    public static boolean myfg;

    public int LikesAdd(String tempKey, String tempCategory, QuoteData myData)
    {
        quoteData = myData;
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference updateRef =  databaseReference.child("Quote").child(tempCategory);
        if(UserData.userLikedQuotes.contains(tempKey))
        {
            int index =UserData.userLikedQuotes.indexOf(tempKey);
            UserData.userLikedQuotes.remove(index);
            flag = false;
            myfg = false;
        }else if(!UserData.userLikedQuotes.contains(tempKey)){
            UserData.userLikedQuotes.add(tempKey);
            flag = true;
            myfg = true;
        }

        //flag=heart;
        updateRef.orderByChild("key").equalTo(tempKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // System.out.println("------>DatasnapShot"+dataSnapshot.toString());

                for (DataSnapshot dataSnapShotOne:dataSnapshot.getChildren()
                     ) {
                    int view = dataSnapShotOne.child("quoteViews").getValue(Integer.class);
                    float prio = dataSnapShotOne.child("priorityScore").getValue(Float.class);
                    likes = dataSnapShotOne.child("quoteLikes").getValue(Integer.class);

                    if(flag==true)
                    {
                        MainActivity.likeFlag=true;
                        likes=likes+1;
                        dataSnapShotOne.child("quoteLikes").getRef().setValue(likes);
                        //myImg = R.drawable.like;
                    }else{
                        //quoteData.setQuoteLikes(--likes);
                        MainActivity.likeFlag=true;
                        likes=likes-1;
                        dataSnapShotOne.child("quoteLikes").getRef().setValue(likes);
                        //myImg = R.drawable.unlike;
                    }
                    if(likes!=0)
                    {
                        prio = (float) view/likes;
                    }
                    dataSnapShotOne.child("priorityScore").getRef().setValue(prio);
                    System.out.println("------>User liked quotes------>"+UserData.userLikedQuotes.toString());
                    quoteData.setQuoteLikes(likes);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (myfg == true){
            return R.drawable.like;
        }
        else{
            return R.drawable.unlike;
        }
    }

    public static void EndUserLikeUpdate(){
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRefLikes =  databaseReference.child("Quote").child("users");
        System.out.println("------>EndUserLikeUpdate here------>");
        userRefLikes.orderByChild("userEmailID").equalTo(auth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshotfor : dataSnapshot.getChildren())
                {
                    dataSnapshotfor.child("userLikedQuotes").getRef().setValue(UserData.userLikedQuotes);
                    System.out.println("------>EndUserLikeUpdate updater order------>");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void quoteViewAdd(String category,String key){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference updateRef =  databaseReference.child("Quote").child(category);
        updateRef.orderByChild("key").equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // System.out.println("------>DatasnapShot"+dataSnapshot.toString());

                for (DataSnapshot dataSnapShotOne:dataSnapshot.getChildren()) {
                   int view = dataSnapShotOne.child("quoteViews").getValue(Integer.class);
                    float prio = dataSnapShotOne.child("priorityScore").getValue(Float.class);
                    int likes = dataSnapShotOne.child("quoteLikes").getValue(Integer.class);
                    view=view+1;
                    if(likes!=0)
                    {
                        prio = (float) view/likes;
                    }

                      //  MainActivity.likeFlag=true;
                    dataSnapShotOne.child("quoteViews").getRef().setValue(view);
                    dataSnapShotOne.child("priorityScore").getRef().setValue(prio);
                    System.out.println("------>User liked quotes------>"+UserData.userLikedQuotes.toString());

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
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference updateRef =  databaseReference.child("Quote").child("users");

        updateRef.orderByChild("userEmailID").equalTo(auth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshotfor : dataSnapshot.getChildren())
                {
                   dataSnapshotfor.child("userViewedQuotes").getRef().setValue(UserData.userViewedQuotes);
                    dataSnapshotfor.child("viewQuoteMap").getRef().setValue(UserData.viewQuoteMap);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }





}
