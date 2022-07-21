public class LinkedList<K,V>{
    Node<K,V> head;
    Node<K,V> tail;
    int length;
    
    public LinkedList(){
    	this.head = null;
    	this.tail = null;
    }
    
    public LinkedList(Node<K, V> node){
    	this.head = node;
    	this.tail = node;
    }
    
    public Node<K, V> retrieve(K item) {
		Node<K, V> n = head;
        while (n != null) {
        	if (n.key.equals(item)) {
        		return n;
        	}
        	n = n.next;
        }
		return null;
	}
    
    int length(){//returns how many nodes are in list
        if(head == null && tail == null) {
        	return 0;
        } else {
            int tmp = 0;
            Node<K,V> tmpList = head;
            while(tmpList != null){
                tmp++;
                tmpList = tmpList.next;
            }
            return tmp;
        }
    }
    
    void append(Node<K, V> node){ //takes a node and adds it to the end of the list
            if(head == null && tail == null){
            	head = node;
            	tail = node;
            } else {
            	Node<K,V> current = head;
            	while(current.next != null){
            		current = current.next;
            	}
            	current.next = node;
            	tail = node;
            }
            length++;
    }
    
    public void appendEnd(Node<K, V> node) {      
        if(tail == null && head == null) {
        	tail = node;
        	head = node;
        	tail.next = null;
        	head.next = null;
        } else {
        	tail.next = node;
        	tail = node;
        }
        length++;
    }
    
    public boolean delete(Node<K, V> node) { //deletes a node, returns true if value is removed
        boolean removed = false;
        Node<K, V> n = head;
        Node<K, V> prev = null;
        while (n != null) {
            if (n.key.equals(node.key)) {
                if (prev == null) {
                    head = n.next;
                    removed = true;
                } else if(tail.next == null) {
                    prev.next = n.next;
                    removed = true;
                    tail = prev;
                } else {
                	prev.next = n.next;
                    removed = true;
                }
                length--;
                break;
            }
            prev = n;
            n = n.next;
        }
        return removed;
    } 
    
    public boolean isEmpty() {
    	//return (this.tail == null) && (this.head == null);
    	return length == 0;
    }
    
    public V getHead() { //type is V because the keys are names and the values are states/items/commands
    	return head.value;
    }
    
    public V getTail() {
    	return tail.value;
    }
    
    void insert(Node<K,V>node){
    	 if(head == null && tail == null){
         	head = node;
         	tail = node;
         } else {
        	 node.next = head;
        	 head = node;
         }
    	 length++;
    }  
    
    boolean contains(K key){
    	Node<K,V> node = head;
    	while(node != null){
    		if(node.key.equals(key)){
    			return true;
    		} else {
    			node = node.next;
    		}
    	}
    	System.out.println(key + " is not in the list");
    	return false;
    }
    
	boolean delete(K key){ //deletes key
		Node<K,V> current = head;
		Node<K,V> previous = null;
		while(current != null){
			if(current.key.equals(key)){
				if(previous == null){
					head = current.next;
				} else {
					previous.next = current.next;
				}
				length--;
				return true;
				
			}
			previous = current;
			current = current.next;
		}
		return false;
	}
	
    public void appendFront(Node<K, V> node) { 
        if (head == null && tail == null) { //if there are zero nodes
            head = node;
            tail = node;
        } else { //if there is one or more nodes
            node.next = head;
            head = node; //resets the beginning of the list to the inserted node
        }
        length++;
    }
    
    public V popFront() {
    	V front = this.getHead();
    	this.delete(head); //remove decreases the length
    	if (this.length == 0) {
    		tail = null;
    	}
    	return front;
    }
    
    public V popEnd() {
    	V end = this.getTail();
    	this.delete(tail);
    	return end;
    }
	
	
    public String solutionToString() {
    Node<K, V> n = this.head;
        String answer = "";
        while (n != null) {
            answer += n.key + ", ";
            n = n.next;
        }
        return answer;
    }
	
    public String toString() {
        Node<K, V> n = this.head;
        String answer = "";
        while (n != null) {
            answer += n.key.toString() + " : " + n.value.toString() + ", ";
            n = n.next;
        }
        return answer;
    }
    
	public static void main(String[] args) {
    	LinkedList<String, Integer> ll = new LinkedList<String, Integer>();
		Node<String, Integer> node1 = new Node<String, Integer>("hello", 17);
		System.out.println(ll.length == 0);
		ll.appendEnd(node1);
		System.out.println(ll.retrieve("hello"));
		System.out.println(ll.length == 1);
		System.out.println(ll.contains("hello") == true);
		Node<String, Integer> node2 = new Node<String,Integer>("world", 18);
		ll.appendEnd(node2);
		System.out.println(ll.retrieve("world"));
		System.out.println(ll.length == 2);
		System.out.println(ll);
		System.out.println(ll.delete(node1) == true);
		System.out.println(ll.length == 1);
		System.out.println(ll.contains("hello") == false);
		System.out.println(ll.contains("world") == true);
		
		ll.appendFront(node1);
		System.out.println(ll.length == 2);
		System.out.println(ll.contains("hello") == true);
		System.out.println(ll.delete(node1) == true);
		System.out.println(ll.delete(node2) == true);
		System.out.println(ll.delete(node2) == false);
		System.out.println(ll);
		
		LinkedList<String, Integer> ll2 = new LinkedList<String, Integer>();
		Node<String, Integer> node3 = new Node<String,Integer>("world", 18);
		Node<String, Integer> node4 = new Node<String,Integer>("hello", 17);
		Node<String, Integer> node5 = new Node<String,Integer>("!", 16);
		ll2.appendEnd(node3);
		System.out.println(ll2.getHead() == 18);
		System.out.println(ll2.popFront() == 18);
		System.out.println(ll2.head == null);
		System.out.println(ll2.tail == null);
		ll2.appendEnd(node4);
		System.out.println(ll2.head == ll2.tail);
		System.out.println(ll2.popFront() == 17);
		System.out.println(ll2.head == null);
		System.out.println(ll2.tail == null);
		
		ll2.appendEnd(node3);
		ll2.appendEnd(node4);
		ll2.appendEnd(node5);
		System.out.println(ll2.getHead() == 18);
		System.out.println(ll2.getTail() == 16);
		System.out.println(ll2);
		System.out.println(ll2.retrieve("!"));
		System.out.println(ll2.retrieve("hello"));
		System.out.println(ll2.retrieve("world"));
		System.out.println(ll2.getHead() == ll2.popFront()); //pop has to be second!!!
		System.out.println(ll2);
		System.out.println(ll2.getTail() == ll2.popEnd()); //pop has to be second!!!
		System.out.println(ll2);
		System.out.println(ll2.getTail() == 17);
		System.out.println(ll2.length == 1);
    }
}