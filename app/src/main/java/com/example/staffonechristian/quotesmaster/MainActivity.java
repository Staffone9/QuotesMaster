package com.example.staffonechristian.quotesmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(getApplicationContext(),Category.class);
        startActivity(intent);

        //Code by Anjali
        quotesList = new ArrayList<>();
        adapter = new QuotesAdapter(this,quotesList,false);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        prepareQuotes();

        //Code by Staffone
        DatabaseReference quoteReference = reference.child("Quote");
        quoteReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot individual : dataSnapshot.getChildren()) {
                    String Quote = individual.child("quote").getValue(String.class);
                    Toast.makeText(getApplicationContext(),"Quote"+Quote,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void prepareQuotes(){
        QuoteData mData = new QuoteData("“The Way To Get Started Is To Quit Talking And Begin Doing.”",
                "-Walt Disney",
                "Motivational");
        quotesList.add(mData);

        mData = new QuoteData("“The Pessimist Sees Difficulty In Every Opportunity. The Optimist Sees The Opportunity In Every Difficulty.”",
                "-Winston Churchill",
                "Motivational");
        quotesList.add(mData);

        mData = new QuoteData("“Don’t Let Yesterday Take Up Too Much Of Today.”",
                "-Will Rogers",
                "Motivational");
        quotesList.add(mData);

        mData = new QuoteData("“You Learn More From Failure Than From Success. Don’t Let It Stop You. Failure Builds Character",
                "-Anonymous",
                "Motivational");
        quotesList.add(mData);

        mData = new QuoteData("“It’s Not Whether You Get Knocked Down, It’s Whether You Get Up.”",
                "-Vince Lombardi",
                "Motivational");
        quotesList.add(mData);

        mData = new QuoteData("“If You Are Working On Something That You Really Care About, You Don’t Have To Be Pushed. The Vision Pulls You.”",
                "-Steve Jobs",
                "Motivational");
        quotesList.add(mData);

        mData = new QuoteData("“People Who Are Crazy Enough To Think They Can Change The World, Are The Ones Who Do.”",
                "-Rob Siltanen",
                "Motivational");
        quotesList.add(mData);

        adapter.notifyDataSetChanged();
    }
}
