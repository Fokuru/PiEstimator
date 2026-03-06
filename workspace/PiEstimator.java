import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PiEstimator{
//the following code is just to jog your memory about how labels and buttons work!
//implement your Pi Estimator as described in the project. You may do it all in main below or you 
//may implement additional functions if you feel it necessary.
	
public static void main(String[] args) {  
	    JFrame f=new JFrame("Button Example");  
		f.setLayout(new FlowLayout(FlowLayout.CENTER));
	    JButton run=new JButton("Calculate");
	    JLabel example = new JLabel(Double.toString(Math.PI));
		
	    f.add(example);
	    f.add(run); 
	    f.setSize(300,300);  
	    f.setLayout(new GridLayout(4, 1));  
	    f.setVisible(true);      

		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (run.getText() == "Pause"){
					run.setText("Go");
					System.out.println("Going");
					f.setVisible(true);     
				} else if (run.getText() == "Go"){
					run.setText("Pause");
					System.out.println("Pausing");
					f.setVisible(true);     
				} else {
					run.setText("Pause");
					System.out.println("Calcing");
					f.setVisible(true);     
				}
				
			}
		});
	}  

	public class calcPi extends Thread {
		boolean running = false;
		

		public final void waitNow(long timeout, int nanos) throws InterruptedException {
			while (!running) {
				wait();
			}
		}

		synchronized void setReady() {
			running = true;
			notifyAll();
		}

		synchronized void setNotReady() {
			running = false;
			notifyAll();
		}

	}
}
