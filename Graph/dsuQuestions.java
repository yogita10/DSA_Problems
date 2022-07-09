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

    public String smallestEquivalentString(String s1, String s2, String baseStr){
        par = new int[26];
        for (int i = 0; i < 26; i++)
            par[i] = i;
        for (int i = 0; i < s1.length(); i++)
        {
            char c1 = s1.charAt(i), c2 = s2.charAt(i);
            int p1 = findPar(c1 - 'a'), p2 = findPar(c2 - 'a');
            par[p1] = Math.min(p1, p2);
            par[p2] = Math.min(p1, p2);
        }

        String ans = "";
        char[] basestr = baseStr.toCharArray();
        for (char ch : basestr)
        {
            int p = findPar(ch - 'a');
            ans += (char)(p + 'a');
        }

        return ans;
    }
}
