import java.awt.*;

/**
 * Represents the methods of maintaining a red-black tree
 * Insertion, deletion, and addition of nodes. As well as rotations.
 *
 *
 * -- Layout of Red-Black Tree implementation. Writing this mainly for my own sake of organization. The insertion methods got quite long.
 * Field values
 * Constructor
 * Insert, Search, Delete Methods
 * Rotation methods, left rotate, right rotate
 * Helper methods, Delete, Insertion, Search
 * Find minimum method.
 * getRoot method
 *
 *
 *
 * @author Bryce Dunlap
 * @version 1.0
 * @since 4/15/2024
 */
public class RedBlackTree {
    private Node root;
    private Node NIL;


    /**
     * Leaf node constructor
     */
    public RedBlackTree()
    {
        NIL = new Node();
        NIL.left = null;
        NIL.right = null;
        NIL.color = Color.BLACK;
        root = NIL;

    }

    /**
     * Insertion of node - needs a helper function to balance the tree after implementing incase the parent node is also red.
     *
     */
    public void insertNode(int dataToEnter)
    {
        //setting up node to be inserted
        Node node = new Node();
        node.data = dataToEnter;
        node.parent = null;
        node.left = NIL;
        node.right = NIL;
        node.color = Color.RED;

        Node previousNode = null;
        Node currentNode = this.root; //starts at root.

        // will navigate as long as we are not on a NIL (leaf node) - starting from root
        while (currentNode != NIL)
        {
            previousNode = currentNode;

            //navigate left
            if(node.data < currentNode.data)
            {
                currentNode = currentNode.left;
            }
            //navigate right
            else
            {
                currentNode = currentNode.right;
            }

        }
        // After exiting the loop, 'previousNode' is the node under which the new node should be attached.
        node.parent = previousNode;
        if(previousNode == null)
        {
            root = node; // If the tree was empty then the new node becomes the root.
        }
        else if (node.data < previousNode.data)
        {
            previousNode.left = node;
        }
        else
        {
            previousNode.right = node;
        }

        if(node.parent == null)
        {
            node.color = Color.BLACK;
            return;
        }
        if(node.parent.parent == null)
        {
            return;
        }
        insertionHelper(node);

    }
    public Node SearchTree(int number)
    {
        // start the search from the root.
        return SearchTreeHelperMethod(this.root, number);
    }

    public void deleteNode (int data)
    {
        deleteNodeHelperMethod(this.root, data);
    }


    //Rotation methods
    //left rotate
    public void leftRotate(Node currentNode){
        Node newParent = currentNode.right;
        currentNode.right = newParent.left;
        if(newParent.left != NIL)
        {
            newParent.left.parent = currentNode;
        }
        newParent.parent = currentNode.parent;
        if(currentNode.parent == null)
        {
            this.root = newParent;
        }
        else if(currentNode == currentNode.parent.left)
        {
            currentNode.parent.left = newParent;
        }
        else
        {
            currentNode.parent.right = newParent;
        }
        newParent.left = currentNode;
        currentNode.parent = newParent;
    }

    //right rotate
    public void rightRotate(Node currentNode)
    {
        Node newParent = currentNode.left;
        currentNode.left = newParent.right;
        if(newParent.right != NIL)
        {
            newParent.right.parent = currentNode;
        }
        newParent.parent = currentNode.parent;
        if(currentNode.parent == null)
        {
            this.root = newParent;
        }
        else if(currentNode == currentNode.parent.right)
        {
            currentNode.parent.right = newParent;
        }
        else
        {
            currentNode.parent.left = newParent;
        }
        newParent.right = currentNode;
        currentNode.parent = newParent;
    }


    // fixer methods, to rebalnce tree
    private void fixDelete(Node currentNode) {
        Node siblingNode;
        while (currentNode != root && currentNode.color == Color.BLACK) {
            if (currentNode == currentNode.parent.left) {
                siblingNode = currentNode.parent.right;
                if (siblingNode.color == Color.RED) {
                    siblingNode.color = Color.BLACK;
                    currentNode.parent.color = Color.RED;
                    leftRotate(currentNode.parent);
                    siblingNode = currentNode.parent.right;
                }

                if (siblingNode.left.color == Color.BLACK && siblingNode.right.color == Color.BLACK) {
                    siblingNode.color = Color.RED;
                    currentNode = currentNode.parent;
                } else {
                    if (siblingNode.right.color == Color.BLACK) {
                        siblingNode.left.color = Color.BLACK;
                        siblingNode.color = Color.RED;
                        rightRotate(siblingNode);
                        siblingNode = currentNode.parent.right;
                    }

                    siblingNode.color = currentNode.parent.color;
                    currentNode.parent.color = Color.BLACK;
                    siblingNode.right.color = Color.BLACK;
                    leftRotate(currentNode.parent);
                    currentNode = root;
                }
            } else {
                siblingNode = currentNode.parent.left;
                if (siblingNode.color == Color.RED) {
                    siblingNode.color = Color.BLACK;
                    currentNode.parent.color = Color.RED;
                    rightRotate(currentNode.parent);
                    siblingNode = currentNode.parent.left;
                }

                if (siblingNode.right.color == Color.BLACK && siblingNode.right.color == Color.BLACK) {
                    siblingNode.color = Color.RED;
                    currentNode = currentNode.parent;
                }
                else
                {
                    if (siblingNode.left.color == Color.BLACK)
                    {
                        siblingNode.right.color = Color.BLACK;
                        siblingNode.color = Color.RED;
                        leftRotate(siblingNode);
                        siblingNode = currentNode.parent.left;
                    }

                    siblingNode.color = currentNode.parent.color;
                    currentNode.parent.color = Color.BLACK;
                    siblingNode.left.color = Color.BLACK;
                    rightRotate(currentNode.parent);
                    currentNode = root;
                }
            }
        }
        currentNode.color = Color.BLACK;
    }

