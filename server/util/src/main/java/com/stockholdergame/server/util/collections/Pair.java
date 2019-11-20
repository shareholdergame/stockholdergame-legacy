package com.stockholdergame.server.util.collections;

/**
 * @author Alexander Savin
 *         Date: 20.11.2010 21.58.23
 */
public class Pair<F extends Comparable<F>, S extends Comparable<S>> implements Comparable<Pair<F, S>> {

    private F first;

    private S second;

    public Pair() {
    }

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
        if (second != null ? !second.equals(pair.second) : pair.second != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[first:" + first.toString() + ", second:" + second.toString() + "]";
    }

    @Override
    public int compareTo(Pair<F, S> o) {
        int result = this.getFirst().compareTo(o.getFirst());
        if (result == 0) {
            result = this.getSecond().compareTo(o.getSecond());
        }
        return result;
    }
}
