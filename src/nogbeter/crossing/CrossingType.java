package nogbeter.crossing;


public enum CrossingType {
		Enter,
		Exit,
		Follow;
		
		public CrossingType flip() {
			switch(this){
			case Enter: return Exit;
			case Exit: return Enter;
			case Follow: return Follow;
			}
			throw new Error("Unkown crossingtype: " + this);
		}
}
