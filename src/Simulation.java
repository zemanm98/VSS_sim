import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulation extends JFrame implements ChangeListener {
    public static MyCanvas canvas = new MyCanvas(800, 800);

    public static JLabel stats = new JLabel("0");
    public static ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            stats.setText("Bees: " + canvas.getBees());
            canvas.repaint();
        }
    };
    public static Timer timer = new Timer(51, taskPerformer);

    public void Init() {
        JFrame frame = new JFrame("Bee_SIM");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        JLabel lbl = new JLabel("BEEEEEEEEE");
        pane.add(lbl, BorderLayout.PAGE_START);
        JLabel lbl2 = new JLabel("SIIIIIIIIIIIIIM");
        pane.add(lbl2, BorderLayout.PAGE_END);
        pane.add(stats, BorderLayout.LINE_START);
        JLabel speed = new JLabel("Speed of simulation:");
        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, 1, 3, 2);
        framesPerSecond.addChangeListener(this);

        //Turn on labels at major tick marks.
        framesPerSecond.setMajorTickSpacing(1);
        framesPerSecond.setMinorTickSpacing(1);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        pane.add(framesPerSecond, BorderLayout.LINE_END);
        JPanel center = new JPanel(new GridBagLayout());
        center.add(canvas);
        pane.add(center, BorderLayout.CENTER);
        //Display the window.
        frame.pack();
        frame.setSize(1300, 1000);
        frame.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            int fps = (int) source.getValue();
            switch (fps){
                case 1:
                    timer.setDelay(200);
                    break;
                case 2:
                    timer.setDelay(25);
                    break;
                case 3:
                    timer.setDelay(1);
                    break;
                default:
                    break;
            }

        }
    }

    public void start() {
        Init();
        //Start (or restart) animating!
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
    }
}
