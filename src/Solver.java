
public class Solver {
	private final Board initial;
	private int movesNumber;
	private boolean solveable;
	private Node goalNode;
	private MinPQ<Node> minPQ1 = new MinPQ<Node>();
	private MinPQ<Node> minPQ2 = new MinPQ<Node>();
	
	private class Node implements Comparable {
		private Board currentBoard = null;
		private int moves = 0;
		private Node previousNode = null;
		
		public Node(Board currentBoard, int moves, Node previousNode) {
			this.currentBoard = currentBoard;
			this.moves = moves;
			this.previousNode = previousNode;
			
		}
		@Override
		public int compareTo(Object obj) {
		    int priority1 = this.moves + this.currentBoard.manhattan();
		    int priority2 = ((Node)obj).moves + ((Node)obj).currentBoard.manhattan();
		    return priority1 - priority2;
		}
	}

	public Solver(Board initial) {
		this.initial = initial;	
		Board twin = initial.twin();
		minPQ1.insert(new Node(initial, 0, null));
		minPQ2.insert(new Node(twin, 0, null));
		while (true) {
			Node mini1 = minPQ1.delMin();
			Node mini2 = minPQ2.delMin();
			Board miniBoard1 = mini1.currentBoard;
			Board miniBoard2 = mini2.currentBoard;
			if (miniBoard1.isGoal()) {
				movesNumber = mini1.moves;
				solveable = true;
				goalNode = mini1;
				break;
			}
			if (miniBoard2.isGoal()) {
				solveable = false;
				movesNumber = -1;
				break;
			}
			int moveX1 = mini1.moves;
			int moveX2 = mini2.moves;
			Iterable<Board> iter1 = miniBoard1.neighbors();
			Iterable<Board> iter2 = miniBoard2.neighbors();
			for(Board boar1 : iter1) {
				if (mini1.previousNode != null) {
					if (boar1.equals(mini1.previousNode.currentBoard)) {
						continue;
					}
				}				
				minPQ1.insert(new Node(boar1, moveX1 + 1, mini1));
			}
			
			for(Board boar2 : iter2) {
				if (mini2.previousNode != null) {
					if (mini2.previousNode.previousNode != null) {
						if (boar2.equals(mini2.previousNode.previousNode.currentBoard)) {
							continue;
						}
					}
				}				
				minPQ2.insert(new Node(boar2, moveX2 + 1, mini2));
			}
		}
	}
	
	public boolean isSolvable() {
		return solveable;
	}
	
	public int moves() {
		return movesNumber;
	}
	
	public Iterable<Board> solution() {
		if (!isSolvable()) {
			return null;
		}
		Stack<Board> stack = new Stack<Board>();
		Node searchNode = goalNode;
		stack.push(searchNode.currentBoard);		
		while(true) {
			if (searchNode.previousNode == null) {
				break;
			}
			else {
				searchNode = searchNode.previousNode;
				stack.push(searchNode.currentBoard);
			}
		}
		return stack;
	}
	
	public static void main(String[] args) {
	    // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
}
