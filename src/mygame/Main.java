package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import mygame.appstates.APathFindingState;
import mygame.appstates.GameAppState;
import mygame.appstates.MenuAppState;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication  {
    ///nifty screen class
   // private Nifty nifty;
    private NiftyJmeDisplay niftyDisplay;
    public static void main(String[] args) {
        Main app = new Main();
        app.setDisplayStatView(false);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(40f);
        flyCam.setDragToRotate(true);
        //cam.setFrustumPerspective(90, settings.getWidth() / settings.getHeight() +0.0f, 1, 1000);
        MenuAppState state = new MenuAppState();
        stateManager.attach(state);
        setUpNiftyDisplay();
        //rootNode.attachChild(geom);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    

    private void setUpNiftyDisplay() {
        niftyDisplay = new NiftyJmeDisplay(  assetManager, getInputManager(),
                getAudioRenderer(), getGuiViewPort());
                  

        getGuiViewPort().getProcessors().add(0, niftyDisplay);
    }

     
}
