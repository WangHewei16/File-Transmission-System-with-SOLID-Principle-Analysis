## File-Transmission-System-with-SOLID-Principle-Analysis
The file transmission system consists of a sender and a receiver, user can run and open multiple program windows on one computer at the same time to simulate senders and receivers. Sockets are used for file transfers, and each task supports multiple files transmission at the same time, and many tasks can occur at the same time period. Both the sender and the receiver can query, pause and start the file transmission.

* Language: Java 8
* Integrated Development Environment (IDE): IntelliJ IDEA
* JDK Version: 1.8 Oracle OpenJDK version 1.8.0_333


## 1. How to Use
* ***Step 1: Run Server***: Run the Server through "App.java" file in package "S".
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Connect%20Client%20to%20Server.png?raw=true" width="380"/></div>

* ***Step 2: Connect Client to Server***: In the first UI, your IP address will be generated automatically. You could input your port number, then click the ``connect" button. Figure below shows this step. Assume port 2001 is the sender, and port 2022 is the receiver.
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Send%20Files%20to%20Receiver.png?raw=true" width="380"/></div>

* ***Step 3: Send Files to Receiver***: In the second UI, you could input the IP address and port number of your target receiver, choose the files that you want to transmit, and input the task name. Then you could click "Send" to transmit the task. Figure below shows this step.
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Query:Pause:Continue.png?raw=true" width="380"/></div>

You could input the task name to do these operations, if the status is finished, you could click the ``Query" button to see the task list and file list. Task name and status will be shown in the task list. If the status is transmitting, you could click ``Pause" button to pause the transmission, and click ``Continue" button to continue transmitting files in this task. Figure below shows this step.
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Four%20kinds%20of%20status%20during%20Transmission.png?raw=true" width="700"/></div>

<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/pop-up%20window.png?raw=true" width="380"/></div>



## 2. Architecture Design Idea

### 2.1 Server Design
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Server%20Design%20Diagram.png?raw=true" width="300"/></div>

### 2.2 Client Design
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Client%20Design%20Diagram.png?raw=true" width="500"/></div>

### 2.3 Design principle of multi-task transmission at the same time
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/C:S%20Architecture%20Diagram.png?raw=true" width="300"/></div>


<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Multi-task%20Transmission.png?raw=true" width="700"/></div>


## 3. SOLID Principles Analysis
### 3.1 Single Responsibility Principle
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/SRP.png?raw=true" width="300"/></div>

### 3.2 Open Close Principle
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/OCP.png?raw=true" width="280"/></div>

### 3.3 Liskov Substitution Principle
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/LSP.png?raw=true" width="300"/></div>

### 3.4 Interface Segregation Principle
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/ISP.png?raw=true" width="300"/></div>

### 3.5 Dependency Inversion Principle
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/DIP.png?raw=true" width="500"/></div>
