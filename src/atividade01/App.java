package atividade01;

import atividade01.classes.*;

import java.util.Random;
import java.io.FileWriter;

public class App {
    public static void main(String args[]){
        Integer[] testQuantities = {100, 10000, 1000000, 10000000}; // Quantidades de itens a serem inseridos
        Integer numExperiments = 25; // Número de rodadas para tirar a média
        String[] file_names = {"LinearProbeHash_Results.csv", "DoubleHash_Results.csv", "LinkedListHash_Results.csv", "FlagRemotionHash_Results.csv"};
        HashTable[] hashTables = {new HashTentativaLinear<Integer, Integer>(),
                                    new HashDuplo<Integer, Integer>(),
                                    new HashListaEncadeada<Integer, Integer>(),
                                    new HashRemocaoFlag<Integer, Integer>()};

        Random rnd = new Random(42);


        
        for(int i=0; i<hashTables.length; i++){
            FileWriter fw = null;
            try{
                fw = new FileWriter(file_names[i]);
                fw.write("Tamanho do vetor,");
                fw.write("Tempo de execução das inserções e tentativa de remoção a cada 10 inserções(s)(média de 25 execuções)\n");

                for(int quant : testQuantities){
                    System.out.println("Inserindo " + quant + " elementos em " + file_names[i]);
                    double overallTime = 0;
                    for(int j=0; j<numExperiments; j++){
                        long startTime = System.currentTimeMillis();
                        for(int k=0; k<quant; k++){
                            hashTables[i].put(rnd.nextInt(quant/10), rnd.nextInt(quant/10));
                            if(k%10==0)
                                hashTables[i].delete(rnd.nextInt(quant/10));
                        }
                        overallTime += (double)(System.currentTimeMillis() - startTime)/1000;
                        hashTables[i].clear();
                    }
                    fw.write(Integer.toString(quant));
                    fw.write(",");
                    fw.write(Double.toString(overallTime/(double)numExperiments));
                    fw.write("\n");
                }
                fw.flush();
                fw.close();
            }catch(Exception e){
                e.printStackTrace();;
                try{
                    fw.flush();
                    fw.close();
                }catch(Exception f){
                    System.err.println(f);
                }
            }
        }

        // HashTentativaLinear<Integer, Integer> linearProbingHash = new HashTentativaLinear<Integer, Integer>();
        // for(Integer a : list)
        //     linearProbingHash.put(a, a);
        
        // queryHash(linearProbingHash, 31);
        // linearProbingHash.delete(31);
        // queryHash(linearProbingHash, 31);

        // HashDuplo<Integer, Integer> doubleHashing = new HashDuplo<Integer, Integer>();
        // for(Integer a : list)
        //     doubleHashing.put(a, a);

        // queryHash(doubleHashing, 131);
        // doubleHashing.delete(432);
        // queryHash(doubleHashing, -3);
        // if(doubleHashing.contains(50))
        //     System.out.println("Contains " + 50);
        // if(doubleHashing.contains(-4))
        //     System.out.println("Contains " + -4);

        // HashListaEncadeada<Integer, Integer> linkedListHashing = new HashListaEncadeada<Integer, Integer>();
        // for(Integer a : list)
        //     linkedListHashing.put(a, a);
        // queryHash(linkedListHashing, 131);
        // linkedListHashing.delete(432);
        // queryHash(linkedListHashing, -3);
        // linkedListHashing.delete(-3);
        // queryHash(linkedListHashing, -3);

        // HashRemocaoFlag<Integer, Integer> flagHashing = new HashRemocaoFlag<Integer, Integer>();
        // for(Integer a : list)
        // flagHashing.put(a, a);
        // queryHash(flagHashing, 131);
        // flagHashing.delete(432);
        // queryHash(flagHashing, -3);
        // flagHashing.delete(-3);
        // queryHash(flagHashing, -3);
        // flagHashing.put(-3, -3);
        // queryHash(flagHashing, -3);
        
    }

    private static Integer[] generateIntegers(Integer n){
        Integer[] r = new Integer[n];
        Random rand = new Random(42);
        for(int i=0; i<n; i++){
            r[i] = rand.nextInt(n);
        }
        return r;
    }

    private static void printIntegers(Integer[] list, Integer limit){
        for(int i=0; i<limit; i++){
            System.out.printf("%d ", list[i]);
        }
        System.out.println("...");
    }

    private static void queryHash(HashTable<Integer, Integer> table, Integer query){
        if(table.get(query)!=null)
            System.out.println("Found " + query);
        else
            System.out.println("Not found " + query);
    }
}
