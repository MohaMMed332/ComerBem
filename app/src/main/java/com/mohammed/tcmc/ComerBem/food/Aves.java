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
import com.mohammed.tcmc.ComerBem.activity.ListaFood;
import com.mohammed.tcmc.ComerBem.edit_activities.Product;
import com.mohammed.tcmc.ComerBem.uitl.Util;

import java.util.ArrayList;

public class Aves extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aves);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final ProgressDialog progressDialog = new ProgressDialog(this);

        if(Util.isConected(this)){
            progressDialog.setMessage(getString(R.string.Carregando));
            progressDialog.setCancelable(false);
            progressDialog.show();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            String categoria = getIntent().getExtras().getString("categoria");
            //mDatabase = FirebaseDatabase.getInstance().getReference().child("Food");
            database.getReference().child(getString(R.string.Resitas)).orderByChild("categoria")
                    .equalTo(categoria).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Product> productItems = new ArrayList<>();
                    Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                    for(DataSnapshot child : iterable){
                        Product p = new Product();
                        p.setImgurl(child.child("imgurl").getValue().toString());
                        p.setId(child.getKey());
                        p.setNome_da_Receita(child.child("nome_da_Receita").getValue().toString());
                        p.setIngredientes(child.child("ingredientes").getValue().toString());
                        p.setIngredientes2(child.child("ingredientes2").getValue().toString());
                        p.setIngredientes3(child.child("ingredientes3").getValue().toString());
                        p.setModo(child.child("modo").getValue().toString());
                        p.setModo2(child.child("modo2").getValue().toString());
                        p.setModo3(child.child("modo3").getValue().toString());
                           productItems.add(p);

                        Log.d(child.getKey(),child.getValue().toString());
                    }
                    recyclerView.setAdapter(new ListaFood(Aves.this,productItems));
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
