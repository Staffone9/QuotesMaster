package com.example.staffonechristian.quotesmaster;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by anjali desai on 20-07-2017.
 */

public class NewQuoteAdapter extends FirebaseRecyclerAdapter<QuoteData,NewQuoteAdapter.MyViewHolder>{

    private List<QuoteData> quoteList;
    private Context context;
    private QuoteData mData;

    public NewQuoteAdapter(Class<QuoteData> modelClass, int modelLayout, Class<MyViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    public void setData(Context mContext, List<QuoteData> mQuoteList){
        this.context = mContext;
        this.quoteList = mQuoteList;
    }

    @Override
    protected void populateViewHolder(final MyViewHolder viewHolder, QuoteData model, int position) {
        //mData = quoteList.get(position);
        model = quoteList.get(position);
        viewHolder.mQuote.setText(model.getQuote());
        viewHolder.mAuthor.setText(model.getAuthor());

        viewHolder.myCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpdText = viewHolder.mQuote.getText().toString()+"\n"+viewHolder.mAuthor.getText().toString();
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied Text: ",cpdText);
                clipboardManager.setPrimaryClip(clipData);
                //Toast.makeText(context,mywid+"",Toast.LENGTH_SHORT).show();
                //Toast.makeText(context,"Copy text: "+cpdText,Toast.LENGTH_LONG).show();
            }
        });

        final QuoteData finalModel = model;
        viewHolder.likeUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = finalModel.getKey();
                Toast.makeText(context,"Key: "+finalModel.getKey(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        protected CardView myCard;
        protected TextView mQuote;
        protected TextView mAuthor;
        protected ImageView myCopy;
        protected ImageView likeUnlike;

        public MyViewHolder(View itemView) {
            super(itemView);
            myCard = (CardView)itemView.findViewById(R.id.card_view);
            mQuote = (TextView)itemView.findViewById(R.id.quoteTxtVw);
            mAuthor = (TextView)itemView.findViewById(R.id.quoteAuthorTxtVw);
            myCopy = (ImageView)itemView.findViewById(R.id.copyIcon);
            likeUnlike = (ImageView)itemView.findViewById(R.id.like_unlike);
        }
    }

}
