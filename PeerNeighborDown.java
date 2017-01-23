
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


// Downloads and requests chunk from neighbors. also, fethces list to find remaining chunks



public class PeerNeighborDown implements Runnable
{
    BufferedReader bw;
    public int NeighborPort;
    Socket requestSocket;           
    ObjectOutputStream out;         
    ObjectInputStream in; 
    String clientName;
    String fileName;
    
    //String requestType;
    int total;
    File file_temp;
    int ClientPort;
        ArrayList server = new ArrayList();
        ArrayList client = new ArrayList();
        int num;
    public PeerNeighborDown(int Client, int Neighbor, int tot, String name)
            {
                NeighborPort=Neighbor;                
                total=tot;
                ClientPort=Client;
                clientName=(new Client()).getClientName(ClientPort);
              fileName = name;
            }
    
    public void run()
    {
                
        try {
            Thread.sleep(30000);    //to wait for the peer server to be established
              requestSocket=new  Socket("localhost", NeighborPort);            
              File dest= new  File("C:\\Users\\anjal\\Desktop\\Project Output\\Logs\\"+(new Client()).getClientName(ClientPort)+"summary");
        FileWriter fileWritter = new FileWriter(dest,true);
    	        BufferedWriter bw = new BufferedWriter(fileWritter);       
          
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            
            
             while(num!=total)
                            {
            
                                int x=0;
//Request the peer server for ChunkID List
            File peer=new File("C:\\Users\\anjal\\Desktop\\Project Output\\Logs\\"+clientName+"summary");
            sendData("ChunkList");
            System.out.println("Client Name:      "+"Send Request to "+(new Client()).getClientName(NeighborPort) +" for chunk List" );
            ArrayList<String> info = (ArrayList<String>)in.readObject();
            System.out.println("Recieved ChunkList from "+(new Client()).getClientName(NeighborPort));
            
            String f=null;
      
          File dir = new File("C:\\Users\\anjal\\Desktop\\Project Output\\Clients\\"+clientName); 
          ArrayList<String> client = new ArrayList<>();
          for(final File chunk : dir.listFiles()){
                   client.add(chunk.getName());
               }
      
       ArrayList<String> add=missing(client, info);        
      
      if(add.size()!=0)
      {
          sendData("GetChunk");
          System.out.println("Requesting "+(new Client()).getClientName(NeighborPort)+"  for the following chunks:");
          System.out.println(add.toString());
          sendData(add);
          
          while(x!=1)
			{
				Object c=in.readObject();
                            
                            if(c.toString().contains("anjali"))
                            {
                                x=1;                                
                                break;
                            }
                                file_temp = (File)c;
                                
                                String b="C:\\Users\\anjal\\Desktop\\Project Output\\Clients\\"+(new Client()).getClientName(ClientPort)+"\\";                         
                                File bfile =new File(b+file_temp.getName());    		
    	    FileInputStream inStream = new FileInputStream(file_temp);
    	    FileOutputStream outStream = new FileOutputStream(bfile);        	
            
    	    byte[] buffer = new byte[100*1024];    		
    	    int length;
    	    while ((length = inStream.read(buffer)) > 0)
            {    	 
    	    	outStream.write(buffer, 0, length);    	 
    	    }
            
            System.out.println("Received chunk:   "+file_temp.getName() +"   from   " +(new Client()).getClientName(ClientPort));
                	    
                inStream.close();
    	    outStream.close(); 
				
                        }
          
          for(String s : info){
              bw.write(s+"\n");
          }
	
            }		
             
            
            
    	 
    	 
            
            
     num= new File("C:\\Users\\anjal\\Desktop\\Project Output\\Clients\\"+(new Client().getClientName(ClientPort))).listFiles().length;     
            
      } 
             sendData("done");
             System.out.println("Recieved All the chunks");
        bw.close();    
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PeerNeighborDown.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PeerNeighborDown.class.getName()).log(Level.SEVERE, null, ex);

            Logger.getLogger(PeerNeighborDown.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        try {
              in.close();
            out.close();
            requestSocket.close();
           combine(clientName,fileName);
        } catch (IOException ex) {
            Logger.getLogger(PeerNeighborDown.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
	void combine(String client, String file) throws IOException {
		String cname = client;
		String fname = file;
		String source = "C:\\Users\\anjal\\Desktop\\Project Output\\Clients\\" + cname;
		File arr[] = (new File(source)).listFiles();
		String dest = "C:\\Users\\anjal\\Desktop\\Project Output\\Combines\\" + cname + "\\" + fname;
		
		File dir = new File("C:\\Users\\anjal\\Desktop\\Project Output\\Combines\\" + cname) ;
		File f = new File(dir, fname);
		f.createNewFile();
		FileOutputStream fs = new FileOutputStream(f);
		
		for(File i : (new File(source)).listFiles()){
			Files.copy(i.toPath(), fs);
		}
		fs.flush();
		fs.close();
		
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
    
    
    
    public ArrayList missing (ArrayList<String> client, ArrayList<String> server)
    {
        ArrayList<String> s=server;       
        ArrayList<String> c=client;      
  s.removeAll(c);   
        return s;
    }
    
  
}