package com.example.mathias.findyourfriends.Helpers;

/**
 * Created by mathi on 27-03-2018.
 */

public class Group {

    private String UID;
    private String groupName;

    public Group(String UID, String groupName) {
        this.UID = UID;
        this.groupName = groupName;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
