import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// Function: send ChunkList and requested Chunks

public class PeerServer implements Runnable
{
    int port;
    ServerSocket peerServer;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    String requestType;
    String clientMessage;
    File chunkFile;
    String clientName;
    
    public PeerServer(int LP) 
    {
    port=LP;
    clientName=(new Client()).getClientName(LP);
    }
 public void run()
         {
        try
        {
            peerServer=new ServerSocket(port);
            
            Socket peerConn=peerServer.accept();
            out = new ObjectOutputStream(peerConn.getOutputStream());
            out.flush();
            in = new ObjectInputStream(peerConn.getInputStream());
            int flag=0;
            String request;
            while(flag==0)
            {
            clientMessage=(String)in.readObject();
            
            if(clientMessage.toLowerCase().equalsIgnoreCase("ChunkList"))
            {
                
               chunkFile=new File("C:\\Users\\anjal\\Desktop\\Project Output\\Logs\\"+clientName+"summary");
               File dir = new File("C:\\Users\\anjal\\Desktop\\Project Output\\Clients\\"+clientName);
               ArrayList<String> info = new ArrayList<>();
               
               for(final File chunk : dir.listFiles()){
                   info.add(chunk.getName());
               }
               //System.out.println(info);
               sendData(info);
                //sendData(chunkFile);
                
            }
            else if(clientMessage.toLowerCase().equalsIgnoreCase("GetChunk"))
            {
                
            ArrayList<String> fileNames=(ArrayList<String>) in.readObject();
             
            for(int i=0;i<fileNames.size();i++)
            {
                sendData(new File("C:\\Users\\anjal\\Desktop\\Project Output\\Clients\\"+clientName+"\\"+fileNames.get(i)));
                
            }
                sendData("anjali");
            }
            else
            {
                
                 flag=1;
                 break;
            }
            }
            
        in.close();
        out.close();
       peerConn.close();
        } catch (IOException ex) {
            Logger.getLogger(PeerServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PeerServer.class.getName()).log(Level.SEVERE, null, ex);
        }
             
         } 
 
 
 
 
 
 public void sendData(Object msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
		
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}