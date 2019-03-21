package com.mohammed.tcmc.ComerBem.Publicar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.edit_activities.Product;

import java.util.ArrayList;


public class Edit extends AppCompatActivity {

    private DatabaseReference mDatabase;

    ArrayList<Product> Edit;
    ListView productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit);


        productsList = (ListView) findViewById(R.id.products_list);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Word));

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Edit = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    Edit.add(product);
                }
                //adapter
                ProductsAdapter adapter = new ProductsAdapter(Edit.this, Edit);

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
