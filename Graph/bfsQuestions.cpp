#include <iostream>
#include <vector>
#include <queue>

using namespace std;

//994
int orangesRotting(vector<vector<int>> &grid)
{
    int n = grid.size(), m = grid[0].size();
    queue<int> que;

    int time = 0, orangeCount = 0;
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < m; j++)
        {
            if (grid[i][j] == 2)
                que.push(i * m + j);
            else if (grid[i][j] == 1)
                orangeCount++;
        }
    }

    if (orangeCount == 0)
        return 0;

    vector<vector<int>> dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    while (que.size() != 0)
    {
        int size = que.size();
        while (size-- > 0)
        {
            int idx = que.front();
            que.pop();

            int sr = idx / m, sc = idx % m;

            for (vector<int> &d : dir)
            {
                int r = sr + d[0];
                int c = sc + d[1];

                if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 1)
                {
                    grid[r][c] = 2;
                    que.push(r * m + c);
                    orangeCount--;
                    if (orangeCount == 0)
                        return time + 1;
                }
            }
        }
        time++;
    }

    return -1;
}



//207
bool canFinish(int N, vector<vector<int>> &prerequisites)
{
    vector<vector<int>> graph(N);
    vector<int> indegree(N, 0);

    for (vector<int> &ar : prerequisites)
    {
        graph[ar[0]].push_back(ar[1]);
        indegree[ar[1]]++;
    }

    queue<int> que;
    for (int i = 0; i < N; i++)
        if (indegree[i] == 0)
            que.push(i);

    int vtxCount = 0;
    while (que.size() != 0)
    {
        int vtx = que.front();
        que.pop();
        vtxCount++;
        for (int v : graph[vtx])
        {
            if (--indegree[v] == 0)
                que.push(v);
        }
    }

    return vtxCount == N;
}

//210
vector<int> findOrder(int N, vector<vector<int>> &prerequisites)
{
    vector<vector<int>> graph(N);
    vector<int> indegree(N, 0);

    for (vector<int> &ar : prerequisites)
    {
        graph[ar[1]].push_back(ar[0]);
        indegree[ar[0]]++;
    }

    queue<int> que;
    for (int i = 0; i < N; i++)
        if (indegree[i] == 0)
            que.push(i);

    vector<int> ans;
    while (que.size() != 0)
    {
        int vtx = que.front();
        que.pop();
        ans.push_back(vtx);
        for (int v : graph[vtx])
        {
            if (--indegree[v] == 0)
                que.push(v);
        }
    }

    if (ans.size() != N)
        ans.clear();

    return ans;
}

//329
int longestIncreasingPath(vector<vector<int>> &matrix)
{
    int n = matrix.size(), m = matrix[0].size();
    vector<vector<int>> indegree(n, vector<int>(m, 0));

    vector<vector<int>> dir{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    queue<int> que;
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < m; j++)
        {
            for (vector<int> &d : dir)
            {
                int r = i + d[0];
                int c = j + d[1];
                if (r >= 0 && c >= 0 && r < n && c < m && matrix[r][c] < matrix[i][j])
                {
                    indegree[i][j]++;
                }
            }

            if (indegree[i][j] == 0)
                que.push(i * m + j);
        }
    }

    int level = 0;
    while (que.size() != 0)
    {
        int size = que.size();
        while (size-- > 0)
        {
            int idx = que.front();
            que.pop();
            int i = idx / m, j = idx % m;

            for (vector<int> &d : dir)
            {
                int r = i + d[0];
                int c = j + d[1];
                if (r >= 0 && c >= 0 && r < n && c < m && matrix[r][c] > matrix[i][j] && --indegree[r][c] == 0)
                    que.push(r * m + c);
            }
        }
        level++;
    }

    return level;
}