public class State {
	String name;
	String description;
	State parent;
	LinkedList<String,Command> commands = new LinkedList<String,Command>();
	LinkedList<String, Item> items = new LinkedList<String,Item>();
	
	public State() {
		this.description = "";
		this.name = "";
	}
	
	public State(String name, String description) {
		this.description = description;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	public String getDescription(){
		return description;
	}
	
	public static void main(String[] args) {
		State state = new State("car", "a car");
		System.out.println(state.getName() == "car");
		System.out.println(state.getDescription() == "a car");
	}	
}
