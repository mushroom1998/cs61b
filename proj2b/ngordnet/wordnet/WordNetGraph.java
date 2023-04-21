package ngordnet.wordnet;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;


import java.util.*;

public class WordNetGraph {
    private String HYPONYMS;
    private int WordsNum;
    private int E;
    private List<Integer>[] adj;

    public WordNetGraph(String Hyponyms, int wordsNum) {
        HYPONYMS = Hyponyms;
        WordsNum = wordsNum;
        In in = new In(HYPONYMS);
        adj = (List<Integer>[]) new List[WordsNum];

        for (int v = 0; v < wordsNum; v++) {
            adj[v] = new LinkedList<>();
        }

        while(in.hasNextLine()) {
            String[] info = in.readLine().split(",");
            int vertex = Integer.parseInt(info[0]);
            for(int i=1; i< info.length; i++) {
                AddEdge(vertex, Integer.parseInt(info[i]));
            }
        }
    }

    public List<Integer> GetVertex(int i) {
        return adj[i];
    }

    public String AdjacentVertices(List<Integer> v) {
        Iterator<Integer> it = v.iterator();
        String vertices = new String();

        while (it.hasNext()) {
            vertices = vertices + it.next() + " ";
        }
        return vertices;
    }

    public void AddEdge(int hyper, int hypo) {
        adj[hyper].add(hypo);
    }

    public Set<Integer> FindHypos(int hyper) {
        Set<Integer> hypos = new HashSet<>();
        Stack<Integer> s = new Stack<>();
        s.push(hyper);

        while(s.size()!=0) {
            int e = s.pop();
            if(adj[e].size()!=0) {
                Iterator<Integer> it = adj[e].iterator();
                while(it.hasNext()) {
                    s.push(it.next());
                }
            }
            hypos.add(e);
        }

        return hypos;
    }
}
