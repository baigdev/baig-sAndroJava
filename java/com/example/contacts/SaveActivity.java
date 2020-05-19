package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class SaveActivity extends AppCompatActivity {

    EditText name,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        name=findViewById(R.id.edit1);
        number=findViewById(R.id.edit2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_save:
                MainActivity.myContactsDataBase.saveData(name.getText().toString(), number.getText().toString());
                MainActivity.myContactsDataBase.getData();
                finish();
                break;
            case R.id.save_cancel:
                finish();
                break;
        }
        return true;
    }
}
