
public class Board {
	private final char[][] blocks;
	private final int dimension;
	//private char[][] goalBoard;
	private int manhattan;

	public Board(int[][] blocks) {
		dimension = blocks.length;
		this.blocks = new char[dimension][dimension];
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				this.blocks[i][j] = (char)blocks[i][j];
			}
		}
		//initilaze();
		int counter = 0;
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				if (blocks[i][j] == 0) continue;
				if (blocks[i][j] != i * dimension + j + 1) {
					int oi = (blocks[i][j] - 1) / dimension;
					int oj = (blocks[i][j] - 1) % dimension;
					int distance = Math.abs(oi - i) + Math.abs(oj - j);					
					counter += distance;
				}
			}
		}
		manhattan = counter;
	}
	private Board(char[][] blocks) {
		dimension = blocks.length;
		this.blocks = new char[dimension][dimension];
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				this.blocks[i][j] = (char)blocks[i][j];
			}
		}
		//initilaze();
		int counter = 0;
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				if (blocks[i][j] == 0) continue;
				if (blocks[i][j] != i * dimension + j + 1) {
					int oi = (blocks[i][j] - 1) / dimension;
					int oj = (blocks[i][j] - 1) % dimension;
					int distance = Math.abs(oi - i) + Math.abs(oj - j);					
					counter += distance;
				}
			}
		}
		manhattan = counter;
	}
	public int dimension() {		
		return dimension;
	}
	public int hamming() {
		int counter = 0;
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				if (blocks[i][j] == 0) continue;
				if (blocks[i][j] != i * dimension + j + 1) {
					counter++;
				}
			}
		}
		return counter;
	}
	public int manhattan() {
		return manhattan;		
	}
	public boolean isGoal() {
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				if (i == dimension - 1 && j == dimension - 1) {
					if (blocks[i][j] != 0) {
						return false;
					}
				} else if (blocks[i][j] != i * dimension + j + 1) {
					return false;
				}
			}
		}
		return true;
	}
	
	public Board twin() {
		
		
		
		char[][] twin = copyRecords();		
		if (twin[0][0] != 0 && twin[0][1] != 0){
			char temp = twin[0][0];
			twin[0][0] = twin[0][1];
			twin[0][1] = temp;
		}
		else {
			char temp = twin[1][0];
			twin[1][0] = twin[1][1];
			twin[1][1] = temp;
		}
		return new Board(twin);
	}
	
	public boolean equals(Object y) {
		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass())
		return false;
		Board that = (Board)y;
		if (this.dimension != that.dimension) return false;
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				if (this.blocks[i][j] != that.blocks[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public Iterable<Board> neighbors() {
		Stack<Board> stack = new Stack<Board>();
		int row = 0;
		int column = 0;
		for(row = 0; row < dimension; row++) {
			boolean findZero = false;
			for(column = 0; column < dimension; column++) {
				if (blocks[row][column] == 0) {
					findZero = true;
					break;
				}
			}
			if (findZero) break;
		}
		if(row == 0 && column == 0) {
			char neighbor[][] = copyRecords();
			char temp = neighbor[row][column];
			neighbor[row][column] = neighbor[row + 1][column];
			neighbor[row + 1][column] = temp;
			stack.push(new Board(neighbor));
			
			char neighbor2[][] = copyRecords();
			char temp2 = neighbor2[row][column];
			neighbor2[row][column] = neighbor2[row][column + 1];
			neighbor2[row][column + 1] = temp2;
			stack.push(new Board(neighbor2));
		} else if (row == dimension -1 && column == dimension -1) {
			char neighbor[][] = copyRecords();
			char temp = neighbor[row][column];
			neighbor[row][column] = neighbor[row - 1][column];
			neighbor[row - 1][column] = temp;
			stack.push(new Board(neighbor));
			
			char neighbor2[][] = copyRecords();
			char temp2 = neighbor2[row][column];
			neighbor2[row][column] = neighbor2[row][column - 1];
			neighbor2[row][column - 1] = temp2;
			stack.push(new Board(neighbor2));
		} else if (row == 0 && column == dimension -1) {
			char neighbor[][] = copyRecords();
			char temp = neighbor[row][column];
			neighbor[row][column] = neighbor[row + 1][column];
			neighbor[row + 1][column] = temp;
			stack.push(new Board(neighbor));
			
			char neighbor2[][] = copyRecords();
			char temp2 = neighbor2[row][column];
			neighbor2[row][column] = neighbor2[row][column - 1];
			neighbor2[row][column - 1] = temp2;
			stack.push(new Board(neighbor2));
		} else if (row == dimension -1 && column == 0) {
			char neighbor[][] = copyRecords();
			char temp = neighbor[row][column];
			neighbor[row][column] = neighbor[row - 1][column];
			neighbor[row - 1][column] = temp;
			stack.push(new Board(neighbor));
			
			char neighbor2[][] = copyRecords();
			char temp2 = neighbor2[row][column];
			neighbor2[row][column] = neighbor2[row][column + 1];
			neighbor2[row][column + 1] = temp2;
			stack.push(new Board(neighbor2));
		} else if(row == 0 && column != 0 && column != dimension - 1) {
			char neighbor[][] = copyRecords();
			char temp = neighbor[row][column];
			neighbor[row][column] = neighbor[row + 1][column];
			neighbor[row + 1][column] = temp;
			stack.push(new Board(neighbor));
			
			char neighbor2[][] = copyRecords();
			char temp2 = neighbor2[row][column];
			neighbor2[row][column] = neighbor2[row][column + 1];
			neighbor2[row][column + 1] = temp2;
			stack.push(new Board(neighbor2));
			
			char neighbor3[][] = copyRecords();
			char temp3 = neighbor3[row][column];
			neighbor3[row][column] = neighbor3[row][column - 1];
			neighbor3[row][column - 1] = temp3;
			stack.push(new Board(neighbor3));
		} else if(row == dimension - 1 && column != 0 && column != dimension - 1) {
			char neighbor[][] = copyRecords();
			char temp = neighbor[row][column];
			neighbor[row][column] = neighbor[row - 1][column];
			neighbor[row - 1][column] = temp;
			stack.push(new Board(neighbor));
			
			char neighbor2[][] = copyRecords();
			char temp2 = neighbor2[row][column];
			neighbor2[row][column] = neighbor2[row][column + 1];
			neighbor2[row][column + 1] = temp2;
			stack.push(new Board(neighbor2));
			
			char neighbor3[][] = copyRecords();
			char temp3 = neighbor3[row][column];
			neighbor3[row][column] = neighbor3[row][column - 1];
			neighbor3[row][column - 1] = temp3;
			stack.push(new Board(neighbor3));
		} else if (row != 0 && row != dimension - 1 && column == 0) {
			char neighbor[][] = copyRecords();
			char temp = neighbor[row][column];
			neighbor[row][column] = neighbor[row - 1][column];
			neighbor[row - 1][column] = temp;
			stack.push(new Board(neighbor));
			
			char neighbor2[][] = copyRecords();
			char temp2 = neighbor2[row][column];
			neighbor2[row][column] = neighbor2[row + 1][column];
			neighbor2[row + 1][column] = temp2;
			stack.push(new Board(neighbor2));
			
			char neighbor3[][] = copyRecords();
			char temp3 = neighbor3[row][column];
			neighbor3[row][column] = neighbor3[row][column + 1];
			neighbor3[row][column + 1] = temp3;
			stack.push(new Board(neighbor3));
		} else if (row != 0 && row != dimension - 1 && column == dimension - 1) {
			char neighbor[][] = copyRecords();
			char temp = neighbor[row][column];
			neighbor[row][column] = neighbor[row - 1][column];
			neighbor[row - 1][column] = temp;
			stack.push(new Board(neighbor));
			
			char neighbor2[][] = copyRecords();
			char temp2 = neighbor2[row][column];
			neighbor2[row][column] = neighbor2[row + 1][column];
			neighbor2[row + 1][column] = temp2;
			stack.push(new Board(neighbor2));
			
			char neighbor3[][] = copyRecords();
			char temp3 = neighbor3[row][column];
			neighbor3[row][column] = neighbor3[row][column - 1];
			neighbor3[row][column - 1] = temp3;
			stack.push(new Board(neighbor3));
		} else {
			char neighbor[][] = copyRecords();
			char temp = neighbor[row][column];
			neighbor[row][column] = neighbor[row - 1][column];
			neighbor[row - 1][column] = temp;
			stack.push(new Board(neighbor));
			
			char neighbor2[][] = copyRecords();
			char temp2 = neighbor2[row][column];
			neighbor2[row][column] = neighbor2[row + 1][column];
			neighbor2[row + 1][column] = temp2;
			stack.push(new Board(neighbor2));
			
			char neighbor3[][] = copyRecords();
			char temp3 = neighbor3[row][column];
			neighbor3[row][column] = neighbor3[row][column - 1];
			neighbor3[row][column - 1] = temp3;
			stack.push(new Board(neighbor3));
			
			char neighbor4[][] = copyRecords();
			char temp4 = neighbor4[row][column];
			neighbor4[row][column] = neighbor4[row][column + 1];
			neighbor4[row][column + 1] = temp4;
			stack.push(new Board(neighbor4));			
		}
		return stack;
	}
	
	private char[][] copyRecords() {
		char[][] copy = new char[dimension][dimension];
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				copy[i][j] = blocks[i][j];
			}
		}
		return copy;
	}
	public String toString() {
		StringBuilder s = new StringBuilder();
	    s.append(dimension + "\n");
	    for (int i = 0; i < dimension; i++) {
	        for (int j = 0; j < dimension; j++) {
	            s.append(String.format("%2d ", (int)(blocks[i][j])));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}
	/*private void initilaze() {
		goalBoard = new char[dimension][dimension];
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				if (i == dimension - 1 && j == dimension -1) {
					goalBoard[i][j] = 0;
				}
				else {
					goalBoard[i][j] = (char) (i * dimension + j + 1);
				}				
			}
		}
	}*/
}
