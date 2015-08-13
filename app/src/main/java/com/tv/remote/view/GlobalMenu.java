package com.tv.remote.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
import com.tv.remote.R;
import com.tv.remote.utils.CircleTransformation;

/**
 * Created by 凯阳 on 2015/7/8.
 */
public class GlobalMenu extends ListView implements View.OnClickListener {

    private OnHeaderClickListener onHeaderClickListener;
    private GlobalMenuAdapter globalMenuAdapter;
    private ImageView ivUserProfilePhoto;
    private int avatarSize;

    public GlobalMenu(Context context) {
        super(context);
        init();
    }

    private void init(){
        setChoiceMode(CHOICE_MODE_SINGLE);
        setDivider(getResources().getDrawable(android.R.color.transparent));
        setDividerHeight(0);
        setBackgroundColor(Color.WHITE);

        setupHeader();
        setupAdapter();
    }

    private void setupAdapter(){
        globalMenuAdapter = new GlobalMenuAdapter(getContext());
        setAdapter(globalMenuAdapter);
    }

    private void setupHeader(){
        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);

        setHeaderDividersEnabled(true);
        View vHeader = LayoutInflater.from(getContext()).inflate(R.layout.view_global_menu_header, null);
        ivUserProfilePhoto = (ImageView)vHeader.findViewById(R.id.ivUserProfilePhoto);
        Picasso.with(getContext())
                .load(R.drawable.ico_tv)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivUserProfilePhoto);
        addHeaderView(vHeader);
        vHeader.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onHeaderClickListener != null){
            onHeaderClickListener.onGlobalMenuHeaderClick(v);
        }
    }

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        this.onHeaderClickListener = onHeaderClickListener;
    }

    public interface OnHeaderClickListener{
        public void onGlobalMenuHeaderClick(View v);
    }
}
