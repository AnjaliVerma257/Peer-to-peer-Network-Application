import java.io.*;
class FileSplitter {
    public void Split(File f) throws IOException {
        int chunkID = 1;

        int sizeOfFiles = 100*1024;// 100Kb : Chunk size
        
         long fsize=f.length();
        if ( (fsize/sizeOfFiles)<5)
         sizeOfFiles=(int) (fsize/5); 
        
        byte[] buffer = new byte[sizeOfFiles];

        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f))) {
            String name = f.getName();

            int tmp = 0;
            while ((tmp = bis.read(buffer)) > 0) {
                
                new File("C:\\Users\\anjal\\Documents\\NetBeansProjects\\PeerToPeer\\test\\"+name).mkdir();
                File newFile = new File("C:\\Users\\anjal\\Documents\\NetBeansProjects\\PeerToPeer\\test\\"+name+"\\"+name+ chunkID++);
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, tmp);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {

    }
}