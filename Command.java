public class Command {
	String[] requiredItemsString;
	String text;
	String fromStateName;
	String toStateName;
	//Dictionary<String,Item> requiredItemsList; //is this used
	
	public Command(String text, String fromStateName, String toStateName, String requiresItemList) {
		this.text = text;
		this.fromStateName = fromStateName;
		this.toStateName = toStateName;
		this.requiredItemsString = requiresItemList.split(",");
	}
	
	public String getName() {
		return text;
	}
	
	public String getFromState() {
		return fromStateName;
	}
	
	public String getToState() {
		return toStateName;
	}
	
	public String[] getRequiredItems() {
		return requiredItemsString;
	}
	
	public static void main(String[] args) {
		Command command = new Command("go", "woodsboro", "frederick", "car");
		System.out.println(command.getName() == "go");
		System.out.println(command.getFromState() == "woodsboro");
		System.out.println(command.getToState() == "frederick");
		System.out.println(command.getRequiredItems());
	}


}
