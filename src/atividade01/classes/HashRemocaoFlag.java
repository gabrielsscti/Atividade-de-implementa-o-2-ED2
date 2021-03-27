package atividade01.classes;

public class HashRemocaoFlag<Key, Value> extends HashTable<Key, Value> {
    private int N; // numero de pares de chaves na tabela
	private int M = 16; // Tamanho da tabela hash com tratamento linear
	private Key[] keys; // the keys
	private boolean[] removed;
	private Value[] vals; // the values
	
	public HashRemocaoFlag() {
		keys = (Key[]) new Object[M];
		vals = (Value[]) new Object[M];
		removed = new boolean[M];
	}
	
	public HashRemocaoFlag(int cap) {
		keys = (Key[]) new Object[cap];
		vals = (Value[]) new Object[cap];
		removed = new boolean[cap];
		M = cap;
	}
	
	/**
	 * Calcula o Hash
	 * @param key
	 * @return
	 */
	private int hash(Key key){ 
		return (key.hashCode() & 0x7fffffff) % M; 
	}
	
	/**
	 * Redimensiona a tabela de acordo com a quantidade de chaves.
	 * @param cap
	 */
	private void resize(int cap) {
		
		HashRemocaoFlag<Key, Value> t;
		t = new HashRemocaoFlag<Key, Value> (cap);
		
		for(int i=0; i<keys.length; i++)
			t.removed[i] = removed[i];

		for (int i = 0; i < keys.length; i++)
			if (keys[i] != null)
				t.put(keys[i], vals[i]);

		keys = t.keys;
		vals = t.vals;
		removed = t.removed;
		M = t.M;
		
	}
	
	 public boolean contains(Key key) {
	        if (key == null) {
	            throw new IllegalArgumentException("Argument to contains() cannot be null");
	        }

	        return get(key) != null;
	 }
	
	/**
	 * Insere um novo objeto no Hash 
	 * @param key
	 * @param val
	 */
	public void put(Key key, Value val) {
		int i;
		if (N >= M/2) 
			resize(2*M); // double M 
		
		for (i = hash(key); keys[i] != null; i = (i + 1) % M)
			if (keys[i].equals(key) || removed[i]) { 
				vals[i] = val; 
				removed[i] = false;
				return; 
			}
		keys[i] = key;
		vals[i] = val;
		N++;
	}
	
	/**
	 * Remove um objeto do Hash
	 * @param key
	 */
	public void delete(Key key)
	{
		if (key == null) 
			throw new IllegalArgumentException("Argument to delete() cannot be null");
		
		if (!contains(key))
			return;
			
		int i = hash(key);
		while (!key.equals(keys[i]) || removed[i])
			i = (i + 1) % M;
		
		removed[i] = true;

		N--;
		if (N > 0 && N == M/8) 
			resize(M/2);
	}
	
	/**
	 * Busca um objeto no Hash
	 * @param key
	 * @return
	 */
	public Value get(Key key) {
		for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
			if (keys[i].equals(key) && !removed[i])
				return vals[i];
		return null;
	}
}
