package atividade02;
import atividade02.classes.*;
import java.util.Iterator;

public class App {
    public static void main(String args[]){
        MyRBTree<Integer, Integer> rbTree = new MyRBTree<Integer, Integer>();
        rbTree.insert(41, 41);
        rbTree.insert(38, 38);
        rbTree.insert(31, 31);
        rbTree.insert(-19, 5);
        
        // Iterator<MyRBTree<Integer, Integer>.Node> it = rbTree.iterator();
        // while(it.hasNext()){
        //     MyRBTree<Integer, Integer>.Node node = it.next();
        //     System.out.println(node.value);
        // }
        
        AVLTree<Integer, Integer> avlTree = RBTreeToAVL.toAvl(rbTree);

        System.out.println("Kappa");
    }
}
