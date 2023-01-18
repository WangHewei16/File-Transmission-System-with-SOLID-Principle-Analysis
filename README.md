[TOC]

## File-Transmission-System-with-SOLID-Principle-Analysis
The file transmission system consists of a sender and a receiver, user can run and open multiple program windows on one computer at the same time to simulate senders and receivers. Sockets are used for file transfers, and each task supports multiple files transmission at the same time, and many tasks can occur at the same time period. Both the sender and the receiver can query, pause and start the file transmission.

* ***Language***: Java 8
* ***Integrated Development Environment (IDE)***: IntelliJ IDEA
* ***JDK Version***: 1.8 Oracle OpenJDK version 1.8.0_333


## 1. How to Use
* ***Step 1: Run Server***: Run the Server through "App.java" file in package "S".

* ***Step 2: Connect Client to Server***: In the first UI, your IP address will be generated automatically. You could input your port number, then click the ``connect" button. Figure below shows this step. Assume port 2001 is the sender, and port 2022 is the receiver.
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Connect%20Client%20to%20Server.png?raw=true" width="380"/></div>

* ***Step 3: Send Files to Receiver***: In the second UI, you could input the IP address and port number of your target receiver, choose the files that you want to transmit, and input the task name. Then you could click "Send" to transmit the task. Figure below shows this step.
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Send%20Files%20to%20Receiver.png?raw=true" width="380"/></div>

* ***Step 4: Query Task Condition / Pause / Continue***: You could input the task name to do these operations, if the status is finished, you could click the "Query" button to see the task list and file list. Task name and status will be shown in the task list. If the status is transmitting, you could click "Pause" button to pause the transmission, and click ``Continue" button to continue transmitting files in this task. Figure below shows this step.
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Query:Pause:Continue.png?raw=true" width="380"/></div>

## 2. Program Running Result
Both tasks and files in each task have four statuses during task transmission. Figure below shows the running result of fours status in my program: **Uploading**, **Pause**, **Downloading**, **Finish**. After the sender selects the files and clicks the "Start" button to start transmitting the task, the task will first be in the "Uploading" state and uploaded to the server, and after it is transmitted to the server, the task will be in the "Downloading" state, which indicates that the receiver is downloading the file in the task. During the above phase, the sender can pause the task transfer by clicking the "Pause" button, and can continue the transmission by clicking the "Continue" button. Finally, the transmission ends and the "Finish" status is displayed.

<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Four%20kinds%20of%20status%20during%20Transmission.png?raw=true" width="700"/></div>

If a task's status has been **Finish**, user click the "Pause" button, program will pop up a yellow window made by JDialog to remind the transmission of the task has finished, because it is meaningless to click the "Pause" button when transmission has finished. Figure below shows this condition.

<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/pop-up%20window.png?raw=true" width="380"/></div>



## 3. Architecture Design Idea

### 3.1 Server Design
* ***The Application layer*** is used for application startup and listening of connection, listening and receiving client connections through Entrance.
* ***The Transmission layer*** is used for forward, where the server only acts as an intermediary to break the private network barriers and forward commands or files to the target client.
* ***The File layer*** is used to encode and decode binary byte streams into serializable objects that are suitable for different transport services. This layer contains InfoMessage class that applies to InfoThread and TransMessage class that applies to FileThread. TransMessage is used to transmit basic instructions, and InfoMessage is used to update the task status.

<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Server%20Design%20Diagram.png?raw=true" width="300"/></div>

### 3.2 Client Design
* ***The Application layer*** is used for application startup and UI interface.

* ***The Transmission layer*** is used for file transmission and contains the FileThread and InfoThread that transfer files and instructions, which hold the pipeline resources from the time the connection is established until the connection is disconnected.  InfoThread is to get the status of the file transmission and FileThread is to transmit the file. ConnectTransImpl provides the application layer Connection Services. FileTrans and InfoTrans interfaces are implemented by FileTransImpl and InfoTransImpl respectively. The design of this part is a good example of the dependency inversion principle (DIP).

* ***The File layer*** is used to encode and decode binary byte streams into serializable objects that are suitable for different transport services. This layer contains FileTransImpl provides services related to file transmission and InfoTransImpl provides services related to information transmission. InfoMessage class applies to InfoThread and TransMessage class applies to FileThread. TransMessage is used to transmit basic instructions and InfoMessage is used to update the task status.

<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Client%20Design%20Diagram.png?raw=true" width="500"/></div>

### 3.3 Design principle of multi-task transmission at the same time
After connecting to the server, a communication thread **InfoThread** will always be maintained for instruction transmission. Also, during connection initialization, we will maintain multiple transmission threads **FileThread** at the same time, the number depends on the maximum number of concurrency, each transmission thread can parallelize tasks at the same time without waiting for each other, thus realizing multi-task transmission. InfoThread is to get the status of the file transmission and FileThread is to transmit the file. Figure below shows the Client/Server architecture diagram in my program that supports the simultaneous transmission of multiple tasks. 

<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/C:S%20Architecture%20Diagram.png?raw=true" width="300"/></div>

Figure below(a) shows several tasks are being transmitted with different statuses when querying many tasks' statuses one by one. It also shows that multiple tasks are transferred in parallel to each other, while multiple files in a task are transferred serially. Figure below(b) shows all tasks finish the transmission. The maximum number of concurrent tasks is approximately 3 to 10, which depends on the size of the files.

<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Multi-task%20Transmission.png?raw=true" width="700"/></div>


## 4. SOLID Principles Analysis
### 4.1 Single Responsibility Principle
In a good example, Figure below shows two kinds of messages are written into two classes and extend to a BaseMessage class. **TransMessage** is used to transmit basic instructions and **InfoMessage** is used to update task status. Each class only has one reason to change, and its cohesion is high. These designs confirm the SRP and are a good embodiment of SRP. Reading my program code, you can find that SRP is very consistent in my program!

<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/SRP.png?raw=true" width="300"/></div>

### 4.2 Open Close Principle
In a good example, we can add a new class related to a thread without modifications by inheriting the Thread directly. It finds an abstraction for what is common in the variation firstly, then use polymorphism to add varying behavior, so it confirms to OCP. Figure below shows the design idea in our program.

<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/OCP.png?raw=true" width="280"/></div>

### 4.3 Liskov Substitution Principle
In a good example, four classes use their own functions and only use the function of the parent class in their own functions. In this way, the subclass can still replace the parent class. Figure below shows a good example of LSP.

<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/LSP.png?raw=true" width="300"/></div>

### 4.4 Interface Segregation Principle
In a good example, three classes implement their corresponding interface. They only implement the function they need. Hence, this is in line with the ISP principle. Figure below shows a good example of an ISP.

<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/ISP.png?raw=true" width="300"/></div>

### 4.5 Dependency Inversion Principle
In a good example, Trans related interface is set between thread related class and message related class. In this way, **thread** doesn't care about the encapsulation of the message. It just needs to send the message to **trans**, which will be responsible for the encapsulation of **message**. High-level modules and low-level modules both depend on an abstraction interface. FileThread class depends on the FileTrans interface and FileTransImpl class implements the FileTrans interface. InfoThread class depends on the InfoTrans interface and InfoTransImpl class implements the InfoTrans interface. It makes high-level modules not depend on low-level modules and both depend on abstractions. These two interfaces do not depend on every class, which confirms that "Abstractions should not depend on details. Details should depend on abstractions". These designs conform to DIP. Figure below shows a good example of DIP. 

<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/DIP.png?raw=true" width="500"/></div>
