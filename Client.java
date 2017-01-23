public class Client {
int UploadPort;
int DownloadPort;
String name;

public String getClientName(int x)
{
    if(x==100)
        return "client1";
    else if(x==200)
        return "client2";
    else if(x==300)
        return "client3";
    else if(x==400)
        return "client4";
    else if(x==500)
        return "client5";
    else
        return null;   
   
}


public int getListeningPort(String a)
{
    String in=a.toLowerCase();
    if(in=="client1")
        return 100;
    else if(in=="client2")
        return 200;
    else if(in=="client3")
        return 300;
    else if(in=="client4")
        return 400;
    else if(in=="client5")
        return 500;
    else
        return 0;
}
}
