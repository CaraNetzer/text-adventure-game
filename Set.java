@SuppressWarnings("rawtypes")
public class Set<K> extends HashTable{
	public int length = 0;
    
    public Set() {
    }
    
	@SuppressWarnings("unchecked")
	public <V> void add(V value){
		int idx = getIdx(value);
		if(hashTable[idx] == null){
			store(value,value,false);
			length++;
		}
		else if(!contains(value)){
			store(value,value,false);
			length++;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean remove(K item) {
        boolean deleted = this.delete(item);
        if (deleted == true) {
            length--;
        }
        return deleted;
    }
	
	public int length(){
		return masterList.length();
	}
	
	public <V> String toStringSet(){
		if(masterList == null)return"{}";
		String s = "{";
		@SuppressWarnings("unchecked")
		Node<K,V> head = masterList.head;
		while(head != null){
			if(head.next == null){
				s += "'" + head.value + "'";
			} else {
				s += "'" + head.value + "', ";
			}
			head = head.next;
		}
		s += "}";
		return s;
	}
	public <V> Set<K> getUnion(Set<K> set2){
		Set<K>union = new Set<K>();
		@SuppressWarnings("unchecked")
		Node<K,V>start = set2.masterList.head;//add checks to make sure there are no duplicates, so this is okay
		while(start != null){
			union.add(start.value);
			start = start.next;
		}
		@SuppressWarnings("unchecked")
		Node<K,V> start2 = masterList.head;
		while(start2 != null){
			union.add(start2.value);//add checks to make sure there are no duplicates, so this is okay
			start2 = start2.next;
		}
		return union;
	}
	
	@SuppressWarnings("unchecked")
	public <V> Set<K> getIntersection(Set<K> set2){
		Set<K> intersection = new Set<K>();
		Node<K,V> start = set2.masterList.head;
		while(start != null){
			K key =(K)start.value;
			if(contains(key)){
				intersection.add(start.value);
			}
			start=start.next;
		}
		return intersection;
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
    	Set<String> set1 = new Set<String>();
    	set1.add("hello");
    	System.out.println(set1.length == 1);
    	System.out.println(set1.retrieve("hello") == "hello");
    	System.out.println(set1.contains("hello"));
    	System.out.println(set1.remove("hello") == true);
    	System.out.println(set1.length() == 0);
    	
    	Double[] nums1 = new Double[10];
    	for(int i = 0; i <= 9; i++) {
    		nums1[i] = Math.random() * 100;
    	}
    	Set<Double> set2 = new Set<Double>();
    	for(int i = 0; i < 10; i++) {
    		set2.add(nums1[i]);
    		System.out.println(set2.retrieve(nums1[i]));
    		System.out.println(set2.contains(nums1[i]) == true);
    		System.out.println(set2);
    		System.out.println(set2.length() == i+1);
    	}
    	for(int i = 0; i < 10; i++) {
    		System.out.println(set2.remove(nums1[i]) == true);
    		System.out.println(set2.contains(nums1[i]) == false);
    		System.out.println(set2);
    		System.out.println(set2.length() == 9-i);
    	}    	
    	
    	Set<Integer> set3 = new Set<Integer>();
    	set3.add(17);
    	set3.add(12);
    	set3.add(65);
    	set3.add(2);
    	System.out.println(set3.length == 4);
    	Set<Integer> set4 = new Set<Integer>();
    	set4.add(90);
    	set4.add(32);
    	set4.add(65);
    	set4.add(2);
    	System.out.println(set4.length == 4);
    	System.out.println(set3.getUnion(set4).length == 6);
    	System.out.println(set3.getUnion(set4));
    	System.out.println((set3.getIntersection(set4)).length == 2);
    	System.out.println((set3.getIntersection(set4)));
    }
}