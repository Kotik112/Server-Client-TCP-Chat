package start;

import server.MessageServer;
import client.MessageClient;

import javax.swing.*;
import java.awt.event.*;

public class StartWindow extends JDialog {
    private JPanel contentPane;
    private JButton clientButton;
    private JButton serverButton;

    public StartWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(clientButton);

        clientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClientPressed();
            }
        });

        serverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onServerPressed();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onServerPressed();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onServerPressed();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onClientPressed() {
        MessageClient messageClient = new MessageClient();
        dispose();
    }

    private void onServerPressed() {
        MessageServer messageServer = new MessageServer();
        dispose();
    }

    public static void main(String[] args) {
        StartWindow dialog = new StartWindow();
        dialog.setLocation(800,500);
        dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }
}
