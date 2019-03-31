import JMyron.JMyron;
import damkjer.ocd.Camera;
import pFaceDetect.PFaceDetect;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Andrew McGlynn
 * 3D Virtual Environment
 * Program that tracks a face and changes the viewing perspective of a 3D room
 * depending on the coordinates received from the face detection.
 **/
public class Room3D extends PApplet {

    private PFaceDetect face;
    private JMyron m;
    PImage img;
    private Camera camera1;
    //textures taken from www.cgtextures.com
    private PImage floorImage;
    private PImage wallTexture;
    private PImage ceilingTexture;
    private PImage woodTexture;
    private PImage leatherTexture;
    //position of the 3D camera
    private int viewX = 0;
    private int viewY = 0;

    private static final int SCREEN_WIDTH = 1080;
    private static final int SCREEN_HEIGHT = 600;
    private static final int CAMERA_WIDTH = 320;
    private static final int CAMERA_HEIGHT = 240;
    //rotation of a graphical fan in the sketch
    private float ang = 0;
    private static final int TIMEOUT_SEC = 4;
    private Face trackedFace;
    private Face previousFace;
    private int timeoutVal;
    private int sec;

    public void setup() {

        size(SCREEN_WIDTH, SCREEN_HEIGHT, OPENGL);
        //camera to view the 3D room
        camera1 = new Camera(this, SCREEN_WIDTH / 2, SCREEN_HEIGHT, 10);


        //change the position of the 3D camera
        camera1.truck(1000);
        camera1.boom(-300);
        camera1.dolly(500);
        float[] attitude = camera1.attitude();
        camera1.tumble(-attitude[0], -attitude[1]);

        trackedFace = new Face(0, 0, 0, 0);
        previousFace = new Face(0, 0, 0, 0);

        //webcam and face recognition initialisation
        m = new JMyron();
        m.start(CAMERA_WIDTH, CAMERA_HEIGHT);
        m.findGlobs(0);

        face = new PFaceDetect(this, CAMERA_WIDTH, CAMERA_HEIGHT, "haarcascade_frontalface_default.xml");
        frameRate(30);
        img = createImage(CAMERA_WIDTH, CAMERA_HEIGHT, ARGB);
        //load textures
        wallTexture = loadImage("wall.jpg");
        ceilingTexture = loadImage("ceiling.jpg");
        floorImage = loadImage("floor.jpg");
        woodTexture = loadImage("wood.jpg");
        leatherTexture = loadImage("Leather.jpg");
        smooth();
        timeoutVal = 0;
        sec = second();

    }

    public void draw() {

        background(0);

        //update the image from the webcam
        m.update();
        arraycopy(m.cameraImage(), img.pixels);
        img.updatePixels();
        face.findFaces(img);
        trackFace();
        camera1.feed();

        camera1.tumble(radians(viewX), 0);
        camera1.arc(-radians(viewY));

        updateView();

        drawRoom(0, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 3000);
        fill(0);
        noStroke();
        drawTable(500, height, -2000);
        drawTable(200, height, -1500);
        drawTV();
        drawChair(160, height, -1550);
        drawChair(500, height, -1050);
        drawChair(300, height, -1900);

        lights();
        pushMatrix();
        fill(255);
        translate(width - 50, height - 50, -1500);
        sphere(50);
        popMatrix();
        drawFan();
        drawBox(200, 1000, 70);
        drawBox(200, 1200, 70);

    }

    //read all the faces tracked. Only track the face with the largest area.
    public void trackFace() {
        int[][] res = face.getFaces();
        int faceNum = -1;
        int largest = 0;
        if (res.length > 0) {
            for (int i = 0; i < res.length; i++) {
                int x = res[i][0];
                int y = res[i][1];
                int w = res[i][2];
                int h = res[i][3];
                int area = w * h;
                //code to check for the largest face in the image
                if (area > largest) {
                    area = largest;
                    faceNum = i;
                }
            }
            previousFace.setX(trackedFace.getX());
            previousFace.setY(trackedFace.getY());
            previousFace.setWidth(trackedFace.getWidth());
            previousFace.setHeight(trackedFace.getHeight());

            trackedFace.setX(res[faceNum][0]);
            trackedFace.setY(res[faceNum][1]);
            trackedFace.setWidth(res[faceNum][2]);
            trackedFace.setHeight(res[faceNum][3]);
            timeoutVal = 0;
        }
        timeout();
    }

