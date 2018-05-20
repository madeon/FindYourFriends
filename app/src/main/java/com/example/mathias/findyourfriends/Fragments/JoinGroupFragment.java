package com.example.mathias.findyourfriends.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mathias.findyourfriends.Database.DatabaseConnector;
import com.example.mathias.findyourfriends.Helpers.ToastMaker;
import com.example.mathias.findyourfriends.R;

/**
 * Created by mathi on 21-03-2018.
 */

public class JoinGroupFragment extends Fragment {


    private EditText editText1, editText2, editText3, editText4, editText5, editText6;
    private Button joinGroup;
    private StringBuilder id;
    private ToastMaker toast = new ToastMaker();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Join Group");
        editText1 = (EditText) view.findViewById(R.id.editText1);
        editText2 = (EditText) view.findViewById(R.id.editText2);
        editText3 = (EditText) view.findViewById(R.id.editText3);
        editText4 = (EditText) view.findViewById(R.id.editText4);
        editText5 = (EditText) view.findViewById(R.id.editText5);
        editText6 = (EditText) view.findViewById(R.id.editText6);
        joinGroup = (Button) view.findViewById(R.id.button2);
        id = new StringBuilder();


        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    id.append(s.toString());
                    editText2.requestFocus();
                }

                if(s.length() == 0) {
                    id.deleteCharAt(0);
                }
            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    id.append(s.toString());
                    editText3.requestFocus();
                }

                if(s.length() == 0) {
                    id.deleteCharAt(1);
                    editText1.requestFocus();
                }
            }
        });

        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    id.append(s.toString());
                    editText4.requestFocus();
                }

                if(s.length() == 0) {
                    id.deleteCharAt(2);
                    editText2.requestFocus();
                }
            }
        });

        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    id.append(s.toString());
                    editText5.requestFocus();
                }

                if(s.length() == 0) {
                    id.deleteCharAt(3);

                    editText3.requestFocus();
                }
            }
        });

        editText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    id.append(s.toString());
                    editText6.requestFocus();
                }

                if(s.length() == 0) {
                    id.deleteCharAt(4);
                    editText4.requestFocus();
                }
            }
        });

        editText6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    id.append(s.toString());
                }

                if(s.length() == 0) {
                    id.deleteCharAt(5);
                    editText5.requestFocus();
                }
            }
        });

        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id.length() != 6) {
                    toast.createToast(getActivity(), "You code must contain 6 digits.");
                }

                else {
                    DatabaseConnector database = new DatabaseConnector("Users");
                    database.joinGroup(id.toString());
                    toast.createToast(getActivity(), "Group joined");
                    switchFragment();
                }

            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void switchFragment() {
        Fragment fragment = new MapFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_join_group, container, false);
    }
}
