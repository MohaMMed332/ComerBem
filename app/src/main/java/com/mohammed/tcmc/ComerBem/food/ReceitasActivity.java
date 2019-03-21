package com.mohammed.tcmc.ComerBem.food;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.activity.Food;
import com.mohammed.tcmc.ComerBem.edit_activities.Product;
import com.mohammed.tcmc.ComerBem.uitl.Util;

import java.util.ArrayList;

public class  ReceitasActivity extends AppCompatActivity {


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ReceitasActivity);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final ProgressDialog progressDialog = new ProgressDialog(this);


        if(Util.isConected(this)){
            progressDialog.setMessage(getString(R.string.Carregando));
            progressDialog.setCancelable(false);
            progressDialog.show();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            String categoria = getIntent().getExtras().getString("categoria");
            database.getReference().child(getString(R.string.categoria)).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Product> productItems = new ArrayList<>();
                    Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                    for(DataSnapshot child : iterable){
                        Product p = new Product();
                        p.setId(child.getKey());
                        p.setNome_da_Receita(child.child("nome_da_Receita").getValue().toString());
                        p.setImgurl(child.child("imgurl").getValue().toString());
                        productItems.add(p);


                        Log.d(child.getKey(),child.getValue().toString());
                    }
                    recyclerView.setAdapter(new Food(ReceitasActivity.this,productItems));
                    progressDialog.cancel();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    R.string.Sem_conexão,
                    Toast.LENGTH_SHORT);

            toast.show();
        }

    }
}
