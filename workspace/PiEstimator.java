import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class PiEstimator{
//the following code is just to jog your memory about how labels and buttons work!
//implement your Pi Estimator as described in the project. You may do it all in main below or you 
//may implement additional functions if you feel it necessary.
	
public static void main(String[] args) {  
	    JFrame f=new JFrame("Button Example");  
		f.setLayout(new FlowLayout(FlowLayout.CENTER));
	    JButton run=new JButton("Calculate");
	    JLabel example = new JLabel("Actual Pi: " + Double.toString(Math.PI));
		JLabel currentPi = new JLabel("Current Pi: " + Double.toString(0.0));
		JLabel currentPoints = new JLabel("Current Points: " + Integer.toString(0));

	    f.add(example);
	    f.add(run); 
		f.add(currentPi); 
		f.add(currentPoints); 
	    f.setSize(300,300);  
	    f.setLayout(new GridLayout(4, 1));  
	    f.setVisible(true);      
		int threads = 1;
		calcPi[] thread = new calcPi[threads];

		// create and start threads
		for(int i = 0; i < threads; i++) {
        	thread[i] = new calcPi();
        	thread[i].start();
       	}

		// add action listener to button
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (run.getText() == "Pause"){
					run.setText("Go");
					System.out.println("Pausing");
					for (int i = 0; i < threads; i++) {
						thread[i].setNotReady();
					}
					System.out.println("Current Pi: " + calcPi.curr);
					f.setVisible(true);     
				} else if (run.getText() == "Go"){
					run.setText("Pause");
					System.out.println("Going");
					for (int i = 0; i < threads; i++) {
						thread[i].setReady();
					}
					f.setVisible(true);     
				} else {
					run.setText("Pause");
					System.out.println("Calcing");
					f.setVisible(true);     
					for (int i = 0; i < threads; i++) {
						thread[i].setReady();
					}
					
				}
				
			}
		});

		// create a thread to update the labels
		new Thread()
		{
			public void run() {
				while (true) {
				currentPi.setText("Current Pi: " + Double.toString(calcPi.curr));
				currentPoints.setText("Current Points: " + Long.toString(calcPi.points));
				f.repaint();     

			}
			}
		}.start();
		 
		
		}  

	public static class calcPi extends Thread {
		boolean running = false;
		static Long points = (long) 0;
		static Long inSide = (long) 0;
		static volatile double curr = 0.0;

		// run method for the thread
		// Pre: none
		// Post: runs the thread and calculates pi until interrupted
		public void run() {
            try {
				waitNow();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            sendBack();
        }

		// method to wait until the thread is ready to run
		// Pre: none
		// Post: waits until the thread is ready to run and then calculates pi
		public final void waitNow() throws InterruptedException {
			while (true){
				synchronized(this){
					//System.out.println("waiting");
					while (!running) {
						wait();
					}
					//System.out.println("Running");
					double phx = Math.random();
					double phy = Math.random();
					if (phx*phx + phy*phy < 1) {
						inSide++;
					}
					points++;
					sendBack();
				}
			}
		}	

		// method to calculate pi and send it back to the main thread
		// Pre: none
		// Post: calculates pi and sends it back to the main thread
		synchronized private static void sendBack() {
			curr = (double)4*inSide/points;
			//System.out.println("Current Pi: " + curr);
		}

		// method to set the thread to ready and notify all threads
		// Pre: none
		// Post: sets the thread to ready and notifies all threads
		synchronized void setReady() {
			running = true;
			System.out.println("Setting ready");
			notifyAll();
		}

		// method to set the thread to not ready
		// Pre: none
		// Post: sets the thread to not ready
		synchronized void setNotReady() {
			running = false;
			System.out.println("Setting not ready");
		}

	}
}
