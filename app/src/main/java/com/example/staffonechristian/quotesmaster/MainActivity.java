package com.example.staffonechristian.quotesmaster;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.Display;
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

import static java.security.AccessController.getContext;

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
    static int size;
    static int counter=0;
    static boolean initialFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getList();
        initialFlag = false;

        quotesList = new ArrayList<>();
        size = mData.listOfCategory.size()-1;

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


        //Code by Anjali
        pBar = (ProgressBar)findViewById(R.id.progress_bar);
        pBar.setVisibility(View.VISIBLE);
        Display display = getWindowManager().getDefaultDisplay();
        Point size1 = new Point();
        display.getSize(size1);
        int width = size1.x;
        int height = size1.y;
        int toolHei = getToolBarHeight();
        int stat = getStatusBarHeight();
        int myheight = height - (toolHei+stat);
        System.out.println("------------Main: "+height+"\nTool: "+toolHei+"\nStat: "+stat+"Total: "+myheight+"-----------");
       // Toast.makeText(this,"Main: "+height+"\nTool: "+toolHei+"\nStat: "+stat+"Total: "+myheight,Toast.LENGTH_LONG).show();
        adapter = new QuotesAdapter(this,quotesList,false,width,myheight);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        DatabaseReference myRef = reference.child("Quote");

        prepareQuotes(0);
        //adp.setData(this,quotesList);
        //recyclerView.setAdapter(adp);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
              //  Toast.makeText(getApplicationContext(),"onScrollStateChanged"+newState,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstItem = layoutManager.findFirstVisibleItemPosition();
                lastItem = layoutManager.findLastVisibleItemPosition();
                QuoteData firstData = quotesList.get(firstItem);
                QuoteData qData = quotesList.get(lastItem);

                System.out.println("--------->Last visible Quote "+qData.getQuote());
               // QuoteData mData = quoteList.get(position);
               // Toast.makeText(getApplicationContext(),"visibleItemCount "+visibleItemCount+" totalItemCount"+totalItemCount,Toast.LENGTH_SHORT).show();
                System.out.println("------->P lastItem="+lastItem + "total Item Count="+totalItemCount);

                if(UserData.userViewedQuotes.size()>0) {
                    if (!UserData.userViewedQuotes.contains(qData.getKey())) {

                        UserData.userViewedQuotes.add(qData.getKey());


                    }

                    if(!UserData.userViewedQuotes.contains(firstData.getKey())){
                        UserData.userViewedQuotes.add(firstData.getKey());

                    }
                }else {
                    UserData.userViewedQuotes.add(qData.getKey());
                }
            //    System.out.println("just check--->"+ lastItem+"k "+k);
                if(lastItem == quotesList.size()-1){
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

    public int getToolBarHeight() {
        int[] attrs = new int[] {R.attr.actionBarSize};
        TypedArray ta = this.obtainStyledAttributes(attrs);
        int toolBarHeight = ta.getDimensionPixelSize(0, -1);
        ta.recycle();
        return toolBarHeight;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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


                            }
                        }else if(mData.getKey()!= null) {
                            quotesList.add(mData);

                            adapter.notifyDataSetChanged();
                            System.out.println("Key--------->"+mData.getKey());



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