    //timeout used so the program will return to the starting position if no face is recognised
    private void timeout() {
        if (sec != second()) {
            sec = second();

            if (timeoutVal++ == TIMEOUT_SEC) {
                timeoutVal = 0;
                trackedFace.setWidth(0);
                trackedFace.setHeight(0);
            }
        }
    }

    //method that draws a 3D table shape in the sketch
    public void drawTable(int x, int y, int z) {
        drawCuboid(x, height, z, 20, -200, 20, woodTexture);
        drawCuboid(x + 200, height, z, 20, -200, 20, woodTexture);
        drawCuboid(x, height, z - 200, 20, -200, 20, woodTexture);
        drawCuboid(x + 200, height, z - 200, 20, -200, 20, woodTexture);
        drawCuboid(x - 10, height - 200, z, 240, -30, 240, woodTexture);
    }

    //method that draws a 3D chair shape in the sketch
    public void drawChair(int x, int y, int z) {
        int w = 10;
        int h = -150;
        int chairWidth = 100;
        drawCuboid(x, y, z, w, h, w, woodTexture);
        drawCuboid(x + chairWidth, y, z, w, h, w, woodTexture);
        drawCuboid(x, y, z - chairWidth, w, h, w, woodTexture);
        drawCuboid(x + chairWidth, y, z - chairWidth, w, h, w, woodTexture);
        drawCuboid(x - 10, y + h - 30, z, chairWidth + 30, 30, chairWidth + 30, leatherTexture);
        drawCuboid(x - 10, y + h - 30, z, 30, -chairWidth, chairWidth + 30, leatherTexture);

    }

    //method that draws and updates the rotation of a ceiling fan.
    public void drawFan() {
        //draw the line holding the fan
        stroke(0);
        strokeWeight(4);
        line(width / 2, 0, -1500, width / 2, 150, -1500);
        strokeWeight(1);
        noStroke();
        fill(150, 150, 150);

        pushMatrix();
        translate(width / 2, 150, -1500);
        rotateY(sin(radians(ang)) * 90);

        int w1 = 5;
        int w2 = 10;
        int h = 100;
        //draw the different fan blades
        for (int i = 0; i < 7; i++) {
            rotateY(sin(PI * 4 / 3));
            beginShape();
            vertex(0, 0, 0);
            vertex(w1, 0, 0);
            vertex(w2, 0, h);
            vertex(0, 0, h);
            endShape();
        }
        popMatrix();
        //update the fan rotation
        ang += 0.2f;
        if (ang >= 30) {
            ang = 0;
        }
    }

    //method to draw a cube with the specified coordinates and size.
    public void drawBox(int x, int z, int boxSize) {
        pushMatrix();
        translate(x, SCREEN_HEIGHT - boxSize / 2, -z);
        box(boxSize);
        popMatrix();
    }

    //this method updates the view point coordinates. It also checks if the view is
//outside of the room boundaries, if it is it repositions the view to inside the room.
    public void updateView() {
        //get the distance between the face tracked in this frame and the last frame.
        viewX = previousFace.getX() - trackedFace.getX();
        viewX /= 2;
        viewY = previousFace.getY() - trackedFace.getY();
        viewY /= 4;
        //get the camera properties about yaw, pitch, roll
        float[] attitude = camera1.attitude();
        //constrict the camera from moving through the right wall
        if (attitude[0] > 0.627f && attitude[0] < 3) {
            float diff = attitude[0] - 0.627f;
            camera1.tumble(-diff, 0);
        }
        //constrict the camera from moving through the left wall
        if (attitude[0] > 3 && attitude[0] < 5.6014f) {
            float diff = attitude[0] - 5.6014f;
            camera1.tumble(-diff, 0);
        }
        //constrict the camera from moving through the ceiling
        if (attitude[1] < -0.122f) {
            float diff = attitude[1] - (-0.122f);
            camera1.tumble(0, -diff);
        }
        //constrict the camera from moving through the floor
        if (attitude[1] > 0.540f) {
            float diff = attitude[1] - 0.54f;
            camera1.tumble(0, -diff);
        }

        depthUpdate();
    }


