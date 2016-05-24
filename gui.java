/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.ui;

/**
 *
 * @author MuhammadJawad
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import static db.ui.executioner.g;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class gui extends JPanel {
    
    public static JButton add, predict,add_enter,p_enter,back,back2;
    static JFrame frame = new JFrame("Ambulance managemnet system");
    static JFrame frame_add = new JFrame("Ambulance managemnet system");
    static JFrame frame_predict = new JFrame("Ambulance managemnet system");
    
    
    static JLabel headerLabel,done;
    static int red,green,blue,blue2;
    static Color myBlue,myBlue2;
    static JLabel location,location2;
    static JLabel post,post2;
    static JTextField loc,loc2; 
    static JTextField po;
    static JLabel answer;
    static gui mygui = new gui();
    static gui mygui2 = new gui();
    static gui mygui3 = new gui();
    gui(){
    add = new JButton("ADD");
    back = new JButton("BACK");
    back2 = new JButton("BACK");
    add_enter = new JButton("ENTER");
    p_enter = new JButton("ENTER");
    
    predict=new JButton("PREDICT");
    headerLabel = new JLabel("AMBULANCE NETWORK MANAGEMENT SYSTEM", JLabel.CENTER);
    done = new JLabel(" ", JLabel.CENTER);
    
    location= new JLabel("LOCATION OF ACCIDENT: ", JLabel.CENTER);
    post= new JLabel("AMBULANCE POST NUMBER #: ", JLabel.CENTER);
    location2= new JLabel("LOCATION OF ACCIDENT: ", JLabel.CENTER);
    post2= new JLabel("AMBULANCE POST NUMBER #: ", JLabel.CENTER);
    
    answer= new JLabel(" ", JLabel.CENTER);
    loc= new JTextField(25);
    loc2= new JTextField(25);
    po=new JTextField(25);
    
    red = 100;
    green = 100;
    blue = 150;
    blue2=200;
    myBlue = new Color(red,green,blue);
    myBlue2 = new Color(red,green,blue2);
    
    add.setForeground(myBlue2);
    predict.setForeground(myBlue2);
    back.setForeground(myBlue2);
    back2.setForeground(myBlue2);
    
    add.setPreferredSize(new Dimension(90, 40));
    predict.setPreferredSize(new Dimension(90, 40));
    back.setPreferredSize(new Dimension(180, 30));
    back2.setPreferredSize(new Dimension(180, 30));
    
    add_enter.setPreferredSize(new Dimension(180, 30));
    p_enter.setPreferredSize(new Dimension(180, 30));
   // enter.setLocation(50,60);    
//add.setBounds(60, 400, 220, 30);
    }
    
 
    static void createGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
                
                
		
		
                frame.setSize(800,600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mygui.setOpaque(true); // content panes must be opaque
		frame.getRootPane().setDefaultButton(add);
                frame.setContentPane(mygui);
                frame.getContentPane().setBackground(myBlue);
		frame.setVisible(true);
                frame.add(headerLabel);
                frame.add(add);
                frame.add(predict);
                
                
                //add frame
                
                frame_add.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        mygui2.setOpaque(true); // content panes must be opaque
		frame_add.getRootPane().setDefaultButton(add_enter);
                frame_add.setContentPane(mygui2);
                frame_add.getContentPane().setBackground(myBlue);
                frame_add.setSize(800,600);      
                frame_add.add(location);
                frame_add.add(loc);
                frame_add.add(post);
                frame_add.add(po);
                frame_add.add(add_enter);
                frame_add.add(back);
                frame_add.add(done);
                add_enter.setLocation(50,60);
                
                
               //predict frame
                
                frame_predict.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        mygui3.setOpaque(true); // content panes must be opaque
		frame_predict.getRootPane().setDefaultButton(p_enter);
                frame_predict.setContentPane(mygui3);
                frame_predict.getContentPane().setBackground(myBlue);
                frame_predict.setSize(800,600);      
                frame_predict.add(location2);
                frame_predict.add(loc2);
                //frame_predict.add(post);
                //frame_predict.add(po);
                frame_predict.add(p_enter);
                frame_predict.add(back2);
                //frame_predict.add(back);
                p_enter.setLocation(50,60);
                
                
                
                
                
                
                headerLabel.setFont(new Font("Serif",Font.BOLD, 30));
                location.setFont(new Font("Serif",Font.BOLD, 24));
                location2.setFont(new Font("Serif",Font.BOLD, 24));
                post.setFont(new Font("Serif",Font.BOLD, 24));
                answer.setFont(new Font("Serif",Font.BOLD, 30));
                done.setFont(new Font("Serif",Font.BOLD, 28));
                
                
                
                
                add.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             //add.hide();
             //predict.hide();
             frame.setVisible(false);
             frame_add.setVisible(true);
             
            //headerLabel.setText("add Button clicked.");
         }          
      });
                
                
                
                back.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             
             frame.setVisible(true);
             frame_predict.setVisible(false);
             answer.hide();
            
         }          
      });
                
                
                
                back2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             //add.hide();
             //predict.hide();
             frame.setVisible(true);
             frame_add.setVisible(false);
             g.done.hide();
            //headerLabel.setText("add Button clicked.");
         }          
      });
                
                
                
                
                
                //predict button
                
                predict.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             
             frame.setVisible(false);
             frame_predict.setVisible(true);
            //.setText("Ok Button clicked.");
         }          
      });
                
             //enter button for adding
                
                add_enter.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             
             
             
            //.setText("Ok Button clicked.");
         }          
      });
                
                //enter button for predicting
                 p_enter.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             
             
             
            //.setText("Ok Button clicked.");
         }          
      });
                
                
	}
    
    
    
    
}



