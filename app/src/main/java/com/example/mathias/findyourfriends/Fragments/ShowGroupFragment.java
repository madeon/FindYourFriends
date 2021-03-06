package com.example.mathias.findyourfriends.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mathias.findyourfriends.Activities.LoginActivity;
import com.example.mathias.findyourfriends.Activities.NavigationDrawerActivity;
import com.example.mathias.findyourfriends.Database.DatabaseConnector;
import com.example.mathias.findyourfriends.Helpers.Group;
import com.example.mathias.findyourfriends.Helpers.User;
import com.example.mathias.findyourfriends.R;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mathi on 19-03-2018.
 */

public class ShowGroupFragment extends Fragment {

    private DatabaseConnector databaseConnector;
    private String groupID, groupName;
    private TextView textView, textViewID, textViewGroup;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Group");
        databaseConnector = new DatabaseConnector("Users");
        textView = (TextView) view.findViewById(R.id.displayGroupIDTextView);
        textViewID = (TextView) view.findViewById(R.id.displayID);
        textViewGroup = (TextView) view.findViewById(R.id.groupNameTextView);
        getGroupID();
        showGroupID();
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_group, container, false);
    }


    private void showGroupID() {
            textView.setText("You need to create a group to get a unique ID to share with your friends.");

            if(groupID != null && groupID.length() == 6) {
                textView.setText("This is your unique ID. Share it with a friend to let them join your group!");
                textViewID.setTextSize(60);
                textViewID.setText("" + groupID);
            }

            else if(groupID != null && groupID.length() > 6) {
                textViewID.setTextSize(11);
                textViewID.setText("Your ID is being generated.. please update the page or return in a minute");
            }
    }
    
    
    private void getGroupID() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String uid = firebaseUser.getUid();


        databaseConnector.getRef().child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                groupID = dataSnapshot.getValue(User.class).getGroupID();

                if (groupID!= null) {
                    showGroupID();
                    showGroupName();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void showGroupName() {

        FirebaseDatabase.getInstance().getReference().child("Groups")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Group group = snapshot.getValue(Group.class);

                            if(group.getID().equals(groupID)) {
                                groupName = group.getGroupName();
                                textViewGroup.setText("Group name: \n" + groupName);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}
