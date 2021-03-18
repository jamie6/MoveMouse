import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.ImageIcon;

public class SettingsFrame extends JFrame {
    private final int gap = 20;
    private Wrappers.IntegerWrapper time, distance;
    private GridLayout gridLayout = new GridLayout(0,2, 100, 100);
    private JTextField timeTextField, distanceTextField;
    
    public SettingsFrame(String name, Wrappers.IntegerWrapper time, Wrappers.IntegerWrapper distance) throws
            UnsupportedLookAndFeelException,
            IllegalAccessException,
            InstantiationException,
            ClassNotFoundException {
        super(name);
        setIconImage(new ImageIcon("icons/smallicon.png").getImage());
        gridLayout.setHgap(gap); //Set horizontal gap value
        gridLayout.setVgap(gap); //Set vertical gap value
        this.time = time;
        this.distance = distance;
        setResizable(false);

        // set the look and feel
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        addComponentsToPane(getContentPane());
    }
    
    private void addComponentsToPane(final Container contentPane) {
        final JPanel jPanel = new JPanel();
        jPanel.setLayout(gridLayout);
        jPanel.setBorder(BorderFactory.createEmptyBorder(gap,gap,gap,gap));
        
        JLabel timeLabel = new JLabel("Time (ms): ");
        JLabel distanceLabel = new JLabel("Distance (px): ");

        timeTextField = new JTextField(String.valueOf(time.getValue()), 15);
        distanceTextField = new JTextField(String.valueOf(distance.getValue()), 15);

        JButton apply = new JButton("Apply");
        JButton cancel = new JButton("Cancel");

        // apply button logic
        apply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int newTime = Integer.parseInt(timeTextField.getText()); // parse text field
                    int newDistance = Integer.parseInt(distanceTextField.getText()); // parse text field
                    time.setValue(newTime);
                    distance.setValue(newDistance);
                    hideSettingsWindow();
                } catch(NumberFormatException exception) {
                    System.out.println(exception.getMessage());
                    timeTextField.setText(time.toString());
                    distanceTextField.setText(distance.toString());
                }
            }
        });

        // cancel button logic
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hideSettingsWindow();
            }
        });

        jPanel.add(timeLabel);
        jPanel.add(timeTextField);
        jPanel.add(distanceLabel);
        jPanel.add(distanceTextField);
        jPanel.add(apply);
        jPanel.add(cancel);

        gridLayout.layoutContainer(jPanel);
        contentPane.add(jPanel, BorderLayout.CENTER);

        getRootPane().setDefaultButton(apply);
    }

    public void showSettingsWindow() {
        timeTextField.setText(time.toString());
        distanceTextField.setText(distance.toString());
        pack();
        setVisible(true);
    }

    public void hideSettingsWindow() {
        setVisible(false);
    }
}