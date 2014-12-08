/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;


import helpers.ReadFileToString;
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
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import helpers.TrawlString;

/**
 *
 * @author BURGER
 */
public class GameAppState extends AbstractAppState implements ActionListener,AnalogListener,ScreenController {
    private SimpleApplication app;
    private AssetManager assetManager;
    private InputManager inputManager;
    private Node rootNode;
    private Camera cam;
    
    private Nifty nifty;
    private NiftyJmeDisplay niftyDisplay;
    
    @Override
    public void initialize(AppStateManager stateManager,Application app){
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.cam = this.app.getCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        System.out.println("GameAppState Attached");
        setUpNiftyGui();
        //setUpModel();
        setUpSun();
        setUpInput();
        
        
    }
    private void  setUpNiftyGui (){
        ///this checks to see if there is a niftyjmeDisplay 
        //in the app if so it sets nifty display as referance
        if(app.getGuiViewPort().getProcessors().get(0)!=null){
       
        niftyDisplay = (NiftyJmeDisplay) app.getGuiViewPort().getProcessors().get(0);
        nifty = niftyDisplay.getNifty();
        
        
        //app.getGuiViewPort().addProcessor(niftyDisplay);
        }
        
    }
    
    public void setUpSun(){
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
      }
    public void setUpModel(){
        Spatial mymodel = assetManager.loadModel(
        "Models/mymodel.mesh.xml");
        mymodel.setName("mymodel");
                mymodel.scale(0.01f,0.01f,0.01f);
        rootNode.attachChild(mymodel);
                
        TangentBinormalGenerator.generate(mymodel);
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        //mat.setColor("Color", ColorRGBA.Blue);
        //mat.getAdditionalRenderState().setWireframe(true);
        
        mat.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/Chestdiffuse.png",false)));
        mat.setTexture("NormalMap", assetManager.loadTexture(new TextureKey("Textures/ChestNormalLast.jpeg",false)));
        mat.setBoolean("UseMaterialColors",true); 
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setTexture("SpecularMap", assetManager.loadTexture(new TextureKey("Textures/Chestspec.png",false)));
        mat.setFloat("Shininess", 20f);
        mymodel.setMaterial(mat);

    }
    
    public void setUpInput(){
         //adds a left input to the app
        inputManager.addMapping("Enter", new KeyTrigger(KeyInput.KEY_RETURN));//new KeyTrigger( KeyInput.KEY_A)
        inputManager.addListener(this, "Enter");
     }
    
    public void onAction(String name, boolean isPressed, float tpf){
        
           if (name.equals("Enter")&&isPressed==false) {
                    
                System.out.println("Enter key pressed");
                if(nifty.isActive("Interface/gameStateScreens.xml", "dialog")){
                    nifty.removeScreen("dialog");
                   
                }else{
                    
                    nifty.fromXml("Interface/gameStateScreens.xml", "dialog",this);
                    readtxt();
                }
                
           }
     }
     
      public void onAnalog(String name, float value, float tpf) {
      //System.out.println("Mouse button hit");
      //System.out.println("Mouse Pic called");
            if(name.equals("mousePick")){
                
            }
      }
      
    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ")");
    }

    public void onStartScreen() {
        System.out.println("onStartScreen");
        
        //Element niftyElement = nifty.getCurrentScreen().findElementByName("dialogtxt");
        // swap old with new text
        
        
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
    }
    
    public void update(float tpf){
        
        //this smipped changes dialog text
        Element niftyElement = nifty.getCurrentScreen().findElementByName("dialogtxt");
        if(niftyElement != null &&trawlString.isRunning()){
            niftyElement.getRenderer(TextRenderer.class).setText(trawlText);
            //System.out.println("still updaing");
            trawlText = trawlString.UpdateString(tpf);
        }else{
            //System.out.println("cannot find dialogtxt");
            
        }
        
            
    }
    private String text;
    private String trawlText;
    private TrawlString trawlString = new TrawlString();
    private void readtxt() {
        ReadFileToString r =new ReadFileToString();
        r.openFile("assets/Interface/Text/chinese.txt");
        r.readFile();
        r.closeFile();
        text = r.getString();
        trawlString.SetString(text);
    }
}
