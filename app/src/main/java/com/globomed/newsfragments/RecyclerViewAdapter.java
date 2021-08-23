package com.globomed.newsfragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    Context mContext;
    List<Article> mArticleList;

    public RecyclerViewAdapter(Context mContext, List<Article> mArticle) {
        this.mContext = mContext;
        this.mArticleList = mArticle;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.with(mContext).load(mArticleList.get(position).getImageUrl()).fit().centerInside().into(holder.mArticleImageView);
        holder.mTextView_Title.setText(mArticleList.get(position).getTitle());
        holder.mTextView_Description.setText(mArticleList.get(position).getDescription());


    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView mArticleImageView;
        public TextView mTextView_Title;
        public TextView mTextView_Description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mArticleImageView = (ImageView) itemView.findViewById(R.id.article_image);
            mTextView_Title = (TextView) itemView.findViewById(R.id.article_title);
            mTextView_Description = (TextView)itemView.findViewById(R.id.article_description);
        }
    }
}
