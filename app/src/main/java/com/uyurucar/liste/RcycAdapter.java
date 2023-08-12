package com.uyurucar.liste;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uyurucar.liste.databinding.RcycLayoutBinding;

import java.util.ArrayList;

public class RcycAdapter extends RecyclerView.Adapter<RcycAdapter.RcycHolder>{
    ArrayList<Item> list;
    Context ct;
    private final OnClickInterface onClickInterface;
    public RcycAdapter(ArrayList<Item> list, Context ct, OnClickInterface onClickInterface)
    {
        this.list = list;
        this.ct = ct;
        this.onClickInterface = onClickInterface;
    }
    @NonNull
    @Override
    public RcycAdapter.RcycHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RcycLayoutBinding binding = RcycLayoutBinding.inflate(LayoutInflater.from(ct),parent,false);
        return new RcycHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RcycAdapter.RcycHolder holder, int position) {
        String priority = String.valueOf(list.get(position).getPriority());
        String price = list.get(position).getPrice().toString();
        String explanation = list.get(position).getExplanation();
        holder.binding.elementPrice.setText(price);
        holder.binding.elementPriority.setText(priority);
        holder.binding.elementName.setText(explanation);
        Linkify.addLinks(holder.binding.elementName, Linkify.ALL);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ct);
                alert.setTitle("Delete?");
                alert.setMessage("You Sure?");
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteItem(list.get(holder.getAdapterPosition()));
                    }
                });
                alert.setNegativeButton("Hayır", null);
                alert.show();
                return false;
            }
        });
    }
    public void DeleteItem(Item item)
    {
        try
        {
            SQLiteDatabase database = ct.openOrCreateDatabase("listing",MODE_PRIVATE ,null );
            SQLiteStatement statement = database.compileStatement("DELETE FROM listing WHERE explanation = ?");
            statement.bindString(1,item.getExplanation());
            statement.execute();
            list.remove(item);
        } catch (Exception e) {e.printStackTrace();}
        onClickInterface.onDelete(item);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class RcycHolder extends RecyclerView.ViewHolder {
        RcycLayoutBinding binding;
        public RcycHolder(RcycLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
