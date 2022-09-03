package view;

import trans.ConnectTransImpl;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;


public class ConnectGUI extends JFrame {
    JPanel panel = new JPanel(null);

    public void initUI() throws IOException {

        // Window properties
        this.setTitle("Connect");
        this.setSize(600, 130);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set fixed point layout
        this.setLayout(null);

        // Settings panel
        panel.setBounds(0, 0, 600, 130);
        Color backGroundColor = new Color(176, 200, 255);
        panel.setBackground(backGroundColor);


        // Input box
        JTextField IPField = new JTextField();
        IPField.setBounds(120,20,130,35);
        JTextField PortField = new JTextField();
        PortField.setBounds(310,20,130,35);

        IPField.setText(InetAddress.getLocalHost().getHostAddress());
        IPField.setEditable(false);

        // Text (label)
        JLabel IPAddressText = new JLabel("IP Address: ");
        IPAddressText.setFont(new Font("黑体",Font.BOLD, 13));
        IPAddressText.setBounds(40,20,100,35);

        JLabel PortText = new JLabel("Port: ");
        PortText.setFont(new Font("黑体",Font.BOLD, 13));
        PortText.setBounds(274,20,100,35);

        JButton ConnectButton = new JButton("Connect");
        ConnectButton.setBounds(470,20,70,35);

        panel.add(IPAddressText);
        panel.add(IPField);
        panel.add(PortText);
        panel.add(PortField);
        panel.add(ConnectButton);
        ConnectButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                (new ConnectTransImpl()).connect(IPField.getText(), PortField.getText());
                try {
                    (new MainGUI()).initUI();
                    setVisible(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        this.add(panel);
        this.setVisible(true);
    }
}
