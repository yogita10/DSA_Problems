import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class l002BST {

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static int size(TreeNode root) {
        return root == null ? 0 : size(root.left) + size(root.right) + 1;
    }

    public static int height(TreeNode root) {
        return root == null ? -1 : Math.max(height(root.left), height(root.right)) + 1;
    }


    public static int maximum(TreeNode root)
    {
        TreeNode curr = root;
        while (curr.right != null)
            curr = curr.right;

        return curr.val;
    }

    public static int minimum(TreeNode root)
    {
        TreeNode curr = root;
        while (curr.left != null)
            curr = curr.left;

        return curr.val;
    }

    public static boolean findData(TreeNode root, int data)
    {
        TreeNode curr = root;
        while (curr != null)
        {
            if (curr.val == data)
                return true;
            else if (curr.val < data)
                curr = curr.right;
            else
                curr = curr.left;
        }

        return false;
    }

    public static ArrayList<TreeNode > nodeToRootPath(TreeNode root, int data)
    {
        TreeNode curr = root;
        ArrayList<TreeNode > ans=new ArrayList<>();
        while (curr != null)
        {
            ans.add(curr);
            if (curr.val == data)
                break;
            else if (curr.val < data)
                curr = curr.right;
            else
                curr = curr.left;
        }

        return ans;
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q)
    {
        TreeNode LCA = null, curr = root;
        while (curr != null)
        {
            if (curr.val < p.val && curr.val < q.val)
                curr = curr.right;
            else if (curr.val > p.val && curr.val > q.val)
                curr = curr.left;
            else
            {
                LCA = curr;
                break;
            }
        }

        return (LCA != null && findData(LCA, p.val) && findData(LCA, q.val)) ? LCA : null;
    }


    public static TreeNode getLeftMost(TreeNode root) {
        if (root == null)
            return null;

        while (root.left != null) {
            root = root.left;
        }

        return root;
    }

    public static TreeNode getRightMost(TreeNode root) {
        if (root == null)
            return null;

        while (root.right != null) {
            root = root.right;
        }

        return root;
    }

    // ceil and floor -> T : O(logN), S : O(1)
    public static void predSucc(TreeNode root, int data) {
        TreeNode curr = root, succ = null, pred = null;

        while (curr != null) {
            if (curr.val == data) {

                TreeNode leftMost = getLeftMost(curr.right);
                succ = leftMost != null ? leftMost : succ;

                TreeNode rightMost = getRightMost(curr.left);
                pred = rightMost != null ? rightMost : pred;
                break;

            } else if (curr.val < data) {
                pred = curr;
                curr = curr.right;
            } else {
                succ = curr;
                curr = curr.left;
            }
        }
    }

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);

        if (root.val < val)
            root.right = insertIntoBST(root.right, val);
        else
            root.left = insertIntoBST(root.left, val);

        return root;
    }

    public int getMin(TreeNode root) {

        while (root.left != null)
            root = root.left;
        return root.val;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null)
            return null;

        if (root.val < key)
            root.right = deleteNode(root.right, key);
        else if (root.val > key)
            root.left = deleteNode(root.left, key);
        else {
            if (root.left == null || root.right == null) {
                TreeNode rNode = root.left != null ? root.left : root.right;
                root.left = root.right = null;
                // delete root;
                return rNode;
            }

            int minEle = getMin(root.right);
            root.val = minEle;

            root.right = deleteNode(root.right, minEle);

        }

        return root;
    }

    // Home work : insert node and delete node -> T : O(LogN), S : O(1) (Iterative
    // Solution)
    // 510. Inorder Successor in BST II





    //1382
    int[] height; 
    void updateHeight(TreeNode root)
    {
        int lh = root.left != null ? height[root.left.val] : -1;
        int rh = root.right != null ? height[root.right.val] : -1;

        height[root.val] = Math.max(lh, rh) + 1;
    }

    int getBal(TreeNode root)
    {
        int lh = root.left != null ? height[root.left.val] : -1;
        int rh = root.right != null ? height[root.right.val] : -1;

        return lh - rh;
    }

    TreeNode rightRotation(TreeNode A)
    {
        TreeNode B = A.left;
        TreeNode BkaRight = B.right;

        B.right = A;
        A.left = BkaRight;

        B.right = getRotation(A);
        return getRotation(B);
    }

    //O(1)
    TreeNode leftRotation(TreeNode A)
    {
        TreeNode B = A.right;
        TreeNode BkaLeft = B.left;

        B.left = A;
        A.right = BkaLeft;

        B.left = getRotation(A);
        return getRotation(B);
    }

    TreeNode getRotation(TreeNode root)
    {

        updateHeight(root);
        if (getBal(root) >= 2) //ll,lr
        {
            if (getBal(root.left) >= 1) // ll
            {
                return rightRotation(root);
            }
            else // lr
            {
                root.left = leftRotation(root.left);
                return rightRotation(root);
            }
        }
        else if (getBal(root) <= -2) // rr,rl
        {

            if (getBal(root.right) <= -1) // rr
            {
                return leftRotation(root);
            }
            else // rl
            {
                root.right = rightRotation(root.right);
                return leftRotation(root);
            }
        }

        return root;
    }

    TreeNode reconstructTree(TreeNode root)
    {
        if (root == null)
            return null;

        root.left = reconstructTree(root.left);
        root.right = reconstructTree(root.right);

        return getRotation(root);
    }

    // T: O(nlogn) ,  S:O(1)    ->  AVL or Morris both are valid 
    //here we have done thorugh AVL  
    public TreeNode balanceBST(TreeNode root) {
        // height.resize((int)1e5 + 1, -1); 
        int n = (int)1e5 + 1;
        height = new int[n];
        return reconstructTree(root);
    }

}
