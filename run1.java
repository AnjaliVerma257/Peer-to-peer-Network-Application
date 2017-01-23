/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anjal
 */
public class run1 {
    public static void main(String args[])
    {
                
        DownFromServer d=new DownFromServer(2507,100,200);        
        Thread DownloadThread=new Thread(d);
        DownloadThread.start();
        
                       
        
    }
}
