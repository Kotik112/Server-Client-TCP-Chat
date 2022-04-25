package server;

import models.Message;
import models.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageServer {
    private List<User> userList = new ArrayList<User>();
    private List<MessageServerThread> serverThreads = new ArrayList<MessageServerThread>();
    private ListeningThread listeningThread;

    public MessageServer () {
        listeningThread = new ListeningThread(this);
        listeningThread.startListening();
    }

    public void addSocket(Socket s) {
        MessageServerThread messageServerThread = new MessageServerThread(s, this);
        serverThreads.add(messageServerThread);
        messageServerThread.startListening();
    }

    public void addUser(User e) {
        userList.add(e);
        System.out.println("Server: Added user. We now have " + userList.size());
        for(MessageServerThread thread : serverThreads) {
            thread.sendUserList((ArrayList<User>) userList);
        }
    }

    public void removeMessageServerThread(MessageServerThread mst) {
        this.serverThreads.remove(mst);
    }

    public void removeUser(User e) {
        userList.remove(e);
    }

    public void handleMessage(Message m) {
        for(MessageServerThread thread : serverThreads) {
            thread.sendMessage(m);
        }
    }
}
