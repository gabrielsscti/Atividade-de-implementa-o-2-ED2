package atividade01;

import atividade01.classes.*;

import java.util.Random;

public class App {
    public static void main(String args[]){
        Integer[] list = {31, -1, 3, 5, 2, 1, -3, 4, 5, 3, 1, 50, 3, 4, 53, 21, 34, 56, 77, 32, 99, 11, -33, -1, -10, -3, 101, -300, -3};

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

        HashRemocaoFlag<Integer, Integer> flagHashing = new HashRemocaoFlag<Integer, Integer>();
        for(Integer a : list)
        flagHashing.put(a, a);
        queryHash(flagHashing, 131);
        flagHashing.delete(432);
        queryHash(flagHashing, -3);
        flagHashing.delete(-3);
        queryHash(flagHashing, -3);
        flagHashing.put(-3, -3);
        queryHash(flagHashing, -3);
        
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
