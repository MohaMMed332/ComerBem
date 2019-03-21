package com.mohammed.tcmc.ComerBem.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohammed.tcmc.ComerBem.food.DetailActivity;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.edit_activities.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/***
 * Created by negibabu on 3/18/17.
 */

public class ListaFood extends RecyclerView.Adapter<ListaFood.ViewHolder>{

    private Context context;

    private ArrayList<Product> productItems;

    public ListaFood(Context context, ArrayList<Product> productItems) {
        this.context = context;
        this.productItems = productItems;
    }

    @Override
    public ListaFood.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.food_items_list_item,parent,false);
        return new ListaFood.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ListaFood.ViewHolder holder, final int position) {


        holder.nameTextView.setText(
                productItems.get(position).getNome_da_Receita()
        );




        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("imgurl",productItems.get(position).getImgurl());
                intent.putExtra("id",productItems.get(position).getId());
                intent.putExtra("nome_da_Receita",productItems.get(position).getNome_da_Receita());
                intent.putExtra("ingredientes",productItems.get(position).getIngredientes());
                intent.putExtra("ingredientes2",productItems.get(position).getIngredientes2());
                intent.putExtra("ingredientes3",productItems.get(position).getIngredientes3());
                intent.putExtra("modo",productItems.get(position).getModo());
                intent.putExtra("modo2",productItems.get(position).getModo2());
                intent.putExtra("modo3",productItems.get(position).getModo3());

                    context.startActivity(intent);
            }
        });


        Picasso.with(context)
                .load(productItems.get(position).getImgurl())
                .placeholder(ContextCompat.getDrawable(context,R.drawable.png_natal))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameTextView;
        View rootView;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.catogaria_imge );
            nameTextView = (TextView) itemView.findViewById(R.id.itemNameTextView);
            rootView = itemView;

        }
    }
}