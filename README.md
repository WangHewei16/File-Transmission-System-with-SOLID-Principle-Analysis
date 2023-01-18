## File-Transmission-System-with-SOLID-Principle-Analysis
The file transmission system consists of a sender and a receiver, user can run and open multiple program windows on one computer at the same time to simulate senders and receivers. Sockets are used for file transfers, and each task supports multiple files transmission at the same time, and many tasks can occur at the same time period. Both the sender and the receiver can query, pause and start the file transmission.

## How to Use


## Architecture Design Idea

### Server Design
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Server%20Design%20Diagram.png?raw=true" width="700"/></div>

### Client Design
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/Client%20Design%20Diagram.png?raw=true" width="700"/></div>

### Design principle of multi-task transmission at the same time
<div align=center><img src="https://github.com/WangHewei16/File-Transmission-System-with-SOLID-Principle-Analysis/blob/main/Figures/C:S%20Architecture%20Diagram.png?raw=true" width="700"/></div>


## SOLID Principles Analysis
### Single Responsibility Principle
### Open Close Principle
### Liskov Substitution Principle
### Interface Segregation Principle
### Dependency Inversion Principle
