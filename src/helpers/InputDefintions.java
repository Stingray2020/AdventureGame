/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.jme3.app.SimpleApplication;
import com.jme3.input.*;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.JoyAxisTrigger;
import com.jme3.input.controls.JoyButtonTrigger;
import com.jme3.input.controls.KeyTrigger;

/**
 *
 * @author stingray
 */
public class InputDefintions {
    
    public void DefineInputs(InputManager inputManager ,ActionListener actionListener, AnalogListener analogListener){
        inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P), new JoyButtonTrigger(0,0));///im guessing with buttons you splly and interger// need to test map them
        inputManager.addMapping("up",   new KeyTrigger(KeyInput.KEY_UP), new JoyAxisTrigger(0, JoyInput.AXIS_POV_Y, true));
        inputManager.addMapping("down",  new KeyTrigger(KeyInput.KEY_DOWN), new JoyAxisTrigger(0, JoyInput.AXIS_POV_Y, false));
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT), new JoyAxisTrigger(0, JoyInput.AXIS_POV_X, false));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT), new JoyAxisTrigger(0, JoyInput.AXIS_POV_X, true));
        inputManager.addMapping("enter", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("inventory", new KeyTrigger(KeyInput.KEY_I));
        
        
        inputManager.addListener(actionListener, "pause");
        inputManager.addListener(analogListener, "up","down","left","right");
        //inputManager.addListener(analogListener, "joyXAxisRight","joyXAxisLeft","joyYAxisUp","joyYAxisDown");
        inputManager.addListener(actionListener, "enter");
        inputManager.addListener(actionListener, "inventory");
                                     // new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    }
    
}
