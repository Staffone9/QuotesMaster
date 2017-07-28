package com.example.staffonechristian.quotesmaster;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by staffonechristian on 2017-07-23.
 */

public class QuoteIntelligence {

    String key;
    QuoteData quoteData;
    public void LikesAdd(String tempKey,String tempCategory)
    {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference updateRef =  databaseReference.child("Quote").child(tempCategory);
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
}
