
package client;

import models.Message;
import models.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MessageClient {

    private List<User> userList = new ArrayList<User>();
    private List<Message> messageList = new ArrayList<Message>();

    private User currentUser;
    private MessageClientThread client;

    private ChatWindow chatWindow;

    public MessageClient() {
        client = new MessageClientThread(this);
        client.startListening();
        createUser();
    }

    private void createUser() {
        String username = JOptionPane.showInputDialog("Enter your username: ");  //Use for username later
        if (username != null) {
            currentUser = new User(username);
            chatWindow = new ChatWindow(this);

            chatWindow.pack();
            chatWindow.setVisible(true);

            client.sendUser(currentUser);
        }

    }

    public void sendMessage(String s) {
        Message m = new Message(currentUser.getUsername(), s);
        client.sendMessage(m);
    }

    public void addMessage(Message m) {
        messageList.add(m);
        chatWindow.getChatArea().append(m.getUsername() + ": " + m.getMessage() + "\n");
    }

    public void addUserToList(User u) {
        userList.add(u);
        chatWindow.updateUserList(userList);
    }

    public void updateUserList(List<User> u) {
        userList = u;
        chatWindow.updateUserList(userList);
    }

    public void removeUserFromList (User e) {
        userList.remove(e);
    }
}
