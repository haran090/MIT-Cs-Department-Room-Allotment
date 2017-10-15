package com.haran.professortimetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private HashMap<String, String> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityElements();
    }

    private void initActivityElements() {
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        rooms = new HashMap<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference room_ref = database.getReference().child("rooms");
        room_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot room_data : dataSnapshot.getChildren()){
                    Log.e("Room" + room_data.getKey(), room_data.getValue(String.class));
                    rooms.put(room_data.getKey(), room_data.getValue(String.class));
                }
                loadListView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadListView() {
        ListView listView = (ListView) findViewById(R.id.rooms_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getKeySetArray(rooms));
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ImageZoomActivity.class);
                intent.putExtra("url", rooms.get(((TextView)view).getText().toString()));
                startActivity(intent);
            }
        });
    }

    private String[] getKeySetArray(HashMap<String, String> rooms) {
        String[] room_keys = new String[rooms.size()];
        int counter = 0;
        for(String string : rooms.keySet()){
            room_keys[counter++] = string;
        }
        return room_keys;
    }
}
