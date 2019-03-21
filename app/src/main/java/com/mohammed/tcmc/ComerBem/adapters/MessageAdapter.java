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
import com.mohammed.tcmc.ComerBem.models.Message;
import com.mohammed.tcmc.ComerBem.uitl.CryptUtil;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
 private static final int DEFAULT_CARD_VIEW = 0;
private static final int OWN_CARD_VIEW = 1;

private List<Message> mData;
private String currentUserEmail;
private View view;

public MessageAdapter(List<Message> mData, String currentUserEmail) {
    this.mData = mData;
    this.currentUserEmail = currentUserEmail;
}


@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
        case MessageAdapter.DEFAULT_CARD_VIEW:

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
       //     ChatActivity.showAlertDialog(messageKey);
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

public void setDatas(List<Message> message) {
    mData = message;
    notifyDataSetChanged();
}

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView username, message2,text_message_time;
    ImageView image,imageSend;

    ViewHolder(View itemView) {
        super(itemView);
    }

    public void setData(Message message) {
        username.setText(message.getUserName());
        text_message_time.setText(message.getDate());
        message2.setText(message.getContent());


        String lienImage;
        if(message.getImgurl().isEmpty()) {
            lienImage = message.getImgurl();
        }else{
            lienImage = "https://www.gravatar.com/avatar/" +  CryptUtil.md5(message.getUserEmail());
        }
        lienImage = message.getImgurl();
        Glide
                .with(image.getContext())
                .load(lienImage)
                .apply(RequestOptions.circleCropTransform())
                .into(image);

        String imageSendString = message.getImage();
        if(!imageSendString.equals("unknown")) {
            imageSend.setVisibility( View.VISIBLE );
            Glide
                    .with( image.getContext() )
                    .load( imageSendString )
                    .into( imageSend );
        }
    }


}


}
