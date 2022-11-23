package deque;
import java.util.ArrayList;
import java.util.Iterator;

public class LinkedListDeque <T> implements Deque<T>, Iterable<T> {

    private class TNode {
        public T item;
        public TNode next;
        public TNode prev;
        public ArrayList<Integer> a;

        public TNode(TNode p, T i, TNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private TNode first;
    private TNode last;
    private int size;

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    public class LinkedListDequeIterator implements Iterator<T> {
        private int pos;
        private TNode curNode;

        public LinkedListDequeIterator() {
            pos = 0;
            curNode = first.next;
        }

        public boolean hasNext() {
            return pos < size;
        }

        public T next() {
            T returnItem = curNode.item;
            curNode = curNode.next;
            pos += 1;
            return returnItem;
        }
    }

    public void addFirst(T item) {
        TNode x = new TNode(first, item, first.next);
        first.next.prev = x;
        first.next = x;
        size += 1;
    }

    public void addLast(T item) {
        TNode x = new TNode(last.prev, item, last);
        last.prev.next = x;
        last.prev = x;
        size += 1;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        TNode p = first.next;
        while (p != last) {
            System.out.println(p.item);
            p = p.next;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        TNode x = first.next;
        x.next.prev = first;
        first.next = x.next;
        size -= 1;
        return x.item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        TNode x = last.prev;
        x.prev.next = last;
        last.prev = x.prev;
        size -= 1;
        return x.item;
    }

    public T get(int index) {
        TNode p = first;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    public boolean equals(Object o) {
        if (o instanceof LinkedListDeque) {
            LinkedListDeque p = (LinkedListDeque) o;

            if (p.size != this.size) {
                return false;
            }

            TNode oFirst = p.first.next;
            TNode thisFirst = first.next;
            while (oFirst.next != null && thisFirst.next != null) {
                if (oFirst.item != thisFirst.item) {
                    return false;
                }
                oFirst = oFirst.next;
                thisFirst = thisFirst.next;
            }
            return true;
        } else {
            return false;
        }
    }

    public LinkedListDeque() {
        last = new TNode(null, null, null);
        first = new TNode(null, null, last);
        last.prev = first;
        size = 0;
    }

    public T getRecursive(int index) {
        if (index == 0) {
            return first.item;
        }
        return getRecursive(index - 1, first.next);
    }

    public T getRecursive(int index, TNode p) {
        if (index == 0) {
            return p.item;
        }
        return getRecursive(index - 1, p.next);
    }

    public static void main (String[] args) {
        LinkedListDeque<Integer> lld = new LinkedListDeque<Integer>();

        for (int i=0;i<5; i++) {
            lld.addLast(i);
        }

        Iterator<Integer> lseer = lld.iterator();
        while(lseer.hasNext()) {
            int i = lseer.next();
            System.out.println(i);
        }
    }
}