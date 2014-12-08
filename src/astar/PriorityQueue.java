/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author BURGER
 */
public class PriorityQueue {
    private List  nodes = new ArrayList();
    
    ///might need to minus one with this
    public int Length(){
        return this.nodes.size();
    }
    
    public boolean Contains(ANode node){
        return this.nodes.contains(node);
    }
    public ANode First(){
        if(this.nodes.size() >0){
            return (ANode) nodes.get(0);
        }
        return null;
    }
    public void Push(ANode node){
        this.nodes.add(node);
        Collections.sort(nodes);
    }
    public void Remove(ANode node){
        this.nodes.remove(node);
        ///this will sort the nodes object list via the estimated Cost value 
        Collections.sort(nodes);
    }
}
