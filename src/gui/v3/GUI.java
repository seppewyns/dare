package gui.v3;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * GUI Class for displaying the CRDT state.
 */
public class GUI {

    /**
     * Handler that can be called for events on the GUI.
     */
    private IGUIHandler handler;

    /**
     * The GUI frame.
     */
    private Frame frame;

    /**
     * The label displaying the current counter value.
     */
    private Label currentValueLabel;
    private Label currentVersionLabel;

    public GUI() { setup(); }
    public static void main(String[] args) { new GUI(); }

    /**
     * Set up the GUI.
     */
    private void setup() {
        frame = new Frame("State-based CRDT increment only counter");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });

        frame.setSize(480, 640);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        frame.setLayout(layout);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        currentValueLabel = new Label("Current value: 0", Label.CENTER);
        frame.add(currentValueLabel);
        layout.setConstraints(currentValueLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        Button button = new Button("Increment");
        frame.add(button);
        layout.setConstraints(button, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        currentVersionLabel = new Label("Current version: ----", Label.CENTER);
        frame.add(currentVersionLabel);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(currentVersionLabel, constraints);

        // call the handler increment on click
        button.addActionListener(e -> {
            if (handler != null)
                handler.increment();
        });

        frame.setVisible(true);
    }

    /**
     * Update the GUI.
     * @param state The updated state.
     */
    public void update(int value) {
        currentValueLabel.setText("Current value: " + value);
    }
    public void updateVersion(String version) { currentVersionLabel.setText("Current version: " + version); }

    /**
     * Set the handler.
     * @param handler The new handler.
     */
    public void setHandler(IGUIHandler handler) {
        this.handler = handler;
    }

}
