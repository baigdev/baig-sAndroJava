package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class Edit_Activity extends AppCompatActivity {

    EditText e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);
        e1=findViewById(R.id.edit_act_e1);
        e2=findViewById(R.id.edit_Act_e2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.edit_menu,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.edit_Save:
                MainActivity.myContactsDataBase.upDate(MainActivity.id_List.get(MainActivity.position),e1.getText().toString(),e2.getText().toString());
                onResume();
                finish();
                break;
            case R.id.edit_cancel:
                finish();
                break;

        }
        return true;
    }
}
