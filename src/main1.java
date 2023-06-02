// import java.io.*;
// import java.lang.System.Logger;
// import java.net.*;
import javax.swing.*;
import java.awt.event.*;

public class main1 extends JFrame
{
    static JTextField textfield = new JTextField();
    static JLabel label = new JLabel("nothing");
    static JLabel label1 = new JLabel("text submitted here");
 
    // Main driver method
    public static void main(String[] args)
    {
        // Creating a button with no text and caption
        JFrame frame = new JFrame();

        // JLabel label = new JLabel("nothing");
 
        // Creating a button with the specified iccon object
        JButton button = new JButton("button!");

        // init panel
        // JPanel panel = new JPanel();
 
        // Setting frame size using setSize(),setLayout(),
        // setBounds
 
        // Setting width and height of a frame
        // using setSize() method
        frame.setSize(375, 250);
 
        frame.setLayout(null);
        button.setBounds(40, 50, 100, 20);
        textfield.setBounds(40, 20, 150, 20);
        label.setBounds(40, 70, 100, 20);
        label1.setBounds(40, 90, 100, 20);
 
        // Component added to panel
        // panel.add(button);
        // panel.add(textfield);
        // panel.add(label);

        // Componen added to frame
        frame.add(button);
        frame.add(textfield);
        frame.add(label);
        frame.add(label1);


 
        // Object of type actionlistener is created
        // with reference of actionperformclass
        ActionListener listener = new actionperformclass();
 
        // Adding the instance of event handler
        // as listener of component
        button.addActionListener(listener);
        frame.setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }    
}
