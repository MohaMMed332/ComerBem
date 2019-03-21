package com.mohammed.tcmc.ComerBem;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.tcmc.ComerBem.adapters.CommentAdapter;
import com.mohammed.tcmc.ComerBem.model.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComentMen extends DialogFragment implements View.OnClickListener {
    View form;
    RecyclerView recyclerView;
    CardView cardView;
    EditText ediText;
    Button bouton;
    DatabaseReference newDbRef;
    DatabaseReference mDatabaseReference;
    String id;
    Map<String, String> userInfos;
    LinearLayoutManager linearLayoutManager;
    CommentAdapter mMessageAdapter;
    static AlertDialog.Builder alertDialogBuilder;
    static Context context;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        id=getIntent().getExtras().getString("id");

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mMessageAdapter);
        connection();

        form = inflater.inflate(R.layout.coment_men, container, false);
        ediText = (EditText) form.findViewById(R.id.inputEditText);
        bouton = (Button) form.findViewById(R.id.sendButton);
        recyclerView = (RecyclerView) form.findViewById(R.id.recyclerView);
        bouton.setOnClickListener(this);
        getDialog().setTitle("Please Enter Your Comennt");
        return form;



        //mDatabase = FirebaseDatabase.getInstance().getReference().child("Food");


    }
    @Override
    public void onClick(View view) {
        sendMessage();
        ediText.setText("");
        //recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
    }



    public void connection() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference(getString(R.string.comments));
        //mDatabaseReference.addValueEventListener(this);
    }

    public void sendMessage() {
        newDbRef = mDatabaseReference.push();
        Log.d("-->",id);
        newDbRef.setValue(
                new Comment(ediText.getText().toString(), userInfos.get("USER_NAME"), userInfos.get("USER_EMAIL"), id ,userInfos.get("IMG_URL")));
    }




    public Cursor getIntent() {
        final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        database2.getReference().child(getString(R.string.comments)).orderByChild("idProduct").equalTo(id).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Comment> items = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Comment comment = postSnapshot.getValue(Comment.class);
                    comment.setKey(postSnapshot.getKey());
                    items.add(comment);
                }
                mMessageAdapter.setDatas(items);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return getIntent();
    }


}