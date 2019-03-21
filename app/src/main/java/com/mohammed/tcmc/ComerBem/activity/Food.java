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

import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.edit_activities.Product;
import com.mohammed.tcmc.ComerBem.food.Aves;
import com.squareup.picasso.Picasso;


        import java.util.ArrayList;


public class Food extends RecyclerView.Adapter<Food.ViewHolder>{

    private Context context;

    private ArrayList<Product> productItems;

    public Food(Context context, ArrayList<Product> productItems) {
        this.context = context;
        this.productItems = productItems;
    }

    @Override
    public Food.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.food_items_list_item,parent,false);
        return new Food.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final Food.ViewHolder holder, final int position) {


        holder.nameTextView.setText(
                productItems.get(position).getNome_da_Receita()
        );




        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Aves.class);
                intent.putExtra("categoria",productItems.get(position).getNome_da_Receita());

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