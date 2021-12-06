package api;/*
 *
 * @project Ex2
 * @auther Renana Rimon
 */

/* Fibonacci Heap Node **/
class FibonacciHeapNode
{
    FibonacciHeapNode child, left, right, parent;
    int element;

    /** Constructor **/
    public FibonacciHeapNode(int element)
    {
        this.right = this;
        this.left = this;
        this.element = element;
    }
}
