package voteVis;

public enum Size {
	S,
	M,
	L;
	
	public static int get_dim_from_size(Size size) {
		switch (size) {
		case L:
			return Settings.UNIT_DIM * 3 + Settings.BOX_GAP * 2;
		case M:
			return Settings.UNIT_DIM * 2 + Settings.BOX_GAP;
		case S:
		default:
			return Settings.UNIT_DIM;
		}
	}
	
	// rank 0 = #1 (most votes)
	// rank 1 = #2
	// ...
	public static Size get_size_from_rank(int rank) {
		switch (rank) {
		case 0:
			return L;
		case 1:
			return M;
		case 2:
			return M;
		case 3:
			return S;
		case 4:
		default:
			return S;
		}
	}
}
