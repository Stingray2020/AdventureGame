/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BURGER
 */
public class GridManager {
    public static GridManager instance =null;
    private static Application app;
    private static Node gridNode;
    private static Node obsticalNode;
    private static AssetManager assetManager;
    public Node sphereNode;
    //public 
    
    public int numOfRows=15;
    public int numOfColumns =15;
    public float gridCellSize =2;
    public boolean showGrid = true;
    public boolean showObstacleBlocks = true;
    public boolean showObsticalesBlocks =true;
    private Vector3f origin = new Vector3f();
    //private Spatial[] obstacleList;
    private List<Spatial> obstacleList = new ArrayList<Spatial>();
    public ANode[][] nodes;
    
    public  GridManager(Node obsticalNode,Node gridNode,AssetManager assetManager){
        this.obsticalNode = obsticalNode;
        this.gridNode =gridNode;
        sphereNode = new Node();
        gridNode.attachChild(sphereNode);
        this.assetManager = assetManager;
        //setOrigin( new Vector3f(-11f,0,-11f));
        if(showGrid)
            DebugDrawGrid(origin,numOfRows,numOfColumns,gridCellSize,ColorRGBA.Yellow);
        instance=this;
        Awake();
        
    }
    
    public Vector3f Origin() {
         return origin; 
    }
    
    public void setOrigin(Vector3f origin) {
          this.origin = origin; 
    }
    
    public void Awake(){
        obstacleList = obsticalNode.getChildren();
        CalculateObstacles();
    }

    private void CalculateObstacles() {
        nodes = new ANode[numOfColumns][numOfRows];
        int index =0;
        for(int i = 0; i< numOfColumns; i++){
            for (int j = 0; j < numOfRows; j++) {
                Vector3f cellPos = GetGridCellCenter(index);
                ANode node = new ANode(cellPos);
                nodes[j][i] = node;
               
                //System.out.println(nodes[i][j].position);
                index++;
            }
        }
        if(obstacleList !=null && obstacleList.size() >0){
            for(Spatial data : obstacleList) {
                int indexCell = GetGridIndex(data.getLocalTranslation());
                int col = GetColumn(indexCell);
                int row = GetRow(indexCell);
                //System.out.println(data.getLocalTranslation());
               //System.out.println(GetGridIndex(data.getLocalTranslation()));
                nodes[col][row].MarkAsObstacle();
                CreateSphere(data.getLocalTranslation(),ColorRGBA.Magenta);
                CreateSphere(nodes[col][row].position,ColorRGBA.White);
            }
        }
    }
    public Vector3f GetGridCellCenter(int index){
            Vector3f cellPosition = GetGridCellPosition(index);
            //gets grid cell posision and ads grid cell size divide by 2
            cellPosition.x += (gridCellSize/2.0f);
            cellPosition.z += (gridCellSize/2.0f);
            return cellPosition;
    }
    ///note errors could be here dependeding on the z/y axis in the engine
    public Vector3f GetGridCellPosition(int index){
        int row = GetRow(index);
        int col = GetColumn(index);
        
        float xPosInGrid = col *gridCellSize;
        float zPosInGrid = row *gridCellSize;
        return origin.add( new Vector3f(xPosInGrid,0.0f,zPosInGrid));
        
    }
    
    //get Grid index returns the grid cell index from any given position
    //it does so by deviding the posistion by the cellsize then 
    //muliplying it by number of columns and adding the col
    //thus if you devide it by the colums you get the rows
    public int GetGridIndex (Vector3f pos){
        if(!IsInBounds(pos)){
            return -1;
        }
        pos.subtractLocal(origin);
        int col = (int)(pos.x / gridCellSize);
        int row = (int)(pos.z / gridCellSize);
        return (row *numOfColumns + col);
    }
    public boolean IsInBounds(Vector3f pos){
        float width = numOfColumns *gridCellSize;
        float height = numOfRows *gridCellSize;
        
        return (pos.x > origin.x && pos.x <=origin.x+width &&
                pos.z <= origin.z +height && pos.z >=origin.z);
    }
    public int GetRow(int index){
        int row = index/numOfColumns;
        return row;
    }
    
