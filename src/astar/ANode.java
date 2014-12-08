/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;

import com.jme3.math.Vector3f;

/**
 *
 * @author BURGER
 */
public class ANode implements Comparable {
    public float nodeTotalCost;
    public float estimatedCost;
    public boolean bObstacle;
    public ANode parent;
    public Vector3f position;
    
    public ANode() {
        this.estimatedCost =0.0f;
        this.nodeTotalCost =1.0f;
        this.bObstacle = false;
        this.parent =null;
    }
    
    public ANode(Vector3f pos){
        this.estimatedCost =0.0f;
        this.nodeTotalCost =1.0f;
        this.bObstacle = false;
        this.parent =null;
        this.position = pos;
    }
    
    public void MarkAsObstacle(){
        this.bObstacle = true;
    }
    
    
    //this is an important class because node interhits icomparable
    /// this is then used to overide Compare to method
    /// this sorts the arrases basied on the total estimated cost
    //the arraylist type has a method called Sort.sort
    //we are basically overriding that to do some work
    ///TODO look into java alternative
    public int compareTo(Object obj){
        ANode node = (ANode)obj;
        
        if (this.estimatedCost < node.estimatedCost){
            /// Negative values means object comes beofre this sort
            return -1;
        }
        if(this.estimatedCost> node.estimatedCost){
               //positive values means this object comes after this sort
            //order
            return 1;
        }
        return 0;
    }

 
}
