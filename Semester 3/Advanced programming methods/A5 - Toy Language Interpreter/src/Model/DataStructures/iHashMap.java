package Model.DataStructures;

import Exceptions.DataStructureException;

import java.util.Map;
import java.util.Set;
import java.util.Collection;

public interface iHashMap<K, V> {

    V find(K key) throws DataStructureException;
    Set<K> getKeys();
    Collection<V> values();


    void insert(K key, V value);
    void erase(K key) throws DataStructureException;
    boolean empty();
    void update(K key, V newValue) throws DataStructureException;

    Map<K, V> getData();

}
