package com.example.staffonechristian.quotesmaster;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by anjali desai on 12-07-2017.
 */

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.MyViewHolder>{

    private List<QuoteData> quoteList;
    private Context context;
    private boolean heartState;

    public QuotesAdapter(Context mContext, List<QuoteData> mQuoteList, boolean defaultState){
        this.context = mContext;
        this.quoteList = mQuoteList;
        heartState = defaultState;
    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        QuoteData mData = quoteList.get(position);
        holder.mQuote.setText(mData.getQuote());
        holder.mAuthor.setText(mData.getAuthor());

        //final int mywid = holder.hellyeah.getWidth();
        //int myhei = holder.myCard.getHeight();

        //Picasso.with(holder.back_image.getContext()).load(R.drawable.mine).resize(dp2px(1000),0).into(holder.back_image);
       // if(position%2==0){
       //     holder.myCard.setBackgroundResource(R.drawable.bird1);
       //     holder.myCard.setAlpha((float) 0.2);
       // }
        //heartState = false;
        holder.myCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpdText = holder.mQuote.getText().toString()+"\n"+holder.mAuthor.getText().toString();
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied Text: ",cpdText);
                clipboardManager.setPrimaryClip(clipData);
                //Toast.makeText(context,mywid+"",Toast.LENGTH_SHORT).show();
                //Toast.makeText(context,"Copy text: "+cpdText,Toast.LENGTH_LONG).show();
            }
        });

        holder.likeUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!heartState){
                    holder.likeUnlike.setImageResource(R.drawable.like);
                }
                else {
                    holder.likeUnlike.setImageResource(R.drawable.unlike);
                }
                heartState = !heartState;
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_card,parent,false);
        return  new MyViewHolder(itemView);
    }

    public int dp2px(int dp) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);
        return (int) (dp * displaymetrics.density + 0.5f);
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
