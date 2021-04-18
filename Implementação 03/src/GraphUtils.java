import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GraphUtils {
    private static Boolean visited[];
    private static Double distance[];
    private static Integer vertexDistance[];
    private static Integer predecessor[];
    private static ArrayList<Integer[]> pontes;

    /**
	 * Função que retorna o resultado da questão 2, letra A
	 * @param graph
     * @param source
     * @param dest
	 * @return
	 */
    public static int BFS_DistanceToDest(Graph g, Integer source, Integer dest){
        validate(g, source, dest);
        BFS(g, source);
        return vertexDistance[dest];
    }

    /**
	 * Função que retorna o resultado da questão 2, letra B
	 * @param graph
     * @param source
	 * @return
	 */
    public static Integer[] BFS_PathFromSourceToDest(Graph g, Integer source, Integer dest){
        validate(g, source, dest);
        BFS(g, source);
        ArrayList<Integer> ans = new ArrayList<Integer>();
        int auxPredecessor = dest;
        ans.add(auxPredecessor);
        while(auxPredecessor != predecessor[auxPredecessor]){
            auxPredecessor = predecessor[auxPredecessor];
            ans.add(auxPredecessor);
        }
        Collections.reverse(ans);
        Integer[] r = new Integer[ans.size()];

        return ans.toArray(r);
    }

    /**
	 * Função que retorna o resultado da questão 2, letra 3
	 * @param graph
     * @param source
     * @param distanceToCheck
	 * @return
	 */
    public static Integer[] BFS_VertexOnDistanceD(Graph g, Integer source, double distanceToCheck){
        validate(g, source);
        BFS(g, source);
        ArrayList<Integer> ans = new ArrayList<Integer>();
        for(int i=1; i<=g.getNumComponents(); i++)
            if(distance[i] == distanceToCheck)
                ans.add(i);

        Integer[] r = new Integer[ans.size()];
        return ans.toArray(r);
    }

    /**
	 * Função que retorna a circunferência de um grafo. Questão 3
	 * @param graph
	 * @return Menor circunferência do grafo g
	 */
    public static Double circunferencia(Graph g){
        validate(g);
        Double smallerCircumference = Double.POSITIVE_INFINITY;
        for(int i=1; i<=g.getNumComponents(); i++)
                smallerCircumference = Math.min(smallerCircumference, DFS_GetSmallerCycle(g, i));
        return smallerCircumference;
    }

    private static Boolean isConnected(Graph g, Integer source, Integer dest){
        for(Graph.Adjacency adjs : g.getAdjacency(source))
            if(adjs.dest == dest) return true;
        return false;
    }

    private static Double getAdjWeight(Graph g, Integer source, Integer dest){
        for(Graph.Adjacency adjs : g.getAdjacency(source))
            if(adjs.dest == dest) return adjs.weight;
        return 0.0;
    }
    

    /**
	 * Função que realiza uma operação BFS
	 * @param graph
     * @param source
	 * @return
	 */
    private static void BFS(Graph g, Integer source){
        initializeFields(g);

        visited[source] = true;
        distance[source] = 0.0;
        vertexDistance[source] = 0;
        predecessor[source] = source;

        Queue<Integer> q = new LinkedList<Integer>();
        q.add(source);
        
        while(!q.isEmpty()){
            Integer u = q.remove();
            for(Graph.Adjacency v : g.getAdjacency(u)){
                if(!visited[v.dest]){
                    visited[v.dest] = true;
                    vertexDistance[v.dest] = vertexDistance[u] + 1;
                    distance[v.dest] = distance[u] + v.weight;
                    predecessor[v.dest] = u;
                    q.add(v.dest);
                }
            }
        }
    }

    /**
	 * Função que retorna o tamanho do menor ciclo em um grafo a partir de um ponto de partida
	 * @param graph
     * @param source
	 * @return
	 */
    private static double DFS_GetSmallerCycle(Graph g, Integer source){
        initializeFields(g);

        visited[source] = true;
        distance[source] = 0.0;
        vertexDistance[source] = 0;
        predecessor[source] = source;

        Queue<Integer> q = new LinkedList<Integer>();
        q.add(source);
        double smallerCycle = Double.POSITIVE_INFINITY;
        
        while(!q.isEmpty()){
            Integer u = q.remove();
            for(Graph.Adjacency v : g.getAdjacency(u)){
                if(visited[v.dest] && predecessor[u] != v.dest)
                    smallerCycle = Math.min(smallerCycle, distance[v.dest] + distance[u] + v.weight);
                

                if(!visited[v.dest]){
                    visited[v.dest] = true;
                    vertexDistance[v.dest] = vertexDistance[u] + 1;
                    distance[v.dest] = distance[u] + v.weight;
                    predecessor[v.dest] = u;
                    q.add(v.dest);
                }
            }
        }
        return smallerCycle;
    }

    /**
    * Método que resolve a questão 04. Printa na tela todas as pontes do grafo.
    * @param g Grafo a ser examinado 
    **/
    public static void DFS_Pontes(Graph g){
        for(int i=1; i<=g.getNumComponents(); i++){
            DFS(g, i);
            Boolean[] originalVisited = visited.clone();

            while(g.deactivateNext()){
                DFS(g, i);
                if(!compareBool(originalVisited, visited)){
                    Graph.Connection connection = g.getActConnection();
                    System.out.println("Ponte: " + connection);
                }
            }
        }
    }

    private static Boolean compareBool(Boolean[] a, Boolean[] b){
        for(int i=0; i<a.length; i++)
            if(a[i] != b[i]) return false;
        
        return true;
    }

    /**
	 * Função que realiza uma operação DFS
	 * @param graph
     * @param source
	 * @return
	 */
    private static void DFS(Graph g, Integer source){
        initializeFields(g);

        visited[source] = true;
        distance[source] = 0.0;
        vertexDistance[source] = 0;
        predecessor[source] = source;

        Stack<Integer> s = new Stack<Integer>();
        s.add(source);

        while(!s.isEmpty()){
            Integer u = s.pop();

            for(Graph.Adjacency v : g.getAdjacency(u)){
                if(!v.activated) continue;
                if(!visited[v.dest]){
                    visited[v.dest] = true;
                    vertexDistance[v.dest] = vertexDistance[u] + 1;
                    distance[v.dest] = distance[u] + v.weight;
                    predecessor[v.dest] = u;
                    s.add(v.dest);
                }
            }
        }

    }

    /**
	 * Função que inicializa os campos necessários para operar com DFS, BFS e Djikstra
	 * @param graph
     * @param source
	 * @return
	 */
    private static void initializeFields(Graph g){
        visited = new Boolean[g.getNumComponents()+1];
        distance = new Double[g.getNumComponents()+1];
        vertexDistance = new Integer[g.getNumComponents()+1];
        predecessor = new Integer[g.getNumComponents()+1];

        Arrays.fill(distance, Double.POSITIVE_INFINITY);
        Arrays.fill(vertexDistance, Integer.MAX_VALUE);
        Arrays.fill(visited, Boolean.FALSE);
        Arrays.fill(predecessor, -1);
    }

    private static void validate(Graph g){
        if(g==null)
            throw new NullPointerException("O grafo passado é nulo.");
    }

    private static void validate(Graph g, Integer source, Integer dest){
        validate(g);
        if(source<0 || dest<0 || source>g.getNumComponents() || dest>g.getNumComponents())
            throw new IllegalArgumentException("Parâmetros inválidos.");
    }

    private static void validate(Graph g, Integer source){
        validate(g);
        if(source<0 || source>g.getNumComponents())
            throw new IllegalArgumentException("Parâmetros inválidos.");
    }
}
