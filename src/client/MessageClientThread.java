package client;

import models.Event;
import models.Message;
import models.User;

import java.io.*;
import java.net.Socket;

public class MessageClientThread implements Runnable {

    Thread listenerThread;
    Socket socketToServer;
    MessageClient client;

    ObjectInputStream in;
    ObjectOutputStream out;

    public MessageClientThread(MessageClient messageClient) {
        this.client = messageClient;
    }

    public void sendUser(User u) {
        try {
            out.writeObject(new Event(1, u));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message m) {
        try {
            out.writeObject(new Event(3, m));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used by the client listener to differentiate between all the Event types and handles
     * them accordingly.
      * @param e Event type. (See the documentation in models.Event)
     */
    public synchronized void handleEvent(Event e) {
        // Add User
        if (e.getEventType() == 1) {
            System.out.println("Client: Got user " + e.getUser().toString());
            this.client.addUserToList(e.getUser());
        }

        // Remove User
        else if (e.getEventType() == 2) {
            this.client.removeUserFromList(e.getUser());
        }

        //Send Message
        else if (e.getEventType() == 3) {
            this.client.addMessage(e.getMessage());
        }

        //Update "Connected users" list on client window.
        else if (e.getEventType() == 4) {
            System.out.println("Client: Got user list with " + e.getUserList().size()); //For debugging. Remove later.
            this.client.updateUserList(e.getUserList());
        }
    }

    public void startListening() {
        System.out.println("Client: Start listening!"); //For debugging. Remove later.
        if (listenerThread == null) {
            listenerThread = new Thread(this);
            listenerThread.start();
        }
    }

    public void stopListening() {
        try {
            socketToServer.close();
            listenerThread.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            socketToServer = new Socket("127.0.0.1", 55555);
            in = new ObjectInputStream(socketToServer.getInputStream());
            out = new ObjectOutputStream(socketToServer.getOutputStream());

            while(true) {
                Event newEvent = (Event) in.readObject();
                this.handleEvent(newEvent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}