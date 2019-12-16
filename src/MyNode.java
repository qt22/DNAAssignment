/*
 * Jerry Tian
 * ICS4U1
 * Dec.15, 2019
 * For Mr.Radulovic
 * ADT assignment
 * 
 *  This class contains information(like value, next, last) for  node.
 */
public class MyNode<T> implements Node<T>{
	private T data;
	Node<T> next;
	Node<T> prev;

	public MyNode(T n) {
		data = n;
	}
	public T getValue() {
		return data;
	}
	@Override
	public void setValue(T n) {
		data  = n;
		
	}
	@Override
	public void setNext(Node<T> n) {
		next = n;
		
	}
	@Override
	public void setPrev(Node<T> n) {
		prev = n;
		
	}
	@Override
	public Node<T> getNext() {
		return next;
	}
	@Override
	public Node<T> getPrev() {
		return prev;
	}
	
	
}