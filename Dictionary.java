public class Dictionary<K,V> extends HashTable<K,V> {
	public int length = 0;
	
    public Dictionary() {
    }
    
	public void add(K key, V value){
		int idx = getIdx(key);
		if(hashTable[idx] == null){
			store(key,value,false);
			length++;
		}
		else if(!contains(key)){
			store(key,value,false);
			length++;
		}
	}

    public boolean isEmpty() {
    	return this.length == 0;
    }
    
    public boolean remove(K item) {
        boolean deleted = this.delete(item);
        if (deleted == true) {
            length--;
        }
        return deleted;
    }
    
    public V get(K key) { //returns value
        return this.retrieve(key);
    }
    
    public String toString() {
        return super.toString();
    }
	
	public static void main(String[] args) {
    	Dictionary<String, Integer> dict1 = new Dictionary<String, Integer>();
    	System.out.println(dict1.isEmpty() == true);
    	dict1.add("hello", 17);
    	System.out.println(dict1.isEmpty() == false);
    	System.out.println(!dict1.isEmpty() == true);
    	System.out.println(dict1.length == 1);
    	System.out.println(dict1.get("hello") == 17);
    	System.out.println(dict1.contains("hello"));
    	System.out.println(dict1.remove("hello") == true);
    	System.out.println(dict1.length == 0);
    	
    	Double[] nums1 = new Double[10];
    	for(int i = 0; i <= 9; i++) {
    		nums1[i] = Math.random() * 100;
    	}
    	Double[] nums2 = new Double[10];
    	for(int i = 0; i <= 9; i++) {
    		nums2[i] = Math.random() * 10;
    	}
    	Dictionary<Double, Double> dict2 = new Dictionary<Double, Double>();
    	for(int i = 0; i < 10; i++) {
    		dict2.add(nums1[i], nums2[i]);
    		System.out.println(dict2.get(nums1[i]));
    		System.out.println(dict2.contains(nums1[i]) == true);
    		System.out.println(dict2);
    		System.out.println(dict2.length == i+1);
    	}
    	for(int i = 0; i < 10; i++) {
    		System.out.println(dict2.remove(nums1[i]) == true);
    		System.out.println(dict2.contains(nums1[i]) == false);
    		System.out.println(dict2);
    		System.out.println(dict2.length == 9-i);
    	}    	

    }
}