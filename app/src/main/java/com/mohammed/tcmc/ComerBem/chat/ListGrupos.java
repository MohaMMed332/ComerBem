package com.mohammed.tcmc.ComerBem.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.Publicar.AddProductActivity;
import com.mohammed.tcmc.ComerBem.edit_activities.Product;

import java.util.ArrayList;

public class ListGrupos extends AppCompatActivity {
    private DatabaseReference mDatabase;

    ArrayList<Product> ListGrupos;
    ListView productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        productsList = (ListView) findViewById(R.id.ListGrupos);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(getString(R.string.categoriaGrupos));

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ListGrupos = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    ListGrupos.add(product);
                }
                //adapter
                GrpuosAdapter adapter = new GrpuosAdapter(ListGrupos.this, ListGrupos);

                //attach
                productsList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }


    public void addProduct(View view) {
        Intent i = new Intent(this, AddProductActivity.class);
        startActivity(i);

    }


}
