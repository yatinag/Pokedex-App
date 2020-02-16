package com.example.mdb_pokedex_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<Pokemon> pokeNames;

    Adapter(Context context, ArrayList<Pokemon> pokeNames){
        this.layoutInflater = LayoutInflater.from(context);
        this.pokeNames = pokeNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_card, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String title = pokeNames.get(i).getPoke_name();
        int num = pokeNames.get(i).getPoke_num();
        Glide.with(viewHolder.pokeImg.getContext()).load(pokeNames.get(i).getPoke_image()).into(viewHolder.pokeImg);

        viewHolder.textNum.setText("#" + num);
        viewHolder.textTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return pokeNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textNum;
        ImageView pokeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                    intent.putExtra("poke_name", pokeNames.get(getAdapterPosition()).getPoke_name_dirty());
                    v.getContext().startActivity(intent);
                }
            });
            pokeImg = itemView.findViewById(R.id.pokeImg);
            textTitle = itemView.findViewById(R.id.textTitle);
            textNum = itemView.findViewById(R.id.textNum);
        }
    }
}