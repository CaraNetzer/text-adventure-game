public class HashTable<K,V> {
	int tableSize = 10;
	@SuppressWarnings("unchecked")
	LinkedList<K,V>[] hashTable=new LinkedList[tableSize];
	int load = 0;
	LinkedList<K,V> masterList;
	
	public int getIdx(K key){
		int hash = key.hashCode();
		return Math.abs(hash%tableSize);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void store(K key, V value, boolean isRehash){
		int idx = getIdx(key);
		if(hashTable[idx] == null){
			hashTable[idx] = new LinkedList(new Node<K,V>(key,value));
		} else {
			hashTable[idx].appendFront(new Node<K,V>(key,value));
		}
		if(masterList != null){
			masterList.append(new Node<K,V>(key,value));
		} else {
			masterList = (new LinkedList<K,V>(new Node<K,V>(key,value)));
		}
		load++;
		if(load>tableSize){
			tableSize = 2*tableSize;
			hashTable = new LinkedList[tableSize];
			Node<K,V> current = masterList.head;
					
			while(current != null){
				reHash(current.key, current.value);
				current = current.next;
			}	
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void reHash(K key, V value){
		int idx = getIdx(key);
		if(hashTable[idx] == null){
			hashTable[idx] = new LinkedList(new Node<K,V>(key,value));
		}
		else{
			hashTable[idx].append(new Node<K,V>(key,value));
		}
	}
	public boolean contains(K key){
		int idx = getIdx(key);
		if(hashTable[idx] == null){
			return false;
		} else {
			Node<K,V> current = hashTable[idx].head;
			while(current != null){
				if(current.key.equals(key)){
					return true;
				}
				current = current.next;
			}
			return false;
		}
		
	}
	public boolean delete(K key) {
		int idx = getIdx(key);
		if(contains(key)){
			masterList.delete(key);
			load--;
			return hashTable[idx].delete(key);
		} else {
			return false;
		}
	}
	public V retrieve(K key) {
	    if(contains(key)){
	    	int idx = getIdx(key);
	    	Node<K,V>current = hashTable[idx].head;
	   		while(current != null){
	   			if(current.key.equals(key)){
	   				return (V)current.value;
	   			}
	   			current = current.next;
    		}
	   		return null;
	    } else{
	    	return null;
	    }
	}
	public String toString() {
	        String s = "[0] ";
	        int count = 0;
	        for(int i=0; i<tableSize; i++){
	            LinkedList<K,V> current = hashTable[i];
	            if(hashTable[i] != null){
	            	Node<K,V> tmp = current.head;
	            	while(tmp != null){
	                	s += "['" + tmp.key + "','" + tmp.value+"'], ";
	                	tmp = tmp.next;
	            	}
	            }
	            count++;
	            s += "\n";
	            if(i < tableSize-1){
	            	s += "["+count+"] ";
	            }
	        }
	        return s;
	}
	
	public static void main(String[] args) {
    	HashTable<String, Integer> ht = new HashTable<String, Integer>();
    	ht.store("hello", 14, false);
    	System.out.println(ht.contains("hello") == true);
    	System.out.println(ht.load == 1);
    	System.out.println(ht);
    	System.out.println(ht.delete("hello") == true);
    	System.out.println(ht.load == 0);
    	
    	Double[] nums1 = new Double[10];
    	for(int i = 0; i <= 9; i++) {
    		nums1[i] = Math.random() * 100;
    	}
    	Double[] nums2 = new Double[10];
    	for(int i = 0; i <=9; i++) {
    		nums2[i] = Math.random() * 10;
    	}
    	HashTable<Double, Double> ht1 = new HashTable<Double, Double>();
    	for(int i = 0; i < 10; i++) {
    		ht1.store(nums1[i], nums2[i], false);
    		System.out.println(ht1.retrieve(nums1[i]));
    		System.out.println(ht1.contains(nums1[i]) == true);
    		System.out.println(ht1);
    		System.out.println(ht1.load == i+1);
    	}
    	for(int i = 0; i < 10; i++) {
    		System.out.println(ht1.delete(nums1[i]) == true);
    		System.out.println(ht1.contains(nums1[i]) == false);
    		System.out.println(ht1);
    		System.out.println(ht1.load == 9-i);
    	}
    }	
}
