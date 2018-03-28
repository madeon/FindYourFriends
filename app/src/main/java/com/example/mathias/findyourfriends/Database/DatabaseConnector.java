package com.example.mathias.findyourfriends.Database;

import com.example.mathias.findyourfriends.Helpers.Group;
import com.example.mathias.findyourfriends.Helpers.ToastMaker;
import com.example.mathias.findyourfriends.Helpers.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mathi on 27-03-2018.
 */

public class DatabaseConnector {

    private DatabaseReference ref;

    public DatabaseConnector(String child) {
        ref = FirebaseDatabase.getInstance().getReference().child(child);
    }

    public void createUser(String name, String email) {
        String id = ref.push().getKey();

        User user = new User(id, email, name);

        ref.child(id).setValue(user);
    }

    public void createGroup(String groupName) {
        String id = ref.push().getKey();

        Group group = new Group(id, groupName);

        ref.child(id).setValue(group);
    }

    public DatabaseReference getRef() {
        return ref;
    }
}
