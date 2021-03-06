package com.example.mathias.findyourfriends.Database;

import android.util.Log;

import com.example.mathias.findyourfriends.Helpers.Group;
import com.example.mathias.findyourfriends.Helpers.ToastMaker;
import com.example.mathias.findyourfriends.Helpers.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

/**
 * Created by mathi on 27-03-2018.
 */

public class DatabaseConnector {

    private static DatabaseReference ref;
    private FirebaseDatabase database;

    public DatabaseConnector(String child) {
        ref = FirebaseDatabase.getInstance().getReference().child(child);
    }



    public void createUser(String email, String name) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String uid = firebaseUser.getUid();

        User user = new User(uid, email, name, 0, 0, "");
        ref.child(uid).setValue(user);
    }

    public void createGroup(String groupName) {

        String id = ref.push().getKey();

        Group group = new Group(groupName, id);

        ref.child(id).setValue(group);

        ref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Group group = dataSnapshot.getValue(Group.class);
                joinGroup(group.getID());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void joinGroup(String id) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String uid = firebaseUser.getUid();

        Map map = new HashMap();
        map.put("groupID", id);

        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).updateChildren(map);
    }

    public void updateLocation(double lat, double lng) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String uid = firebaseUser.getUid();

        database.getInstance().getReference("Users").child(uid).child("lat").setValue(lat);
        database.getInstance().getReference("Users").child(uid).child("lng").setValue(lng);
    }



    public DatabaseReference getRef() {
        return ref;
    }
}
