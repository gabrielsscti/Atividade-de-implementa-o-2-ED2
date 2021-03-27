package atividade01.classes;

public class HashTentativaLinear<Key, Value> extends HashTable<Key, Value> {
	private int N; // numero de pares de chaves na tabela
	private int M = 16; // Tamanho da tabela hash com tratamento linear
	private Key[] keys; // the keys
	private Value[] vals; // the values
	
	public HashTentativaLinear() {
		keys = (Key[]) new Object[M];
		vals = (Value[]) new Object[M];
	}
	
	public HashTentativaLinear(int cap) {
		keys = (Key[]) new Object[cap];
		vals = (Value[]) new Object[cap];
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
		
		HashTentativaLinear<Key, Value> t;
		t = new HashTentativaLinear<Key, Value> (cap);
		
		for (int i = 0; i < keys.length; i++)
			if (keys[i] != null)
				t.put(keys[i], vals[i]);
		keys = t.keys;
		vals = t.vals;
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
			if (keys[i].equals(key)) { 
				vals[i] = val; 
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
		while (!key.equals(keys[i]))
			i = (i + 1) % M;
		
		keys[i] = null;
		vals[i] = null;
		i = (i + 1) % M;
		
		while (keys[i] != null){
			Key keyToRedo = keys[i];
			Value valToRedo = vals[i];
			keys[i] = null;
			vals[i] = null;
			N--;
			put(keyToRedo, valToRedo);
			i = (i + 1) % M;
		}
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
			if (keys[i].equals(key))
				return vals[i];
		return null;
	}
}
