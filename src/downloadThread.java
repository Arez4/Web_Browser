import java.net.URL;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;

public class downloadThread extends Thread{
    private String FILE_URL;
    private String FILE_NAME;
    private String FILE_TYPE;

    public downloadThread(String FILE_URL, String FILE_NAME, String FILE_TYPE){
        this.FILE_URL = FILE_URL;
        this.FILE_NAME = FILE_NAME;
        this.FILE_TYPE = FILE_TYPE;
    }

    @Override
    public void run(){
        try{
            URL url = new URL(FILE_URL);
    
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            FileOutputStream fis = new FileOutputStream("./" + FILE_NAME + "." + FILE_TYPE);
    
            byte[] buffer = new byte[8192];
            int count = 0;
    
            while ((count = bis.read(buffer, 0, 8192)) != -1) {
                fis.write(buffer, 0, count);
            }
    
            fis.close();
            bis.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}