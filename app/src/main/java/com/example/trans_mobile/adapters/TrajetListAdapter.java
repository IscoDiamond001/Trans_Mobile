package com.example.trans_mobile.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trans_mobile.Compagnie.trajet;
import com.example.trans_mobile.R;

import java.util.List;

public class TrajetListAdapter extends RecyclerView.Adapter<TrajetListAdapter.MyViewHolder> {

    private final List<trajet> trajetList;
    private final trajetListClickListener clickListener;

    public TrajetListAdapter(List<trajet> trajetList, trajetListClickListener clickListener) {
        this.trajetList = trajetList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TrajetListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trajet_recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TrajetListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.trajetName.setText(trajetList.get(position).getName());
        holder.trajetHeure.setText("Heure: "+trajetList.get(position).getHeure());
        holder.trajetPrix.setText("prix: "+trajetList.get(position).getPrix()+" XOF");

        holder.itemView.setOnClickListener(view -> clickListener.onItemClick(trajetList.get(position)));
    }

    @Override
    public int getItemCount() {
        return trajetList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView trajetName;
        TextView trajetHeure;
        TextView trajetPrix;



        public MyViewHolder(View view) {
            super(view);
            trajetName = view.findViewById(R.id.trajetName);
            trajetHeure = view.findViewById(R.id.trajetHeure);
            trajetPrix = view.findViewById(R.id.trajetPrix);

        }
    }

    public interface trajetListClickListener {
        void onItemClick(trajet trajet);
    }
}
