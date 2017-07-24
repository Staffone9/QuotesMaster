package com.example.staffonechristian.quotesmaster;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuoteCreator extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    EditText quoteText, quoteAuthorText, quoteCategoryText;
    Button submitButton;
    boolean flag=false;
    String categoriesString="unknown";
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_creator);

        quoteText = (EditText) findViewById(R.id.quote);
        quoteAuthorText = (EditText) findViewById(R.id.quoteAuthor);
        submitButton = (Button) findViewById(R.id.Submit);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        final ArrayList<String> categories = new ArrayList<String>();
        categories.add("Motivational");
        categories.add("Life");
        categories.add("Inspirational");
        categories.add("Friendship");
        categories.add("Love");
        categories.add("Positive");

        ArrayAdapter dataAdapter = new ArrayAdapter (this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference referenceWriteOne = FirebaseDatabase.getInstance().getReference();

                referenceWriteOne.child("Quote").child(categoriesString).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        flag= true;
                        for (DataSnapshot individual : dataSnapshot.getChildren()) {
                          // Toast.makeText(getApplicationContext(),""+individual.child("quote").getValue(String.class),Toast.LENGTH_SHORT).show();
                            System.out.println("----->"+individual.child("quote").getValue(String.class));
                            String quote = individual.child("quote").getValue(String.class);
                            if(quote.equals(quoteText.getText().toString()))
                            {
                                Snackbar snackbar = Snackbar
                                        .make(findViewById(android.R.id.content), "Already exists", Snackbar.LENGTH_LONG);

                                snackbar.show();
                                flag = false;
                                break;
                            }else{

                                String[] splitOne = quote.split("\\s+");
                                String[] splitTwo = quoteText.getText().toString().split("\\s+");
                                int counter=0;
                                for(int i=0;i<10 && i<splitOne.length && i<splitTwo.length;i++)
                                {
                                    System.out.println("----->splitOne[i]="+splitOne[i]+"------->splitTwo[i]"+splitTwo[i]);
                                    if(splitOne[i].equals(splitTwo[i]))
                                    {
                                        counter++;
                                    }
                                    if(counter>=6)
                                    {
                                        Snackbar snackbar = Snackbar
                                                .make(findViewById(android.R.id.content), "Already exists", Snackbar.LENGTH_LONG);

                                        snackbar.show();
                                        flag = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


                if(flag)
                {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "Quote added Successfully", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    DatabaseReference referenceWrite = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference drWrite = referenceWrite.child("Quote").child(categoriesString).push();
                    //change**************************************************************************************
                    key = drWrite.getKey();
                    //change complete*****************************************************************************
                    QuoteData quoteData = new QuoteData();
                    quoteData.setQuote(quoteText.getText().toString());
                    quoteData.setAuthor("-"+quoteAuthorText.getText().toString());
                    quoteData.setQuoteLikes(0);
                    quoteData.setQuoteViews(0);
                    quoteData.setPriorityScore(0);
                    quoteData.setCategory(categoriesString);
                    //change**************************************************************************************
                    quoteData.setKey(key);
                    //change complete*****************************************************************************
                    drWrite.setValue(quoteData);
                }
            }


        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        categoriesString = parent.getItemAtPosition(position).toString();

        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), "Selected "+categoriesString, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
