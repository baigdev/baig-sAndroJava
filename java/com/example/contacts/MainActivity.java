package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity {

    AlertDialog alertDialog;
    static ArrayList<String> nameList = new ArrayList<String>();
    static ArrayList<String> numberList = new ArrayList<String>();
    static ArrayList<Integer> id_List = new ArrayList<Integer>(10);
    static MyContactsDataBase myContactsDataBase;
    static int position;
    static ListView listView;
    SearchView searchView;

    MyArrayAddapter myArrayAddapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_View);
        myContactsDataBase = new MyContactsDataBase(this);
        myContactsDataBase.getData();
        registerForContextMenu(listView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        myArrayAddapter = new MyArrayAddapter(MainActivity.this, nameList, numberList);
        listView.setAdapter(myArrayAddapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_mode, menu);
        MenuItem menuItem=menu.findItem(R.id.main_search);
        androidx.appcompat.widget.SearchView searchView=(androidx.appcompat.widget.SearchView)menuItem.getActionView();
        searchView.setQueryHint("Search Here");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myArrayAddapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.newNum:
                Intent intent = new Intent(MainActivity.this, SaveActivity.class);
                startActivity(intent);
                break;

            case R.id.deleteAll:
                final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
                myAlert.setTitle("Are You Sure?");
                myAlert.setCancelable(true);
                myAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyAsyncForDelete my = new MyAsyncForDelete(MainActivity.this);
                        my.execute();
                    }
                });
                myAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                myAlert.show();
                break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.floating_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete:

                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                int position = info.position;
                myContactsDataBase.deleteData(MainActivity.id_List.get(position));
                myContactsDataBase.getData();
                onResume();
                break;
            case R.id.edit:
                AdapterContextMenuInfo info2 = (AdapterContextMenuInfo) item.getMenuInfo();
                int position2 = info2.position;
                this.position = position2;
                Intent intent = new Intent(MainActivity.this, Edit_Activity.class);
                startActivity(intent);
                break;
        }

        return true;
    }
}