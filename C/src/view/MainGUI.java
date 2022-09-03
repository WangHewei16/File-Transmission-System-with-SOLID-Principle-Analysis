package view;

import manage.ConnectStorage;
import manage.FileStorage;
import manage.PeerStorage;
import manage.TaskStorage;
import pojo.InfoMessage;
import pojo.Task;
import trans.FileTransImpl;
import trans.InfoTransImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.List;


public class MainGUI extends JFrame {
    JPanel panel = new JPanel(null);

    public void initUI() throws IOException {

        Task t = new Task();

        // Window properties
        this.setTitle("File Transmission System - Hewei Wang");
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set fixed point layout
        this.setLayout(null);

        // Set fixed point layout
        panel.setBounds(0, 0, 900, 600);
        Color backGroundColor = new Color(176, 200, 255);
        panel.setBackground(backGroundColor);

        // Input box
        JTextField IPField = new JTextField();
        IPField.setBounds(110,20,130,35);
        JTextField PortField = new JTextField();
        PortField.setBounds(301,20,130,35);
        JTextField TaskNameField = new JTextField();
        TaskNameField.setBounds(545,20,180,35);
        TextArea TaskListField = new TextArea();
        TaskListField.setBackground(new Color(255,252,238));
        TaskListField.setBounds(50,130,200,400);
        TextArea FileListField = new TextArea();
        FileListField.setBackground(new Color(255,252,238));
        FileListField.setBounds(300,130,200,280);
        JTextField TaskNameField2 = new JTextField();
        TaskNameField2.setBounds(300,460,200,35);
        TaskNameField2.setBackground(new Color(255,252,238));

        // Monitor thread, monitor task status, can be set according to computer configuration sleep, 1-3 seconds is the best
//        Thread thread = new Thread(() -> {
//            while(true){
//
//            }
//        });
//        thread.start();


        // Text (label)
        JLabel IPAddressText = new JLabel("IP Address: ");
        IPAddressText.setFont(new Font("黑体",Font.BOLD, 13));
        IPAddressText.setBounds(33,20,100,35);

        JLabel PortText = new JLabel("Port: ");
        PortText.setFont(new Font("黑体",Font.BOLD, 13));
        PortText.setBounds(266,20,100,35);

        JLabel TaskNameText = new JLabel("Task Name:");
        TaskNameText.setFont(new Font("黑体",Font.BOLD, 13));
        TaskNameText.setBounds(463,20,100,35);

        JLabel TaskListText = new JLabel("Task List");
        TaskListText.setFont(new Font("黑体",Font.BOLD, 18));
        TaskListText.setBounds(112,100,100,35);

        JLabel FileListText = new JLabel("File List");
        FileListText.setFont(new Font("黑体",Font.BOLD, 18));
        FileListText.setBounds(360,100,100,35);


        JButton StartButton = new JButton("Start");
        StartButton.setBounds(770,20,70,35);

        JLabel TaskNameText2 = new JLabel("Task name: ");
        TaskNameText2.setFont(new Font("黑体",Font.BOLD, 13));
        TaskNameText2.setBounds(305,432,100,35);

        JButton QueryButton = new JButton("Query");
        QueryButton.setBounds(297,500,60,35);

        JButton PauseButton = new JButton("Pause");
        PauseButton.setBounds(362,500,60,35);

        JButton RestartButton = new JButton("Continue");
        RestartButton.setBounds(427,500,80,35);




        JButton ChooseFileButton = new JButton("Choose files");
        final TextArea label = new TextArea();
        label.setBounds(642,140,180,270);
        label.setEditable(false);
        ChooseFileButton.setBounds(654,100,150,35);
        ChooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                FileStorage.clear();
                int option = fileChooser.showOpenDialog(panel);
                if(option == JFileChooser.APPROVE_OPTION){
                    File[] files = fileChooser.getSelectedFiles();
                    HashMap<String, String> fl = new HashMap<>();
                    String fileNames = "";
                    label.setText("The chosen files are:");
                    label.append("\n");
                    for(File file: files) {
                        fileNames += file.getName() + "\n";
                        fl.put(file.getName(),"0");
                        FileStorage.add(file.getAbsolutePath());
                    }
                    t.setFiles(fl);
                    label.append(fileNames);
                }else{
                    label.setText("Open command cancel");
                }
            }
        });
        StartButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // send operation
                String ip = IPField.getText();
                String port = PortField.getText();
                String taskName = TaskNameField.getText();
                t.setTaskName(taskName);
                t.setAim(ip + "-" + port);
                TaskStorage.change(t);
                System.out.println("send out");
                label.setText("Please choose files");
                (new InfoTransImpl()).sendInfo("0", "2", taskName, t.getFiles(), ip + "-" + port);
                (new FileTransImpl()).sendTask(taskName);
            }
        });
        QueryButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // send operation
                FileListField.setText("");
                String taskName = TaskNameField2.getText();
                FileListField.setText("Task " + taskName + " includes:\n");
                Task task = TaskStorage.getTask().get(taskName);
                HashMap<String, String> files = task.getFiles();
                Set<Map.Entry<String, String>> entries = files.entrySet();
                int count = 1;
                for (Map.Entry<String, String> entry : entries) {
                    FileListField.append("File " + count + ": " + entry.getKey() + "\n");
                    String tmp = null;
//                    System.out.println(entry.getValue());
                    switch(entry.getValue()){
                        case "0":
                            tmp = "Uploading";
                            break;
                        case "1":
                            tmp = "Downloading";
                            break;
                        case "2":
                            tmp = "Finish";
                            break;
                        case "3":
                            tmp = "Pause";
                            break;
                    }
                    FileListField.append("Status: " + tmp + "\n");
                    count += 1;
                }
                TaskListField.setText("");
                Map<String, Task> task2 = TaskStorage.getTask();
                for (Map.Entry<String, Task> stringTaskEntry : task2.entrySet()) {
                    TaskListField.append("Task name: " + stringTaskEntry.getKey()+ "\n");
                    String tmp = null;
                    switch(stringTaskEntry.getValue().getStatus()) {
                        case "0":
                            tmp = "Uploading";
                            break;
                        case "1":
                            tmp = "Downloading";
                            break;
                        case "2":
                            tmp = "Finish";
                            break;
                        case "3":
                            tmp = "Pause";
                            break;
                    }
                    TaskListField.append("Status: " + tmp + "\n");
                    TaskListField.append("=====================\n");
                }
            }
        });

        PauseButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // send operation
                String taskName = TaskNameField.getText();
                // update itself firstly
                // pause: only can pause sending when not finishing download
                Task task = TaskStorage.getTask().get(taskName);
                if (!task.getStatus().equals("2")){
                    task.setStatus("3");
                    TaskStorage.change(task);
                    // Let the service notify the opposite side of the "pause" may currently flow on the opposite side
                    InfoMessage message = new InfoMessage();
                    message.setType("1");
                    message.setDetailType("5");
                    message.setTaskName(taskName);
                    message.setContent(task.getAim() + "*" + PeerStorage.getInfo());
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(ConnectStorage.getInfo().getInfoSocket().getOutputStream());
                        oos.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    new MyDialog();
                }

            }
        });

        RestartButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // send operation
                String taskName = TaskNameField.getText();
                // update itself firstly
                // recover pause: only can pause sending when not finishing download
                Task task = TaskStorage.getTask().get(taskName);
                System.out.println(task.getStatus());
                if (task.getStatus().equals("3")){
                    task.setStatus("1");
                    TaskStorage.change(task);
                    // Let the service notify the opposite side of the "pause" may currently flow on the opposite side
                    InfoMessage message = new InfoMessage();
                    message.setType("1");
                    message.setDetailType("6");
                    message.setTaskName(taskName);
                    message.setContent(task.getAim() + "*" + PeerStorage.getInfo());
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(ConnectStorage.getInfo().getInfoSocket().getOutputStream());
                        oos.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    new MyDialog();
                }
            }
        });

        panel.add(IPAddressText);
        panel.add(IPField);
        panel.add(PortText);
        panel.add(PortField);
        panel.add(TaskNameField);
        panel.add(TaskNameText);
        panel.add(TaskListField);
        panel.add(TaskListText);
        panel.add(FileListField);
        panel.add(FileListText);
        panel.add(StartButton);
        panel.add(TaskNameField2);
        panel.add(TaskNameText2);
        panel.add(QueryButton);
        panel.add(PauseButton);
        panel.add(RestartButton);
        panel.add(ChooseFileButton);
        panel.add(label);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    ConnectStorage.getInfo().getInfoSocket().close();
                    ConnectStorage.getFile().getFileSocket().close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            }
        });

        this.add(panel);
        this.setVisible(true);
    }
}
