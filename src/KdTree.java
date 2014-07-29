import java.util.Comparator;


public class KdTree {
	
	private Node root;
	private double nearestDistance;
	private Point2D currentNearestPoint;
	
	private static class Node {
		private Point2D p;      // the point
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node lb;        // the left/bottom subtree
		private Node rt;        // the right/top subtree
		private int N;
		private boolean Xseperate = true;
		public Node(Point2D point, int number, boolean Xseperate, RectHV rect) {
			N = number;
			p = point;
			this.Xseperate = Xseperate;
			this.rect = rect;
		}
	}
	
	public KdTree() {
		// construct an empty set of points
	}
	public boolean isEmpty() {
		return root == null;                 // is the set empty?
	}
	public int size() {
		return size(root);          // number of points in the set
	}
	private int size(Node x) {
		if (x == null) return 0;
		else           return x.N;
	}
	public void insert(Point2D p) {		
		if (p == null) return;
		//int counter = 0;
		if (root == null) {
			root = new Node(p, 1, true, new RectHV(0, 0, 1, 1));
			return;
		}
		
		root = insert(root, p);
	}
	
	private Node insert(Node x, Point2D p) {
		//if (x == null && counter % 2 == 0) return new Node(p, 1, true, rect);
		//else if (x == null && counter % 2 == 1) return new Node(p, 1, false, rect);
		int cmp; 
		if (x.Xseperate) {
			Comparator<Point2D> comparator = Point2D.X_ORDER;
			cmp = comparator.compare(p, x.p);
			if (cmp < 0){
				if (x.lb == null) {
					x.lb = new Node(p, 1, false, new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax()));
				}
				else {
					x.lb = insert(x.lb, p);
				}				
			}
			else if(cmp > 0){
				if (x.rt == null) {
					x.rt = new Node(p, 1, false, new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax()));
				}
				else {
					x.rt = insert(x.rt, p);
				}
			} 
			else {
				if (x.p.equals(p)) {
					x.N = size(x.lb) + size(x.rt) + 1;
					return x;
				}
				else  {
					if (x.rt == null) {
						x.rt = new Node(p, 1, false, new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax()));
					}
					else {
						x.rt = insert(x.rt, p);
					}
				}
			}
		}
		else {
			Comparator<Point2D> comparator = Point2D.Y_ORDER;
			cmp = comparator.compare(p, x.p);
			if (cmp < 0) {
				if (x.lb == null) {
					x.lb = new Node(p, 1, true, new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y()));
				}
				else {
					x.lb = insert(x.lb, p);
				}
			}
			else if (cmp > 0){
				if(x.rt == null) {
					x.rt = new Node(p, 1, true, new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax()));
				}
				else {
					x.rt = insert(x.rt, p);
				}
			}
			else {
				if (x.p.equals(p)) {
					x.N = size(x.lb) + size(x.rt) + 1; // do nothing
					return x;
				}
				else {
					if(x.rt == null) {
						x.rt = new Node(p, 1, true, new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax()));
					}
					else {
						x.rt = insert(x.rt, p);
					}
				}
			}
		}				
		x.N = size(x.lb) + size(x.rt) + 1;
		return x;
	}
	public boolean contains(Point2D p) {
		if (p == null) return false;
		return contains(root, p);
	}
	
	private boolean contains(Node x, Point2D p) {
		if (x == null) return false;
		int cmp;
		if (x.Xseperate) {
			Comparator<Point2D> comparator = Point2D.X_ORDER;
			cmp = comparator.compare(p, x.p);			
		}
		else {
			Comparator<Point2D> comparator = Point2D.Y_ORDER;
			cmp = comparator.compare(p, x.p);			
		}	
		if (cmp < 0) {
			return contains(x.lb, p);
		}
		else if (cmp > 0){
			return contains(x.rt, p);
		}
		else {
			if (x.p.equals(p)) return true;
			else return contains(x.rt, p);
		}
	}
	public void draw() {
		if (root == null) return;
		draw(root);
	}
	private void draw(Node x) {
		if (x == null) return;
		StdDraw.setPenColor(StdDraw.BLACK);
    	StdDraw.setPenRadius(.01);
		x.p.draw();
		if (x.Xseperate) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius();
			StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
		}
		else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius();
			StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
		}
		draw(x.lb);
	    draw(x.rt);
	}
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) return null;
		Queue<Point2D> inRangePoints = new Queue<Point2D>();
		findRange(rect, root, inRangePoints);
		return inRangePoints;
	}
	private void findRange(RectHV rect, Node x, Queue<Point2D> inRangePoints) {
		if (x == null) return;
		if (!rect.intersects(x.rect)) return;
		if (rect.contains(x.p)) {
			inRangePoints.enqueue(x.p);
		}
		findRange(rect, x.lb, inRangePoints);
		findRange(rect, x.rt, inRangePoints);
	}
	public Point2D nearest(Point2D p) {
		if (root == null) return null;
		nearestDistance = p.distanceSquaredTo(root.p);
		currentNearestPoint = root.p;
		findNearest(root, p);
		return currentNearestPoint;		
	}
	
	private void findNearest(Node x, Point2D p) {
		if (x == null) return;
		if (x.rect.distanceSquaredTo(p) >= nearestDistance) {
			return ;
		}
		double distance = p.distanceSquaredTo(x.p);
		if (distance < nearestDistance) {
			nearestDistance = distance;
			currentNearestPoint = x.p;
		}
		findNearest(x.lb, p);
		findNearest(x.rt, p);
	}
}
