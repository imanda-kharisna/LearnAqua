package com.example.learnaqua;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuickAddAdapter extends RecyclerView.Adapter<QuickAddAdapter.ViewHolder> {

    int[] mlList = {100,200,300,400,500,600,700,800,900};
    OnMlClickListener listener;

    public interface OnMlClickListener {
        void onClick(int ml);
    }

    public QuickAddAdapter(OnMlClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quick_ml, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int ml = mlList[position];
        holder.txtMl.setText(ml + " ml");

        holder.itemView.setOnClickListener(v -> listener.onClick(ml));
    }

    @Override
    public int getItemCount() {
        return mlList.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMl;
        ViewHolder(View itemView) {
            super(itemView);
            txtMl = itemView.findViewById(R.id.txtMl);
        }
    }
}
