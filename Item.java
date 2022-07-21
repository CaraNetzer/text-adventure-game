public class Item {
	String name;
	String description;
	String initialState;
	
	public Item(String name, String description, String initState) {
		this.name = name;
		this.description = description;
		this.initialState = initState;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getInitState(){
		return initialState;
	}
	
	public static void main(String[] args) {
		Item item = new Item("car", "a car", "woodsboro");
		System.out.println(item.getName() == "car");
		System.out.println(item.getDescription() == "a car");
		System.out.println(item.getInitState() == "woodsboro");
	}
	
}
