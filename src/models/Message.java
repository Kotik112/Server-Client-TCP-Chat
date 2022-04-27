/**
 * Used for Event type 3 - Send message. (see model.Event comments.)
 */

package models;

import java.io.Serializable;

public class Message implements Serializable {
    private String username;
    private String message;

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
