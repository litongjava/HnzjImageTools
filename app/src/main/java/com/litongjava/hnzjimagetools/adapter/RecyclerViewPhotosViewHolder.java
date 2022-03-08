package com.litongjava.hnzjimagetools.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.litongjava.hnzjimagetools.R;
import com.litongjava.hnzjimagetools.listener.RecyclerViewPhotosOnClickListener;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022/2/26
 */
public class RecyclerViewPhotosViewHolder extends RecyclerView.ViewHolder {
  public FrameLayout frameLayoutImage;
  public ImageView ivImg;
  public ImageView ivAdd;
  public ImageView ivDel;



  public RecyclerViewPhotosViewHolder(Activity activity, Fragment fragment,View view, RecyclerViewPhotosAdapter adapter) {
    super(view);
    frameLayoutImage = (FrameLayout) itemView.findViewById(R.id.frameLayoutImage);
    ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
    ivAdd = (ImageView) itemView.findViewById(R.id.iv_add);
    ivDel = (ImageView) itemView.findViewById(R.id.iv_del);

    RecyclerViewPhotosOnClickListener recyclerViewOnClickListener = new RecyclerViewPhotosOnClickListener(activity,fragment,this,adapter);
    ivAdd.setOnClickListener(recyclerViewOnClickListener);
    ivImg.setOnClickListener(recyclerViewOnClickListener);
    ivDel.setOnClickListener(recyclerViewOnClickListener);

  }
}