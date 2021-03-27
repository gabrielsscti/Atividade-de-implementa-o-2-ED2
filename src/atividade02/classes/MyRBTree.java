package atividade02.classes;

import java.util.ArrayList;
import java.util.Iterator;

public class MyRBTree<Key extends Comparable<Key>, Value> implements Iterable{
    protected static final boolean RED = true;
    protected static final boolean BLACK = false;

    public class Node {
        public Key key;
        public Value value;
        public Node left, right;
        public Node parent;
        public int numLeft;
        public int numRight;

        boolean color;

        Node() {
            color = BLACK;
        }

        Node(Key key, Value value, int size, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }

        Node(Key key, Value value){
            this.key = key;
            this.value = value;
        }
    }

    private Node nil = new Node();
    private Node root = nil;

    public MyRBTree() {
        root.left = nil;
        root.right = nil;
        root.parent = nil;
    }

    private boolean isNil(Node x) {
        return x == nil;
    }

    /**
     * Rotação à equerda
     * 
     * @param node
     * @return
     */
    private void leftRotate(Node x) {
        leftRotateFixup(x);
        Node y;
        y = x.right;
        x.right = y.left;
        if (!isNil(y.left))
            y.left.parent = x;
        y.parent = x.parent;

        if (isNil(x.parent))
            root = y;

        else if (x.parent.left == x)
            x.parent.left = y;
        else
            x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    /**
     * Atualiza o número de nós a esquerda e a direita afetados por leftRotate
     * 
     * @param node
     * @return
     */
    private void leftRotateFixup(Node x) {
        if (isNil(x.left) && isNil(x.right.left)) {
            x.numLeft = 0;
            x.numRight = 0;
            x.right.numLeft = 1;
        } else if (isNil(x.left) && !isNil(x.right.left)) {
            x.numLeft = 0;
            x.numRight = 1 + x.right.left.numLeft + x.right.left.numRight;
            x.right.numLeft = 2 + x.right.left.numLeft + x.right.left.numRight;
        } else if (!isNil(x.left) && isNil(x.right.left)) {
            x.numRight = 0;
            x.right.numLeft = 2 + x.left.numLeft + x.left.numRight;
        } else {
            x.numRight = 1 + x.right.left.numLeft + x.right.left.numRight;
            x.right.numLeft = 3 + x.left.numLeft + x.left.numRight + x.right.left.numLeft + x.right.left.numRight;
        }
    }

    /**
     * Rotação à direita
     * 
     * @param node
     * @return
     */
    private void rightRotate(Node y) {

        rightRotateFixup(y);

        Node x = y.left;
        y.left = x.right;

        if (!isNil(x.right))
            x.right.parent = y;
        x.parent = y.parent;

        if (isNil(y.parent))
            root = x;

        else if (y.parent.right == y)
            y.parent.right = x;

        else
            y.parent.left = x;
        x.right = y;

        y.parent = x;

    }

    /**
     * Atualiza o número de nós a esquerda e a direita afetados por rightRotate
     * 
     * @param node
     * @return
     */
    private void rightRotateFixup(Node y) {

        if (isNil(y.right) && isNil(y.left.right)) {
            y.numRight = 0;
            y.numLeft = 0;
            y.left.numRight = 1;
        }

        else if (isNil(y.right) && !isNil(y.left.right)) {
            y.numRight = 0;
            y.numLeft = 1 + y.left.right.numRight + y.left.right.numLeft;
            y.left.numRight = 2 + y.left.right.numRight + y.left.right.numLeft;
        }

        else if (!isNil(y.right) && isNil(y.left.right)) {
            y.numLeft = 0;
            y.left.numRight = 2 + y.right.numRight + y.right.numLeft;

        }

        else {
            y.numLeft = 1 + y.left.right.numRight + y.left.right.numLeft;
            y.left.numRight = 3 + y.right.numRight + y.right.numLeft + y.left.right.numRight + y.left.right.numLeft;
        }

    }

    /**
     * Função de inserir externa
     * 
     * @param key
     * @param value
     * @return
     */
    public void insert(Key key, Value value){
        insert(new Node(key, value));
    }

    /**
     * Função de inserir interna
     * 
     * @param node
     * @return
     */
    private void insert(Node z){
        Node y = nil;
        Node x = root;

        while(!isNil(x)){
            y = x;

            if(z.key.compareTo(x.key) < 0){
                x.numLeft++;
                x = x.left;
            }else{
                x.numRight++;
                x = x.right;
            }
        }

        z.parent = y;
        if(isNil(y))
            root = z;
        else if(z.key.compareTo(y.key) < 0)
            y.left = z;
        else
            y.right = z;

        z.left = nil;
        z.right = nil;
        z.color = RED;

        insertFixup(z);
    }

    private void insertFixup(Node z){
        Node y = nil;

        while(isRed(z.parent)){
            if(z.parent == z.parent.parent.left){
                y = z.parent.parent.right;
                if(isRed(y)){
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                }
                else if(z == z.parent.right){
                    z = z.parent;
                    leftRotate(z);
                }
                else{
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rightRotate(z.parent.parent);
                }
            }else{
                y = z.parent.parent.left;
                if(isRed(y)){
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                }
                else if(z == z.parent.left){
                    z = z.parent;
                    rightRotate(z);
                }
                else{
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    private boolean isRed(Node x){
        return x.color == RED;
    }
    private boolean isBlack(Node x){
        return !isRed(x);
    }
    private void swapColor(Node x){
        x.color = !x.color;
    }
    public double redRatio(){
        if(isNil(root))
            return 0;

        int numBlack = countBlack(root);
        int numNodes = countNodes(root);

        return 1 - (numBlack/(float)numNodes);
    }

    private int countBlack(Node x) {
        if(isNil(x))
            return 0;
        
        return (isBlack(x) ? 1 : 0) + countBlack(x.left) + countBlack(x.right);
    }
    private int countNodes(Node x){
        if(isNil(x))
            return 0;
        return 1 + countNodes(x.left) + countNodes(x.right);
    }

    @Override
    public Iterator<Node> iterator() {
        ArrayList<Node> list = new ArrayList<Node>();
        copyToList(root, list);
        return list.iterator();
    }

    private void copyToList(Node x, ArrayList<Node> list){
        if(isNil(x))
            return;
        list.add(x);
        copyToList(x.left, list);
        copyToList(x.right, list);
    }

}
