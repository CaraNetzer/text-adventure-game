
public class Node<K,V> {
    Node<K,V> next;
    K key;
    V value;
    
    public Node(K key, V value) {
    	this.key=key;
        this.value = value;
    }
    
    public String toString() {
    	return this.key.toString();
    }
    
    public static void main(String[] args) {
		Node<String, Integer> node = new Node<String, Integer>("Cat", 17);
		System.out.println(node.key == "Cat");
		System.out.println(node.value == 17);
		System.out.println(node);
	}

	
}
