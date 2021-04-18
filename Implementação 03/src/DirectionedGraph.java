import java.util.ArrayList;
import java.util.Hashtable;

public class DirectionedGraph extends Graph{
    
    /**
	 * Cria um grafo orientado
	 * @param n
	 * @return
	 */
    public DirectionedGraph(int n){
        super(n);

    }
    

    /**
	 * Adiciona uma adjacência com peso ao grafo
	 * @param source
     * @param dest
     * @param double
	 * @return
	 */
    @Override
    public void connect(Integer source, Integer dest, double weight){
        super.connect(source, dest, weight);

    }

    /**
	 * Adiciona uma adjacência sem peso ao grafo
	 * @param source
     * @param dest
	 * @return
	 */
    @Override
    public void connect(Integer source, Integer dest){
        super.connect(source, dest);
    }
}
