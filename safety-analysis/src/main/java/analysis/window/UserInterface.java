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
  private JLabel info;
  private JLabel textLabel;
  private JPanel controlPanel;
  private JPanel outputPanel;

  public UserInterface(QueryProcessor qIn){
    queryP = qIn;
    setGUI();
  }

  public void setGUI(){
    mainframe = new JFrame("Safety Analysis of LA");
    mainframe.setSize(1000, 1000);
    mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainframe.setLayout(new GridLayout(1, 2));

    header = new JLabel("Crime and Arrest Data Analysis of Los Angeles (2010-2019)", JLabel.CENTER);
    header.setSize(500, 100);

    String infoText = "<html>"
      + "<center> Year: [2010-2019] and Area ID: [1-21]</center> <br>"
      + "<b>Query 1 (Q1):</b> Retrieves the number of crime reports file in [year] <br>"
      + "<b>Query 2 (Q2):</b> Retrieves the most common types of crime by either [year] or [area id] <br>"
      + "<b>Query 3 (Q3):</b> Retrieves the area with highest crime rate in [year] <br>"
      + "<b>Query 4 (Q4):</b> Retrieves overall demographic info of offendors in [year] <br>"
      + "<b>Query 5 (Q5):</b> Calculates future predictions of offendors' demographic <br>"
      + "<b>Query 6 (Q6):</b> Calculates age statistics of offendors <br>"
      + "<b>Query 7 (Q7):</b> Calculates the safety value and grade based on [area id] <br>"
      + "<b>Query 8 (Q8):</b> Calculates the future safety value and grade based on [area id]<br>"
      + "</html>";

    info = new JLabel(infoText, JLabel.CENTER);
    info.setSize(500, 300);
    info.setBorder(BorderFactory.createEmptyBorder(25, 25, 10, 10)); //top, left, bottom, right?

    textLabel = new JLabel("", JLabel.CENTER);
    textLabel.setSize(500, 800);
    textLabel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25)); //top, left, bottom, right?

    controlPanel = new JPanel();
    controlPanel.setLayout(null);
    controlPanel.setBackground(new Color(206, 236, 245));

    outputPanel = new JPanel();

    outputPanel.setLayout(null);
    outputPanel.setBackground(new Color(250, 250, 250));



    mainframe.add(controlPanel);
    mainframe.add(outputPanel);

    controlPanel.add(header);
    controlPanel.add(info);
    outputPanel.add(textLabel);

    setButtons();
    mainframe.setVisible(true);

  }

  public void setButtons(){
    //For Query 1
    JLabel q1Label = new JLabel("Q1) Insert year: ");
    q1Label.setBounds(10, 280, 150, 25); // x, y, width, height

    JTextField q1Input = new JTextField("2015", 4);
    q1Input.setBounds(150, 280, 105, 25);

    JButton q1Button = new JButton("OK");
    q1Button.setActionCommand("OK");
    q1Button.setBounds(300, 280, 80, 25);

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
      JLabel q2_1Label = new JLabel("Q2) Insert year: ");
      q2_1Label.setBounds(10, 320, 100, 25); // x, y, width, height

      JTextField q2_1Input = new JTextField("2015", 4);
      q2_1Input.setBounds(110, 320, 70, 25);

      JButton q2_1Button = new JButton("OK");
      q2_1Button.setActionCommand("OK");
      q2_1Button.setBounds(190, 320, 80, 25);

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
      JLabel q2_2Label = new JLabel("Area ID: ");
      q2_2Label.setBounds(280, 320, 100, 25); // x, y, width, height

      JTextField q2_2Input = new JTextField("1", 4);
      q2_2Input.setBounds(330, 320, 70, 25);

      JButton q2_2Button = new JButton("OK");
      q2_2Button.setActionCommand("OK");
      q2_2Button.setBounds(400, 320, 80, 25);

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
      JLabel q3Label = new JLabel("Q3) Insert year: ");
      q3Label.setBounds(10, 360, 150, 25); // x, y, width, height

      JTextField q3Input = new JTextField("2015", 4);
      q3Input.setBounds(150, 360, 105, 25);

      JButton q3Button = new JButton("OK");
      q3Button.setActionCommand("OK");
      q3Button.setBounds(300, 360, 80, 25);

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
        JLabel q4_1Label = new JLabel("Q4) Insert year: ");
        q4_1Label.setBounds(10, 400, 100, 25); // x, y, width, height

        JTextField q4_1Input = new JTextField("2015", 4);
        q4_1Input.setBounds(110, 400, 70, 25);

        JButton q4_1Button = new JButton("OK");
        q4_1Button.setActionCommand("OK");
        q4_1Button.setBounds(190, 400, 80, 25);

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
        JLabel q4_2Label = new JLabel("Area ID: ");
        q4_2Label.setBounds(280, 400, 100, 25); // x, y, width, height

        JTextField q4_2Input = new JTextField("1", 4);
        q4_2Input.setBounds(330, 400, 70, 25);

        JButton q4_2Button = new JButton("OK");
        q4_2Button.setActionCommand("OK");
        q4_2Button.setBounds(400, 400, 80, 25);

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
        JLabel q5Label = new JLabel("Q5) Click to get predicted results: ");
        q5Label.setBounds(10, 440, 270, 25); // x, y, width, height

        JButton q5Button = new JButton("Predict");
        q5Button.setActionCommand("Predict");
        q5Button.setBounds(300, 440, 80, 25);

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
        JLabel q6Label = new JLabel("Q6) Insert year: ");
        q6Label.setBounds(10, 480, 150, 25); // x, y, width, height

        JTextField q6Input = new JTextField("2015", 4);
        q6Input.setBounds(150, 480, 105, 25);

        JButton q6Button = new JButton("OK");
        q6Button.setActionCommand("OK");
        q6Button.setBounds(300, 480, 80, 25);

        controlPanel.add(q6Label);
        controlPanel.add(q6Input);
        controlPanel.add(q6Button);

        q6Button.addActionListener(new ActionListener() {
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
        JLabel q7Label = new JLabel("Q7) Insert Area ID: ");
        q7Label.setBounds(10, 520, 185, 25); // x, y, width, height

        JTextField q7Input = new JTextField("1", 4);
        q7Input.setBounds(150, 520, 105, 25);

        JButton q7Button = new JButton("OK");
        q7Button.setActionCommand("OK");
        q7Button.setBounds(300, 520, 80, 25);

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

        //For Query 8
        JLabel q8Label = new JLabel("Q8) Insert Area ID: ");
        q8Label.setBounds(10, 560, 185, 25); // x, y, width, height

        JTextField q8Input = new JTextField("1", 4);
        q8Input.setBounds(150, 560, 105, 25);

        JButton q8Button = new JButton("OK");
        q8Button.setActionCommand("OK");
        q8Button.setBounds(300, 560, 80, 25);

        controlPanel.add(q8Label);
        controlPanel.add(q8Input);
        controlPanel.add(q8Button);

        q8Button.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                String input = q8Input.getText();
                Result r = queryP.process(QNames.SAFETY_VAL_FUTURE, input);
                if(r == null){
                  System.out.println("ERROR HERE IN UI");
                }
                String result = r.toTextString();
                textLabel.setText(result);
             }
          });

  }

}
