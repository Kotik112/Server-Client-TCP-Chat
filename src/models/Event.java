/*
* Arman Iqbal
* Class of IoT21
* 26-04-2022
* */

package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable {
    /**
     * # Event Types #
     * 1 = Add User
     * 2 = Remove User
     * 3 = Send Message
     * 4 = Update User List
     */
    private int eventType;

    private Message message;
    private User user;
    private ArrayList<User> userList;

    /**
     * Used for adding and removing users from connected user list.
     * @param eventType Should be used with Event type 1 and 2  (See the topmost comment in this class.)
     * @param user Should be currentUser
     */
    public Event(int eventType, User user) {
        this.eventType = eventType;
        this.user = user;
    }

    /**
     * Constructor for sending an Event message.
     * @param eventType This should be used with Event type 3 (See the topmost comment in this class.)
     * @param message Message object containing the message.
     */
    public Event(int eventType, Message message) {
        this.eventType = eventType;
        this.message = message;
    }

    /**
     * Note: Currently buggy and needs fixing.
     * Constructor for an "Update user list" requests when new clients are connected.
     * @param eventType
     * @param userList
     */
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
