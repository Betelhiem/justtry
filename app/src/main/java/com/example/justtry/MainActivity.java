 package com.example.justtry;

 import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    List<String> items;
    Button bntAdd;
    EditText etItem;
    RecyclerView rvItem;
    ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bntAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItem = findViewById(R.id.rvItems);
        etItem.setText("here we go!!!!");


        loadItems();


        ItemsAdapter.OnLongClickListener OnLongClickListener = new ItemsAdapter.OnLongClickListener() {

            @Override
            public void OnItemLongClicked(int Position) {
                //Delete the item
                items.remove(Position);
                // Notify
                itemsAdapter.notifyItemRemoved(Position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };
        itemsAdapter = new ItemsAdapter(items, OnLongClickListener);
        rvItem.setAdapter(itemsAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(this));

        bntAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                //ADD the item
                items.add(todoItem);
                //notify the adaptor
                itemsAdapter.notifyItemInserted(items.size() - 1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was add", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        });


    }



    private File getDatafile(){
        return new File(getFilesDir(), "data.txt");
    }

    //This function will load items by reading every line of the data file
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDatafile(), Charset.defaultCharset()));
        }
        catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    //This function saves items by writing them into the data file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDatafile(), items);
        }
        catch (IOException e){
            Log.e("MainActivitiy", "Error writing items", e);
        }
    }

}
