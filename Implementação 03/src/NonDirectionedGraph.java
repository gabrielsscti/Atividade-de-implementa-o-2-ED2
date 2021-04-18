import java.util.ArrayList;

public class NonDirectionedGraph extends Graph{
    
    /**
	 * Cria um grafo não orientado
	 * @param n
	 * @return
	 */
    public NonDirectionedGraph(int n) {
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
        super.connect(dest, source, weight);

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
        super.connect(dest, source);
    }
    
}
