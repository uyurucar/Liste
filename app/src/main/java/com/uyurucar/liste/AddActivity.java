package com.uyurucar.liste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.uyurucar.liste.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    SQLiteDatabase database;
    ActivityAddBinding binding;
    String[] string_priority = {"1","2","3","4","5","6","7","8","9","10"};
    String[] string_price = {"Low","Medium","High","VeryHigh"};
    AutoCompleteTextView priorityTextView;
    AutoCompleteTextView priceTextView;
    ArrayAdapter<String> priorityAdapter;
    ArrayAdapter<String> priceAdapter;
    String selectedPriority, selectedPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        priceTextView = binding.dropdownPriceItem;
        priorityTextView = binding.dropdownPriorityItem;
        priorityAdapter = new ArrayAdapter<String>(this,R.layout.dropdown_item,string_priority);
        priceAdapter = new ArrayAdapter<String>(this,R.layout.dropdown_item,string_price);
        priceTextView.setAdapter(priceAdapter);
        priorityTextView.setAdapter(priorityAdapter);

        priceTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedPrice = parent.getItemAtPosition(position).toString();
                    }
                }
        );
        priorityTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedPriority = parent.getItemAtPosition(position).toString();
                    }
                }
        );
    }
    public void Save(View view)
    {
        if(selectedPrice == null || selectedPriority == null) { Toast.makeText(this,"PICK",Toast.LENGTH_LONG).show(); return; }
        String explanation = binding.explanationInput.getText().toString();
        try {
            database = openOrCreateDatabase("listing", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS listing(id INTEGER PRIMARY KEY, priority VARCHAR, price VARCHAR, explanation VARCHAR)");
            SQLiteStatement statement = database.compileStatement("INSERT INTO listing(priority,price,explanation) VALUES(?,?,?)");
            statement.bindString(1,selectedPriority);
            statement.bindString(2,selectedPrice);
            statement.bindString(3,explanation);
            statement.execute();
            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {e.printStackTrace(); }
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}