    public void stop() {
        m.stop();
        super.stop();
    }

    /**
     * Method to draw a room with the specified coordinates.
     * different textures are used for the ground, walls and ceiling
     */
    public void drawRoom(int x, int y, int z, int w, int h, int d) {
        //pushMatrix specifies only performing the transform only
        //on shapes until popMatrix is called, shapes drawn after
        //pop matrix will not be affected
        pushMatrix();

        translate(x, y, z);
        //draw back of room
        beginShape();
        texture(wallTexture);
        vertex(0, 0, -d, 0, 0);
        vertex(w, 0, -d, wallTexture.width, 0);
        vertex(w, h, -d, wallTexture.width, wallTexture.height);
        vertex(0, h, -d, 0, wallTexture.height);
        endShape();

        //draw leftWall of the room
        beginShape();
        texture(wallTexture);
        vertex(0, 0, 0, 0, 0);
        vertex(0, 0, -d, wallTexture.width, 0);
        vertex(0, h, -d, wallTexture.width, wallTexture.height);
        vertex(0, h, 0, 0, wallTexture.height);
        endShape();

        //draw right wall of the room
        beginShape();
        texture(wallTexture);
        vertex(w, 0, 0 - d, 0, 0);
        vertex(w, 0, 0, wallTexture.width, 0);
        vertex(w, h, 0, wallTexture.width, wallTexture.height);
        vertex(w, h, -d, 0, wallTexture.height);
        endShape();

        //draw the ceiling
        beginShape();
        texture(ceilingTexture);
        vertex(0, 0, 0, ceilingTexture.width, 0);
        vertex(0, 0, -d, 0, 0);
        vertex(w, 0, -d, 0, ceilingTexture.height);
        vertex(w, 0, 0, ceilingTexture.width, ceilingTexture.height);
        endShape();

        //draw the floor
        beginShape();
        texture(floorImage);
        vertex(0, h, 0, 0, 0);
        vertex(0, h, -d, floorImage.width, 0);
        vertex(w, h, -d, floorImage.width, floorImage.height);
        vertex(w, h, 0, 0, floorImage.height);
        endShape();

        popMatrix();
    }

    /**
     * Method that draws a Television. The television consists of a box and an image.
     * The box is drawn first and the image is drawn over the side of the box.
     */
    public void drawTV() {
        fill(0);
        drawCuboid(width - 50, height - 200, -1500, 50, -400, 800);
        beginShape();
        texture(img);  //webcam texture image
        int x = width - 51;
        int y = height - 220;
        int z = -2280;

        int h = y - 360;

        int depth = -1520;
        int w = depth;
        vertex(x, y, z, 0, 400);
        vertex(x, h, z, 0, 0);
        vertex(x, h, depth, 400, 0);
        vertex(x, y, depth, 400, 400);
        endShape();

    }

    /**
     * Method that creates a box with the specified coordinates.
     */
    public void drawCuboid(int x, int y, int z, int w, int h, int d) {
        //pushMatrix specifies only performing the transform only
        //on shapes until popMatrix is called, shapes drawn after
        //pop matrix will not be affected
        pushMatrix();

        translate(x, y, z);
        beginShape();
        vertex(0, 0, -d);
        vertex(w, 0, -d);
        vertex(w, h, -d);
        vertex(0, h, -d);
        endShape();

        beginShape();
        vertex(0, 0, 0);
        vertex(0, 0, -d);
        vertex(0, h, -d);
        vertex(0, h, 0);
        endShape();

        beginShape();
        vertex(w, 0, 0 - d);
        vertex(w, 0, 0);
        vertex(w, h, 0);
        vertex(w, h, -d);
        endShape();

        beginShape();
        vertex(0, 0, 0);
        vertex(0, 0, -d);
        vertex(w, 0, -d);
        vertex(w, 0, 0);
        endShape();

        beginShape();
        vertex(0, h, 0);
        vertex(0, h, -d);
        vertex(w, h, -d);
        vertex(w, h, 0);
        endShape();

        beginShape();
        vertex(0, 0, 0);
        vertex(w, 0, 0);
        vertex(w, h, 0);
        vertex(0, h, 0);
        endShape();
        popMatrix();
    }

