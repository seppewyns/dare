package gui.v0;

import java.awt.*;
import java.awt.event.*;

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
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        frame.setLayout(layout);

        currentValueLabel = new Label("Current value: 0    ");
        frame.add(currentValueLabel);

        Button button = new Button("Increment");
        frame.add(button);

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

    /**
     * Set the handler.
     * @param handler The new handler.
     */
    public void setHandler(IGUIHandler handler) {
        this.handler = handler;
    }

}
