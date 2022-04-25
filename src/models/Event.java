package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable {

    private int eventType;
    /**
     * 1 = Add User
     * 2 = Remove User
     * 3 = Send Message
     * 4 = Update User List
     */
    private Message message;
    private User user;
    private ArrayList<User> userList;

    public Event(int eventType, User user) {
        this.eventType = eventType;
        this.user = user;
    }

    public Event(int eventType, Message message) {
        this.eventType = eventType;
        this.message = message;
    }

    public Event(int eventType, ArrayList<User> userList) {
        this.eventType = eventType;
        this.userList = userList;
    }

    public int getEventType() {
        return eventType;
    }

    public Message getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }
}
