package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>,Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;


    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    public class ArrayDequeIterator implements Iterator<T> {
        private int pos;

        public ArrayDequeIterator() {
            pos = 0;
        }

        public boolean hasNext() {
            return pos < size;
        }

        public T next() {
            T returnItem = items[pos];
            pos += 1;
            return returnItem;
        }
    }

    private void resize(int capacity){
        T[] a = (T[]) new Object[capacity];
        if ((nextFirst+1)%items.length > (nextLast+items.length-1)%items.length) {
            System.arraycopy(items, (nextFirst+1)%items.length, a, 0, items.length-nextFirst-1);
            System.arraycopy(items, 0, a, items.length-nextFirst-1, nextLast);
        } else {
            System.arraycopy(items, (nextFirst+1)%items.length, a, 0, size);
        }
        items = a;
        nextFirst = capacity - 1;
        nextLast = (nextFirst + size + 1) % capacity;
    }

    public void addFirst(T item){
        if (size == items.length){
            resize(size * 2);
        }
        items[nextFirst] = item;
        size +=1;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
    }

    public void addLast(T item){
        if (size == items.length){
            resize(size * 2);
        }
        items[nextLast] = item;
        size +=1;
        nextLast = (nextLast + 1) % items.length;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        for (int i=0; i<size; i++){
            System.out.println(items[(nextFirst+i+1)%items.length]);
        }
    }

    public T removeFirst(){
        if (size == 0){
            return null;
        }
        T x = items[(nextFirst+1)%items.length];
        items[(nextFirst+1)%items.length] = null;
        if (items.length > 16 && (double)(size-1) / (double)items.length < 0.25){
            resize(items.length / 2);
        }
        nextFirst = (nextFirst + 1) % items.length;
        size -= 1;
        return x;
    }

    public T removeLast(){
        if (size == 0){
            return null;
        }
        T x = items[(nextLast-1)%items.length];
        items[(nextLast-1)%items.length] = null;
        if (items.length > 16 && (double)(size-1) / (double)items.length < 0.25){
            resize(items.length / 2);
        }
        nextLast = (nextLast - 1) % items.length;
        size -= 1;
        return x;
    }

    public T get(int index){
        return items[(nextFirst+index) % items.length];
    }

    public boolean equals(Object o){
        if (o instanceof Deque){
            ArrayDeque p = (ArrayDeque) o;
            if(p.size != size){
                return false;
            }
            for (int i=0; i<size; i++) {
                if (p.items[(p.nextFirst+1+i)%p.items.length] != items[(nextFirst+1+i)%items.length]) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public ArrayDeque(){
        items = (T[]) new Object[8];
        size= 0;
        nextFirst = 7;
        nextLast = 0;
    }

    public static void main (String[] args) {
//        ArrayDeque<Integer> ad = new ArrayDeque<>();
//        for (int i=0; i<5; i++) {
//            ad.addLast(i);
//        }
//
//        Iterator<Integer> aseer = ad.iterator();
//        while(aseer.hasNext()) {
//            int i = aseer.next();
//            System.out.println(i);
//        }
        String keyboard="q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        System.out.println(keyboard.indexOf('q'));

    }

}
