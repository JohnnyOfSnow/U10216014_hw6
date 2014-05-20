import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.Timer;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class BounceBallApplet extends javax.swing.JApplet {

  static {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {}
  }

  public static void main(String args[]){
    BounceBallJFrame frame = new BounceBallJFrame();
     
  }
  
  // 建構函式
  public BounceBallApplet() {
    System.out.println("JApplet建構函式");
  
  }

  public void init() {
    System.out.println("JApplet init()");
    

    DisplayPanel displayPanel = new DisplayPanel();
    
    // JDK 1.4的用法
    // 取得其Content Pane 
    //Container contentPane = getContentPane();
    // 定義 Layout Manager 為 BorderLayout
    //contentPane.setLayout(new BorderLayout());
    // 將物件加至Content Pane中
    //contentPane.add(displayPanel, BorderLayout.CENTER);

    // JDK 5.0之後的用法
    // 直接定義JFrame之Layout BorderLayout
    this.setLayout(new BorderLayout());
    this.add(displayPanel, BorderLayout.CENTER);
  }

  public void start() {
    System.out.println("JApplet start()");
   
  }
  
  public void stop() {
    System.out.println("JApplet stop()");
  }
  
  public void destroy() {
    System.out.println("JApplet destroy()");
  }
}

class BounceBallJFrame extends javax.swing.JFrame {
  // 建構函式
  public BounceBallJFrame() {
    super("Bounce Ball JFrame");

    //DisplayPanel displayPanel = new DisplayPanel();
    BallControl myControl = new BallControl();
    

    // JDK 1.4的用法
    // 取得其Content Pane 
    //Container contentPane = getContentPane();
    // 定義 Layout Manager 為 BorderLayout
    //contentPane.setLayout(new BorderLayout());
    // 將物件加至Content Pane中
    //contentPane.add(displayPanel, BorderLayout.CENTER);

    // JDK 5.0之後的用法
    // 直接定義JFrame之Layout BorderLayout
    this.setLayout(new BorderLayout());
    this.add(myControl, BorderLayout.CENTER);

    this.validate();
    this.setSize(new Dimension(600, 400));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}

class DisplayPanel extends javax.swing.JPanel {
  String text;
  
  // 建構函式
  public DisplayPanel() {
    
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    
			
  }
}

class Ball extends JPanel {
  private int delay = 10;

  // Create a timer with delay 1000 ms
  private Timer timer = new Timer(delay, new TimerListener());

  private int x = 0; private int y = 0; // Current ball position
  private int radius = 5; // Ball radius
  private int dx = 2; // Increment on ball's x-coordinate
  private int dy = 2; // Increment on ball's y-coordinate

  public Ball() {
    timer.start();
  }

  private class TimerListener implements ActionListener {
    @Override /** Handle the action event */
    public void actionPerformed(ActionEvent e) {
      repaint();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(Color.red);

    // Check boundaries
    if (x < 0 || x > getWidth()) 
      dx *= -1;
    if (y < 0 || y > getHeight()) 
      dy *= -1;

    // Adjust ball position
    x += dx;
    y += dy;
    g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
  }

  public void suspend() {
    timer.stop(); // Suspend timer
  }

  public void resume() {
    timer.start(); // Resume timer
  }

  public void setDelay(int delay) {
    this.delay = delay;
    timer.setDelay(delay);
  }
}

class BallControl extends JPanel {
  private Ball ball = new Ball();
  private JButton jbtSuspend = new JButton("Suspend");
  private JButton jbtResume = new JButton("Resume");
  private JScrollBar jsbDelay = new JScrollBar();

  public BallControl() {
    // Group buttons in a panel
    JPanel panel = new JPanel();
    panel.add(jbtSuspend);
    panel.add(jbtResume);

    // Add ball and buttons to the panel
    ball.setBorder(new javax.swing.border.LineBorder(Color.red));
    jsbDelay.setOrientation(JScrollBar.HORIZONTAL);
    ball.setDelay(jsbDelay.getMaximum());
    setLayout(new BorderLayout());
    add(jsbDelay, BorderLayout.NORTH);
    add(ball, BorderLayout.CENTER);
    add(panel, BorderLayout.SOUTH);

    // Register listeners
    jbtSuspend.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ball.suspend();
      }
    });
    jbtResume.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ball.resume();
      }
    });
    jsbDelay.addAdjustmentListener(new AdjustmentListener() {
      public void adjustmentValueChanged(AdjustmentEvent e) {
        ball.setDelay(jsbDelay.getMaximum() - e.getValue());
      }
    });
  }
}

