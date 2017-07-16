package com.example.staffonechristian.quotesmaster;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        recyclerView.setAdapter(adapter);

        prepareQuotes();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void prepareQuotes(){
        //Code by Staffone
        getList();
        for(int i=0;i<6;i++)
        {

            DatabaseReference quoteReference = reference.child("Quote").child(mData.listOfCategory.get(i));

            quoteReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot individual : dataSnapshot.getChildren()) {
                        String quote = individual.child("quote").getValue(String.class);
                        String author = individual.child("author").getValue(String.class);
                        mData = new QuoteData(quote, author);
                        quotesList.add(mData);

                       // Toast.makeText(getApplicationContext(),"Quote"+quote,Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    pBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


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
