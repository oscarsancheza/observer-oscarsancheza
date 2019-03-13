package observer.pattern;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

import observer.CourseRecord;
import observer.LayoutConstants;

public class PieChartObserver extends JPanel implements Observer{

	private Vector<CourseRecord> courseData;
	
	public PieChartObserver(CourseData data) {
		data.attach(this);
		this.courseData = data.getUpdate();
		this.setPreferredSize(new Dimension(2 * LayoutConstants.xOffset
				+ (LayoutConstants.barSpacing + LayoutConstants.barWidth)
				* this.courseData.size(), LayoutConstants.graphHeight + 2
				* LayoutConstants.yOffset));
		this.setBackground(Color.white);
	}
	
	
	@Override
	public void update(Observable o) {
		CourseData data = (CourseData) o;
		this.courseData = data.getUpdate();

		this.setPreferredSize(new Dimension(2 * LayoutConstants.xOffset
				+ (LayoutConstants.barSpacing + LayoutConstants.barWidth)
				* this.courseData.size(), LayoutConstants.graphHeight + 2
				* LayoutConstants.yOffset));
		this.revalidate();
		this.repaint();
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		double startAngle = 0.0;
		double total = 0;
		for(CourseRecord courseData:courseData) {
			total += courseData.getNumOfStudents();
		}
		
		for (int i = 0; i < courseData.size(); i++) {
			int radius = 100;
			CourseRecord record = courseData.elementAt(i);
			double ratio = (record.getNumOfStudents() / total) * 360.0;
			g.setColor(LayoutConstants.courseColours[i % LayoutConstants.courseColours.length]);
			g.fillArc(LayoutConstants.xOffset, LayoutConstants.yOffset, 2 * radius, 2 * radius, (int) startAngle, (int) ratio);
			startAngle += ratio;
		}
	}

}
