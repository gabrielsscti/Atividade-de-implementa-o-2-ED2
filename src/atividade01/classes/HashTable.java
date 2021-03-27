package atividade01.classes;

public abstract class HashTable<Key, Value> {
    public abstract boolean contains(Key key);
    public abstract void delete(Key key);
    public abstract void put(Key key, Value val);
    public abstract Value get(Key key);

}
