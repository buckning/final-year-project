package com.andrewmcglynn.application;

import com.andrewmcglynn.sunspot.SunSpotSwitchEvent;
import com.andrewmcglynn.sunspot.SunSpotTiltEvent;
import com.andrewmcglynn.sunspot.SunSpotListener;
import com.andrewmcglynn.sunspot.CommunicationInterface;
import com.andrewmcglynn.sunspot.SunSpotReceiver;
import com.andrewmcglynn.sunspot.SunSpotDigitalIOEvent;
import com.andrewmcglynn.imageutils.BufferedImageUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class PhotoApplication implements CommunicationInterface{
    Timer closeAppTimer;

    JFrame frame;
//    JPanel panel;

    SunSpotReceiver rightReceiver;
    SunSpotReceiver leftReceiver;

    BufferStrategy strategy;
    Dimension screenSize;

    AudioPlayer audioPlayer;

    SunSpotCursor leftCursor;
    SunSpotCursor rightCursor;
    ArrayList<Photo> photos;
    ArrayList<Photo> markedPhotos;
    ArrayList<BufferedImage> images;
    RecyclingBin bin;
    RecyclingQueue deleteQueue;
    Timer timer;
    ArrayList<Point> animateDeletePoints;
    private boolean finished = false;

    private static final double ROTATE_SENSITIVITY = 0.01;

    public PhotoApplication(SunSpotReceiver leftReceiver, SunSpotReceiver rightReceiver){
        this.leftReceiver = leftReceiver;
        this.rightReceiver = rightReceiver;
        this.images = new ArrayList<BufferedImage>();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //animate to the bin
        timer = new Timer(5, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                for(Photo p:markedPhotos){

                    if(p.getY() < (animateDeletePoints.get(0).getY())){
                            p.setY(p.getY() + 1);
                        }
                    if(!(p.getX() >= (animateDeletePoints.get(0).getX()-3) && p.getX() <= animateDeletePoints.get(0).getX()+3 )){
                        if(p.getX() < animateDeletePoints.get(0).getX()){
                            p.setX(p.getX() + 5);
                        }

                    }
                    else{
                        if(p.getTheta() > 0){
                            p.setTheta(p.getTheta()-0.01);
                            if(p.getTheta() < 0){
                                p.setTheta(0);
                            }
                        }
                        if(p.getTheta() < 0){
                            p.setTheta(p.getTheta()+0.01);
                            if(p.getTheta() > 0){
                                p.setTheta(0);
                            }
                        }

                        if(p.getWidth() > 20 && p.getHeight() > 20){
                            p.resizePhoto(p.getWidth()-1, p.getHeight()-1);
                        }
                    }
                }
            }
        });

        closeAppTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                finish();
            }
        });


    }


    

    /**
     * Print out our radio address.
     */
    public void run() {

        leftReceiver.addSunSpotListener(new CursorUpdater(leftCursor));
        rightReceiver.addSunSpotListener(new CursorUpdater(rightCursor));
        finished = false;
        while(!finished){
            Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
            paintBackground(g);

            paintPhotos(g);
            paintMarkedPhotos(g);
            paintRecycleBin(g);
            paintCursors(g);
            strategy.show();
            g.dispose();
            removeMarkedElements();
        }
        
    }

    public void setup(){
//        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        audioPlayer = new AudioPlayer();

        animateDeletePoints = new ArrayList<Point>();

//        System.out.println("Our radio address = " + IEEEAddress.toDottedHex(ourAddr));
        markedPhotos = new ArrayList<Photo>();
        photos = new ArrayList<Photo>();

        leftCursor = new SunSpotCursor(loadImage("images/handCursorLeft.png"));
        rightCursor = new SunSpotCursor(loadImage("images/handCursorRight.png"));
        rightCursor.setX(600);
        BufferedImage moveImage = loadImage("images/moveCursor.png");
        BufferedImage resizeImage = loadImage("images/resizeCursor.png");
        resizeImage = BufferedImageUtils.getScaledVersion(resizeImage, resizeImage.getWidth()/2, resizeImage.getHeight()/2);
        moveImage = BufferedImageUtils.getScaledVersion(moveImage,moveImage.getWidth()/2,moveImage.getHeight()/2);
        BufferedImage rotateImage = loadImage("images/rotateCursor.png");
        rotateImage = BufferedImageUtils.getScaledVersion(rotateImage,rotateImage.getWidth()/2, rotateImage.getHeight()/2);

        leftCursor.setMoveImage(moveImage);
        leftCursor.setRotateImage(rotateImage);
        leftCursor.setResizeImage(resizeImage);

        rightCursor.setMoveImage(moveImage);
        rightCursor.setRotateImage(rotateImage);
        rightCursor.setResizeImage(resizeImage);
//        setupGUI();

        BufferedImage binImage = loadImage("images/bin.png");
        bin = new RecyclingBin(binImage, screenSize.width - binImage.getWidth(), screenSize.height - binImage.getHeight());
//        loadPhotos(photoNames);
        

        animateDeletePoints.add(new Point(bin.getX()+bin.getImage().getWidth()/3,bin.getY()+bin.getImage().getHeight()/2));

        //move mouse off the the screen
        try{
            Robot r = new Robot();
            r.mouseMove(screenSize.width, screenSize.height-50);
        }catch(Exception e){
            e.printStackTrace();
        }

        timer.start();
    }

    private void paintBackground(Graphics2D g) {
        g.setPaint(new GradientPaint(new Point(0,0),Color.BLACK, new Point(screenSize.width,screenSize.height), Color.WHITE));
        g.fillRect(0, 0, screenSize.width, screenSize.height);
    }

    private void paintCursors(Graphics2D g) {
        g.drawImage(leftCursor.getImage(), null, leftCursor.getX(), leftCursor.getY());
        g.drawImage(rightCursor.getImage(), null, rightCursor.getX(),rightCursor.getY());

            //boundary check, dont allow the cursor to move away from the screen
        if(leftCursor.getX() > screenSize.width) leftCursor.setX(screenSize.width);
        if(leftCursor.getX() < 0) leftCursor.setX(0);
        if(leftCursor.getY() > screenSize.height) leftCursor.setY(screenSize.height);
        if(leftCursor.getY() < 0) leftCursor.setY(0);

        if(rightCursor.getX() > screenSize.width) rightCursor.setX(screenSize.width);
        if(rightCursor.getX() < 0) rightCursor.setX(0);
        if(rightCursor.getY() > screenSize.height) rightCursor.setY(screenSize.height);
        if(rightCursor.getY() < 0) rightCursor.setY(0);
    }

    private void paintPhotos(Graphics2D g) {
        synchronized (this) {
            for(Photo p:photos){
                p.paint(g);
            }
        }
    }

    private void paintMarkedPhotos(Graphics2D g){
        for(Photo p:markedPhotos){
            p.paint(g);
        }
    }

    private void removeMarkedElements(){
        synchronized (this) {
            for(Photo p:photos){
                if(p.isMarkedForRemoval())markedPhotos.add(p);
            }
            for(Photo p:markedPhotos){
                photos.remove(p);
            }
        }
    }

    private void paintRecycleBin(Graphics2D g) {
        g.drawImage(bin.getImage(), null, bin.getX(), bin.getY());
    }


    public void setupGUI(BufferStrategy strategy){
        this.strategy = strategy;
        Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
        g.drawString("Loading . . .", 500, 300);
        strategy.show();
    }

    public BufferedImage loadImage(String file){
		URL url=null;
        url = getClass().getClassLoader().getResource(file);
        System.out.print("Loading: "+url.getPath() +"..." );
		BufferedImage image = null;

		try{
			image = ImageIO.read(new File(url.getPath()));
            System.out.println("Loaded successfully");
		}
		catch(Exception ex){
            System.out.println("image load failed");
			ex.printStackTrace();
		}
		return image;
	}

    class CursorUpdater implements SunSpotListener{
        public SunSpotCursor cursor;

        public CursorUpdater(SunSpotCursor cursor){
            this.cursor = cursor;
        }

        public void tiltInputReceived(SunSpotTiltEvent ev) {
            int xaccel = ev.getxTilt();
            int yaccel = ev.getyTilt();

            if(cursor.getMode() == SunSpotCursor.MODE_IDLE){
                cursor.moveX(xaccel);
                cursor.moveY(yaccel);
            }
            else if(cursor.getMode() == SunSpotCursor.MODE_MOVE){
                cursor.moveX(xaccel);
                cursor.moveY(yaccel);

                if(cursor.hasPhotoSelected()){
                    cursor.getSelectedPhoto().setX(cursor.getSelectedPhoto().getX()+xaccel);
                    cursor.getSelectedPhoto().setY(cursor.getSelectedPhoto().getY()+yaccel);
                }
            }

            else if (cursor.getMode() == SunSpotCursor.MODE_ROTATE){
                if(cursor.hasPhotoSelected()){
                    cursor.getSelectedPhoto().setTheta(cursor.getSelectedPhoto().getTheta()+(ROTATE_SENSITIVITY*xaccel));
                }
            }
            else if (cursor.getMode() == SunSpotCursor.MODE_RESIZE){
                SunSpotCursor otherCursor;
                if(cursor.equals(leftCursor))otherCursor = rightCursor;
                else otherCursor = leftCursor;

                if(cursor.hasPhotoSelected()){
                    if(otherCursor.hasPhotoSelected()){
                        if(otherCursor.getSelectedPhoto().equals(cursor.getSelectedPhoto())){
                            cursor.moveX(xaccel);
                            cursor.moveY(yaccel);

                            int xDistance = Math.abs(cursor.getX() - otherCursor.getX());
                            int yDistance = Math.abs(cursor.getY() - otherCursor.getY());

                            cursor.getSelectedPhoto().resizePhoto(xDistance, yDistance);
                        }
                    }
                }
            }
        }

        public void digitalPinInputChange(SunSpotDigitalIOEvent ev) {
            URL url=null;
            url = getClass().getClassLoader().getResource("sounds/click_one.wav");
//            audioPlayer.playAudioFile( url.getPath() );
            switch(ev.getIoPinNumber()){
                case 0:

                    if(ev.pinState() == false){
                        int numOfSelectedPhotos = 0;
                        cursor.setMode(SunSpotCursor.MODE_MOVE);
                        //count the number of selected photos for this point
                        for(Photo p:photos){
                            if(p.containsPoint(new Point(cursor.getX(),cursor.getY()))){
                                numOfSelectedPhotos++;
                            }
                        }

                        int count = 0;
                        for(Photo p:photos){
                            if(p.containsPoint(new Point(cursor.getX(),cursor.getY()))){
                                count++;
                                //only select the latest photo
                                if(count == numOfSelectedPhotos){
                                    cursor.setSelectedPhoto(p);
                                    Collections.swap(photos,photos.indexOf(p),photos.size()-1 );
                                }
                            }
                        }

                        if(numOfSelectedPhotos == 0){
                            cursor.setSelectedPhoto(null);
                        }
                    }
                    else{
                        cursor.setSelectedPhoto(null);
                        cursor.setMode(SunSpotCursor.MODE_IDLE);
                    }
                    break;
                case 1:

                    if(ev.pinState() == false){
                        int numOfSelectedPhotos = 0;
                        cursor.setMode(SunSpotCursor.MODE_ROTATE);
                        //count the number of selected photos for this point
                        for(Photo p:photos){
                            if(p.containsPoint(new Point(cursor.getX(),cursor.getY()))){
                                numOfSelectedPhotos++;
                            }
                        }

                        int count = 0;
                        for(Photo p:photos){
                            if(p.containsPoint(new Point(cursor.getX(),cursor.getY()))){
                                count++;
                                //only select the latest photo
                                if(count == numOfSelectedPhotos){
                                    cursor.setSelectedPhoto(p);
                                    Collections.swap(photos,photos.indexOf(p),photos.size()-1 );
                                }
                            }
                        }

                        if(numOfSelectedPhotos == 0){
                            cursor.setSelectedPhoto(null);
                        }
                    }
                    else{
                        cursor.setSelectedPhoto(null);
                        cursor.setMode(SunSpotCursor.MODE_IDLE);
                    }
                    break;
                case 2:

                    if(ev.pinState() == false){
                        int numOfSelectedPhotos = 0;
                        cursor.setMode(SunSpotCursor.MODE_RESIZE);
                        //count the number of selected photos for this point
                        for(Photo p:photos){
                            if(p.containsPoint(new Point(cursor.getX(),cursor.getY()))){
                                numOfSelectedPhotos++;
                            }
                        }

                        int count = 0;
                        for(Photo p:photos){
                            if(p.containsPoint(new Point(cursor.getX(),cursor.getY()))){
                                count++;
                                //only select the latest photo
                                if(count == numOfSelectedPhotos){
                                    cursor.setSelectedPhoto(p);
                                    Collections.swap(photos,photos.indexOf(p),photos.size()-1 );
                                }
                            }
                        }

                        if(numOfSelectedPhotos == 0){
                            cursor.setSelectedPhoto(null);
                        }
                    }
                    else{
                        cursor.setSelectedPhoto(null);
                        cursor.setMode(SunSpotCursor.MODE_IDLE);
                    }
                    break;
                case 3:
                    if(ev.pinState() == false){
                        
                        if(!closeAppTimer.isRunning()){
                            closeAppTimer.start();
                        }
                        int numOfSelectedPhotos = 0;
                        cursor.setMode(SunSpotCursor.MODE_IDLE);
                        //count the number of selected photos for this point
                        for(Photo p:photos){
                            if(p.containsPoint(new Point(cursor.getX(),cursor.getY()))){
                                numOfSelectedPhotos++;
                            }
                        }

                        int count = 0;
                        for(Photo p:photos){
                            if(p.containsPoint(new Point(cursor.getX(),cursor.getY()))){
                                count++;
                                //only select the latest photo
                                if(count == numOfSelectedPhotos){
                                    cursor.setSelectedPhoto(p);
                                }
                            }
                        }
                        if(count == numOfSelectedPhotos){
                            if(cursor.getSelectedPhoto() != null){
                                cursor.getSelectedPhoto().setMarkedForRemoval(true);
                            }
                            cursor.setSelectedPhoto(null);
                        }

                        if(numOfSelectedPhotos == 0){
                            cursor.setSelectedPhoto(null);
                        }

                    }
                    else{
                        cursor.setSelectedPhoto(null);
                        if(closeAppTimer.isRunning()){
                            closeAppTimer.stop();
                            closeAppTimer.setDelay(1000);
                        }
                        cursor.setMode(SunSpotCursor.MODE_IDLE);
                    }
                    break;
            }
        }

        public void pushButtonSwitchChange(SunSpotSwitchEvent ev) {
            //quit the program if switch 2 on the right glove is pressed
            if(ev.getPort() == RIGHT_COMM_PORT && ev.getSwitchNumber() == SunSpotSwitchEvent.SWITCH_2){
                System.exit(0);
            }
        }
    }
    public ArrayList<BufferedImage> getImages(){
        return images;
    }
    public void loadPhotos(ArrayList<BufferedImage> images){
        for(int i = 0; i< images.size(); i++){

			photos.add(new Photo(images.get(i),
                        (int)(Math.random()*(screenSize.width - 700)),
                            (int)(Math.random()*(screenSize.height-100))));
		}
        for(Photo p: photos){
			p.setTheta(Math.random());
		}
    }
   public void loadPhotos(String[] fileNames){
		for(int i = 0; i< fileNames.length; i++){
            BufferedImage loadedImage = loadImage(fileNames[i]);
            images.add(loadedImage);
			photos.add(new Photo(loadedImage,
                        (int)(Math.random()*(screenSize.width - 700)),
                            (int)(Math.random()*(screenSize.height-100))));
		}
        for(Photo p: photos){
			p.setTheta(Math.random());
		}
	}

   public void resizeAllPhotos(int scale){
		for(Photo p: photos){
			p.resizePhoto(p.getWidth()/scale, p.getHeight()/scale);
            p.setPreferredSize(new Dimension(p.getWidth()/scale, p.getHeight()/scale));
		}
	}

   public void finish(){
        finished = true;
        timer.stop();
        closeAppTimer.stop();

        leftReceiver.addSunSpotListener(null);
        rightReceiver.addSunSpotListener(null);
        
   }
}
