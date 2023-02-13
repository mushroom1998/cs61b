package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int initialSize = 16;
    private double maxLoad = 0.75;
    private int elementNum = 0;
    private int bucketNum = 16;
    // You should probably define some more!

    /**
     * Constructors
     */
    public MyHashMap() {
        buckets = createTable(initialSize);
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        this.bucketNum = initialSize;
        buckets = createTable(initialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initialSize = initialSize;
        this.maxLoad = maxLoad;
        this.bucketNum = initialSize;
        buckets = createTable(initialSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void clear() {
        buckets = createTable(initialSize);
        elementNum = 0;
        bucketNum = initialSize;
    }

    @Override
    public boolean containsKey(K key) {
        if (elementNum == 0) {
            return false;
        }
        int hashKey = key.hashCode() & Integer.MAX_VALUE % bucketNum;
        Iterator it = buckets[hashKey].iterator();
        while (it.hasNext()) {
            Node n = (Node) it.next();
            if (key.equals(n.key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (elementNum == 0) {
            return null;
        }
        int hashKey = key.hashCode() & Integer.MAX_VALUE % bucketNum;
        Iterator it = buckets[hashKey].iterator();
        while (it.hasNext()) {
            Node n = (Node) it.next();
            if (key.equals(n.key)) {
                return n.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return elementNum;
    }

    @Override
    public void put(K key, V value) {
        if (get(key) == value) {
            return;
        }
        if (containsKey(key)) {
            int hashKey = key.hashCode() & Integer.MAX_VALUE % bucketNum;
            Iterator it = buckets[hashKey].iterator();
            while (it.hasNext()) {
                Node n = (Node) it.next();
                if (key.equals(n.key)) {
                    n.value = value;
                    return;
                }
            }
        }
        Node newNode = createNode(key, value);
        elementNum += 1;
        double loadFactor = elementNum / bucketNum;
        if (loadFactor > maxLoad) {
            bucketNum *= 2;
            Collection[] originBuckets = buckets;
            buckets = createTable(bucketNum);
            for (int i = 0; i < bucketNum / 2; i++) {
                Iterator it = originBuckets[i].iterator();
                while (it.hasNext()) {
                    Node n = (Node) it.next();
                    int newKey = n.key.hashCode() & Integer.MAX_VALUE % bucketNum;
                    buckets[newKey].add(n);
                }
            }
        }
        int hashKey = key.hashCode() & Integer.MAX_VALUE % bucketNum;
        buckets[hashKey].add(newNode);
        return;
    }

    @Override
    public Set<K> keySet() {
        Set<K> returnSet = new HashSet<>();
        for (int i = 0; i < bucketNum; i++) {
            Iterator it = buckets[i].iterator();
            while (it.hasNext()) {
                Node n = (Node) it.next();
                returnSet.add(n.key);
            }
        }
        return returnSet;
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        int hashKey = key.hashCode() & Integer.MAX_VALUE % bucketNum;
        Iterator it = buckets[hashKey].iterator();
        while (it.hasNext()) {
            Node n = (Node) it.next();
            if (key.equals(n.key)) {
                it.remove();
                return n.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if (!containsKey(key)) {
            return null;
        }
        int hashKey = key.hashCode() & Integer.MAX_VALUE % bucketNum;
        Iterator it = buckets[hashKey].iterator();
        while (it.hasNext()) {
            Node n = (Node) it.next();
            if (key.equals(n.key) && value.equals(n.value)) {
                it.remove();
                return n.value;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator<K> {

        private Node curNode;
        private int count = 0;
        private int curBucketIndex = 0;
        private Iterator curBucketItr;

        public HashMapIterator() {
            if (elementNum == 0) {
                return;
            }
        }

        public boolean hasNext() {
            return count < elementNum;
        }

        public K next() {
            if (curBucketItr == null) {
                FindNextNode();
            }
            else {
                if (curBucketItr.hasNext()) {
                    curNode = (Node) curBucketItr.next();
                } else {
                    curBucketIndex += 1;
                    FindNextNode();
                }
                count += 1;
            }
            return curNode.key;
        }

        public void FindNextNode() {
            while (curBucketIndex < bucketNum) {
                if (buckets[curBucketIndex].size() > 0) {
                    curBucketItr = buckets[curBucketIndex].iterator();
                    curNode = (Node) curBucketItr.next();
                    break;
                }
                curBucketIndex += 1;
            }
        }
    }
}