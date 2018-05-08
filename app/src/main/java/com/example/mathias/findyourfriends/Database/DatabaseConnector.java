package com.example.mathias.findyourfriends.Database;

import android.util.Log;

import com.example.mathias.findyourfriends.Helpers.Group;
import com.example.mathias.findyourfriends.Helpers.ToastMaker;
import com.example.mathias.findyourfriends.Helpers.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        String id = ref.push().getKey();

        User user = new User(id, email, name, 0, 0);

        ref.child(id).setValue(user);
    }

    public void createGroup(String groupName, String id) {
        //String id = ref.push().getKey();

        Group group = new Group(groupName, id);

        ref.child(id).setValue(group);
    }

    public void joinGroup(String id) {

    }

    public void updateLocation(double lat, double lng) {
        //database.getInstance().getReference("Users").child("lat").setValue(lat);
        //database.getInstance().getReference("Users").child("lng").setValue(lng);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        database.getInstance().getReference("Users").child(user.getUid()).child("lat").setValue(lat);
        database.getInstance().getReference("Users").child(user.getUid()).child("lng").setValue(lng);

        Log.d("Test", "User: " + database.getInstance().getReference("Users").child(user.getUid()));
    }



    public DatabaseReference getRef() {
        return ref;
    }
}
