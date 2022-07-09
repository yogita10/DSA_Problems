import java.util.*;

public class dfsQuestions {

    // 200
    public void dfsIsland(int i, int j, int n, int m, char[][] grid, int[][] dir) {
        grid[i][j] = '0'; // visited ka space lene ki jarurat nahi h isme aie hi ho jayega kaam
        for (int d = 0; d < dir.length; d++) {
            int r = i + dir[d][0];
            int c = j + dir[d][1];
            if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == '1') {
                dfsIsland(r, c, n, m, grid, dir);
            }
        }
    }

    public int numIslands(char[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int count = 0;
        int n = grid.length;
        int m = grid[0].length;
        int[][] dir = { { 0, -1 }, { 0, 1 }, { 1, 0 }, { -1, 0 } };
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfsIsland(i, j, n, m, grid, dir);
                }
            }
        }

        return count;
    }

    // 695
    public static int dfsArea(int i, int j, int n, int m, int[][] grid, int[][] dir) {
        grid[i][j] = 0;
        int area = 1;
        for (int d = 0; d < dir.length; d++) {
            int r = i + dir[d][0];
            int c = j + dir[d][1];
            if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 1) {
                area += dfsArea(r, c, n, m, grid, dir);
            }
        }

        return area;
    }

    public static int maxAreaOfIsland(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0)
            return 0;

        int area = 0;
        int n = grid.length;
        int m = grid[0].length;
        int[][] dir = { { 0, -1 }, { 0, 1 }, { 1, 0 }, { -1, 0 } };
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    area = Math.max(area, dfsArea(i, j, n, m, grid, dir));
                }
            }
        }

        return area;
    }

    // 463
    // hum agar 4 direction me neighbour dekhenge to ek nbr do baar count ho jayega
    // -> abhi ke liye humne chaaron dir me call lagayi hby dir array
    // if sirf do direction me dekh re ho to 2 se multiply kardena ans same aayegaaa
    public int islandPerimeter(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int nbrs = 0;
        int count = 0;
        int n = grid.length;
        int m = grid[0].length;
        int[][] dir = { { 0, -1 }, { 0, 1 }, { 1, 0 }, { -1, 0 } };
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    count++;
                    for (int d = 0; d < dir.length; d++) {
                        int r = i + dir[d][0];
                        int c = j + dir[d][1];
                        if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 1) {
                            nbrs++;
                        }
                    }
                }
            }
        }

        return count * 4 - nbrs;

    }

    // 130
    // boundary se call lagana is compulsory because agar kahin aur se call lagaya
    // and backtrack ho gaye to vapis
    // us jagah ko trace nahi kar paogye aap, sokuch identification mark hona jaruri
    // h ki boundary reach hogyi h ,
    // so it is always beneficial ki boundary se hi call laga lo
    public void surroundedRegionDFS(int i, int j, int n, int m, char[][] grid, int[][] dir) {
        grid[i][j] = '$';
        for (int d = 0; d < dir.length; d++) {
            int r = i + dir[d][0];
            int c = j + dir[d][1];

            if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 'O')
                surroundedRegionDFS(r, c, n, m, grid, dir);
        }
    }

    public void solve(char[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return;
        }

        int n = grid.length;
        int m = grid[0].length;
        int[][] dir = { { 0, -1 }, { 0, 1 }, { 1, 0 }, { -1, 0 } };
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 || j == 0 || i == n - 1 || j == m - 1) {
                    if (grid[i][j] == 'O')
                        surroundedRegionDFS(i, j, n, m, grid, dir);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 'O')
                    grid[i][j] = 'X';
                if (grid[i][j] == '$')
                    grid[i][j] = 'O';
            }
        }
    }

    // 694

    // Journey to moon - Hacker rank
    public static int dfs(int src, List<Integer>[] graph, boolean[] vis) {
        int size = 1;
        vis[src] = true;
        for (int v : graph[src]) {
            if (!vis[v])
                size += dfs(v, graph, vis);
        }

        return size;
    }

    public static long journeyToMoon(int n, List<List<Integer>> edges) {
        List<Integer>[] graph = new ArrayList[n];

        for (List<Integer> e : edges) {
            graph[e.get(0)].add(e.get(1));
            graph[e.get(1)].add(e.get(0));
        }

        boolean[] vis = new boolean[n];
        long sum = 0, ans = 0;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                int size = dfs(i, graph, vis);
                ans += size * sum;
                sum += size;
            }
        }

        return ans;
    }

    // 210
    public boolean dfs_isCycle(int src, int[] vis, ArrayList<Integer> ans, ArrayList<Integer>[] graph) {
        vis[src] = 1;
        boolean isCycle = false;
        for (int v : graph[src]) {
            if (vis[v] == 0) {
                isCycle = isCycle || dfs_isCycle(v, vis, ans, graph);
            } else if (vis[v] == 1)
                return true;
        }

        ans.add(src);
        vis[src] = 2;
        return isCycle;
    }

    public int[] findOrder(int N, int[][] prerequisites) {

        ArrayList<Integer>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] ar : prerequisites) {
            graph[ar[0]].add(ar[1]);
        }

        ArrayList<Integer> ans = new ArrayList<>();
        int[] vis = new int[N];

        boolean cycle = false;
        for (int i = 0; i < N; i++) {
            if (vis[i] == 0) {
                cycle = cycle || dfs_isCycle(i, vis, ans, graph);
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

    

}
