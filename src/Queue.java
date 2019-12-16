/*
 * Jerry Tian
 * ICS4U1
 * Dec.15, 2019
 * For Mr.Radulovic
 * ADT assignment
 * 
 *  This class contains information and functions for a link queue(dynamic).
 *  A queue is like an arraylist that its size is dynamic 
 *  but each element has a pointer that point to the previous element or the next element
 */
public class Queue<T>{

	private Node<T> first = new MyNode<T>(null);
	// The node first represents the head of the queue.
	// The node itself is null and it acts as a pointer 
	// and it points to the first element in the queue. 
	private Node<T> last = new MyNode<T>(null);
	// The node last represents the tail of the queue.
		// The node itself is null and it acts as a pointer 
		// and it points to the last element in the queue. 
	private int size = 0;

	public Queue() {
		first.setNext(last);// point the node first to the node last 
		last.setPrev(first);// point the node last to the node first
		// the queue now becomes:  first -- last
	}

	public void enqueue(Node<T> n) {
		Node<T> temp = last.getPrev();
		
		last.setPrev(n);
		// set the previous element of last to n
		n.setNext(last);
		// set the next element of n to last
		n.setPrev(temp); 
		temp.setNext(n);
		size++;
	}

	public Node<T> dequeue() {
		
		Node<T> n = first.getNext();
		// get the first node in the queue
		Node<T> newfirst =  n.getNext();
		// get the second node in the queue
		first.setNext(newfirst);
		// set the second node as the first node in the queue(skip the first node)
		newfirst.setPrev(first);
		// set the previous node of the second node to first.
		size--;
		return n;
	}

	public Node<T> peek() {
		return first.getNext();
	}

	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		if(size == 0)
			return true;
		else
			return false;
	}
}