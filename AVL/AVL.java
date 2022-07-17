import java.util.*;

public class AVL {

    public static class TreeNode{
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
        int height = 0;
        int bal = 0;
        TreeNode(int val)
        {
            this.val = val;
        }
    }

    //O(1)
    public static void updateHeightandBalance(TreeNode root)
    {
        int lh = root.left!=null ? root.left.height : -1;
        int rh = root.right!=null ? root.right.height : -1;

        int bal = lh-rh;
        root.bal = bal;
        root.height = Math.max(lh,rh) + 1;
    }

    //O(1)
    public static TreeNode leftRotation(TreeNode A)
    {
        TreeNode B = A.left;
        TreeNode BkaRight = B.right;

        B.right = A;
        A.left = BkaRight;
        updateHeightandBalance(A);
        updateHeightandBalance(B);
        return B;
    }

    //O(1)
    public static TreeNode rightRotation(TreeNode A)
    {
        TreeNode B = A.right;
        TreeNode Bkaleft = B.left;

        B.left = A;
        A.right = Bkaleft;
        updateHeightandBalance(A);
        updateHeightandBalance(B);
        return B;
    }

    public static TreeNode getRotation(TreeNode root)
    {
        updateHeightandBalance(root);
        if(root.bal == 2) // ll lr
        {
            if(root.left.bal == 1)//ll
            {
                return rightRotation(root);
            }
            else//lr
            {
                root.left = leftRotation(root.left);
                return rightRotation(root);
            }

        }
        else if(root.bal == -2) // rr rl
        {
            if(root.left.bal == -1)//rr
            {
                return leftRotation(root);
            }
            else//rl
            {
                root.right = rightRotation(root.right);
                return leftRotation(root);
            }
        }
        return root;
    }

    public static TreeNode addData(TreeNode root,int data)
    {
        if(root == null)
            return new TreeNode(data);
        
        if(data<root.val)
        {
            root.left = addData(root.left,data);
        }
        else
        {
            root.right = addData(root.right,data);
        }
        return getRotation(root);
    }

    public static int getMin(TreeNode root) {
        while (root.left != null)
            root = root.left;
        return root.val;
    }

    public static TreeNode deleteData(TreeNode root,int data)
    {
        if(root == null)
            return null;
        
        if(data < root.val)
        {
            root.left = deleteData(root.left,data);
        }
        else if(data > root.val)
        {
            root.right = deleteData(root.right,data);
        }
        else
        {
            if(root.left == null || root.right == null)
                return root.left!=null?root.left:root.right;
            
            int min = getMin(root.right);
            root.val = min;
            root.right = deleteData(root.right, min);
        }
        return getRotation(root);
    }

    public static void display(TreeNode node) {
        if (node == null)
            return;

        StringBuilder sb = new StringBuilder();
        sb.append((node.left != null ? node.left.val : "."));
        sb.append(" -> " + node.val + " <- ");
        sb.append((node.right != null ? node.right.val : "."));
        System.out.println(sb.toString());
        display(node.left);
        display(node.right);
    }

    public static void main(String[]args)
    {
        // addData(new TreeNode(8),3);
        // display(addData(new TreeNode(8),5));
    }
}