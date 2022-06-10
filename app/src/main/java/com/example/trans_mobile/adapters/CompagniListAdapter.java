package com.example.trans_mobile.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trans_mobile.Compagnie.Compagnie;
import com.example.trans_mobile.R;

import java.util.List;

public class CompagniListAdapter extends RecyclerView.Adapter<CompagniListAdapter.MyViewHolder> {

    private final List<Compagnie> CompagnieList;
    private final CompagnieListClickListener clickListener;

    public CompagniListAdapter(List<Compagnie> CompagnieList,CompagnieListClickListener clickListener) {
        this.CompagnieList = CompagnieList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CompagniListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CompagniListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.compagnieName.setText(CompagnieList.get(position).getName());
        holder.compagnieAdresse.setText("Adresse:"+CompagnieList.get(position).getAdresse());

        holder.itemView.setOnClickListener(view -> clickListener.onItemClick(CompagnieList.get(position)));
    }

    @Override
    public int getItemCount() {
        return CompagnieList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView compagnieName;
        public TextView compagnieAdresse;


        public MyViewHolder(View view) {
            super(view);
            compagnieName = view.findViewById(R.id.compagnieName);
            compagnieAdresse = view.findViewById(R.id.compagnieAdresse);

        }
    }

    public interface CompagnieListClickListener {
        void onItemClick(Compagnie compagnie);
    }
}
