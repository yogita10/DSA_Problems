import java.util.*;

public class bfsQuestions {
    // 785
    public boolean bipartite(int[][] graph, int src, int[] vis) {
        LinkedList<Integer> que = new LinkedList<>();
        que.addLast(src);

        // No Color : -1 , Red : 0, Green : 1
        int color = 0;
        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                int rvtx = que.removeFirst();
                if (vis[rvtx] != -1) {
                    if (color != vis[rvtx]) // conflict
                        return false;
                    continue;
                }

                vis[rvtx] = color;
                for (int v : graph[rvtx]) {
                    if (vis[v] == -1) {
                        que.addLast(v);
                    }
                }
            }

            color = (color + 1) % 2;
        }

        return true;
    }

    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] vis = new int[n]; // vector<int> vis(n,-1);
        Arrays.fill(vis, -1);

        for (int i = 0; i < n; i++) {
            if (vis[i] == -1 && !bipartite(graph, i, vis))
                return false;
        }

        return true;
    }

    // 994
    public int orangesRotting(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int time = 0;
        int freshorange = 0;
        int[][] dir = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };

        Queue<Integer> qu = new LinkedList<Integer>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    qu.add(i * m + j);
                } else if (grid[i][j] == 1)
                    freshorange++;
            }
        }

        if (freshorange == 0)
            return 0;
        while (qu.size() != 0) {
            int size = qu.size();
            while (size-- > 0) {
                int idx = qu.peek();
                qu.remove();
                int r = idx / m;
                int c = idx % m;

                for (int d = 0; d < 4; d++) {
                    int x = r + dir[d][0];
                    int y = c + dir[d][1];

                    if (x >= 0 && y >= 0 && x < n && y < m && grid[x][y] == 1) {
                        freshorange--;
                        grid[x][y] = 2;
                        qu.add(x * m + y);
                        if (freshorange == 0)
                            return time + 1; // if ye chee for loop ke baharlikhte to time return karte kyunki voh
                                             // already next state pe pahunch chuka h
                    }
                }
            }

            time++;
        }

        return -1;
    }

    // 1091
    public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0)
            return 0;

        int n = grid.length, m = grid[0].length, shortestPath = 1;
        if (grid[0][0] == 1 || grid[n - 1][m - 1] == 1)
            return -1;

        LinkedList<Integer> que = new LinkedList<>();
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 } };

        que.addLast(0);
        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                int idx = que.removeFirst();
                int sr = idx / m, sc = idx % m;
                if (sr == n - 1 && sc == m - 1)
                    return shortestPath;
                for (int[] d : dir) {
                    int r = sr + d[0];
                    int c = sc + d[1];

                    if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 0) {
                        grid[r][c] = 1;
                        que.addLast(r * m + c);

                    }

                }
            }

            shortestPath++;
        }

        return -1;
    }

    // 542
    public int[][] updateMatrix(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0)
            return grid;

        int n = grid.length, m = grid[0].length;

        LinkedList<Integer> que = new LinkedList<>();
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

        boolean[][] vis = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    que.push(i * m + j);
                    vis[i][j] = true;
                }
            }
        }

        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                int idx = que.removeFirst();
                int sr = idx / m, sc = idx % m;

                for (int[] d : dir) {
                    int r = sr + d[0];
                    int c = sc + d[1];

                    if (r >= 0 && c >= 0 && r < n && c < m && !vis[r][c]) {
                        grid[r][c] = grid[sr][sc] + 1;
                        vis[r][c] = true;
                        que.addLast(r * m + c);
                    }

                }
            }
        }

        return grid;
    }

    // 1020

    // 286

    // 207
    public boolean canFinish(int N, int[][] prerequisites) {
        // ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

        // for (int i = 0; i <N; i++) {
        // graph.add(new ArrayList<>());
        // }

        ArrayList<Integer>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }

        int[] indegree = new int[N];

        for (int[] arr : prerequisites) {

            graph[arr[0]].add(arr[1]);
            indegree[arr[1]]++;
        }

        Queue<Integer> qu = new LinkedList<Integer>();

        for (int i = 0; i < N; i++) {
            if (indegree[i] == 0)
                qu.add(i);
        }

        int level = 0;
        int vtxcount = 0;
        while (qu.size() != 0) {
            int size = qu.size();
            while (size-- > 0) {
                int idx = qu.peek();
                qu.remove();
                vtxcount++;
                for (int v : graph[idx]) {
                    if (--indegree[v] == 0) {
                        qu.add(v);
                    }
                }

            }
            level++;
        }

        return vtxcount == N;
    }

    // 210
    public int[] findOrder(int N, int[][] prerequisites) {

        ArrayList<Integer>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }
        int[] indegree = new int[N];

        for (int[] ar : prerequisites) {
            graph[ar[1]].add(ar[0]);
            indegree[ar[0]]++;
        }

        Queue<Integer> que = new LinkedList<>();
        for (int i = 0; i < N; i++)
            if (indegree[i] == 0)
                que.add(i);

        ArrayList<Integer> ans = new ArrayList<>();
        while (que.size() != 0) {
            int vtx = que.peek();
            que.remove();
            ans.add(vtx);
            for (int v : graph[vtx]) {
                if (--indegree[v] == 0)
                    que.add(v);
            }
        }

        if (ans.size() != N)
            return new int[0];

        int[] a = new int[N];
        for (int i = 0; i < ans.size(); i++) {
            a[i] = ans.get(i);
        }

        return a;
    }

    //329
    public int longestIncreasingPath(int[][] matrix) {
        int n = matrix.length ; 
        int m = matrix[0].length ; 
        int[][] indegree = new int[matrix.length][matrix[0].length] ; 
        int[][] dir = {{0,1},{0,-1},{1,0},{-1,0}} ; 
        
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                for(int d=0;d<4;d++){
                    int x = i + dir[d][0] ; 
                    int y = j + dir[d][1] ; 
                    
                    if(x>=0 && y>=0 && x<n && y<m && matrix[x][y] > matrix[i][j]) indegree[x][y]++ ; 
                }
            }
        }
        
        Queue<Integer> que = new LinkedList<Integer>() ; 
        
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(indegree[i][j] == 0) que.add(i*m + j ) ; 
            }
        }
        
        int level = 0; 
        while(que.size()!=0){
            int size = que.size(); 
            while(size-- > 0){
                int idx = que.peek(); 
                que.remove(); 
                int r = idx/m; 
                int c = idx%m; 
                
                for(int d=0;d<4;d++){
                    int x = r + dir[d][0] ; 
                    int y = c + dir[d][1] ;
                    
                    if(x>=0 && y>=0 && x<n && y<m && matrix[x][y] > matrix[r][c] && --indegree[x][y] == 0) que.add(x*m+y) ;
                }
            }
            
            level++ ;
            
        }
        
        
        return level ; 
        
    }


}
