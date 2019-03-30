/*
 * SunSpotHostApplication.java
 *
 * Created on 02-Feb-2010 21:14:44;
 */
package com.andrewmcglynn.application;
import com.andrewmcglynn.sunspot.CommunicationInterface;
import com.andrewmcglynn.sunspot.SunSpotReceiver;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

public class SunSpotHostApplication implements CommunicationInterface{

    private JFrame frame;
    private JFrame loginFrame;
    private SunSpotReceiver rightReceiver;
    private SunSpotReceiver leftReceiver;
    private PhotoApplication app;
    private ArrayList<BufferedImage> images;
    private Environment3D env;
    private BufferStrategy strategy;
    private JPasswordField passwordField;
    private LoginDatabase database;
    private JTextField userNameField;
    private boolean valid = false;
    private JButton loginButton;
    private JButton cancelButton;

    public static void main(String[] args) {
        new SunSpotHostApplication().login();
        

    }
    public void login(){

        Thread diagnosticThread = new Thread(){
            @Override
            public void run(){
                while(true){
                    System.out.println("Max Memory:"+Runtime.getRuntime().maxMemory());
                    if(Runtime.getRuntime().freeMemory() < Runtime.getRuntime().totalMemory()/10){
                        System.out.println("Running Garbage Collector");
                        Runtime.getRuntime().gc();
                    }
                    try{
                        Thread.sleep(1000);
                    }
                    catch(Exception e){}
                }
            }
        };
        diagnosticThread.start();

        try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e){
			e.printStackTrace();
		}

        loginFrame = new JFrame("Login");
        JPanel panel = new JPanel(new MigLayout());
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");
        passwordField = new JPasswordField();
        userNameField = new JTextField(30);
        panel.add(new JLabel("User Name "));
        panel.add(userNameField,"growx,wrap");
        panel.add(new JLabel("Password "));
        panel.add(passwordField,"growx,wrap");
        panel.add(new JLabel(""));
        panel.add(loginButton,"split");
        panel.add(cancelButton);
        loginFrame.add(panel);
        loginFrame.setSize(new Dimension(320, 120));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        loginFrame.setLocation(screenSize.width/3, screenSize.height/3);
        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        database = new LoginDatabase();

        try{
            database.connect();
        }
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to Database!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        ActionListener loginListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateLogin();
            }
        };
        passwordField.addActionListener(loginListener);
        loginButton.addActionListener(loginListener);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //the javax swing timer doesn't seem to work when the execute function is
        //called from an event, it has to be poled to work correctly, Bug???
        while(!valid){}
        execute();
    }

   public void execute(){
        rightReceiver = new SunSpotReceiver(RIGHT_COMM_PORT);
        leftReceiver = new SunSpotReceiver(LEFT_COMM_PORT);

        rightReceiver.start();
        leftReceiver.start();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setUndecorated(true);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = ge.getDefaultScreenDevice();

        // Use full screen exclusive mode.
        // This allows the program to draw onto the screen faster than ordinary JFrame programs.
        graphicsDevice.setFullScreenWindow(frame);
        frame.validate();

        frame.createBufferStrategy(2);
        
        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {    }
            public void keyReleased(KeyEvent e) {   }
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }
        });
        strategy = frame.getBufferStrategy();
        
        
        app = new PhotoApplication(leftReceiver, rightReceiver);
        
        startPhotoApplication();
        env = new Environment3D(rightReceiver, leftReceiver,strategy,frame);
        env.run();
        System.exit(0);
   }

   public void startPhotoApplication(){
        app.setup();
        app.setupGUI(strategy);
        String[] photoNames = {"images/Desert.jpg", "images/Jellyfish.jpg","images/Koala.jpg",
                            "images/Penguins.jpg","images/Lighthouse.jpg","images/Tulips.jpg"};

        if(images == null) app.loadPhotos(photoNames);
        else app.loadPhotos(images);
        images = app.getImages();
        app.resizeAllPhotos(4);
        
        app.run();
        
   }
   public void validateLogin(){
        //read the username and password fields
        char[] p = passwordField.getPassword();
        String password = new String(p);
        String username = userNameField.getText();
        //check the username against the password
        if(database.validate(username, password)){
            JOptionPane.showMessageDialog(null, "Login Successful! \nPut on the Interactive Gloves and turn the gloves on", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            valid = true;
            loginFrame.setVisible(false);
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Username or Password incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
        }
   }
}
