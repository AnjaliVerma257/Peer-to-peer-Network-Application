import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


class DownFromServer implements Runnable
{
    Socket requestSocket;           
	ObjectOutputStream out;     
 	ObjectInputStream in;       
	String message;             
	String FileName;            

File f;
int FileOwnersPort;    //Port number of the original file owner
int ClientListenerPort;   //Port number via which we upload to other Peers
int ClientDownloadPeerPort;   //Port number of peer to download from

    public DownFromServer(int FileOP, int ClientLP, int ClientDPP)
    {
        FileOwnersPort=FileOP;
        ClientListenerPort=ClientLP;
        ClientDownloadPeerPort=ClientDPP;
    }
    public void run()
    {
       {
		try{
			requestSocket = new Socket("localhost", FileOwnersPort);
			
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			int x=0;
                        int total_chunks = 0;
                        message="filename";
				sendData(message);
				FileName = (String)in.readObject();
			System.out.println("Receiving Chunks for File Name: " + FileName);
			
                        while(x!=1)
			{
				
				
                            Object c=in.readObject();
                            
                            if(c.toString().contains("anjali"))
                            {
                                x=1;
                                total_chunks=Integer.parseInt(c.toString().replace("anjali",""));
                                break;
                            }
                                f = (File)c;
                                (new File("C:\\Users\\anjal\\Desktop\\Project Output\\Clients\\"+(new Client()).getClientName(ClientListenerPort))).mkdir();
                                String b="C:\\Users\\anjal\\Desktop\\Project Output\\Clients\\"+(new Client()).getClientName(ClientListenerPort)+"\\";                         
                                File bfile =new File(b+f.getName());    		
    	    FileInputStream inStream = new FileInputStream(f);
    	    FileOutputStream outStream = new FileOutputStream(bfile);        	
    	    byte[] buffer = new byte[100000];    		
    	    int length;
    	    while ((length = inStream.read(buffer)) > 0)
            {    	  
    	    	outStream.write(buffer, 0, length);    	 
    	    }
System.out.println("Recieved Chunk "+ f.getName() +" from Server");
    	    inStream.close();
    	    outStream.close();
                
			
				
			}
                        
                        createSummary(ClientListenerPort);
                        
Thread upload=new Thread(new PeerServer(ClientListenerPort));
upload.setPriority(10);                        
upload.start();
                        



                       
                        
                            
                                           
                        
        Thread download=new Thread(new PeerNeighborDown(ClientListenerPort,ClientDownloadPeerPort,total_chunks,FileName));
        download.setPriority(1);            
        
        download.start();
                    
                            
		}
		catch (ConnectException e) {
    			System.err.println("Connection refused. You need to initiate a server first.");
		} 
		catch ( ClassNotFoundException e ) {
            		System.err.println("Class not found");
        	} 
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
		}
		finally{
			//Close connections
			try{
				in.close();
				out.close();
				requestSocket.close();
                                
			}
			catch(IOException ioException){
			}
		}
	}
    }
    
    void createSummary(int port) throws IOException
    {
        
        String source="C:\\Users\\anjal\\Desktop\\Project Output\\Clients\\"+(new Client()).getClientName(port);
        File f=new File(source);
        File dest= new  File("C:\\Users\\anjal\\Desktop\\Project Output\\Logs\\"+(new Client()).getClientName(port)+"summary");
        FileWriter fw = new FileWriter(dest,true);
    	        BufferedWriter bw = new BufferedWriter(fw);
    	                
                
                
        File l[]=f.listFiles();
        for(int i=0;i<l.length;i++)
            bw.write(l[i].getName()+"\n");
            
    	    bw.close();
    }
    
    
    void sendData(String msg)
	{
		try{
			
			out.writeObject(msg);
			out.flush();
		}
		catch(IOException ioException){
		}
	}
    
    
    
}









public class MasterClient
{
    public static void main(String args[])
    {
        
        
    }
}



