package deque;
import java.util.Comparator;

public class MaxArrayDequeComparator<T> implements Comparator<T>{

    @Override
    public int compare(T o1, T o2) {
        String a = o1.toString();
        String b = o2.toString();
        return a.compareTo(b);
    }

}
