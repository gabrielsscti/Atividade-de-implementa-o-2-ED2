package atividade02.classes;
import java.util.Iterator;

public class RBTreeToAVL {
    public static AVLTree toAvl(MyRBTree rbTree){
        AVLTree retAvl = new AVLTree();
        Iterator<MyRBTree.Node> it = rbTree.iterator();
        while(it.hasNext()){
            MyRBTree.Node node = it.next();
            retAvl.put(node.key, node.value);
        }
        return retAvl;
    }
}
