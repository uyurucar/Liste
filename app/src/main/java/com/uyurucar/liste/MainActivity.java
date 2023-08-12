package com.uyurucar.liste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.uyurucar.liste.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<Item> itemList;
    RcycAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        itemList = new ArrayList<Item>();
        FillList();
        adapter = new RcycAdapter(itemList, this, (Item item) -> itemDeleted(item));
        binding.RcycView.setAdapter(adapter);
        binding.RcycView.setLayoutManager(new LinearLayoutManager(this));
        setContentView(view);

    }
    public void itemDeleted(Item item)
    {
        adapter.notifyDataSetChanged();
        itemList.remove(item);
        //recreate();
    }
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drop_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.Add)
        {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            finish();
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.Sort_price)
        {
            Collections.sort(itemList,Item.itemPrice);
            adapter.notifyDataSetChanged();
            //Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == R.id.Sort_priority)
        {
            Collections.sort(itemList,Item.itemPriority);
            adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    public void FillList()
    {
        try {
            SQLiteDatabase database = openOrCreateDatabase("listing",MODE_PRIVATE ,null );
            Cursor cursor = database.rawQuery("SELECT * FROM listing", null);
            // to do: tamamla
            int priorityIdx = cursor.getColumnIndex("priority");
            int priceIdx = cursor.getColumnIndex("price");
            int explanationIdx = cursor.getColumnIndex("explanation");
            while(cursor.moveToNext())
            {
               itemList.add( new Item(Integer.parseInt(cursor.getString(priorityIdx)), Price.valueOf(cursor.getString(priceIdx)), cursor.getString(explanationIdx) ) );
            }
            adapter.notifyDataSetChanged();
            cursor.close();
        } catch (Exception e) { e.printStackTrace(); }

    }
}