    //helper function to balance tree after insertion
    // bottom up. starting from insertion point up.
    private void insertionHelper(Node newNode) {
        Node uncleNode;

        // Loop until the tree is balanced or root reached...
        // as long as parent of the new node is red.
        while (newNode.parent.color == Color.RED)
        {
            // this if condition checks if the newNodes parent is on the right
            if(newNode.parent == newNode.parent.parent.right)
            {
                uncleNode = newNode.parent.parent.left; // sets the uncle node now that we know parent node is on the right
                // now this if condition is primarily the reason we need fixing because a parent node that is red cannot have children nodes that are red
                if(uncleNode.color == Color.RED)
                {
                    uncleNode.color = Color.BLACK;
                    newNode.parent.color = Color.BLACK;
                    newNode.parent.parent.color = Color.RED;
                    newNode = newNode.parent.parent;
                }

                else // if uncleNode is BLACK
                {
                    // condition checks if newNode is on the left - rotates if so.
                    if(newNode == newNode.parent.left)
                    {
                        newNode = newNode.parent;
                        rightRotate(newNode);
                    }
                    newNode.parent.color = Color.BLACK;
                    newNode.parent.parent.color = Color.RED;
                    leftRotate(newNode.parent.parent);
                }
            }

            // checks if parent of the newNode is on the left
            else
            {
                uncleNode = newNode.parent.parent.right; // since we know the parent node is on the left; assign its brother to the right
                // traverse up the tree if red
                if(uncleNode.color == Color.RED)
                {
                    uncleNode.color = Color.BLACK;
                    newNode.parent.color = Color.BLACK;
                    newNode.parent.parent.color = Color.RED;
                    newNode = newNode.parent.parent;
                }
                else // if the uncle node is black
                {
                    // checks to make sure the newNode is on the right to perform a left rotate
                    if(newNode == newNode.parent.right)
                    {
                        newNode = newNode.parent;
                        leftRotate(newNode);
                    }
                    newNode.parent.color = Color.BLACK;
                    newNode.parent.parent.color = Color.RED;
                    rightRotate(newNode.parent.parent);
                }
            }

            // exits the loop if the new node is at the root.
            if(newNode == root)
            {
                break;
            }
        }
        // gota make sure the root is always a black node.
        root.color = Color.BLACK;
    }


    // Helper method for searching
    private Node SearchTreeHelperMethod(Node node, int data)
    {
        if(node == NIL || data == node.data)
        {
            return node;
        }

        // if data is less than the current node then move left, otherwise move right
        if(data < node.data)
        {
            return SearchTreeHelperMethod(node.left, data);
        }
        return SearchTreeHelperMethod(node.right, data);
    }

    private void rbTransplant(Node originalNode, Node newNode) {
        if (originalNode.parent == null) {
            root = newNode;
        }
        else if (originalNode == originalNode.parent.left)
        {
            originalNode.parent.left = newNode;
        }
        else
        {
            originalNode.parent.right = newNode;
        }
        newNode.parent = originalNode.parent;
    }

    private void deleteNodeHelperMethod(Node node, int data)
    {
        Node nodeToDelete = NIL;
        Node adjustmentNode, replacementChild;
        while (node != NIL)
        {
            if (node.data == data)
            {
                nodeToDelete = node;
            }

            if (node.data <= data)
            {
                node = node.right;
            }
            else
            {
                node = node.left;
            }
        }

        if (nodeToDelete == NIL) {
            throw new IllegalArgumentException("Number entered not found.");
        }

        adjustmentNode = nodeToDelete;
        Color originalColor = adjustmentNode.color;
        if (nodeToDelete.left == NIL)
        {
            replacementChild = nodeToDelete.right;
            rbTransplant(nodeToDelete, nodeToDelete.right);
        }
        else if (nodeToDelete.right == NIL)
        {
            replacementChild = nodeToDelete.left;
            rbTransplant(nodeToDelete, nodeToDelete.left);
        }
        else
        {
            adjustmentNode = min(nodeToDelete.right);
            originalColor = adjustmentNode.color;
            replacementChild = adjustmentNode.right;
            if (adjustmentNode.parent == nodeToDelete)
            {
                replacementChild.parent = adjustmentNode;
            }
            else
            {
                rbTransplant(adjustmentNode, adjustmentNode.right);
                adjustmentNode.right = nodeToDelete.right;
                adjustmentNode.right.parent = adjustmentNode;
            }

            rbTransplant(nodeToDelete, adjustmentNode);
            adjustmentNode.left = nodeToDelete.left;
            adjustmentNode.left.parent = adjustmentNode;
            adjustmentNode.color = nodeToDelete.color;
        }
        if (originalColor == Color.BLACK)
        {
            fixDelete(replacementChild);
        }
    }


    public Node min(Node node) {
        while (node.left != NIL)
        {
            node = node.left;
        }
        return node;
    }

    //getter - not sure if I will use this or not
    public Node getRoot()
    {
        return this.root;
    }




}
