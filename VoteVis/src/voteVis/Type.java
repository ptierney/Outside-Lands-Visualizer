package voteVis;

// Note: I know, I know, this really could have been named something 
// more descriptive, but it's too late to change it.
// Also I should have set this up with proper serialize / deserialize methods
// instead of relying on the order of the elements.

public enum Type {
	MUSIC,
	FOOD,
	WINE,
	ECO,
	ART;
	
	public static Type deserialize(int num) {
		switch (num) {
		case 0:
			return MUSIC;
		case 1:
			return FOOD;
		case 2:
			return WINE;
		case 3:
			return ECO;
		case 4:
		default:
			return ART;
		}
	}
	
	public static int max_num() {
		return 4;
	}
}
