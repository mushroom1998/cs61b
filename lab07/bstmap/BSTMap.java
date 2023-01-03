package bstmap;

import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V>{

    class BSTNode {
        public K key;
        public V value;
        public BSTNode leftChild;
        public BSTNode rightChild;
        public BSTNode parent;

        public BSTNode (K k, V v) {
            key = k;
            value = v;
            leftChild = null;
            rightChild = null;
            parent = null;
        }
    }

    private BSTNode root;
    private int size;


    public BSTMap () {
        root = null;
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        if (root == null) {
            return;
        }
        root = null;
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (root == null) {
            return false;
        }
        BSTNode p = root;
        while (p != null) {
            if (key.equals(p.key)) {
                return true;
            } else if (key.compareTo(p.key) < 0) {
                p = p.leftChild;
            } else if (key.compareTo(p.key) > 0) {
                p = p.rightChild;
            }
        }
        return false;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        BSTNode p = root;
        while (p != null) {
            if (key.equals(p.key)) {
                return p.value;
            } else if (key.compareTo(p.key) < 0) {
                p = p.leftChild;
            } else if (key.compareTo(p.key) > 0) {
                p = p.rightChild;
            }
        }
        return null;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size(){
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new BSTNode(key, value);
            size += 1;
            return;
        }
        BSTNode p =root;
        while (p != null) {
            if (key.compareTo(p.key) < 0) {
                if (p.leftChild == null) {
                    p.leftChild = new BSTNode(key, value);
                    p.leftChild.parent = p;
                    size += 1;
                }
                p = p.leftChild;
            } else if (key.compareTo(p.key) > 0) {
                if (p.rightChild == null) {
                    p.rightChild = new BSTNode(key, value);
                    p.rightChild.parent = p;
                    size += 1;
                }
                p = p.rightChild;
            } else if (key.equals(p.key)) {
                p.value = value;
                return;
            }
        }
    }

    /* prints out your BSTMap in order of increasing Key. */
    public void printInOrder() {
        if (root == null) {
            return;
        }
        BSTNode p = root;
        Stack<BSTNode> s = new Stack<>();
        while (!s.isEmpty() || p != null) {
            while (p != null) {
                s.push(p);
                p = p.leftChild;
            }
            BSTNode q = s.pop();
            System.out.println(q.key + " " + q.value);
            if (q.rightChild != null) {
                p = q.rightChild;
            }
        }
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        HashSet<K> retSet = new HashSet<>();
        if (root == null) {
            return retSet;
        }
        BSTNode p = root;
        Stack<BSTNode> s = new Stack<>();
        while (!s.isEmpty() || p != null) {
            while (p != null) {
                s.push(p);
                p = p.leftChild;
            }
            BSTNode q = s.pop();
            retSet.add(q.key);
            if (q.rightChild != null) {
                p = q.rightChild;
            }
        }
        return retSet;
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (root == null || !containsKey(key)) {
            return null;
        }
        BSTNode target = root;
        while (target != null) {
            if (key.equals(target.key)) {
                break;
            } else if (key.compareTo(target.key) < 0) {
                target = target.leftChild;
            } else if (key.compareTo(target.key) > 0) {
                target = target.rightChild;
            }
        }
        V ret = target.value;
        if (size == 1) {
            root = null;
        } else {
            if (target.leftChild == null && target.rightChild == null) { //no subtree
                if (target.parent.leftChild == target) {
                    target.parent.leftChild = null;
                } else {
                    target.parent.rightChild = null;
                }
            } else if (target.leftChild != null && target.rightChild != null) { //two subtrees
                BSTNode swt = target.leftChild;
                while (swt.rightChild != null) {
                    swt = swt.rightChild;
                }
                K newKey = swt.key;
                V newValue = swt.value;

                remove(swt.key);

                target.key = newKey;
                target.value = newValue;
                return ret;

            } else if (target.leftChild != null) { //only left subtree
                if (size == 2) {
                    root = target.leftChild;
                    root.parent = null;
                    size = 1;
                    return ret;
                }
                if (target.parent.leftChild == target) { //target is a left subtree
                    target.parent.leftChild = target.leftChild;
                    target.leftChild.parent = target.parent;
                } else { //target is a right subtree
                    target.parent.rightChild = target.leftChild;
                    target.rightChild.parent = target.parent;
                }
            } else { //only right subtree
                if (size == 2) {
                    root = target.rightChild;
                    root.parent = null;
                    size = 1;
                    return ret;
                }
                if (target.parent.leftChild == target) { //target is a left subtree
                    target.parent.leftChild = target.rightChild;
                    target.rightChild.parent = target.parent;
                } else { //target is a right subtree
                    target.parent.rightChild = target.rightChild;
                    target.rightChild.parent = target.parent;
                }
            }
        }
        size -= 1;
        return ret;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (root == null || get(key) != value || !containsKey(key)) {
            return null;
        }
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
