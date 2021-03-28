package atividade02;
import atividade02.classes.*;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.Random;

public class App {
    public static void main(String args[]){
        Integer[] testQuantities = {100, 10000, 1000000, 10000000}; // Quantidades de itens a serem inseridos
        Integer numExperiments = 25; // Número de rodadas para tirar a média
        Random rand = new Random(42);
        FileWriter fw = null;
        try{
            fw = new FileWriter("TreeInsert.csv");
            fw.write("Tamanho do vetor,");
            fw.write("Tempo de execução das inserções(s)(média de 25 execuções)");
            for(int quant : testQuantities){
                System.out.println("Inserindo " + quant + "elementos");
                double overallTime = 0;
                for(int j=0; j<numExperiments; j++){
                    long startTime = System.currentTimeMillis();
                    MyRBTree<Integer, Integer> rbTree = new MyRBTree<Integer, Integer>();
                    for(int k=0; k<quant; k++){
                        rbTree.insert(rand.nextInt(quant/10), rand.nextInt(quant/10));
                    }
                    overallTime += (double)(System.currentTimeMillis() - startTime)/1000;
                }
                fw.write(Integer.toString(quant));
                fw.write(",");
                fw.write(Double.toString(overallTime/(double)numExperiments));
                fw.write("\n");
            }
            
            fw.flush();
            fw.close();
        }catch(Exception e){
            try{
                fw.flush();
                fw.close();
            }catch(Exception f){
                System.err.println(f);
            }
        }
        
        // Iterator<MyRBTree<Integer, Integer>.Node> it = rbTree.iterator();
        // while(it.hasNext()){
        //     MyRBTree<Integer, Integer>.Node node = it.next();
        //     System.out.println(node.value);
        // }
        
        //AVLTree<Integer, Integer> avlTree = RBTreeToAVL.toAvl(rbTree);

        System.out.println("Kappa");
    }
}
