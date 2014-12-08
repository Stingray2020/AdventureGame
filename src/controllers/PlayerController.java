/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author stingray
 */
public class PlayerController extends AbstractControl {
    
    public int xAxis;
    public int yAxis;
    
    private Vector3f camDir;
    private Vector3f moveDir;
    private float speed;
    
    
    @Override
    protected void controlUpdate(float tpf) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        ///throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
