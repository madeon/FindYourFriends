package com.example.mathias.findyourfriends.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mathias.findyourfriends.Database.DatabaseConnector;
import com.example.mathias.findyourfriends.Helpers.ToastMaker;
import com.example.mathias.findyourfriends.R;

/**
 * Created by mathi on 19-03-2018.
 */

public class CreateGroupFragment extends Fragment {

    private EditText editText;
    private Button createGroupButton;
    private DatabaseConnector database;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Create Group");
        super.onViewCreated(view, savedInstanceState);
        editText = (EditText) view.findViewById(R.id.groupNameTextEdit);
        createGroupButton = (Button) view.findViewById(R.id.createGroupButton);

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }

    public void createGroup() {

        database = new DatabaseConnector("Groups");

        String groupName = editText.getText().toString();

        if (!TextUtils.isEmpty(groupName)) {
            database.createGroup(editText.getText().toString(), "TEST ID");
            Toast.makeText(getContext(), "Group Created", Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(getContext(), "You must enter a group name!", Toast.LENGTH_SHORT).show();
        }
    }


}
