package Graph.AdjacencyGraph.matrix;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class AdjacencyMatrixGraph {

    // 열과 행의 인덱스는 정점을 표현하고, 배열값은 간선을 여부를 표현한다
    final int[][] graph;
    final int[] visit;

    public AdjacencyMatrixGraph(int size) {
        graph = new int[size][size];
        visit = new int[graph.length];
    }

    public void addUndirectedEdge(int u, int v) {
        // TODO : 무방향 간선
        addUndirectedEdge(u, v, 1);
    }

    public void addUndirectedEdge(int u, int v, int w) {
        // TODO : 무방향 간선
        graph[u][v] = w;
        graph[v][u] = w;
    }

    public void addDirectEdge(int u, int v) {
        // TODO : 방향 간선
        addDirectEdge(u, v, 1);
    }

    public void addDirectEdge(int u, int v, int w) {
        // TODO : 방향 간선
        graph[u][v] = w;
    }

    public void clear() {
        Arrays.fill(visit, 0);
        for (int i = 0; i < graph.length; i++) {
            Arrays.fill(graph[i], 0);
        }
    }

    public void dfsTraversal(int start) {
        // TODO : start 매개변수는 탐색 시장의 정점이다.
        Arrays.fill(visit, 0);
        System.out.println("---Matrix dfs---");
        dfs(start);
        System.out.println("----------------");
    }

    void dfs(int u) {
        // TODO : 깊이 우선 탐색
        if (1 == visit[u]) {
            return;
        }
        visit(u);
        for (int v = 0; v < graph.length; v++) {
            if (0 != graph[u][v] && 0 == visit[v]) {
                dfs(v);
            }
        }
    }

    public void bfsTraversal(int start) {
        // TODO : start 매개변수는 탐색 시장의 정점이다.
        Arrays.fill(visit, 0);
        System.out.println("---Matrix bfs---");
        bfs(start);
        System.out.println("----------------");
    }

    void bfs(int start) {
        // TODO : 너비 우선 탐색
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visit(start);
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < graph.length; v++) {
                if (0 != graph[u][v] && 0 == visit[v]) {
                    queue.add(v);
                    visit(v);
                }
            }
        }
    }

    void visit(int vertex) {
        System.out.printf("vertex:%d\n", vertex);
        visit[vertex] = 1;
    }

    public void printEdge() {
        System.out.println("---Matrix Edge---");
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (0 != graph[i][j]) {
                    System.out.printf("(%d, %d, %d) ", i, j, graph[i][j]);
                }
            }
        }
        System.out.println("\n-----------------");
    }
}
