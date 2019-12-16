/*
 * Jerry Tian
 * ICS4U1
 * Dec.15, 2019
 * For Mr.Radulovic
 * ADT assignment
 * 
 *  This interface provides methods for MyNode class.
 */
public interface Node<T> {

	
	public T getValue();
	
	public void setValue(T n);
	
	/*
	 * Sets the next node reference
	 */
	public void setNext(Node<T> n);
	
	/*
	 * Sets the previous node reference
	 */
	public void setPrev(Node<T> n);
	
	/*
	 * Returns a reference to the next node
	 */
	public Node<T> getNext();
	
	/*
	 * Returns a reference to the previous node
	 */
	public Node<T> getPrev();
	
	public String toString();

}