package analysis.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class testListener implements ActionListener{

  private JLabel statusLabel;
  private String inputString;

  public testListener(JLabel labelIn, JTextField textIn){
    statusLabel = labelIn;
    inputString = textIn.getText();
  }

  public String getText(String s){
    return "aaaa" + s;
  }

  public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();
         String out = getText(inputString);

         if( command.equals( "OK" ))  {
            statusLabel.setText(out);

         } else if( command.equals( "Submit" ) )  {
            statusLabel.setText("Submit Button clicked.");
         } else {
            statusLabel.setText("Cancel Button clicked.");
         }
      }
}
