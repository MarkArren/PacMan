package Panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterNamePanel extends JPanel {
    private MainFrame frame;

    private JTextField textField;
    private JLabel textFieldLabel;
    private JButton butConfirm;

    private String nameOfPlayer;

    private Color fontColour;


    public EnterNamePanel(int width, int height, MainFrame frame){
        this.frame = frame;

        setFont(frame.getFont());
        fontColour = frame.getFontColour();

        setBorder(new EmptyBorder(30, 20, 30, 20));
        setBackground(Color.black);
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add 'Game Over' text
        JLabel gameOverLabel = new JLabel("GAME OVER ");
        gameOverLabel.setFont(getFont());
        gameOverLabel.setForeground(Color.red);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(gameOverLabel);


        // Add text field label
        textFieldLabel = new JLabel("ENTER INITIALS: ");
        textFieldLabel.setFont(getFont());
        textFieldLabel.setForeground(Color.ORANGE);
        textFieldLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(textFieldLabel);

        // Add text field
        textField = new JTextField();
        textField.setColumns(3);
        textField.setMaximumSize(new Dimension(50, 30));
        textField.setOpaque(false);
        textField.setBorder(new MatteBorder(0, 0, 2, 0 , fontColour));
        textField.setFont(getFont());
        textField.setForeground(fontColour);
        add(textField);

        // Adding spacer between button and text field
        add(Box.createRigidArea(new Dimension(10,10)));

        // Add confirm button
        butConfirm = new Button("CONFIRM", this);
        add(butConfirm);
    }

    public void setNameOfPlayer(String nameOfPlayer) {
        this.nameOfPlayer = nameOfPlayer;
    }

    public String getNameOfPlayer() {
        return nameOfPlayer;
    }

    public JTextField getTextField() {
        return textField;
    }

    public Color getFontColour() {
        return fontColour;
    }

    public MainFrame getFrame() {
        return frame;
    }
}

class Button extends JButton{
    private Color pressedColour = Color.red;
    private Color hoverColour = Color.green;
    private Color backgroundColor = Color.cyan;


    public Button(String name, EnterNamePanel panel){
        super(name);
        setFont(panel.getFont());
        setForeground(panel.getFontColour());
        setBorder(new EmptyBorder(5,5,5,5));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(backgroundColor);

        getModel().addActionListener(new ButtonHandler(panel));
        getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (getModel().isPressed()) {
                    setBackground(pressedColour);
                } else if (getModel().isRollover()) {
                    setBackground(hoverColour);
                } else {
                    setBackground(backgroundColor);
                }
            }
        });
    }
}

class ButtonHandler implements ActionListener{
    JTextField jTextField;
    EnterNamePanel panel;
    MainFrame frame;

    public ButtonHandler(EnterNamePanel panel){
        this.jTextField = panel.getTextField();
        this.panel = panel;
        this.frame = panel.getFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = jTextField.getText().toUpperCase();
        // Replacing any non letters with space
            text = text.replaceAll("[^A-Za-z]", "");

            // Setting name to first three letters of text box
            if (text.length() > 0){
            text = text.substring(0, 3);
            panel.setNameOfPlayer(text);
            frame.addHighScore(text);
        }
    }


}
