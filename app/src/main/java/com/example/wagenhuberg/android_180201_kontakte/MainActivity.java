package com.example.wagenhuberg.android_180201_kontakte;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
//Fehler weil Haken bei Projekt-Compability entfernt wurde!!!
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;




public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button button;
    private static final int RQ_CONTACTS = 4711;
    private ArrayList<String> personen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);


        listView = findViewById(R.id.listView);
        final ArrayList<String> personen = new ArrayList<>(Arrays.asList("Andreas", "Hans", "Paul", "Tanja"));
        personen.add("Michael");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, personen.get(i));
                Toast.makeText(MainActivity.this, "Hallo " + personen.get(i), Toast.LENGTH_SHORT).show();
            }
        });


    }//ende onCreate


    @Override
    protected void onStart() {
        //Funktionsrumpf der Methode onStart in der Elternklasse wird ebenfalls ausgeführt!
        super.onStart();
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Berechtigung nicht vorhanden
            // Prüfen ob bereits einmal Berechtigung abgelehnt wurde
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                // Zusätzliche Infos, warum App Berechtigung benötigt
                // Toast ist Hinweisnachricht
                Toast.makeText(this, "Bitte Berechtigung gewähren, sonst können Kontakte nicht angezeigt werden", Toast.LENGTH_LONG).show();
                requestPermission();
            } else {
                // Berechtigung muss angefordert werden
                requestPermission();
            }
        } else {
            //Berechtigung vorhanden
            showContacts();
        }
    }

    private void requestPermission() {
        String[] permissions = new String[1];
        permissions[0] = Manifest.permission.READ_CONTACTS;
        requestPermissions(permissions, RQ_CONTACTS);
    }

    private void showContacts() {
        //Logmeldung erzeugen
        Log.d(TAG, "showContacts");

        ContentResolver contentResolver = getContentResolver();

        String[] projection = new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ?";
        String[] selectionArgs = new String[]{"1"};
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, projection, selection, selectionArgs, null);
        while (cursor.moveToNext()) {
            personen.add(cursor.getString(0) + " " + cursor.getString(1));
        }
        cursor.close();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, personen);
        listView.setAdapter(adapter);

    }

    @Override
    //Wird aufgerufen wenn User der Berechtigung zugestimmt hat
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RQ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Berechtigung wurde gewährt
                showContacts();
            } else {
                Toast.makeText(this, "Schade!", Toast.LENGTH_LONG).show();
            }
        }
    }


    /*public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CONTACT_PICKER_RESULT);
    }*/


}
