package com.haran.professortimetable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by haran on 15-Oct-17.
 */

public class ListFragment extends Fragment {

    private HashMap<String, String> rooms;

    public static Fragment newInstance(Context context, int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        return ListFragment.instantiate(context, ListFragment.class.getName(), bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initActivityElements(view, getArguments().getInt("type", 1));
        return view;
    }



    private void initActivityElements(final View view, int type) {
        rooms = new HashMap<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference room_ref;
        if(type == 1){
            room_ref = database.getReference().child("rooms");
        }
        else{
            room_ref = database.getReference().child("professor");
        }
        room_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot room_data : dataSnapshot.getChildren()){
                    Log.e("Room" + room_data.getKey(), room_data.getValue(String.class));
                    rooms.put(room_data.getKey(), room_data.getValue(String.class));
                }
                loadListView(view);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.rooms_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getKeySetArray(rooms));
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ImageZoomActivity.class);
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
