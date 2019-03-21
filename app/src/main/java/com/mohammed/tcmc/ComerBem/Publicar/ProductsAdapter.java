package com.mohammed.tcmc.ComerBem.Publicar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mohammed.tcmc.ComerBem.edit_activities.Product;
import com.mohammed.tcmc.ComerBem.food.DetailActivity;
import com.mohammed.tcmc.ComerBem.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProductsAdapter extends ArrayAdapter<Product> {
    private Uri img_uri;
    private Context context;


    TextView nameText;
    ImageView foodImageView;

    private DatabaseReference mDatabase;
    public Context ctx;

    public ProductsAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
        ctx = context;
        mDatabase = FirebaseDatabase.getInstance().getReference().child(" ");
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         final Product product = getItem(position);

         if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_products_right,
                    parent, false);
        }


        Bitmap bitmap;     // pfoto on text
        ImageView updateImage, edit1, delete;
        TextView nome_da_Receita, nomeUser;
        LinearLayout linearLayoutClick;


        nome_da_Receita = (TextView) convertView.findViewById(R.id.product_name);
        nome_da_Receita.setText(product.getNome_da_Receita());


        foodImageView = (ImageView) convertView.findViewById(R.id.imge_public);
        nameText = (TextView) convertView.findViewById(R.id.nameText);
        nomeUser = (TextView) convertView.findViewById(R.id.name_publec);

        updateImage = (ImageView) convertView.findViewById(R.id.imge_public);
        linearLayoutClick = (LinearLayout) convertView.findViewById(R.id.linearLayoutClick);
        // edit1=(ImageView) convertView.findViewById(R.id.edit1);
        //  delete=(ImageView) convertView.findViewById(R.id.delete);
        Picasso.with(ctx).load(product.getImgurl()).into(updateImage);


        linearLayoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(getContext(), DetailActivity.class);

                edit.putExtra("imgurl", product.getImgurl());
                edit.putExtra("id", product.getId());

                edit.putExtra("nome_da_Receita", product.getNome_da_Receita());
                edit.putExtra("ingredientes", product.getIngredientes());
                edit.putExtra("ingredientes2", product.getIngredientes2());
                edit.putExtra("ingredientes3", product.getIngredientes3());
                edit.putExtra("modo", product.getModo());
                edit.putExtra("modo2", product.getModo2());
                edit.putExtra("modo3", product.getModo3());
                edit.putExtra("categoria", product.getCategoria());

                getContext().startActivity(edit);
            }
        });

        return convertView;


    }


}