    /**
     * Method that draws a textured box with the specified coordinates and texture.
     */
    public void drawCuboid(int x, int y, int z, int w, int h, int d, PImage shapeTexture) {
        //pushMatrix specifies only performing the transform only
        //on shapes until popMatrix is called, shapes drawn after
        //pop matrix will not be affected
        pushMatrix();

        translate(x, y, z);
        //draw the back of the box
        beginShape();
        texture(shapeTexture);
        vertex(0, 0, -d, 0, 0);
        vertex(w, 0, -d, shapeTexture.width, 0);
        vertex(w, h, -d, shapeTexture.width, wallTexture.height);
        vertex(0, h, -d, 0, wallTexture.height);
        endShape();

        //draw left side of the box
        beginShape();
        texture(shapeTexture);
        vertex(0, 0, 0, 0, 0);
        vertex(0, 0, -d, shapeTexture.width, 0);
        vertex(0, h, -d, shapeTexture.width, shapeTexture.height);
        vertex(0, h, 0, 0, shapeTexture.height);
        endShape();

        //draw right side of the box
        beginShape();
        texture(shapeTexture);
        vertex(w, 0, 0 - d, 0, 0);
        vertex(w, 0, 0, shapeTexture.width, 0);
        vertex(w, h, 0, shapeTexture.width, shapeTexture.height);
        vertex(w, h, -d, 0, shapeTexture.height);
        endShape();

        //draw the top
        beginShape();
        texture(shapeTexture);
        vertex(0, 0, 0, shapeTexture.width, 0);
        vertex(0, 0, -d, 0, 0);
        vertex(w, 0, -d, 0, shapeTexture.height);
        vertex(w, 0, 0, shapeTexture.width, shapeTexture.height);
        endShape();

        //draw the bottom
        beginShape();
        texture(shapeTexture);
        vertex(0, h, 0, 0, 0);
        vertex(0, h, -d, shapeTexture.width, 0);
        vertex(w, h, -d, shapeTexture.width, shapeTexture.height);
        vertex(w, h, 0, 0, shapeTexture.height);
        endShape();

        //draw front of box
        beginShape();
        texture(shapeTexture);
        vertex(0, 0, 0, 0, 0);
        vertex(w, 0, 0, shapeTexture.width, 0);
        vertex(w, h, 0, shapeTexture.width, wallTexture.height);
        vertex(0, h, 0, 0, shapeTexture.height);
        endShape();
        popMatrix();
    }

    /**
     * Class that stores the x, y, z, width and height of a tracked face.
     */
    class Face {
        private int x;
        private int y;
        private int width;
        private int height;
        //this is the distance from the camera. It is not in any specific units
        private int distanceFromCamera;

        public Face(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            distanceFromCamera = this.width * this.height / 100;
        }


        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setWidth(int w) {
            this.width = w;
            update();
        }

        public void setHeight(int h) {
            this.height = h;
            update();
        }

        /**
         * Returns a number based on the distance of the tracked face to the camera.
         * This is not an actual distance in units as it can vary on the
         * size of the tracked face and the accuracy of the camera.
         */
        public int getDistanceFromCamera() {
            return distanceFromCamera;
        }

        //updates the distance of the face from the camera
        private void update() {
            this.distanceFromCamera = this.width * this.height / 300;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }
    }

    /**
     * Update the viewers position in terms of depth into the room.
     */
    public void depthUpdate() {
        float[] position = camera1.position();
        float[] attitude = camera1.attitude();
        //reset the rotation view back to 0,0 so the dolly method will get the correct depth
        camera1.tumble(-attitude[0], -attitude[1]);
        //apply the viewers distance into the room
        camera1.dolly(-position[2] - 75 * sqrt(trackedFace.getDistanceFromCamera()));
        //apply the old rotation view
        camera1.tumble(attitude[0], attitude[1]);
    }

    public static void main(String args[]) {
        PApplet.main(new String[]{"--bgcolor=#F0F0F0", "Room3D"});
    }
}
