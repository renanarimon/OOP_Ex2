package api;/*
 *
 * @project Ex2
 * @auther Renana Rimon
 */
class FibonacciHeap
{
    private FibonacciHeapNode root;
    private int count;

    /** Constructor **/
    public FibonacciHeap()
    {
        root = null;
        count = 0;
    }

    /** Check if heap is empty **/
    public boolean isEmpty()
    {
        return root == null;
    }

    /** Make heap empty **/
    public void clear()
    {
        root = null;
        count = 0;
    }

    /** Function to insert **/
    public void insert(int element)
    {
        FibonacciHeapNode node = new FibonacciHeapNode(element);
        node.element = element;

        if (root != null)
        {
            node.left = root;
            node.right = root.right;
            root.right = node;
            node.right.left = node;
            if (element < root.element)
                root = node;
        }
        else
            root = node;
        count++;
    }

    /** function to display **/
    public void display()
    {
        System.out.print("\nHeap = ");
        FibonacciHeapNode ptr = root;
        if (ptr == null)
        {
            System.out.print("Empty\n");
            return;
        }
        do
        {
            System.out.print(ptr.element +" ");
            ptr = ptr.right;
        } while (ptr != root && ptr.right != null);
        System.out.println();
    }
}

