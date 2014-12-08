/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BURGER
 */
public class APathFinding extends AbstractAppState{
    
    private SimpleApplication app;
    private AssetManager assetManager;
    private InputManager inputManager;
    private Node rootNode;
    private Camera cam;
    ///List<MyObject> list = new ArrayList<MyObject>();
    //you can put any object into a list but you can generics to limit types 
    // of objects in lists
    
    //MyObject myObject = list.get(0);

    //for(MyObject anObject : list){
   //do someting to anObject...   
    //}
    private List closedList = new ArrayList();
    private List openList = new ArrayList();
    
    
     public void initialize(AppStateManager stateManager,Application app){
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.cam = this.app.getCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        System.out.println("PathFinder State Attached");
        
        
    }
     
     /*
      * List listA = new ArrayList();

listA.add("element 0");
listA.add("element 1");
listA.add("element 2");

//access via index
String element0 = listA.get(0);
String element1 = listA.get(1);
String element3 = listA.get(2);


//access via Iterator
Iterator iterator = listA.iterator();
while(iterator.hasNext(){
  String element = (String) iterator.next();
}


//access via new for-loop
for(Object object : listA) {
    String element = (String) object;
}
      */
}
