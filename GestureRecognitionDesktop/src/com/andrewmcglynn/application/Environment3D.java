package com.andrewmcglynn.application;

import engine3D.Engine3D;
import engine3D.camera.Camera;
import engine3D.math.Transform3D;
import engine3D.math.Vector3D;
import engine3D.shapes.Sphere;
import com.andrewmcglynn.sunspot.CommunicationInterface;
import com.andrewmcglynn.sunspot.SunSpotDigitalIOEvent;
import com.andrewmcglynn.sunspot.SunSpotListener;
import com.andrewmcglynn.sunspot.SunSpotReceiver;
import com.andrewmcglynn.sunspot.SunSpotSwitchEvent;
import com.andrewmcglynn.sunspot.SunSpotTiltEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author andrew
 */
public class Environment3D implements CommunicationInterface{
    
Dimension screenSize;
    private Sphere sph;
    private BufferedImage image;
    private BufferStrategy strategy;
    private boolean paintLines = true;
    private boolean paintPolygons = true;
    private boolean shading = true;
    private SunSpotReceiver leftReceiver;
    private SunSpotReceiver rightReceiver;
    private Transform3D transform;
    private Transform3D zoomTransform;
    private SunSpotCursor leftCursor;
    private SunSpotCursor rightCursor;
    private boolean finished = false;
    private boolean sphExplode = false;
    private JFrame frame;

    public Environment3D(){
        run();
    }


    public Environment3D(SunSpotReceiver rightReceiver, SunSpotReceiver leftReceiver, BufferStrategy strategy, JFrame frame){
       this.strategy = strategy;
       this.frame = frame;
        this.rightReceiver = rightReceiver;
        this.leftReceiver = leftReceiver;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        zoomTransform = new Transform3D();
        //move mouse off the the screen
        try{
            Robot robot = new Robot();
            robot.mouseMove(screenSize.width, screenSize.height-50);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        initialise3DEnvironment();

        leftCursor = new SunSpotCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
        rightCursor = new SunSpotCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
    }

    public void run() {

        setupSunSpotListeners();
        finished = false;

        while(!finished){
            long time = System.currentTimeMillis();
            sph.setPaintLines(paintLines);
            sph.setPaintPolygons(paintPolygons);
            sph.setShading(shading);
            Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
            sph.transform(transform);
            sph.transform(zoomTransform);
            Engine3D.update();
            paint(g);
            while(System.currentTimeMillis() < time + 17){
                Thread.yield();
            }
            strategy.show();
            g.dispose();
        }
    }
    public void setupSunSpotListeners(){

        leftReceiver.addSunSpotListener(new SunSpotListener() {

            @Override
            public void tiltInputReceived(SunSpotTiltEvent ev) {
                Engine3D.camera.translate( 0, ev.getxTilt(), ev.getyTilt() );
            }

            @Override
            public void digitalPinInputChange(SunSpotDigitalIOEvent ev) {
                if(ev.getIoPinNumber() == 0 && ev.pinState() == true){
                    paintLines = !paintLines;
                }
                if(ev.getIoPinNumber() == 1 && ev.pinState() == true){
                    paintPolygons = !paintPolygons;
                }
                if(ev.getIoPinNumber() == 2 && ev.pinState() == true){
                    shading = !shading;
                }
                if(ev.getIoPinNumber() == 3 && ev.pinState() == true){
                    sph.setPolygonColour(new Color((int)(Math.random()*0xffffff)));
                }
            }

            @Override
            public void pushButtonSwitchChange(SunSpotSwitchEvent ev) {

            }
        });
        rightReceiver.addSunSpotListener(new SunSpotListener() {

            public void tiltInputReceived(SunSpotTiltEvent ev) {
                transform = new Transform3D();
                    if(ev.getxTilt() > 10)transform.addRotation(Vector3D.X_AXIS, -5);
                    else if(ev.getxTilt() < -10)transform.addRotation(Vector3D.X_AXIS, 5);
                    else transform.addRotation(Vector3D.X_AXIS, 0);
                    if(ev.getyTilt() > 10)transform.addRotation(Vector3D.Y_AXIS, -5);
                    else if(ev.getyTilt() < -10)transform.addRotation(Vector3D.Y_AXIS, 5);
                    else transform.addRotation(Vector3D.Y_AXIS, 0);

            }

            public void digitalPinInputChange(SunSpotDigitalIOEvent ev) {
                zoomTransform = new Transform3D();
                if(ev.getIoPinNumber() == 0){
                    Engine3D.camera.translate( -10, 0, 0 );
                }
                if(ev.getIoPinNumber() == 1){

                    Engine3D.camera.translate( 10, 0, 0 );
                }
                if(ev.getIoPinNumber()== 2){
                    if(!sphExplode){
                        sph.explode();
                        sphExplode = true;
                    }
                }

            }

            public void pushButtonSwitchChange(SunSpotSwitchEvent ev) {
                //not implemented in this application
            }
        });

    }


    public void initialise3DEnvironment(){
        Engine3D.initialise(new Camera(0, 0, 0, 0, 0), frame.getContentPane());
        transform = new Transform3D();

        image = new BufferedImage( screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);

        sph = new Sphere(200, 0, 0, 100);
        sph.setPaintLines(false);
        sph.setPaintPolygons(true);

        Engine3D.objects.add(sph);
    }


    public void paint(Graphics2D g2){
        g2.setPaint(new GradientPaint(new Point(image.getWidth(),0),Color.BLUE, new Point(image.getWidth(),image.getHeight()), Color.WHITE));
        g2.fillRect(0, image.getHeight()/2,image.getWidth(), image.getHeight());
        g2.setPaint(new GradientPaint(new Point(image.getWidth(),image.getHeight()),Color.BLUE, new Point(image.getWidth(),0), Color.WHITE));
        g2.fillRect(0, 0,image.getWidth(), image.getHeight()/2);

        Engine3D.paint(g2);
    }


    public BufferedImage loadImage(String file){
		URL url=null;
        url = getClass().getClassLoader().getResource(file);
        System.out.print("Loading: "+url.getPath() +"..." );
		BufferedImage loadImage = null;

		try{
			loadImage = ImageIO.read(new File(url.getPath()));
            System.out.println("Loaded successfully");
		}
		catch(Exception ex){
            System.out.println("image load failed");
			ex.printStackTrace();
		}
		return loadImage;
	}

    public void finish(){
        finished = true;
    }
}
