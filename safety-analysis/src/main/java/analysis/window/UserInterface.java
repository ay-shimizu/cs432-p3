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
    JLabel q1Label = new JLabel("Q1) Insert year(2010-2019): ");
    q1Label.setBounds(10, 80, 180, 25); // x, y, width, height

    JTextField q1Input = new JTextField("2015", 4);
    q1Input.setBounds(180, 80, 105, 25);

    JButton q1Button = new JButton("OK");
    q1Button.setActionCommand("OK");
    q1Button.setBounds(300, 80, 80, 25);

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

      //For Query 2_1
      JLabel q2_1Label = new JLabel("Q2) Insert year(2010-2019): ");
      q2_1Label.setBounds(10, 120, 180, 25); // x, y, width, height

      JTextField q2_1Input = new JTextField("2015", 4);
      q2_1Input.setBounds(180, 120, 105, 25);

      JButton q2_1Button = new JButton("OK");
      q2_1Button.setActionCommand("OK");
      q2_1Button.setBounds(300, 120, 80, 25);

      controlPanel.add(q2_1Label);
      controlPanel.add(q2_1Input);
      controlPanel.add(q2_1Button);

      q2_1Button.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              String input = q2_1Input.getText();
              Result r = queryP.process(QNames.COMMON_CRIMES_YEAR, input);
              if(r == null){
                System.out.println("ERROR HERE IN UI");
              }
              String result = r.toTextString();
              textLabel.setText(result);
           }
        });
      //For Query 2_2
      JLabel q2_2Label = new JLabel("Q2) Insert area ID(1-50): ");
      q2_2Label.setBounds(10, 160, 180, 25); // x, y, width, height

      JTextField q2_2Input = new JTextField("1", 4);
      q2_2Input.setBounds(180, 160, 105, 25);

      JButton q2_2Button = new JButton("OK");
      q2_2Button.setActionCommand("OK");
      q2_2Button.setBounds(300, 160, 80, 25);

      controlPanel.add(q2_2Label);
      controlPanel.add(q2_2Input);
      controlPanel.add(q2_2Button);

      q2_2Button.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              String input = q2_2Input.getText();
              Result r = queryP.process(QNames.COMMON_CRIMES_AREA, input);
              if(r == null){
                System.out.println("ERROR HERE IN UI");
              }
              String result = r.toTextString();
              textLabel.setText(result);
           }
        });

      //For Query 3
      JLabel q3Label = new JLabel("Q3) Insert year(2010-2019): ");
      q3Label.setBounds(10, 200, 180, 25); // x, y, width, height

      JTextField q3Input = new JTextField("2015", 4);
      q3Input.setBounds(180, 200, 105, 25);

      JButton q3Button = new JButton("OK");
      q3Button.setActionCommand("OK");
      q3Button.setBounds(300, 200, 80, 25);

      controlPanel.add(q3Label);
      controlPanel.add(q3Input);
      controlPanel.add(q3Button);

      q3Button.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              String input = q3Input.getText();
              Result r = queryP.process(QNames.HIGHEST_CRIMES, input);
              if(r == null){
                System.out.println("ERROR HERE IN UI");
              }
              String result = r.toTextString();
              textLabel.setText(result);
           }
        });
        //For Query 4_1
        JLabel q4_1Label = new JLabel("Q4) Insert year(2010-2019): ");
        q4_1Label.setBounds(10, 240, 180, 25); // x, y, width, height

        JTextField q4_1Input = new JTextField("2015", 4);
        q4_1Input.setBounds(180, 240, 105, 25);

        JButton q4_1Button = new JButton("OK");
        q4_1Button.setActionCommand("OK");
        q4_1Button.setBounds(300, 240, 80, 25);

        controlPanel.add(q4_1Label);
        controlPanel.add(q4_1Input);
        controlPanel.add(q4_1Button);

        q4_1Button.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                String input = q4_1Input.getText();
                Result r = queryP.process(QNames.DEMO_INFO_YEAR, input);
                if(r == null){
                  System.out.println("ERROR HERE IN UI");
                }
                String result = r.toTextString();
                textLabel.setText(result);
             }
          });
        //For Query 4_2
        JLabel q4_2Label = new JLabel("Q4) Insert area ID(1-50): ");
        q4_2Label.setBounds(10, 280, 180, 25); // x, y, width, height

        JTextField q4_2Input = new JTextField("1", 4);
        q4_2Input.setBounds(180, 280, 105, 25);

        JButton q4_2Button = new JButton("OK");
        q4_2Button.setActionCommand("OK");
        q4_2Button.setBounds(300, 280, 80, 25);

        controlPanel.add(q4_2Label);
        controlPanel.add(q4_2Input);
        controlPanel.add(q4_2Button);

        q4_2Button.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                String input = q4_2Input.getText();
                Result r = queryP.process(QNames.DEMO_INFO_AREA, input);
                if(r == null){
                  System.out.println("ERROR HERE IN UI");
                }
                String result = r.toTextString();
                textLabel.setText(result);
             }
          });
        //For Query 5
        JLabel q5Label = new JLabel("Q5) Insert year(2010-2019): ");
        q5Label.setBounds(10, 320, 180, 25); // x, y, width, height

        JButton q5Button = new JButton("Predict");
        q5Button.setActionCommand("Predict");
        q5Button.setBounds(300, 320, 80, 25);

        controlPanel.add(q5Label);
        controlPanel.add(q5Button);

        q5Button.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                Result r = queryP.process(QNames.DEMO_FUTURE, "");
                if(r == null){
                  System.out.println("ERROR HERE IN UI");
                }
                String result = r.toTextString();
                textLabel.setText(result);
             }
          });
        //For Query 6
        JLabel q6Label = new JLabel("Q6) Insert year(2010-2019): ");
        q6Label.setBounds(10, 360, 180, 25); // x, y, width, height

        JTextField q6Input = new JTextField("2015", 4);
        q6Input.setBounds(180, 360, 105, 25);

        JButton q6Button = new JButton("OK");
        q6Button.setActionCommand("OK");
        q6Button.setBounds(300, 360, 80, 25);

        controlPanel.add(q6Label);
        controlPanel.add(q6Input);
        controlPanel.add(q6Button);

        q5Button.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                String input = q6Input.getText();
                Result r = queryP.process(QNames.OFFEND_STATS, input);
                if(r == null){
                  System.out.println("ERROR HERE IN UI");
                }
                String result = r.toTextString();
                textLabel.setText(result);
             }
          });


        //For Query 7
        JLabel q7Label = new JLabel("Insert Area ID(1-50): ");
        q7Label.setBounds(10, 400, 180, 25); // x, y, width, height

        JTextField q7Input = new JTextField("1", 4);
        q7Input.setBounds(180, 400, 105, 25);

        JButton q7Button = new JButton("OK");
        q7Button.setActionCommand("OK");
        q7Button.setBounds(300, 400, 80, 25);

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

}