    public int GetColumn(int index){
        int col = index % numOfColumns;
        return col;
    }
    ///gets neightbour nodes of a particular node
    //and sends them to assign neightbour method using the 
    ///array past which will go into the open or closed lists upon h and 
    ///manhattan block checks
    public void GetNeightbours(ANode node , ArrayList neighbors ){
        Vector3f neighborPos = node.position;
        int neighborIndex = GetGridIndex(neighborPos);
        
        int row = GetRow(neighborIndex);
        int column = GetColumn(neighborIndex);
        
        //Bottom 
        int tempNodeRow = row +1;
        int tempNodeColumn = column;
        AssignNeighbour(tempNodeRow,tempNodeColumn,neighbors);
        
        //top 
        tempNodeRow = row -1;
        tempNodeColumn = column;
        AssignNeighbour(tempNodeRow,tempNodeColumn,neighbors);
        
        //right 
        tempNodeRow = row ;
        tempNodeColumn = column +1;
        AssignNeighbour(tempNodeRow,tempNodeColumn,neighbors);
        
        //left
        tempNodeRow = row ;
        tempNodeColumn = column -1;
        AssignNeighbour(tempNodeRow,tempNodeColumn,neighbors);    
        
    }
    
    //this with the above methods adds each of the neighbors  
    public void AssignNeighbour(int row, int column,ArrayList neighbours){
        ///it is not more or less that the max and min of the Anodes list
        if(row != -1 && column != -1 && row < numOfRows && column <numOfColumns){
            ANode nodeToAdd = nodes[column][row];
            if(!nodeToAdd.bObstacle){
                neighbours.add(nodeToAdd);
                 //CreateSphere(nodeToAdd.position,ColorRGBA.Blue);
            }else{
                
            }
        }
    }
    
    public void DrawGizmos(){
        if(showObsticalesBlocks){
            if(obstacleList !=null && obstacleList.size()>0){
                Vector3f cellSize = new Vector3f(gridCellSize, 1.0f,gridCellSize);
                for(Spatial data : obstacleList) {
                    //CreateSphere(GetGridCellCenter(GetGridIndex(data.getLocalTranslation())));//,cellSize
                }
            }
        }
    }
    
    public void DebugDrawGrid(Vector3f origin,int numRows,int numCols,float cellSize, ColorRGBA color){
        float width = (numCols *cellSize);
        float height = (numRows *cellSize);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        for(int i =0; i < numRows +1; i++){
            
            Vector3f startPos =  origin.add( new Vector3f(0.0f,0.0f,1.0f).mult(i*cellSize));
            
            Vector3f endPos = startPos.add(new Vector3f(1.0f,0.0f,0.0f).mult(width));
            Line beam = new Line(startPos,endPos);
            
            Geometry beam_geo = new Geometry("Beam", beam);
            beam_geo.setMaterial(mat);
            gridNode.attachChild(beam_geo);
            
        }
        for(int i =0; i < numCols +1; i++){
            
            Vector3f startPos =  origin.add( new Vector3f(1.0f,0.0f,0.0f).mult(i*cellSize));
            
            Vector3f endPos = startPos.add(new Vector3f(0.0f,0.0f,1.0f).mult(height));
            Line beam = new Line(startPos,endPos);
            
            Geometry beam_geo = new Geometry("Beam", beam);
            beam_geo.setMaterial(mat);
            gridNode.attachChild(beam_geo);
            
        }
        
        
    }
    
    public void CreateSphere(Vector3f pos,ColorRGBA color){
        Spatial Sphere = new Geometry("sphere",new Box(1,1,1));
        //mymodel.setName("mymodel");
        Sphere.scale(0.2f,0.2f,0.2f);
        Sphere.setLocalTranslation(pos);
                
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Sphere.setMaterial(mat);
        mat.setColor("Color", color);
        mat.getAdditionalRenderState().setWireframe(true);
        sphereNode.attachChild(Sphere);
    }
}
