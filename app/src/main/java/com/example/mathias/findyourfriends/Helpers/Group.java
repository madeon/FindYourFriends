package com.example.mathias.findyourfriends.Helpers;

/**
 * Created by mathi on 27-03-2018.
 */

public class Group {

    private String groupName;
    private String ID;


    public Group(String groupName, String ID) {
        this.groupName = groupName;
        this.ID = ID;
    }


    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
