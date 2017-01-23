
############################################################ CNT5106 Project #############################################################

Name: Anjali Verma
UFID: 04862395

Steps to run the program:
----------------------------------------
The program is highly dependent on the local directory structure.
One needs to create the following directories to establish a suitable environment for the program to run in:
***** Output Directory: All the output and file transactions by the software are stored in this directory
C:\Users\anjal\Desktop\Project Output
C:\Users\anjal\Desktop\Project Output\Logs 			(Stores log file of recieved chunks for each individual client)
C:\Users\anjal\Desktop\Project Output\Clients
C:\Users\anjal\Desktop\Project Output\Combines\client5 		(Files downloaded by client5)
C:\Users\anjal\Desktop\Project Output\Combines\client4 		(Files downloaded by client4)
C:\Users\anjal\Desktop\Project Output\Combines\client3 		(Files downloaded by client3)
C:\Users\anjal\Desktop\Project Output\Combines\client2 		(Files downloaded by client2)
C:\Users\anjal\Desktop\Project Output\Combines\client1		(Files downloaded by client1)
C:\Users\anjal\Documents\NetBeansProjects\PeerToPeer\test       (Chunks stored here)

***** Input Directory: It fetches inputs from here and uses it to store chunks

C:\Users\anjal\Documents\NetBeansProjects\PeerToPeer\src    (store input file here)


1. Specify the name of the file along with extension in MasterServer.java under the variable "name" of type String.
2. Run MasterServer.java
3. Then, in any order run:    run1.java       run2.java       run3.java        run4.java           run5.java
4. After this, you will see the files recieved by clients in the client subdirectory.
5. "Logs" will then contain the summary files for each of the clients. 
 

MasterServer.java
-----------------
1. Independent on it's own, doesn't require any values.
2. If needed one can use a different input by simply changing the value of "name" field
3. Server gets it's input from "C:\Users\anjal\Documents\NetBeansProjects\PeerToPeer\test". This too can be changed if needed.
4. When the program is run, it takes the input and breaks into small chunks no larger than 100KB each. Whenever, a connection 
   with a peer is established, it sends a set amount of chunks to the peer.
5. To start the server just execute the MasterServer.java file.

run1.java     run2.java     run3.java       run4.java       run5.java
----------------------------------------------------------------------
1. These programs each individually represent a peer.
2. When these programs are executed, they create a thread that connects to the server and recieves chunks of data intitally.
3. This thread, then calls upon two threads, PeerServer and PeerNeighborDown, they act as an uploader and downloader respectively
4. Successful execution of all these 5 program marks the end of the project.


PeerNeighborDown.java
----------------------
1. It is the code for the thread for dowloading.
2. It asks it's PeerSever for it's ChunkList, and then determines what chunks it doesn't have and then gets those from the PeerServer.
3. It consistently probes the server for Chunk List, and then repeats the process all over again until the number of chunks the client
has equals the number of total chunks originaly.



