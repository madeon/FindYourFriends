package com.example.mathias.findyourfriends.Helpers;

/**
 * Created by mathi on 27-03-2018.
 */

public class Group {

    private String groupName;
    private String ID;
    private String users;

    public Group(String groupName, String ID, String users) {
        this.groupName = groupName;
        this.ID = ID;
        this.users = users;
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
    public String getusers() {
        return users;
    }
    public void setusers(String users) {
        this.users = users;
    }

}
