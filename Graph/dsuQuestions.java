import java.util.*;

public class dsuQuestions {

    // 684
    public int findPar(int[] par, int u) {
        if (par[u] == u)
            return u;
        else
            return par[u] = findPar(par, par[u]);
    }

    public int[] findRedundantConnection(int[][] edges) {
        int N = edges.length;
        int[] par = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            par[i] = i;
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            int p1 = findPar(par, u);
            int p2 = findPar(par, v);

            if (p1 != p2) {
                par[p1] = p2;
            } else
                return edge;
        }

        return new int[0];
    }

    // 1061
    int[] par;

    public int findPar(int u) {
        if (par[u] == u)
            return u;
        else
            return par[u] = findPar(par, par[u]);
    }

    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        par = new int[26];
        for (int i = 0; i < 26; i++)
            par[i] = i;
        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i), c2 = s2.charAt(i);
            int p1 = findPar(c1 - 'a'), p2 = findPar(c2 - 'a');
            par[p1] = Math.min(p1, p2);
            par[p2] = Math.min(p1, p2);
        }

        String ans = "";
        char[] basestr = baseStr.toCharArray();
        for (char ch : basestr) {
            int p = findPar(ch - 'a');
            ans += (char) (p + 'a');
        }

        return ans;
    }

    // 695
    int[] par;

    public int findPar(int u) {
        if (par[u] == u)
            return u;
        else
            return par[u] = findPar(par[u]);
    }

    public int maxAreaOfIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        par = new int[n * m];
        for (int i = 0; i < n * m; i++)
            par[i] = i;

        int[] componentSize = new int[n * m];
        Arrays.fill(componentSize, 1);

        int maxArea = 0;

        int[][] dir = { { 1, 0 }, { 0, 1 } };

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    int p1 = findPar(i * m + j);
                    for (int d = 0; d < 2; d++) {
                        int x = i + dir[d][0];
                        int y = j + dir[d][1];

                        if (x >= 0 && y >= 0 && x < n && y < m && grid[x][y] == 1) {
                            int p2 = findPar(x * m + y);
                            if (p1 != p2) {
                                par[p2] = p1;
                                componentSize[p1] += componentSize[p2];
                            }
                        }
                        maxArea = Math.max(maxArea, componentSize[p1]);
                    }
                } else
                    componentSize[i * m + j] = 0;
            }
        }

        return maxArea;
    }

    // 839
    int[] par, size;

    public int findPar(int u) {
        return par[u] == u ? u : (par[u] = findPar(par[u]));
    }

    public boolean isSimilar(String s1, String s2) {
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i) && ++count > 2)
                return false;
        }
        return true;
    }

    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        par = new int[n];
        for (int i = 0; i < n; i++)
            par[i] = i;

        int noOfSet = n;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isSimilar(strs[i], strs[j])) {
                    int p1 = findPar(i);
                    int p2 = findPar(j);

                    if (p1 != p2) {
                        par[p1] = p2;
                        noOfSet--;
                    }
                }
            }
        }

        return noOfSet;
    }

    // 305
    int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    
    public List<Integer> numIslands2(int n, int m, int[][] positions) {
        int[][] grid = new int[n][m];
        par = new int[n * m];
        for (int i = 0; i < n * m; i++)
            par[i] = i;

        List<Integer> ans = new ArrayList<Integer>();
        int count = 0;
        for (int[] pos : positions) {
            int i = pos[0], j = pos[1];
            if (grid[i][j] == 1) {
                ans.add(count);
                continue;
            }

            grid[i][j] = 1;
            count++;
            int p1 = findPar(i * m + j);
            for (int[] d : dir) {
                int r = i + d[0];
                int c = j + d[1];

                if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] == 1) {
                    int p2 = findPar(r * m + c);
                    if (p1 != p2) {
                        count--;
                        par[p2] = p1;
                    }
                }
            }

            ans.add(count);
        }
        return ans;
    }

    // without using grid
    public List<Integer> numIslands2(int n, int m, int[][] positions) {
        par = new int[n * m];
        Arrays.fill(par, -1);
        List<Integer> ans = new ArrayList<Integer>();
        int count = 0;
        for (int[] pos : positions) {
            int i = pos[0], j = pos[1];
            if (par[i * m + j] != -1) {
                ans.add(count);
                continue;
            }

            par[i * m + j] = (i * m + j);
            count++;

            for (int[] d : dir) {
                int r = i + d[0];
                int c = j + d[1];

                if (r >= 0 && r < n && c >= 0 && c < m && par[r * m + c] != -1) {
                    int p1 = findPar(i * m + j);
                    int p2 = findPar(r * m + c);
                    if (p1 != p2) {
                        count--;
                        par[p1] = p2;
                    }
                }
            }

            ans.add(count);
        }
        return ans;
    }





}
