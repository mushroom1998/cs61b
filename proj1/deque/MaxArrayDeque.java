package deque;

import java.util.Comparator;

public class MaxArrayDeque<T>{
    public double usageFactor;
    private T[] items;
    private int size;
    private int start;
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        //creates a MaxArrayDeque with the given Comparator
        items = (T[]) new Object[8];
        size= 0;
        usageFactor = 0.0;
        start = 1;
        comparator = c;
    }

    public T max() {
        //returns the maximum element in the deque as governed by the previously given Comparator.
        //If the MaxArrayDeque is empty, simply return null
        if (this.items == null) {
            return null;
        }
        T biggest = items[start];
        for (int i=0; i<size; i++){
            if (comparator.compare(items[(i+start)%items.length], biggest) > 0) {
                biggest = items[(i+start)%items.length];
            }

        }
        return biggest;
    }

    public T max(Comparator<T> c) {
        //returns the maximum element in the deque as governed by the parameter Comparator c.
        //If the MaxArrayDeque is empty, simply return null.
        if (this.items == null) {
            return null;
        }
        T biggest = items[start];
        for (int i=0; i<size; i++){
            if (c.compare(items[(i+start)%items.length], biggest) > 0) {
                biggest = items[(i+start)%items.length];
            }

        }
        return biggest;
    }

    private void resize(int capacity){
        T[] a = (T[]) new Object[capacity];
        if (start+size > items.length) {
            int rest = items.length - start;
            System.arraycopy(items, start, a, 0, rest);
            System.arraycopy(items, 0, a, rest, size-rest);
        } else {
            System.arraycopy(items, start, a, 0, size);
        }
        items = a;
        start = 0;
    }

    public void addFirst(T item){
        if (size == items.length){
            resize(size * 2);
        }
        start -= 1;
        if (start < 0) {
            start += items.length;
        }
        items[start] = item;
        size +=1;
        usageFactor = (double)size / (double)items.length;
    }

    public void addLast(T item){
        if (size == items.length){
            resize(size * 2);
        }
        items[size] = item;
        if (size == 0){
            start =0;
        }
        size +=1;
        usageFactor = (double)size / (double)items.length;
    }

    public boolean isEmpty(){
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        for (int i=0; i<size; i++){
            System.out.println(items[(start+i)%items.length]);
        }
    }

    public T removeFirst(){
        if (size-1 < 0){
            return null;
        }
        T x = items[start];
        items[start] = null;
        if (items.length > 16 && (double)(size-1) / (double)items.length < 0.25){
            resize(items.length / 2);
        }
        start = (start+1) % items.length;
        size -= 1;
        usageFactor = (double)size / (double)items.length;
        return x;
    }

    public T removeLast(){
        if (size-1 < 0){
            return null;
        }
        T x = items[size-1];
        items[size-1] = null;
        if (items.length > 16 && (double)(size-1) / (double)items.length < 0.25){
            resize(items.length / 2);
        }
        size -= 1;
        usageFactor = (double)size / (double)items.length;
        return x;
    }

    public T get(int index){
        return items[(start+index-1) % items.length];
    }

    public static void main(String[] args) {
        MaxArrayDequeComparator<String> c = new MaxArrayDequeComparator<>();
        MaxArrayDeque<String> ad = new MaxArrayDeque<String>(c);

        ad.addLast("a");
        ad.addLast("z");
        ad.addLast("apple");
        ad.addLast("beta");

        System.out.println(ad.max());
        System.out.println(ad.max(c));
    }

}
