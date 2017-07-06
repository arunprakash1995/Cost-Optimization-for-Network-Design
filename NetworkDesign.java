package Atn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkDesign {

	/* Function to Initialize TrafficDemandMatrix*/
	public static int[][] trafficDemand(int N){
      int d[] = new int[N];
      int difference;
      int b[][] = new int[N][N]; // Declaring the traffic Demand matrix
      d[0] = 2; // Initializing D with the UTD Id
      d[1] = 0;
      d[2] = 2;
      d[3] = 1;
      d[4] = 3;
      d[5] = 1;
      d[6] = 3;
      d[7] = 2;
      d[8] = 8;
      d[9] = 2;
      for(int j=10,i=0;j<N;++j,++i){
    	  d[j] = d[i];
      }
      /*Initializing the trafficDemandMatrix*/
      for(int i=0;i<N;++i) 
    	  for(int j=0;j<N;++j){
    		  difference = d[i]-d[j] ;
    		  b[i][j] = Math.abs(difference);
    	  }
      return b;
		
	}
	
	/* To initialize the Cose Matrix */
	public static int[][] adjacencyMatrix(int N , int k){
		int a[][] = new int[N][N]; //Declaring Cost Matrix
		List<Integer> indices = new ArrayList<Integer>();
		
		for(int i=0 ;i<N ;++i)
			indices.add(i);
		int index[] = new int[k] ;
		
		/*Intializing Cost Matrix */
		for(int i=0;i<N ;++i){
			
			Collections.shuffle(indices); // Randomizing the Indices
			
			/* Generating k Random Indices */
			for(int m=0;m<k;++m){
				if(indices.get(m) == i)
					index[m] = indices.get(N-k);
				else
					index[m] = indices.get(m);
			}
			
			for(int j=0; j<N;++j){
			    if (i==j)
			    	a[i][j] = 0; //Initializing cost to 0 if i==j
			    else
			      for(int m=0 ;m<k ;++m){
			    	if(index[m] == j){
			    		a[i][j] = 1;
			    	    break ;
			    	}
			    	else
			    		a[i][j] = 200;
			      }
			    
			}
		}	
		return a;
	}
	
	static final int V=20;
    
	public static int minDistance(int dist[], Boolean sptSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index=-1;
 
        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min)
            {
                min = dist[v];
                min_index = v;
            }
 
        return min_index;
    }
 
 
    // Funtion that implements Dijkstra's single source shortest path
    // algorithm for a graph represented using adjacency matrix
    // representation
    public static int[] dijkstra(int graph[][], int src)
    {
        int dist[] = new int[V]; // The output array. dist[i] will hold
                                 // the shortest distance from src to i
 
        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[V];
 
        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
 
        // Distance of source vertex from itself is always 0
        dist[src] = 0;
 
        // Find shortest path for all vertices
        for (int count = 0; count < V-1; count++)
        {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet);
 
            // Mark the picked vertex as processed
            sptSet[u] = true;
 
            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < V; v++)
 
                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if (!sptSet[v] && graph[u][v]!=0 &&
                        dist[u] != Integer.MAX_VALUE &&
                        dist[u]+graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }
        
        return dist;
    }
 
	public static int costCalculation(int b[][], int g[][] ,int N){
		int cost= 0;
		for(int i=0; i<N; ++i)
			for(int j=0; j<N ;++j){
				cost = cost+( b[i][j]*g[i][j]) ;
			}
		return cost;
	}
	
	public static float densityCalculation(int[][] cost, int N){
		float density;
		float possibleConnection = N*(N-1) ;
		int actualConnection = 0;
		for(int i=0; i<N; ++i){
			for(int j=0; j<N ;++j){
				if(cost[i][j] != 0)
					actualConnection++;
			}
		}
		density = (float)(actualConnection/possibleConnection) ;
		return density;
	}
	public static void main(String[] args) {
     
		
		int N = 20; //Number of Nodes
		for(int k=3; k<=13; ++k){
		int cost = 0;
		int B[][] = trafficDemand(N);
		int A[][] = adjacencyMatrix(N,k);
		int distance[] = new int[N];
		int graph[][] = new int[N][N];
	    for(int source =0; source<N ;++source){
			distance = dijkstra(A, source);
			graph[source] = distance;
		}
		cost = costCalculation(B,graph,N);
		float density = densityCalculation(A ,N);
		
		System.out.println(" K = " + k + "\n Cost = " +cost + "    Density = " + density + "\n");
	}
  }

}
