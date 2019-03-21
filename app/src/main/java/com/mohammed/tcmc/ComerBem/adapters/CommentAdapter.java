package com.mohammed.tcmc.ComerBem.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.chat.ChatActivity;
import com.mohammed.tcmc.ComerBem.model.Comment;
import com.mohammed.tcmc.ComerBem.uitl.CryptUtil;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private static final int DEFAULT_CARD_VIEW = 0;
    private static final int OWN_CARD_VIEW = 1;

    private List<Comment> mData;
    private String currentUserEmail;
    private View view;

    public CommentAdapter(List<Comment> mData, String currentUserEmail) {
        this.mData = mData;
        this.currentUserEmail = currentUserEmail;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case CommentAdapter.DEFAULT_CARD_VIEW:

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setData(mData.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                String messageKey = mData.get(position).getKey();
            //    ChatActivity.showAlertDialog(messageKey);
                notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getUserEmail().equals(this.currentUserEmail)) {
            return OWN_CARD_VIEW;
        }else {
            return DEFAULT_CARD_VIEW;
        }
    }

    public void setDatas(List<Comment> comment) {
        mData = comment;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView1, textView2;
        ImageView image,imageSend;

        ViewHolder(View itemView) {
            super(itemView);

          //  imageSend = (ImageView) itemView.findViewById( R.id.imageSend );
        }

        public void setData(Comment comment) {
            textView1.setText(comment.getUserName() + " - "+ comment.getDate());
            textView2.setText(comment.getContent());

            String lienImage;
            if(comment.getUserimg().isEmpty()) {
                lienImage = comment.getUserimg();
            }else{
                lienImage = "https://www.gravatar.com/avatar/" +  CryptUtil.md5(comment.getUserEmail());
            }
            lienImage = comment.getUserimg();
            Glide
                    .with(image.getContext())
                    .load(lienImage)
                    .apply(RequestOptions.circleCropTransform())
                    .into(image);


        }


    }


}
