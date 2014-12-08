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
import com.jme3.asset.TextureKey;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.TangentBinormalGenerator;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author BURGER
 */
public class MenuAppState extends AbstractAppState implements ActionListener,AnalogListener, ScreenController{
    private SimpleApplication app;
    private AssetManager assetManager;
    private InputManager inputManager;
    private Node rootNode;
    private Camera cam;
    private NiftyJmeDisplay niftyDisplay;
    
    private Boolean swtichState=false;
    
    
    private Nifty nifty;
    private boolean guiLoaded =false;
    
    ///todo make nifty to run off a start screen controll that does actions
    //private StartScreenController startScreenController;
    
    @Override
    public void initialize(AppStateManager stateManager,Application app){
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.cam = this.app.getCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        //this.niftyDisplay = 
        
        setUpNiftyGui();
        
        setUpInput();
        
    }
    private void  setUpNiftyGui (){
        if(app.getGuiViewPort().getProcessors().get(0)!=null){
        //app.getGuiViewPort().removeProcessor(app);
        niftyDisplay = (NiftyJmeDisplay) app.getGuiViewPort().getProcessors().get(0);
        
        
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/screen.xml", "start",this);
        }
        
    }
    public void update(float tpf) {
        
    }
    
    public void cleanup(){
        super.cleanup();
        //rootNode.detachChildNamed("mymodel");
        nifty.removeScreen("start");
        
    }
    
    public void setEnabled(boolean enabled) {}
    
    public void setUpInput(){
         //adds a left input to the app
        //inputManager.addMapping("Enter", new KeyTrigger(KeyInput.KEY_RETURN));//new KeyTrigger( KeyInput.KEY_A)
        //inputManager.addListener(this, "Enter");
     }
    
    public void onAction(String name, boolean isPressed, float tpf){
        
           if (name.equals("Enter")&&isPressed==false) {
                //System.out.println("enter key pressed");
                //nifty.exit();//.removeScreen("start");
           }
     }
     
      public void onAnalog(String name, float value, float tpf) {
      //System.out.println("Mouse button hit");
      //System.out.println("Mouse Pic called");
            if(name.equals("mousePick")){
                
            }
      }
      
      public void setUpSun(){
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
      }
      
      public void startGame(){
          //nifty.removeScreen("start");
          
          app.getStateManager().detach(this);
          app.getStateManager().attach( new APathFindingState());
      }
      public void quitGame(){
          System.out.println("called quitGame");
          
          app.stop();
          
      }
      
    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ")");
    }

    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

}
