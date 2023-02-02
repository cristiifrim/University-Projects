package Model.DataStructures;

import Exceptions.DataStructureException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class szHashMap<K, V> implements iHashMap<K, V> {

    HashMap<K, V> __hashmap;

    public szHashMap() {
        __hashmap = new HashMap<>();
    }

    @Override
    public V find(K key) throws DataStructureException{
        if(!__hashmap.containsKey(key))
            throw new DataStructureException("DS-ERROR: " + key + " is not found in the hashmap.");

        return __hashmap.get(key);
    }

    @Override
    public Set<K> getKeys() {
        return __hashmap.keySet();
    }

    @Override
    public Collection<V> values() {
        return __hashmap.values();
    }

    @Override
    public void insert(K key, V value) {
        __hashmap.put(key, value);
    }

    @Override
    public void erase(K key) throws DataStructureException {
        if(!__hashmap.containsKey(key))
            throw new DataStructureException("DS-ERROR: " + key + " is not found in the hashmap.");
        __hashmap.remove(key);
    }

    @Override
    public boolean empty() {
        return __hashmap.isEmpty();
    }

    @Override
    public void update(K key, V newValue) throws DataStructureException {
        if(!__hashmap.containsKey(key))
            throw new DataStructureException("DS-ERROR: " + key + " is not found in the hashmap.");
        __hashmap.put(key, newValue);
    }

    @Override
    public iHashMap<K, V> copy() throws DataStructureException {
        iHashMap<K, V> hashCopy = new szHashMap<>();
        for (K key: getKeys())
            hashCopy.insert(key, find(key));
        return hashCopy;
    }

    @Override
    public Map<K, V> getData() {
        return __hashmap;
    }

    @Override
    public String toString() {
        return __hashmap.toString();
    }
}
