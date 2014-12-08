package helpers;


import java.io.*;
import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BURGER
 */
public class ReadFileToString {
    private Scanner x;
    private String text = "";
    
    public void openFile(String fileName){
        try{
            x = new Scanner(new File(fileName));
            
            
        }
        catch(Exception e){
            System.out.println("could not find file");
            //File file = new File(fileName);
            //System.out.printf(file.getAbsolutePath());
        }
    }
    public void readFile(){
        
        //makes a while loop has another input 
        //then it will turn false and break out of loop
        while(x.hasNext()){
            String a = x.nextLine();
            
            if(x.hasNext()){
                text += a + "\n";
            }else{
                text += a;
            }
            //String b = x.next();
            //String c = x.next();
            
            //System.out.printf(" %s \n", a);
            
        }
        //System.out.printf("%s \n", text);
    }
    
    public String getString(){
        return text;
    }
    
    public void closeFile(){
        x.close();
    }
    public String LoadToString(String fileName){
        openFile(fileName);
        readFile();
        closeFile();
        return getString();
    }
}
