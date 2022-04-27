package server;

import models.Message;
import models.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageServer {
    private final List<User> userList = new ArrayList<>();
    private List<MessageServerThread> serverThreads = new ArrayList<>();
    private ListeningThread listeningThread = null;

    public MessageServer () {
        listeningThread = new ListeningThread(this);
        listeningThread.startListening();
    }

    // Adds a socket to the server's thread list.
    public void addSocket(Socket s) {
        MessageServerThread messageServerThread = new MessageServerThread(s, this);
        serverThreads.add(messageServerThread);
        messageServerThread.startListening();
    }

    // Add a user to the server User list.
    public void addUser(User e) {
        userList.add(e);
        System.out.println("Server: Added user. We now have " + userList.size()); //For debugging. Remove later.
        for(MessageServerThread thread : serverThreads) {
            thread.sendUserList((ArrayList<User>) userList);
        }
    }

    // Removes a server thread from the server's thread list.
    public void removeMessageServerThread(MessageServerThread mst) {
        this.serverThreads.remove(mst);
    }

    //Will be incorporated when Connected user list is functional.
    public void removeUser(User e) {
        userList.remove(e);
    }

    public void handleMessage(Message m) {
        for(MessageServerThread thread : serverThreads) {
            thread.sendMessage(m);
        }
    }
}
