package sk_program;

import java.util.ArrayList; 
import java.util.List;
import java.util.Vector; 

public class Graph { 
    private int v;  
    private ArrayList<Integer>[] adjList;  
    public Graph(int vertices){ 
        this.v = vertices; 
        initAdjList();  
    } 
   
    private double value=0;
    private double ans=0;
    private double polygonArea(Vector<Double> X,Vector<Double> Y,int n)
    {
    	 double area = 0.0;
         int j = n - 1; 
         for (int i = 0; i < n; i++) 
         { 
             area += (X.get(j) + X.get(i)) * (Y.get(j) - Y.get(i)); 
             j = i;  
         } 
         return Math.abs(area / 2.0); 
    }
    
    @SuppressWarnings("unchecked") 
    private void initAdjList() 
    { 
        adjList = new ArrayList[v]; 
          
        for(int i = 0; i < v; i++) 
        { 
            adjList[i] = new ArrayList<>(); 
        } 
    } 
    public void addEdge(int u, int v) 
    { 
        adjList[u].add(v);
        adjList[v].add(u); 
    } 
    public void printAllPaths(int s, int d)  
    { 
        boolean[] isVisited = new boolean[v]; 
        ArrayList<Integer> pathList = new ArrayList<>();     
        pathList.add(s); 
        printAllPathsUtil(s, d, isVisited, pathList); 
    } 
    private void printAllPathsUtil(Integer u, Integer d, 
                                    boolean[] isVisited, 
                            List<Integer> localPathList) {
    	Vector<Double> X= new Vector<Double>();
		Vector<Double> Y= new Vector<Double>();
        isVisited[u] = true; 
        if (u.equals(d))  
        { 
//            System.out.println(localPathList);
        	for(Integer it:localPathList)
        	{
//        		System.out.print(it+" ");
        		X.add(Double.valueOf(it%8));
        		Y.add(Double.valueOf(7-it/8));
        	}
//        	System.out.print(polygonArea(X,Y,X.size()));
        	value=Math.max(value,polygonArea(X,Y,X.size()));
//        	System.out.print("\n");
            isVisited[u]= false; 
            return ; 
        } 
        for (Integer i : adjList[u])  
        { 
            if (!isVisited[i]) 
            { 
                localPathList.add(i); 
                printAllPathsUtil(i, d, isVisited, localPathList); 
                localPathList.remove(i); 
            } 
        }  
        isVisited[u] = false; 
    } 
    
    public Double answer(int s,int d)
    {
    	printAllPaths(s, d);
        ans=value;
        value=0;
    	return ans;
    }
    public static void main(String[] args)  
    { 
//        Graph g = new Graph(64); 
//        
//        g.addEdge(0,8);
//        g.addEdge(8,16);
//        g.addEdge(16,17);
//        g.addEdge(17,18);
//        g.addEdge(18,10);
//        g.addEdge(10,9);
//        g.addEdge(9,1);
////        g.addEdge(15,11);
////        g.addEdge(11,10);
////        g.addEdge(10,9);
////        g.addEdge(9,5);
////        g.addEdge(5,1);
//        int s = 0; 
//        int d = 1;
//        System.out.println("Following are all different paths from "+s+" to "+d); 
//        g.printAllPaths(s, d); 
//        System.out.println((int)value);
    } 
} 