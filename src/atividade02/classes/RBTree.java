package atividade02.classes;

public class RBTree<Key extends Comparable<Key>, Value>
{	
	
	protected static final boolean RED = true;
    protected static final boolean BLACK = false;

    protected class Node {
        public Key chave;
        public Value valor;
        public Node esq, dir;

        boolean cor;
        int size;

        Node(Key key, Value value, int size, boolean color) {
            this.chave = key;
            this.valor = value;

            this.size = size;
            this.cor = color;
        }
    }

    protected Node raiz;

	private boolean isRed(Node h) {
	
	}
	
	private boolean isBlack(Node h) {
		// Implementar método que verifica se o nó é preto
	}
	
	 public int size() {
        return size(raiz);
    }

    protected int size(Node no) {
        if (no == null) {
            return 0;
        }

        return no.size;
    }

    public boolean isEmpty() {
        return size(raiz) == 0;
    }
	
	 /**
	  * Rotação à equerda
	  * @param node
	  * @return
	  */
	 protected Node rotacaoEsquerda(Node no) {
        if (no == null || no.dir == null) {
            return no;
        }

        Node novaRaiz = no.dir;

        no.dir = novaRaiz.esq;
        novaRaiz.esq = no;

        novaRaiz.cor = no.cor;
        novaRaiz.cor = RED;

        novaRaiz.size = no.size;
        novaRaiz.size = size(no.esq) + 1 + size(no.dir);

        return novaRaiz;
    }
		
	/**
	 * Implementar o esse método
	 * @param h
	 * @return
	 */
	private Node rotacaoDireita(Node h) {
		// Implementar método que aplica a rotação à direita.
		return null;
	}
	private void trocaCor(Node h) {
		// Implementar método que troca as cores.
	}
	
	
	/**
	 * Insere um novo nó
	 * @param key
	 * @param val
	 */
	public void insere(Key key, Value val){ 
		raiz = insere(raiz, key, val);
		raiz.cor = BLACK;
	}
	
	private Node insere(Node h, Key key, Value val)
	{
		if (h == null) // Do standard insert, with red link to parent.
			return new Node(key, val, 1, RED);
		
		int cmp = key.compareTo(h.chave);
		if (cmp < 0) 
			h.esq = insere(h.esq, key, val);
		else if (cmp > 0) 
			h.dir = insere(h.dir, key, val);
		else h.valor = val;
		
		if (isRed(h.dir) && !isRed(h.esq)) 
			h = rotacaoEsquerda(h);
		if (isRed(h.esq) && isRed(h.esq.esq)) 
			h = rotacaoDireita(h);
		if (isRed(h.esq) && isRed(h.dir)) 
			trocaCor(h);
		
		h.size = size(h.esq) + size(h.dir) + 1;
		return h;
	}
}
