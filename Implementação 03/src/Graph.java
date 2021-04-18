import java.util.ArrayList;
import java.util.Iterator;

public abstract class Graph {
    private ArrayList<Adjacency>[] components;
    private Integer numComponents;
    private Integer actDeactivationPos = 1;
    private Iterator<Adjacency> actDeactivationIt = null;
    private Adjacency lastAdjacency = null;

    public class Adjacency{
        Integer dest;
        double weight;
        boolean activated;

        public Adjacency(Integer dest, double weight){
            this.dest = dest;
            this.weight = weight;
            this.activated = true;
        }
        public void setActivation(Boolean newValue){
            this.activated = newValue;
        }
    }

    public class Connection{
        Integer from;
        Integer to;

        public Connection(Integer from, Integer to){
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return from + " to " + to;
        }
    }

    public Connection getActConnection(){
        return new Connection(actDeactivationPos, lastAdjacency.dest);
    }

    public Boolean deactivateNext(){
        if(actDeactivationPos>this.getNumComponents())
            return false;
        if(actDeactivationIt == null)
            actDeactivationIt = components[actDeactivationPos].iterator();
        
        if(actDeactivationIt.hasNext()){
            if(lastAdjacency!=null)
                lastAdjacency.setActivation(true);
            Adjacency adj = actDeactivationIt.next();
            lastAdjacency = adj;
            adj.setActivation(false);
            return true;
        }else{
            actDeactivationPos++;
            if(actDeactivationPos<=numComponents)
                actDeactivationIt = components[actDeactivationPos].iterator();
            
            deactivateNext();
        }
        return true;

    }

    /**
	 * Cria um grafo
	 * @param n
	 * @return
	 */
    public Graph(Integer n){
        components = (ArrayList<Adjacency>[]) new ArrayList[n+1];
        this.numComponents = n;
        for(int i=1; i<=n; i++)
            components[i] = new ArrayList<Adjacency>();
    }

    /**
	 * Adiciona uma adjacência com peso ao grafo
	 * @param source
     * @param dest
     * @param double
	 * @return
	 */
    public void connect(Integer source, Integer dest, double weight){
        components[source].add(new Adjacency(dest, weight));

    }

    /**
	 * Adiciona uma adjacência sem peso ao grafo
	 * @param source
     * @param dest
	 * @return
	 */
    public void connect(Integer source, Integer dest){
        components[source].add(new Adjacency(dest, 1));

    }

    public Integer getNumComponents(){
        return this.numComponents;
    }

    public ArrayList<Adjacency> getAdjacency(Integer i){
        return components[i];
    }

    @Override
    public String toString() {
        String ans = "";
        for(int i=1; i<components.length; i++)
            for(Adjacency adj : components[i])
                ans += i + " -> " + adj.dest + " - Weight: " + adj.weight + "\n";

        return ans;
    }
    
}
