#include <iostream>
#include <vector>
#include <queue>

using namespace std;

//200
void dfs_numIslands(vector<vector<char>> &grid, int i, int j, vector<vector<int>> &dir)
{
    int n = grid.size(), m = grid[0].size();
    grid[i][j] = '0';
    for (int d = 0; d < dir.size(); d++)
    {
        int r = i + dir[d][0];
        int c = j + dir[d][1];

        if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == '1')
            dfs_numIslands(grid, r, c, dir);
    }
}

int numIslands(vector<vector<char>> &grid)
{
    vector<vector<int>> dir{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    int n = grid.size(), m = grid[0].size(), components = 0;

    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < m; j++)
        {
            if (grid[i][j] == '1')
            {
                components++;
                dfs_numIslands(grid, i, j, dir);
            }
        }
    }

    return components;
}

int dfs_area(vector<vector<int>> &grid, int i, int j, vector<vector<int>> &dir)
{
    int n = grid.size(), m = grid[0].size(), size = 0;
    grid[i][j] = 0;
    for (int d = 0; d < dir.size(); d++)
    {
        int r = i + dir[d][0];
        int c = j + dir[d][1];

        if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 1)
            size += dfs_area(grid, r, c, dir);
    }

    return size + 1;
}

//695
int maxAreaOfIsland(vector<vector<int>> &grid)
{
    vector<vector<int>> dir{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    int n = grid.size(), m = grid[0].size(), maxArea = 0;

    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < m; j++)
        {
            if (grid[i][j] == 1)
            {
                int area = dfs_area(grid, i, j, dir);
                maxArea = max(area, maxArea);
            }
        }
    }

    return maxArea;
}

//463
int islandPerimeter(vector<vector<int>> &grid)
{
    vector<vector<int>> dir{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    int n = grid.size(), m = grid[0].size(), onceCount = 0, nbrCount;

    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < m; j++)
        {
            if (grid[i][j] == 1)
            {
                onceCount++;
                for (int d = 0; d < dir.size(); d++)
                {
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];

                    if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 1)
                        nbrCount++;
                }
            }
        }
    }

    return 4 * onceCount - nbrCount;
}


//130
void dfs_surrounded(vector<vector<char>> &grid, int i, int j, vector<vector<int>> &dir)
{
    int n = grid.size(), m = grid[0].size();
    grid[i][j] = '$';
    for (int d = 0; d < dir.size(); d++)
    {
        int r = i + dir[d][0];
        int c = j + dir[d][1];

        if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 'O')
        {
            dfs_surrounded(grid, r, c, dir);
        }
    }
}

void solve(vector<vector<char>> &grid)
{
    vector<vector<int>> dir{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    int n = grid.size(), m = grid[0].size();

    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < m; j++)
        {
            if (i == 0 || j == 0 || i == n - 1 || j == m - 1)
            {
                if (grid[i][j] == 'O')
                    dfs_surrounded(grid, i, j, dir);
            }
        }
    }

    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < m; j++)
        {
            if (grid[i][j] == 'O')
                grid[i][j] = 'X';
            else if (grid[i][j] == '$')
                grid[i][j] = 'O';
        }
    }
}

//694

// Journey to the Moon
int dfs(int src, vector<vector<int>> &graph, vector<bool> &vis)
{
    int size = 1;
    vis[src] = true;
    for (int v : graph[src])
    {
        if (!vis[v])
            size += dfs(v, graph, vis);
    }

    return size;
}

long journeyToMoon(int n, vector<vector<int>> edges)
{
    vector<vector<int>> graph(n);
    for (vector<int> &e : edges)
    {
        graph[e[0]].push_back(e[1]);
        graph[e[1]].push_back(e[0]);
    }

    vector<bool> vis(n, false);
    long sum = 0, ans = 0;
    for (int i = 0; i < n; i++)
    {
        if (!vis[i])
        {
            int size = dfs(i, graph, vis);
            ans += size * sum;
            sum += size;
        }
    }

    return ans;
}

//210 
bool dfs_isCycle(int src, vector<int> &vis, vector<int> &ans, vector<vector<int>> &graph)
{
    vis[src] = 1;
    bool isCycle = false;
    for (int v : graph[src])
    {
        if (vis[v] == 0)
        {
            isCycle = isCycle || dfs_isCycle(v, vis, ans, graph);
        }
        else if (vis[v] == 1)
            return true;
    }

    ans.push_back(src);
    vis[src] = 2;
    return isCycle;
}

vector<int> findOrder(int N, vector<vector<int>> &prerequisites)
{
    vector<vector<int>> graph(N);
    for (vector<int> &ar : prerequisites)
    {
        graph[ar[0]].push_back(ar[1]);
    }

    vector<int> ans;
    vector<int> vis(N, 0);

    bool cycle = false;
    for (int i = 0; i < N; i++)
    {
        if (vis[i] == 0)
        {
            cycle = cycle || dfs_isCycle(i, vis, ans, graph);
        }
    }

    if (cycle)
        ans.clear();

    return ans;
}


