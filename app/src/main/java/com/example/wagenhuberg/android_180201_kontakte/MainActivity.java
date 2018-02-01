package com.example.wagenhuberg.android_180201_kontakte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);


        listView = findViewById(R.id.listView);
        final ArrayList<String> personen = new ArrayList<>(Arrays.asList("Andreas", "Hans", "Paul", "Tanja"));
        personen.add("Michael");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, personen);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, personen.get(i));
                Toast.makeText(MainActivity.this, "Hallo " + personen.get(i), Toast.LENGTH_SHORT).show();
            }
        });


    }//ende onCreate

    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK),
        ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CONTACT_PICKER_RESULT);
    }


}
