package qianlima.io;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Test1 {

	public static void main(String[] args) throws Exception {  
        File file = new File("F:\\text.txt");  
        if(!file.exists()) {  
            file.createNewFile();  
        }  
        FileOutputStream fos = new FileOutputStream(file);  
        BufferedOutputStream bos = new BufferedOutputStream(fos);  
        DataOutputStream dos = new DataOutputStream(fos);  
        dos.writeBytes("java io");  
    }  
}
