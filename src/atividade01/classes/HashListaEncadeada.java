package atividade01.classes;

import java.util.LinkedHashMap;

public class HashListaEncadeada<Key, Value> extends HashTable<Key, Value> {
    private int N; // numero de pares de chaves na tabela
	private int M = 16; // Tamanho da tabela hash com tratamento linear
	private LinkedHashMap<Key, Value>[] vals; // the values
	
	public HashListaEncadeada() {
		vals = new LinkedHashMap[M];
        for(int i=0; i<M; i++)
            vals[i] = new LinkedHashMap<Key, Value>();
	}
	
	public HashListaEncadeada(int cap) {
		vals = new LinkedHashMap[cap];
		M = cap;
        
        for(int i=0; i<M; i++)
            vals[i] = new LinkedHashMap<Key, Value>();
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
		
		HashListaEncadeada<Key, Value> t;
		t = new HashListaEncadeada<Key, Value> (cap);
		
		for (int i = 0; i < vals.length; i++)
			if (!vals[i].isEmpty())
				t.put((Key)((Object)i), vals[i]);
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
		if (N >= M/2) 
			resize(2*M); // double M 

        int hashKey = hash(key);
        vals[hashKey].put(key, val);

		N++;
	}

    /**
	 * Insere um novo objeto de lista encadeada no Hash 
	 * @param key
	 * @param val
	 */
	private void put(Key key, LinkedHashMap<Key, Value> val){
        if(N >= M/2)
            resize(2*M);

        int hashKey = hash(key);
        vals[hashKey] = val;

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
			
		int hashKey = hash(key);
        
        vals[hashKey].remove(key);

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
        int hashKey = hash(key);
        if(vals[hashKey].isEmpty())
            return null;

        return vals[hashKey].get(key);
	}
}
