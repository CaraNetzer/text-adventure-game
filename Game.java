import java.io.*;

public class Game {
	Dictionary<String,State> states = new Dictionary<String,State>();
	Dictionary<String,Item> items = new Dictionary<String,Item>();
	Set<Command> commands = new Set<Command>();
	Set<Item> backpack = new Set<Item>();
	State currentState = null;
	String input;
	
	boolean play = true;
	String args = "";
	boolean planted = false;
	
	public Game(String fileName) {
		try {
			this.args = fileName;
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null){
				String[] parts = line.split("::");
				if(parts[0].equals("state")){
					State tmpState = new State(parts[1],parts[2]);
					states.add(parts[1],tmpState); 
					
				} else if(parts[0].equals("item")){
					Item tmpItem=new Item(parts[1],parts[2],parts[3]);
					items.add(parts[1], tmpItem);
					
				} else if(parts[0].equals("command")){	
					Command tmpCommand=new Command(parts[1],parts[2],parts[3],parts[4]);
					commands.add(tmpCommand);
					
				}else{
				}
			}
			bufferedReader.close();
			addCommandsToStates();
			addItemsToStates();
			currentState = states.masterList.head.value;
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void addCommandsToStates() {
		Node<String,State> currentStateNode = states.masterList.head;
		while(currentStateNode != null){
			State currentState = currentStateNode.value;
			Node<Command,Command> currentCommandNode = commands.masterList.head;
			while(currentCommandNode != null){
				Command currentCommand = currentCommandNode.key;
				if(currentState.name.equals(currentCommand.fromStateName)){
					currentState.commands.append(new Node<String,Command>(currentCommand.text, (Command)commands.retrieve(currentCommand)));
				}
				currentCommandNode = currentCommandNode.next;
			}
			currentStateNode = currentStateNode.next;
		}
	}
	
	public void addItemsToStates() {
		Node<String,State>currentStateNode = states.masterList.head;
		while(currentStateNode != null){
			State currentState = currentStateNode.value;
			Node<String,Item> currentItemsNode = items.masterList.head;
			while(currentItemsNode != null){
				Item currentItem = currentItemsNode.value;
				if(currentState.name.equals(currentItem.initialState)){
					currentState.items.append(new Node<String,Item>(currentItem.name, currentItem));
				}
				currentItemsNode = currentItemsNode.next;
			}
			currentStateNode = currentStateNode.next;
		}
		
	}
	
	public String solve(String start, String end) { // \u2022 = bullet for bulleted list
		String solution = "You're currently at the ";
		LinkedList<String, State> path = this.getPath(this.createParentPointers(start, end));
		solution += path.head.toString() + ". (You may need to add 'go', 'go to', or 'enter' to some of these commands) \nNext go to:\n";
		solution += "\u2022" + path.head.next.toString() + " by typing east, then\n";
		Node<String, State> state = path.head.next;
		while (state.next != null) {	
			Node<String, Command> command = state.value.commands.head; 
			String match = "";
			while (command != null) {
				if (command.value.toStateName.equals(state.next.value.name)) {
					match = command.value.text; 
					//the key for commands is the toStateName not the command text --> i changed it
					if (match != "") { 
						solution += "\u2022" + state.next.toString() + " by typing " + state.value.commands.retrieve(match) + ", then\n";
					}
				}
				command = command.next;
			}
			state = state.next;
		}
		solution += "You win!";
		System.out.println(solution);
		System.out.println("However, you'll need these items to win the game which may not be in the locations listed above: \n\u2022tank of gas \n\u2022seeds \n\u2022fuel tank \n\u2022go cart \n\u2022ice");
		return solution;
	}
	
	public LinkedList<String, State> getPath(State last) {
		LinkedList<String, State> path = new LinkedList<String, State>();
		State current = last;
		while (current.parent!=null) {	
			path.appendFront(new Node<String, State>(current.name, current));
			current = current.parent;
		}
		path.appendFront(new Node<String, State>(current.name, current));
		return path;
	}
	
	@SuppressWarnings("unchecked")
	public State createParentPointers(String start, String end) {
		State current = states.retrieve(start);
		current.parent = null;
		Set<String> visited = new Set<String>();
	    LinkedList<String, State> queue = new LinkedList<String, State>();
		queue.appendEnd(new Node<String, State>(current.name, current));

		while (!queue.isEmpty()) {
            current = queue.popFront();
            if (current.name.equals(end)) {
                return current;
            }
            if (!visited.contains(current.name)) {
                visited.add(current.name);
                
                if (!current.commands.isEmpty()) {
                	Node<String, Command> n = current.commands.head;
	                while (n != null) {
				        State toState = states.retrieve(n.value.toStateName);
				        Node<String, State> m = new Node<String, State>(toState.name, toState);
				        if(!visited.contains(m.key)){
				        	queue.appendEnd(m);
				        	toState.parent = current;
				        }
				        n = n.next;
			        }          
                }
		    }
		}
	    return null;
	}
	
	public boolean canGetTo(String to){ //is this here twice?
		boolean canGetTo = false;
		if(currentState.name.equals(to)) {
			return false;
		}
		LinkedList<String,Command> tmpCommands = currentState.commands;
		Node<String,Command> com = tmpCommands.head;
		while(com != null){
			if(com.value.toStateName.equals(to)){
				canGetTo = true;
			}
			com = com.next;
		}
		return canGetTo;
	}
	
	public boolean canGetTo(State to){
		boolean canGetTo = false;
		if(currentState.name.equals(to.name)) {
			return false;
		}
		LinkedList<String,Command> tmpCommands = currentState.commands;
		Node<String,Command> com = tmpCommands.head;
		while(com != null){
			if(com.value.toStateName.equals(to.name)){
				canGetTo = true;
			}
			com = com.next;
		}
		return canGetTo;
	}
	
	public Dictionary<String,Item> getRequiredItems(Command c1){
		Dictionary<String,Item> d1 = new Dictionary<String,Item>();
		for(String s1: c1.requiredItemsString){
			if(!s1.equals("")){
				d1.add(s1, items.retrieve(s1));
			}
		}
		return d1;
	}
	public String getInput(String prompt){
		System.out.println(prompt);
		String input=null;
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			input=br.readLine();
		}catch(IOException io){
			io.printStackTrace();
		}
		return input;
	}
	
