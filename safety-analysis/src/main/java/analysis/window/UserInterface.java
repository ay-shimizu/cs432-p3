package analysis.window;
import analysis.queries.QueryProcessor;
import analysis.queries.QNames;
import analysis.util.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserInterface{
  private QueryProcessor queryP;

  private JFrame mainframe;
  private JLabel header;
  private JLabel textLabel;
  private JPanel controlPanel;
  private JScrollPane outputPanel;

  public UserInterface(QueryProcessor qIn){
    queryP = qIn;
    setGUI();
  }

  public void setGUI(){
    mainframe = new JFrame("Safety Analysis of LA");
    mainframe.setSize(1000, 800);
    mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainframe.setLayout(new GridLayout(1, 2));

    header = new JLabel("This is the header... write some instructions here", JLabel.CENTER);
    header.setSize(500, 100);
    textLabel = new JLabel("hmmmm", JLabel.CENTER);
    textLabel.setSize(500, 800);

    controlPanel = new JPanel();
    controlPanel.setLayout(null);
    controlPanel.setBackground(new Color(206, 236, 245));

    outputPanel = new JScrollPane();
    // outputPanel.
    outputPanel.setLayout(null);
    outputPanel.setBackground(new Color(250, 250, 250));


    mainframe.add(controlPanel);
    mainframe.add(outputPanel);

    controlPanel.add(header);
    outputPanel.add(textLabel);

    setButtons();
    mainframe.setVisible(true);

  }

  public void setButtons(){
    //For Query 1
    JLabel q1Label = new JLabel("Insert year: ");
    q1Label.setBounds(10, 80, 100, 25); // x, y, width, height

    JTextField q1Input = new JTextField("2015", 4);
    q1Input.setBounds(115, 80, 105, 25);

    JButton q1Button = new JButton("OK");
    q1Button.setActionCommand("OK");
    q1Button.setBounds(280, 80, 80, 25);

    controlPanel.add(q1Label);
    controlPanel.add(q1Input);
    controlPanel.add(q1Button);

    q1Button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            String input = q1Input.getText();
            Result r = queryP.process(QNames.NUM_CRIMES, input);
            if(r == null){
              System.out.println("ERROR HERE IN UI");
            }
            String result = r.toTextString();
            textLabel.setText(result);
         }
      });

      //For Query 2
      JLabel q2Label = new JLabel("Insert year: ");
      q2Label.setBounds(10, 120, 100, 25); // x, y, width, height

      JTextField q2Input = new JTextField("2015", 4);
      q2Input.setBounds(115, 120, 105, 25);

      JButton q2Button = new JButton("OK");
      q2Button.setActionCommand("OK");
      q2Button.setBounds(280, 120, 80, 25);

      controlPanel.add(q2Label);
      controlPanel.add(q2Input);
      controlPanel.add(q2Button);

      q2Button.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              String input = q2Input.getText();
              Result r = queryP.process(QNames.HIGHEST_CRIMES, input);
              if(r == null){
                System.out.println("ERROR HERE IN UI");
              }
              String result = r.toTextString();
              textLabel.setText(result);
           }
        });


        //For Query 7
        JLabel q7Label = new JLabel("Insert Area ID: ");
        q7Label.setBounds(10, 320, 100, 25); // x, y, width, height

        JTextField q7Input = new JTextField("1", 4);
        q7Input.setBounds(115, 320, 105, 25);

        JButton q7Button = new JButton("OK");
        q7Button.setActionCommand("OK");
        q7Button.setBounds(280, 320, 80, 25);

        controlPanel.add(q7Label);
        controlPanel.add(q7Input);
        controlPanel.add(q7Button);

        q7Button.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                String input = q7Input.getText();
                Result r = queryP.process(QNames.SAFETY_VAL, input);
                if(r == null){
                  System.out.println("ERROR HERE IN UI");
                }
                String result = r.toTextString();
                textLabel.setText(result);
             }
          });

  }

  public void printSomething(){
    System.out.println("HEllo...");
  }
}
