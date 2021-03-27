package atividade01.classes;

public class HashDuplo<Key, Value> extends HashTable<Key, Value> {
	private int N; // numero de pares de chaves na tabela
	private int M = 16; // Tamanho da tabela hash com tratamento linear
	private Key[] keys; // the keys
	private Value[] vals; // the values

	/**
	 * Construtor
	 * 
	 * @param 
	 */
	public HashDuplo() {
		keys = (Key[]) new Object[M];
		vals = (Value[]) new Object[M];
	}

	private HashDuplo(int cap) {
		keys = (Key[]) new Object[cap];
		vals = (Value[]) new Object[cap];
		M = cap;
	}

	/**
	 * Calcula função hash primária
	 * 
	 * @param key
	 * @return int
	 */
	private int hash1(Key key) {
		return (key.hashCode() & 0x7fffffff) % M;
	}

	/**
	 * Calcula função hash secundária
	 * 
	 * @param key
	 * @return int
	 */
	private int hash2(Key key) {
		int temp = (key.hashCode() & 0x7fffffff) % M;
		return (temp % 2 == 0 ? temp + 1 : temp);

	}

	/**
	 * Calcula o incremento do hash
	 * 
	 * @param key
	 * @param position
	 * @return Integer
	 */
	private int hashIncrement(Key key, int position) {
		return (hash1(key) + position * hash2(key)) % M;
	}

	/**
	 * Redimensiona a tabela de acordo com a quantidade de chaves.
	 * 
	 * @param cap
	 */
	private void resize(int cap) {

		HashDuplo<Key, Value> t;
		t = new HashDuplo<Key, Value>(cap);

		for (int i = 0; i < keys.length; i++)
			if (keys[i] != null)
				t.put(keys[i], vals[i]);
		keys = t.keys;
		vals = t.vals;
		M = t.M;

	}
	
	@Override
	public boolean contains(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument to contains() cannot be null");
		}

		return get(key) != null;
	}

	/**
	 * Insere um novo objeto no Hash
	 * 
	 * @param key
	 * @param val
	 */
	@Override
	public void put(Key key, Value val) {
		int i, count = 0;
		if (N >= M / 2)
			resize(2 * M); // double M

		for (i = hash1(key); keys[i] != null; i = hashIncrement(key, ++count))
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
	 * 
	 * @param key
	 */
	@Override
	public void delete(Key key) {
		if (key == null)
			throw new IllegalArgumentException("Argument to delete() cannot be null");

		if (!contains(key))
			return;

		int i = hash1(key), count=0;
		while (!key.equals(keys[i]))
			i = hashIncrement(key, ++count);

		keys[i] = null;
		vals[i] = null;
		i = hashIncrement(key, ++count);

		while (keys[i] != null) {
			Key keyToRedo = keys[i];
			Value valToRedo = vals[i];
			keys[i] = null;
			vals[i] = null;
			N--;
			put(keyToRedo, valToRedo);
			i = hashIncrement(key, ++count);
		}
		N--;
		if (N > 0 && N == M / 8)
			resize(M / 2);
	}

	/**
	 * Busca um objeto no Hash
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public Value get(Key key) {
		int count = 0;
		for (int i = hash1(key); keys[i] != null; i = hashIncrement(key, ++count))
			if (keys[i].equals(key))
				return vals[i];
		return null;
	}
}