/*  Arman Iqbal
    Class of IoT 21

    Multicast Chat program.
     */

package client;

import models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.util.List;

public class ChatWindow extends JDialog {

    private JPanel contentPane;
    private JButton sendButton;
    private JPanel chatWindow;
    private JTextField userText;
    private JTextArea chatArea;
    private JTextArea connectedUsers;

    private MessageClient messageClient;
    public ChatWindow(MessageClient client) {
        this.messageClient = client;
        setContentPane(contentPane);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSend();

            }
        });

        userText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSend();

            }
        });

    }

    private void onSend() {
        if (userText.getText().length() > 0) {
            this.messageClient.sendMessage(userText.getText());
        }
        userText.setText("");
    }

    public void updateUserList(List<User> userList) {
        String userListString = "";
        for(User user : userList) {
            userListString += user.getUsername() + "\n";
        }
        this.connectedUsers.setText(userListString);
    }

    /**
     * Getters and setters
     */
    public JTextField getUserText() {
        return userText;
    }

    public void setUserText(JTextField userText) {
        this.userText = userText;
    }

    public JTextArea getChatArea() {
        return chatArea;
    }

    public void setChatArea(JTextArea chatArea) {
        this.chatArea = chatArea;
    }

    public JTextArea getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(JTextArea connectedUsers) {
        this.connectedUsers = connectedUsers;
    }


}
