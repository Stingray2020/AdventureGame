/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;

import astar.ANode;
import astar.AStar;
import astar.GridManager;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BURGER
 */
public class APathFindingState extends AbstractAppState implements ActionListener,AnalogListener{
    
    private SimpleApplication app;
    private AssetManager assetManager;
    private InputManager inputManager;
    private Node rootNode;
    private Node gridNode;
    private Node obsticalNode;
    private Node playerNode;
    private Node enemyNode;
    private Camera cam;
    private AStar astar;
    
    
    ///List<MyObject> list = new ArrayList<MyObject>();
    //you can put any object into a list but you can generics to limit types 
    // of objects in lists
    
    //MyObject myObject = list.get(0);

    //for(MyObject anObject : list){
   //do someting to anObject...   
    //}
    
    private float elapsedTime = 0.0f;
    //Interval time between pathfinding

    private float intervalTime = 1.0f;
    private List closedList = new ArrayList();
    private List openList = new ArrayList();
    private ArrayList pathArray;
    private ANode startNode , goalNode;
    
    
     public void initialize(AppStateManager stateManager,Application app){
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.cam = this.app.getCamera();
        setUpCam();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        System.out.println("PathFinder State Attached");
        ///set up models
        modelSetUp();
        gridNode = new Node();
        rootNode.attachChild(gridNode);
        astar = new AStar(obsticalNode,gridNode,assetManager);
        
        pathArray = new ArrayList();
        FindPath();
         setUpInput();
        
        
    }
    
    @Override
     public void update(float tpf){
     
        elapsedTime += tpf;
        if (elapsedTime >= intervalTime) {
            elapsedTime = 0.0f;
            FindPath();
        }
       //System.out.println(cam.getRotation());
        
     }
    
    private void FindPath() {
        
        Vector3f startpos,endpos; 
        startpos = new Vector3f(enemyNode.getChild(0).getLocalTranslation());
        endpos = new Vector3f(playerNode.getLocalTranslation());
        
        
        startNode = new ANode(GridManager.instance.GetGridCellCenter(GridManager.instance.GetGridIndex(startpos)));
        goalNode = new ANode(GridManager.instance.GetGridCellCenter(GridManager.instance.GetGridIndex(endpos)));
        
        pathArray = AStar.FindPath(startNode, goalNode);
        //System.out.println(pathArray.toString());
    }
     
     
     private Spatial createBox(Vector3f pos , ColorRGBA color){
         Geometry box = new Geometry("Box", new Box(1,1,1));
         Material mat =  new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
         mat.setColor("Color", color);
         mat.getAdditionalRenderState().setWireframe(true);
         Spatial spatial = box;
         spatial.setMaterial(mat);
         spatial.setLocalTranslation(pos);
         //System.out.println("created");
         return spatial;
     }
     
     public void modelSetUp(){
        obsticalNode=new Node();
        enemyNode=new Node();
        playerNode=new Node();
        playerNode.setLocalTranslation(23, 1, 27);
                
        for (int i =0; i<4; i++ ){
            obsticalNode.attachChild(createBox(new Vector3f(19-(i*2),1f,19.0f),ColorRGBA.Blue));
        }
        
        for (int i =0; i<4; i++ ){
            obsticalNode.attachChild(createBox(new Vector3f(21f,1f,19f+(i*2f)),ColorRGBA.Blue));
        }
       
        enemyNode.attachChild(createBox(new Vector3f(11.0f,1.0f,11.0f),ColorRGBA.Red));
        playerNode.attachChild(createBox(new Vector3f(0,0,0),ColorRGBA.Green));
        
        this.rootNode.attachChild(obsticalNode);
        this.rootNode.attachChild(enemyNode);
        this.rootNode.attachChild(playerNode);
     }
     public void setUpInput(){
         //adds a left input to the app
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_UP));//, new KeyTrigger(KeyInput.KEY_W));//new KeyTrigger( KeyInput.KEY_A)
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_DOWN));//, new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));//, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));//, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(this, "up");
        inputManager.addListener(this, "down");
        inputManager.addListener(this, "left");
        inputManager.addListener(this, "right");
        
     }

    private void setUpCam() {
        cam.setLocation(new Vector3f(21,17,-29));
        cam.setRotation(new Quaternion(0.16959405f, -0.012775801f, 0.0019558885f, 0.9854292f));
    }

    public void onAction(String name, boolean isPressed, float tpf) {
       if (name.equals("up")&&isPressed==false) {    
                System.out.println("Enter up pressed");
                playerNode.setLocalTranslation(playerNode.getLocalTranslation().add(Vector3f.UNIT_Z.mult(2)));
                Node v = (Node)gridNode.getChild(0);
       }
       if (name.equals("down")&&isPressed==false) {    
                System.out.println("Enter down pressed");
                playerNode.setLocalTranslation(playerNode.getLocalTranslation().subtract(Vector3f.UNIT_Z.mult(2)));
                Node v = (Node)gridNode.getChild(0);
       }
       if (name.equals("right")&&isPressed==false) {    
                System.out.println("Enter right pressed");
                playerNode.setLocalTranslation(playerNode.getLocalTranslation().subtract(Vector3f.UNIT_X.mult(2)));
                Node v = (Node)gridNode.getChild(0);
       }
       if (name.equals("left")&&isPressed==false) {    
                System.out.println("Enter left pressed");
                playerNode.setLocalTranslation(playerNode.getLocalTranslation().add(Vector3f.UNIT_X.mult(2)));
                Node v = (Node)gridNode.getChild(0);
                
       }
       Node v = (Node)gridNode.getChild(0);
       v.detachAllChildren();
    }

    public void onAnalog(String name, float value, float tpf) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
