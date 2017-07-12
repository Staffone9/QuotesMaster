package com.example.staffonechristian.quotesmaster;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuoteCreator extends AppCompatActivity {

    EditText quoteText, quoteAuthorText, quoteCategoryText;
    Button submitButton;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_creator);

        quoteText = (EditText) findViewById(R.id.quote);
        quoteAuthorText = (EditText) findViewById(R.id.quoteAuthor);
        quoteCategoryText = (EditText) findViewById(R.id.quoteCategory);
        submitButton = (Button) findViewById(R.id.Submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference referenceWriteOne = FirebaseDatabase.getInstance().getReference();

                referenceWriteOne.child("Quote").addValueEventListener(new ValueEventListener() {
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
                    DatabaseReference drWrite = referenceWrite.child("Quote").push();
                    QuoteData quoteData = new QuoteData();
                    quoteData.setQuote(quoteText.getText().toString());
                    quoteData.setAuthor(quoteAuthorText.getText().toString());
                    quoteData.setCategory(quoteCategoryText.getText().toString());

                    drWrite.setValue(quoteData);
                }
            }


        });
    }
}
