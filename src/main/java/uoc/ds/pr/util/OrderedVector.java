package uoc.ds.pr.util;

import edu.uoc.ds.adt.helpers.KeyValue;
import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;

import java.util.Comparator;

public class OrderedVector<E> implements FiniteContainer<E> {

    private final Comparator<E> comparator;
    private final E[] vector;
    private int n;

    public OrderedVector(int size, Comparator<E> comparator) {
        this.comparator = comparator;
        vector = (E[])new Object[size];
        n = 0;
    }

    public E[] getVector() {
        return vector;
    }

    public E getFirstElement() {
        return vector[0];
    }

    public void swapWithNext(int next) {
        int lastElemIndex = n-1;
        while (lastElemIndex > next) {
            vector[lastElemIndex+1] = vector[lastElemIndex];
            lastElemIndex--;
        }
    }

    public void swapWithPrev(int prev) {
        E aux = getVector()[prev];
        getVector()[prev] = getVector()[prev+1];
        getVector()[prev+1] = aux;
    }

    public void put(E elem) {
        if (!this.isFull()) {
            vector[this.size()] = elem;
            int prev = this.size() - 1;
            n++;
            boolean ordered = false;
            while (prev >= 0 && !ordered) {
                if (comparator.compare(getVector()[prev], elem) <= 0) {
                    ordered = true;
                } else {
                    // swap with previous element
                    swapWithPrev(prev);
                    prev--;
                }
            }
        }
    }

    public boolean update(E elem) {
        boolean found = false;
        boolean ordered = false;
        int i = 0;
        while (!found && i < this.size()) {
            if (vector[i] == elem) {
                found = true;
            } else {
                i++;
            }
        }
        if (found && i > 0) {
            if (comparator.compare(vector[i-1], vector[i]) > 0) {
                int prev = i-1;
                while (prev >= 0 && !ordered) {
                    if (comparator.compare(vector[prev], vector[prev+1]) <= 0) {
                        ordered = true;
                    } else {
                        // swap with previous element
                        swapWithPrev(prev);
                        prev--;
                    }
                }
            } else if (i < this.size()-1 && comparator.compare(vector[i], vector[i+1]) > 0) {
                int next = i+1;
                while (next < this.size() && !ordered) {
                    if (comparator.compare(vector[next-1], vector[next]) <= 0) {
                        ordered = true;
                    } else {
                        // swap with previous element
                        swapWithNext(next);
                        next++;
                    }
                }
            }
        }
        return found;
    }

    @Override
    public boolean isFull() {
        return this.size() == this.getVector().length;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public Iterator<E> values() {
        return new IteratorArrayImpl<>(getVector(), n, 0);
    }
}
