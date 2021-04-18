import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Integer v = in.nextInt();

        NonDirectionedGraph graph = new NonDirectionedGraph(v);

        for(int i=0; i<v; i++)
            graph.connect(in.nextInt(), in.nextInt());
        
        System.out.println(GraphUtils.circunferencia(graph));
        
        System.out.println();

        GraphUtils.DFS_Pontes(graph);

        in.close();
    }
}
