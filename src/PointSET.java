import java.util.Iterator;
import java.util.TreeSet;



public class PointSET {
	private SET<Point2D> pointsSet = new SET<Point2D>();
	public PointSET() {		
	}
    public boolean isEmpty()  {
    	return pointsSet.isEmpty();
    }
    public int size() {
    	return pointsSet.size();
    }
    public void insert(Point2D p) {
    	if (p == null) return;
    	if (pointsSet.contains(p)) return;
    	pointsSet.add(p);
    }
    public boolean contains(Point2D p) {
    	if (p == null) return false;
    	return pointsSet.contains(p);
    }
    public void draw() {
    	StdDraw.setPenColor(StdDraw.BLACK);
    	StdDraw.setPenRadius(.01);
    	for(Point2D point : pointsSet) {
    		point.draw();
    	}
    	//StdDraw.show(50);
    }
    public Iterable<Point2D> range(RectHV rect) {
    	Queue<Point2D> queue = new Queue<Point2D>();    	
    	for(Point2D point : pointsSet) {
    		if(rect.contains(point)) {
    			queue.enqueue(point);
    		}
    	}
    	return queue;
    }
    public Point2D nearest(Point2D p)  {
    	if (p == null) return null;
    	double distance = 1;
    	Point2D nearestPoint = null;
    	for(Point2D point : pointsSet) {
    		double temDistance = p.distanceSquaredTo(point);
    		if (temDistance < distance) {
    			distance = temDistance;
    			nearestPoint = point;
    		}
    	}
    	return nearestPoint;
    }
}