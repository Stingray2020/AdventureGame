/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author BURGER
 */
/*Program goes through set text and returns a string char by char using the char index in the string
 * over a certain amount of time
 * usefull for doing  dialog boxes , simulating the effect of typing text . 
 * 
 */
public class TrawlString {
    private static String text;
    private static String returnText ="";
    private static float timeTakes =0.02f;
    private static float timer=timeTakes;
    private static int index=0;
    private static boolean isRunning=false;
    
    ///sets up string and starts Stringtrawlling
    public void SetString(String text2){
        this.text = text2;
        returnText ="";
        isRunning=true;
        index=0;
        timer=timeTakes;
        //System.out.println(text2);
    }
    
    public String UpdateString( float tpf){
        timer -=tpf;
        if(timer <0 && text.length() !=index ){
            timer = timeTakes;
            //System.out.println(""+text.charAt(index));
            returnText += text.charAt(index);
            //if(text.length()-1 !=index){
            index++;
                //System.out.println(index);
            //}else{
            ///Remove this process or reset it
            //}

        }else if( text.length() ==index ){
            isRunning=false;
        }
        
        //System.out.println(returnText);
        return returnText;
    }
    public boolean isRunning(){
        return isRunning;
    }
    
    public String ScrollString(String text, float tpf){
        return text;
    }
    
}
