package tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileInputer{
    private static BufferedReader reader;
    public FileInputer(String path){
        try {
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public String readLine(){
        String res="";
        try {
            res=reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public void close(){
        try {
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
