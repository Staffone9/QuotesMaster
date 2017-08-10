package com.example.staffonechristian.quotesmaster;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by anjali desai on 12-07-2017.
 */

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.MyViewHolder>{

    private List<QuoteData> quoteList;
    private Context context;
    public boolean heartState=false;
    public int height,width,lineCount;
    public CardView.LayoutParams params;
    QuoteIntelligence quoteIntelligence;

    public QuotesAdapter(Context mContext, List<QuoteData> mQuoteList, boolean defaultState, int mwid,int mhei){
        this.context = mContext;
        this.quoteList = mQuoteList;
        heartState = defaultState;
        this.width = mwid;
        this.height = mhei;
    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }


    public void onBindViewHolder(final MyViewHolder holder, int position) {

        UserData.lastPosition = position;
        final QuoteData mData = quoteList.get(position);
        holder.mQuote.setText(mData.getQuote());

        lineCount = holder.mQuote.getLineCount();
        System.out.println("LC----------------->>>>>>>>>>>>>>>>>"+lineCount);
        if(lineCount > 4){
            params = new CardView.LayoutParams(width-60,height-800);
            //params = new CardView.LayoutParams(width-60, holder.mQuote.getLineHeight()+holder.mAuthor.getLineHeight()+40);
            //System.out.println("++++++++++++++++++"+holder.mQuote.getLineHeight()+"++++++++++++++++++++++");
        }
        else {
            params = new CardView.LayoutParams(width-60,(height/2)-30);
        }
        params.setMargins(30,20,30,20);
        holder.myCard.setLayoutParams(params);

        int posMod = position % 10;
        switch (posMod){
            case 0:
                holder.myImage.setImageResource(R.drawable.bot);
                break;
            case 1:
                holder.myImage.setImageResource(R.drawable.bot2);
                break;
            default:
                holder.myImage.setImageResource(R.drawable.bot);
                break;
        }

        holder.mAuthor.setText(mData.getAuthor());
        if (mData.getQuoteLikes() == 1){
            holder.mLikes.setText(mData.getQuoteLikes()+" ");
            holder.mLikeText.setText("Like"+" ");
        }
        else {
            holder.mLikes.setText(mData.getQuoteLikes()+" ");
            holder.mLikeText.setText("Likes");
        }

        holder.myCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpdText = holder.mQuote.getText().toString()+"\n"+holder.mAuthor.getText().toString();
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied Text: ",cpdText);
                clipboardManager.setPrimaryClip(clipData);
            }
        });

        holder.likeUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag;
                quoteIntelligence = new QuoteIntelligence();
                if(holder.likeUnlike.getTag()==null){
                   holder.likeUnlike.setTag("unlike");
                }
                String temp = holder.likeUnlike.getTag().toString();
                if(temp.equals("unlike")){
                    flag=true;
                    holder.likeUnlike.setTag("like");
                    //holder.likeUnlike.setImageResource(R.drawable.like);
                }
                else {
                    flag=false;
                    holder.likeUnlike.setTag("unlike");
                    //holder.likeUnlike.setImageResource(R.drawable.unlike);
                }

                System.out.println("---->image source"+String.valueOf(holder.likeUnlike.getTag()));
                //Toast.makeText(context,"Key: "+mData.getKey()+"\nLikes: "+mData.getQuoteLikes() +"\nCategory: "+mData.getCategory(),Toast.LENGTH_LONG).show();
                System.out.println("---->mData"+mData.toString());
                if(mData.getCategory() != null)
                {
                    quoteIntelligence.LikesAdd(mData.getKey(), mData.getCategory(),flag,mData);
                    if (mData.getQuoteLikes() == 1){
                        holder.mLikes.setText(mData.getQuoteLikes()+" ");
                        holder.mLikeText.setText("Like"+" ");
                        if(flag==true){
                            holder.likeUnlike.setImageResource(R.drawable.like);
                        }
                        else {
                            holder.likeUnlike.setImageResource(R.drawable.unlike);
                        }
                    }
                    else {
                        holder.mLikes.setText(mData.getQuoteLikes()+" ");
                        holder.mLikeText.setText("Likes");
                        if(flag==true){
                            holder.likeUnlike.setImageResource(R.drawable.like);
                        }
                        else {
                            holder.likeUnlike.setImageResource(R.drawable.unlike);
                        }
                    }
                }
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_card,parent,false);
        return  new MyViewHolder(itemView);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        protected CardView myCard;
        protected TextView mQuote;
        protected TextView mAuthor;
        protected TextView mLikes;
        protected TextView mLikeText;
        protected ImageView myImage;
        protected ImageView icon;
        protected ImageView myCopy;
        protected ImageView likeUnlike;
        public MyViewHolder(View itemView) {
            super(itemView);
            myCard = (CardView)itemView.findViewById(R.id.card_view);
            mQuote = (TextView)itemView.findViewById(R.id.quoteTxtVw);
            mAuthor = (TextView)itemView.findViewById(R.id.quoteAuthorTxtVw);
            mLikes = (TextView)itemView.findViewById(R.id.like_numbers);
            mLikeText = (TextView)itemView.findViewById(R.id.like_text);
            myImage = (ImageView)itemView.findViewById(R.id.back_img);
            icon = (ImageView)itemView.findViewById(R.id.myImage);
            myCopy = (ImageView)itemView.findViewById(R.id.copyIcon);
            likeUnlike = (ImageView)itemView.findViewById(R.id.like_unlike);
        }
    }
}
