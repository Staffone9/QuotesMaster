package com.example.staffonechristian.quotesmaster;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {

    ListView listview1, listview2;
    //String[] categories;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        context = this;
        listview1 = (ListView)findViewById(R.id.myListView1);
        listview2 = (ListView)findViewById(R.id.myListView2);
        //categories = new String[]{"Motivational", "Life", "Inspirational", "Friendship", "Love", "Positive"};

        final ArrayList<String> categories1 = new ArrayList<String>();
        categories1.add("Motivational");
        categories1.add("Life");
        categories1.add("Inspirational");
        categories1.add("Friendship");
        categories1.add("Love");
        categories1.add("Positive");

        final ArrayList<String> categories2 = new ArrayList<String>();
        categories2.add("Motivational");
        categories2.add("Life");
        categories2.add("Inspirational");
        categories2.add("Friendship");
        categories2.add("Love");
        categories2.add("Positive");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.custom_list_item,categories1);
        listview1.setAdapter(adapter1);
        listview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.custom_list_item,categories2);
        listview2.setAdapter(adapter2);
        listview2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
