package com.example.staffonechristian.quotesmaster;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuotesAdapter adapter;
    private List<QuoteData> quotesList;
    QuoteData mData;
    ProgressBar pBar;
    int lastItem=1;
    QuoteIntelligence quoteIntelligence;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    int k;
    int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getList();

        for(int i=0;i < mData.listOfCategory.size() && counter<8 ;i++)
        {
            prepareQuotes(i);
            counter++;

        }

        k=0;
        System.out.println("--->Array ma value check"+UserData.userViewedQuotes.toString()+"---->Size"+UserData.userViewedQuotes.size());
        //code by JamesBond007

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    Intent intent = new Intent(getApplicationContext(),SignIn.class);
                    startActivity(intent);
                }
            }
        };

        if(UserData.userViewedQuotes.contains("KpXFiAB6FNkJ2jhtnEf"))
        {
            System.out.println("-------Contain");

        }else {
            System.out.println("--------Do not Contain");
        }
        //Code by Anjali
        pBar = (ProgressBar)findViewById(R.id.progress_bar);
        pBar.setVisibility(View.VISIBLE);
        quotesList = new ArrayList<>();
        adapter = new QuotesAdapter(this,quotesList,false);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        DatabaseReference myRef = reference.child("Quote");


        //adp.setData(this,quotesList);
        //recyclerView.setAdapter(adp);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                lastItem = layoutManager.findLastVisibleItemPosition();
               // Toast.makeText(getApplicationContext(),"visibleItemCount "+visibleItemCount+" totalItemCount"+totalItemCount,Toast.LENGTH_SHORT).show();
             //   System.out.println("------->P lastItem="+lastItem);
            //    System.out.println("just check--->"+ lastItem+"k "+k);
                if(lastItem <= quotesList.size()-1){
                    if(k<5){
                        prepareQuotes(++k);
                    }
                    if(k==5)
                    {
                        k=0;
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);


    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    private void prepareQuotes(int j){
        //Code by Staffone

            DatabaseReference quoteReference = reference.child("Quote").child(mData.listOfCategory.get(j));

            quoteReference.orderByChild("priorityScore").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot individual : dataSnapshot.getChildren()) {
//
                        mData = new QuoteData();
                        mData = individual.getValue(QuoteData.class);

                        if(UserData.userViewedQuotes.size() > 0 )
                        {

                            if(!UserData.userViewedQuotes.contains(mData.getKey()))
                            {
                                System.out.println("aa dofu ni size--------->"+UserData.userViewedQuotes.size());
                                quotesList.add(mData);
                                adapter.notifyDataSetChanged();
                                UserData.userViewedQuotes.add(mData.getKey());
                            }
                        }else if(mData.getKey()!= null) {
                            quotesList.add(mData);
                            adapter.notifyDataSetChanged();
                            System.out.println("Key--------->"+mData.getKey());
                            UserData.userViewedQuotes.add(mData.getKey());

                        }else {
                            System.out.println("aa else ni size--------->"+UserData.userViewedQuotes.size());
                        }



                        //adp.notifyDataSetChanged();
                       // Toast.makeText(getApplicationContext(),"Quote"+quote,Toast.LENGTH_SHORT).show();
                    }

                    recyclerView.setVisibility(View.VISIBLE);
                    pBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

//        System.out.println("key "+UserData.userViewedQuotes.toString());
    }

    @Override
    protected void onPause() {
        QuoteIntelligence.UserViewsAdd();
        QuoteIntelligence.EndUserLikeUpdate();
        super.onPause();

    }



    @Override
    protected void onDestroy() {
        QuoteIntelligence.UserViewsAdd();
        QuoteIntelligence.EndUserLikeUpdate();
        super.onDestroy();
    }

    public void getList() {
        ArrayList<String> listOfCategory = new ArrayList<>();
        listOfCategory.add("Motivational");
        listOfCategory.add("Life");
        listOfCategory.add("Inspirational");
        listOfCategory.add("Friendship");
        listOfCategory.add("Love");
        listOfCategory.add("Positive");
        mData.setListOfCategory(listOfCategory);
    }


}