	public String generatePrompt(){
		String s = "\n";
		s += "You are at the " + currentState.name + "...\n";
		s += currentState.description + "\n";
		if(currentState.items.head != null){
			s += "There is ";
			Node<String,Item> tmpItem = currentState.items.head;
			while(tmpItem != null){
				s += " a " + tmpItem.value.name + ",";
				tmpItem = tmpItem.next;
			}
		}
		return s;
	}
	
	@SuppressWarnings("unchecked")
	public void interpretInput(){
		String[]words = input.split(" ");
		if(words[0].equalsIgnoreCase("go")||words[0].equalsIgnoreCase("enter")){
			//System.out.println(input.substring(input.indexOf(words[2])));
			LinkedList<Command,Command> possibleCommands;
			if(words[1].equalsIgnoreCase("to")){
				possibleCommands = getCommands(input.substring(input.indexOf(words[2])));
			} else{
				possibleCommands = getCommands(input.substring(input.indexOf(words[1])));
			}
			if(possibleCommands.head != null){
				if(hasNecessaryItems(possibleCommands)){
					currentState = states.retrieve(possibleCommands.head.key.toStateName);
				} else {
					System.out.println("you are missing something...");
				}
			} else {
				System.out.println("Nothing over there.");
			}
		} else if(input.equalsIgnoreCase("blast off")||input.equalsIgnoreCase("launch")){
			if(currentState.name.equals("spaceship")){
				currentState = states.retrieve("space");
			} else if(currentState.name.equals("space craft")){
				currentState = states.retrieve("space(again!)");
			}
		} else if(words[0].equalsIgnoreCase("pick")||words[0].equalsIgnoreCase("get")||words[0].equalsIgnoreCase("take")){
			//trying to get an item and removing it from the LinkedList of items in the current state(the state it was found)
			String itemName = "";
			if(words[0].equalsIgnoreCase("pick")){
				if(words[1].equalsIgnoreCase("up")){
					System.out.println(input.substring(input.indexOf(words[1])));
					itemName = input.substring(input.indexOf(words[2]));
				}
			} else {
				itemName = input.substring(input.indexOf(words[1]));	
			}
			Item currentItem = items.retrieve(itemName);
			if(currentItem != null){
				if(currentState.items.contains(itemName)){
					backpack.add(currentItem);
					currentState.items.delete(currentItem.name);
					if(backpack.contains(currentItem)){
						System.out.println(currentItem.name + " taken.");
					}
				} else {
					System.out.println("there is no " + itemName+" in the " + currentState.name);
				}
			} else {
				System.out.println(itemName+" does not exist in this vast and everexpanding universe.");
			}
			
		} else if(words[0].equalsIgnoreCase("drop")||words[0].equalsIgnoreCase("lose")||
				words[0].equalsIgnoreCase("put")){
			//removing an item from your backpack and putting it in the state linked list of items
			String droppedName = "";
			if(words[0].equalsIgnoreCase("put")){
				if(words[1].equalsIgnoreCase("away")||words[1].equalsIgnoreCase("down")){
					droppedName = input.substring(input.indexOf(words[2]));
				}
			} else {
				droppedName = input.substring(input.indexOf(words[1]));
			}
			Item currentItem = items.retrieve(droppedName);
			if(backpack.contains(currentItem)){
				currentState.items.appendFront(new Node<String,Item>(currentItem.name,currentItem));
				backpack.delete(currentItem);
				if(!backpack.contains(currentItem) && currentState.items.contains(currentItem.name)){
					System.out.println(currentItem.name + " dropped.");
				}
			} else {
				System.out.println("You do not currently have the " + currentItem.name);
			}	
		} else if(words[0].equalsIgnoreCase("quit")){
			cease();
		} else if(words[0].equalsIgnoreCase("solve")){
			this.solve(currentState.name, "patch of smooth dirt");
		} else if(words[0].equalsIgnoreCase("restart")){
			restart();
		} else if(words[0].equalsIgnoreCase("hint")){
			LinkedList<String, State> path = this.getPath(this.createParentPointers(currentState.name, "patch of smooth dirt"));
			if(path.head.next != null){
				System.out.println(path.head.next.key);
			}
		} else if(words[0].equalsIgnoreCase("plant")){
			if(backpack.contains(items.retrieve("seeds")) && currentState.name.equals("patch of smooth dirt")){
				System.out.println("You planted the seeds!");
				backpack.delete(items.retrieve("seeds"));
				planted=true;
			} else {
				if(!backpack.contains(items.retrieve("seeds"))){
					System.out.println("You can't plant anything without seeds...");
				}
				if(!currentState.name.equals("patch of smooth dirt")){
					System.out.println("You can't plant seeds here....");
				}
			}
		} else if(words[0].equalsIgnoreCase("water")){
			if(planted&&backpack.contains(items.retrieve("ice"))){
				System.out.println("You watered the plants!");
				System.out.println("trees on mars!");
				System.out.println("You've reached the end of the game! You created sustainable life on mars! yay! Type 'quit' to end game, 'restart' to restart.");
				backpack.delete(items.retrieve("ice"));
				currentState = states.retrieve("forest on mars");
			} else if(!backpack.contains(items.retrieve("ice"))){
				System.out.println("What are you going to water the plants with???");
			}
		} else if(words[0].equalsIgnoreCase("help")){
			System.out.print("Type 'quit' to quit, 'restart' to restart.");
			help();
		} else if(words[0].equalsIgnoreCase("backpack")){
			System.out.print(backpack.toStringSet()); //just prints out item codes
		} else {
			System.out.println("Sorry, I don't quite get that.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean hasNecessaryItems(LinkedList<Command,Command>p) {
		boolean has=true;
		if(!items.contains(p.head.key.requiredItemsString[0])){
			return true;
		}
		else{
			Dictionary<String,Item>d=getRequiredItems(p.head.key);
			Node<String,Item>current=d.masterList.head;
			while(current!=null){
				if(!backpack.contains(d.retrieve(current.key))){
					System.out.println("missing "+current.key);
					has=false;
				}
				current=current.next;
			}
		}
		return has;
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<Command,Command> getCommands(String direction){
		LinkedList<Command,Command>ll=new LinkedList<Command,Command>();
		Node<Command,Command>current=commands.masterList.head;
		while(current!=null){
			State tmpState=states.retrieve(current.key.fromStateName);
			if(current.key.text.equals(direction)&&tmpState.equals(currentState)){
				ll.append(new Node<Command,Command>(current.key,current.key));
			}
			current=current.next;
		}
		return ll;	
	}
	
	private void play() {
		while(play){
			System.out.println(generatePrompt());
			input = getInput(">>>");
			interpretInput();
		}
	}
	
	void cease(){
		play = false;
		System.out.println("Game terminating....");
	}
	
	void restart(){
		cease();
		System.out.println("Rebooting......");
		play = true;
		Game g = new Game(args);
		g.play();
	}
	
	void help() {
		System.out.println("\nType 'go', 'go to', or 'enter' in front of these commands which are available to you at this location:");
		Node<String, Command> command = currentState.commands.head;
		while(command != null) {
			System.out.println("\u2022" + command.value.text); //bulleted list
			command = command.next;
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game("gametext");
		System.out.println("Welcome! Earth is dying, you need to find your way to Mars and create life there! Type help or hint if you are stuck.");
		game.play();
		
	}
}
