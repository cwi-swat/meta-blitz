package nogbeter.crossing;


public enum CrossType {
		Enter,
		Exit,
		Parallel,
		Impossible;

		public CrossType flip() {
			switch(this){
			case Enter: return Exit;
			case Exit : return Enter;
			}
			return this;
		}
}
