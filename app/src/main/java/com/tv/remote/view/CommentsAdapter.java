package com.tv.remote.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tv.remote.R;
import com.tv.remote.utils.CircleTransformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 凯阳 on 2015/8/12.
 */
public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int itemsCount = 0;
    private int avatarSize;

    private List<String> comments;

    public CommentsAdapter(Context context) {
        this.context = context;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
        comments = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_comment, viewGroup, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CommentViewHolder holder = (CommentViewHolder) viewHolder;
        holder.tvComment.setText(comments.get(position));
        Picasso.with(context)
                .load(R.drawable.btn_optedit)
                .centerCrop()
                .resize(avatarSize, avatarSize)
                .transform(new CircleTransformation())
                .into(holder.ivUserAvatar);
    }

    @Override
    public int getItemCount() {
        return itemsCount;
    }

    public void addItem(String msg) {
        itemsCount++;
        comments.add(msg);
        notifyItemInserted(itemsCount - 1);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.ivUserAvatar)
        ImageView ivUserAvatar;
        @InjectView(R.id.tvComment)
        TextView tvComment;

        public CommentViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
