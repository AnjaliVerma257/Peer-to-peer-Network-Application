import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MasterServer {
        private static final int sPort = 2507;
        private static String fname;

	public static void main(String[] args) throws Exception {
                fname = "test file 2.zip";
                FileSplitter s=new FileSplitter();
                System.out.println("Split Done");
                s.Split(new File("C:\\Users\\anjal\\Documents\\NetBeansProjects\\PeerToPeer\\src\\"+fname));
        	ServerSocket newPeer = new ServerSocket(sPort);
		int peerID = 1;
        	try {
            		while(peerID<6) {
                            Thread a=new peerConnection(newPeer.accept(),peerID);
                		a.start();
				System.out.println("Client "  + peerID + " is connected!");
				peerID=peerID+1;
            			}
        	} finally {
            		newPeer.close();
        	} 
 
    	}

    	private static class peerConnection extends Thread {
                private ObjectInputStream in;	
        	private ObjectOutputStream out;
                private String FromPeer;    //received from the peer
		private String toPeer;    //sent to the peer
		private Socket connection;        	
                String ClientRequest;
        
		private int ClientNum;	
                
//The index number of the client
private String folder = "C:\\Users\\anjal\\Documents\\NetBeansProjects\\PeerToPeer\\test\\"+fname;
        	public peerConnection(Socket s, int no) {
            		connection = s;
	    		ClientNum = no;
        	}

        public void run() {
 		try{
                    System.out.println("File Distribution Master Server Start");
                    out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
                        ClientRequest=in.readObject().toString();
   if(ClientRequest.equalsIgnoreCase("Filename"))
                    {
                        sendData(fname);
                    }
                    String a="C:\\Users\\anjal\\Documents\\NetBeansProjects\\PeerToPeer\\test\\"+fname;
                    File x=new File(a);
                    int tot= x.listFiles().length;
                    
			int i=(int) Math.ceil((tot/5)+0.49);
                        System.out.println(i);
                        int start=((ClientNum-1)*i)+1;
                        int end=ClientNum*i;
                        if(end>tot)
                            end=tot;
			
                        while(start<=end)
                        {
                            
                            File f=new File("C:\\Users\\anjal\\Documents\\NetBeansProjects\\PeerToPeer\\test\\"+fname+"\\"+fname+start);
                            sendData(f);
                            start=start+1;
                            System.out.println("Sent Chunk"+fname+start+"  to   "+"ClientNum:  " + ClientNum);
                        }

                        Object ter="anjali"+tot;
                        sendData(ter);
                        System.out.println("All Chunks Have Been Sent");
                        
                        in.close();
				out.close();
				connection.close();
		}
		catch(IOException ioException){
			
		}   catch (ClassNotFoundException ex) {   
                        Logger.getLogger(MasterServer.class.getName()).log(Level.SEVERE, null, ex);
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

}