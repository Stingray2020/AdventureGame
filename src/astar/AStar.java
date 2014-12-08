/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author BURGER
 * Class ASTAR impliments Anode , GridManager and PriorityQueue
 * Uses open lists and closed lists 
 * 
 */
public class AStar {
    public static PriorityQueue closedList,openList;
    public static GridManager gridManager;
    private static Node obstacleNode;
    private static Node gridNode;
    private static AssetManager assetManager;
    //public GridManager gridManager = new GridManager(obstacleList);
    //HeuristicEstimateCost calculates the cost between two nodes 
    //it finds the direction vector between the two by subtracting one pos from
    //the other
    //the magnitude of the vector gives us the direct distance from the current
    //node to the goal;
    private static float HeuristicEstimateCost(ANode curNode, ANode goalNode){
        Vector3f vecCost = curNode.position.subtract(goalNode.position);
        
        return vecCost.length();
        
    }
    public AStar(Node obstacleNode,Node gridNode, AssetManager assetManager){
        this.obstacleNode = obstacleNode;
        this.gridNode = gridNode;
        this.assetManager=assetManager;
        gridManager = new GridManager(obstacleNode,gridNode,assetManager);
        System.out.println(gridNode.getChildren().size());
    }
    
    //Main findPath method
    //which initialises open and closed lists starting with the start node
    //which is places in the open list and starts processing the open list
    public static ArrayList FindPath(ANode start , ANode goal) {
        openList = new PriorityQueue();
        openList.Push(start);
        start.nodeTotalCost =0.0f;
        start.estimatedCost = HeuristicEstimateCost(start,goal);
        
        closedList = new PriorityQueue();
        ANode node = null;
        
        while(openList.Length() !=0){
        //for(int p=0; p <60;p++){
            //get the first node of our open list/// open list sorts every time
            //a new node is added so
            ///the first node is always the node with the least estimated cost 
            //to the goal node;
            node = openList.First();
            //check if the curent node is goal node if so exit loop and build path
            if(node.position.x== goal.position.x && node.position.z== goal.position.z){
                System.out.println("called");
                
                System.out.println("nodepos : " +node.position);
                System.out.println("goalpos : " +goal.position);
                System.out.println("parentpos :" + node.parent.position);
               /* System.out.println(node.parent.parent.position);
                System.out.println(node.parent.parent.parent.position);
                System.out.println(node.parent.parent.parent.parent.position);
                System.out.println(node.parent.parent.parent.parent.parent.position);*/
               
                return CalculatePath(node);
            }
            
            //create an ArrayList to store the Neighboring nodes
            //for every node is the neighbours array we check if its already
            ///in the closed list // if not put in the calculated cost vaules,
            ///update the nodes properties with the new cost values as well as the 
            //parent node data (last node we came from) and put it in the openlist
            System.out.println("nodepos : " +node.position);
            ArrayList neighbours = new ArrayList();
            
            gridManager.GetNeightbours(node, neighbours);
            
            for(int i=0; i < neighbours.size(); i++){
                ANode neighbourNode = (ANode)neighbours.get(i);
                
                if(!closedList.Contains(neighbourNode)){
                    float cost = HeuristicEstimateCost(node,neighbourNode);
                        
                    //this is F the sum of the 
                    float totalCost = node.nodeTotalCost + cost;
                    float neighbourNodeEstCost = HeuristicEstimateCost(neighbourNode,goal);
                        
                    neighbourNode.nodeTotalCost = totalCost;
                    neighbourNode.parent = node;
                    neighbourNode.estimatedCost = totalCost + neighbourNodeEstCost;
                    if(!openList.Contains(neighbourNode)){
                        openList.Push(neighbourNode);
                    }
                }
            }
            
            //push the current node to the closed list and remove
            //it form the openlist then repeat steps
            ///push the current node to the closed list
            closedList.Push(node);
            //gridManager.CreateSphere(node.position,ColorRGBA.Red);
            
            /// and remove it form the openList
            openList.Remove(node);
            
        }    
        
                
        if (node.position != goal.position) {
           System.out.println("goal not found");
           
           System.out.println("nodepos : " +node.position);
           //gridManager.CreateSphere(node.position,ColorRGBA.Pink);
           System.out.println("goalpos : " +goal.position);
           return null;
        }
        return CalculatePath(node);
    }
    //if there ar eno more nodes we just caulcate a path
    
    // this method traces through each nodes parent node object and builds an arraylist
    //it gives an array list with nodes from target node to start node
    //since we want a path array from start to target we reverse the list
    private static ArrayList CalculatePath(ANode node){
        ANode pos= node;
        System.out.println("node" + node.parent);
        ArrayList<ANode> list =new ArrayList<ANode>();
        while(node!=null){
            list.add(node);
            node = node.parent;
            //System.out.print("node pos" + node.position.x);
            //gridManager.CreateSphere(node.position,ColorRGBA.Red);
        }
        System.out.print("is returning list :" +list.size());
        Collections.reverse(list);
        
        for(ANode data : list) {
                
                gridManager.CreateSphere(data.position,ColorRGBA.Magenta);
        }
        
        return list;
    }
}
