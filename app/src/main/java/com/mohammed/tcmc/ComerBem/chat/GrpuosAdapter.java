package com.mohammed.tcmc.ComerBem.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.edit_activities.Product;
import com.mohammed.tcmc.ComerBem.food.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GrpuosAdapter extends ArrayAdapter<Product> {
    private Uri img_uri;
    private Context context;


    TextView nameText;
    ImageView foodImageView;

    private DatabaseReference mDatabase;
    public Context ctx;

    public GrpuosAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
        ctx = context;
        mDatabase = FirebaseDatabase.getInstance().getReference().child(" ");
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Product product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_grpuos_adapter,
                    parent, false);
        }


        Bitmap bitmap;
        ImageView updateImage;
        TextView nome_da_Receita;
        LinearLayout linearLayoutClick;


        nome_da_Receita = (TextView) convertView.findViewById(R.id.grupo_name);
        nome_da_Receita.setText(product.getNome_da_Receita());


        foodImageView = (ImageView) convertView.findViewById(R.id.imgurl_grupo);
        nameText = (TextView) convertView.findViewById(R.id.grupo_name);

        updateImage = (ImageView) convertView.findViewById(R.id.imgurl_grupo);
        linearLayoutClick = (LinearLayout) convertView.findViewById(R.id.grupo_linearLayoutClick);

        Picasso.with(ctx).load(product.getImgurl()).into(updateImage);


        linearLayoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(getContext(), ChatGrupo.class);

                edit.putExtra("imgurl", product.getImgurl());
                edit.putExtra("id", product.getId());
                edit.putExtra("nome_da_Receita", product.getNome_da_Receita());
                edit.putExtra("categoria", product.getCategoria());

                getContext().startActivity(edit);
            }
        });

        return convertView;


    }


}
