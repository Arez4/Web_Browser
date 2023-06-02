// Java Program that creates the file with the method
// actionPerformed(ActionEvent o) of ActionListener

// Importing awt module and Swing class
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.io.*;

// Class
// An action listener that prints a message
public class actionperformclass
	extends main1 implements ActionListener {

	// Method
	public void actionPerformed(ActionEvent event)
	{
		String copystr = new String(" ");
		// String responses = new String();

		try {
			// socket uses the input from the textfield
            Socket socket = new Socket("" + textfield.getText(), 80);

            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

            // exchange msg here
            String message = "hello youtube, give me a list of videos\r\n\r\n";
            bos.write(message.getBytes(), 0, message.length());
            bos.flush();

            String responses = new String(bis.readAllBytes());
            // System.out.println(responses);
			// set the label into the server's response
			label.setText(responses);

			copystr = String.valueOf(responses);

            socket.close();
        } catch (IOException ex) {
            // Logger.getLogger(StreamTest.class.getName()).log(Level.SEVERE, null, ex);
        }


		// settext bottom label to the submitted text
		label1.setText(textfield.getText());
		
		// settext of textfield object of Jtextfield
		textfield.setText(copystr);
		

		
	}
}
