package uoc.ds.pr.util;

import edu.uoc.ds.adt.helpers.KeyValue;
import edu.uoc.ds.adt.sequential.DictionaryArrayImpl;

import java.util.Comparator;

public class DictionaryOrderedVector<K, V> extends DictionaryArrayImpl<K, V> {

    private final Comparator<K> comparator;
    public DictionaryOrderedVector(int n, Comparator<K> comparator) {
        super(n);
        this.comparator = comparator;
    }

    @Override
    public void put(K key, V value) {
        super.put(key, value);

        int prev = this.size() - 2;
        boolean ordered = false;
        while (prev >= 0 && !ordered) {
            if (comparator.compare(getVector()[prev].getKey(), key) <= 0) {
                ordered = true;
            } else {
                // swap with previous element
                KeyValue<K, V> aux = getVector()[prev];
                getVector()[prev] = getVector()[prev+1];
                getVector()[prev+1] = aux;
                prev--;
            }
        }
    }

    @Override
    public V get(K key) {
        int first = 0;
        int last = this.size() - 1;
        while (last >= first && last < this.size()) {
            int i = first + (last - first)/2;
            if (comparator.compare(getVector()[i].getKey(), key) == 0) {
                return getVector()[i].getValue();
            } else if (comparator.compare(key, getVector()[i].getKey()) > 0) {
                first = i + 1;
            } else {
                last = i - 1;
            }
        }
        return null;
    }
}
