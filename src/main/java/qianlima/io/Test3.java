package qianlima.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Test3 {

	public static void main(String[] args) throws Exception {  
        File file = new File("F:\\text.txt");  
        if(!file.exists()) {  
            file.createNewFile();  
        }  
        FileOutputStream fos = new FileOutputStream(file);  
        BufferedOutputStream bos = new BufferedOutputStream(fos);  
        byte[] b = new byte[1024*8];  
        bos.write(b);  
        //bos.flush();  
    }  
}
