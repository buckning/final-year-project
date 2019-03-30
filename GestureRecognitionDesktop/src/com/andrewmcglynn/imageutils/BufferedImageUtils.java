package com.andrewmcglynn.imageutils;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Class that has useful buffered image manipulation functions
 *
 * @author Andrew McGlynn
 */

public class BufferedImageUtils {
    /**
     * Resize a buffered image, returns a scaled copy of the image
     * @param image the image to be scaled
     * @param width the width of the new image
     * @param height the height of the new image
     * @return the scaled copyof the image
     */
	public static BufferedImage getScaledVersion(BufferedImage image, int width, int height){
        if(width <= 0) width = 1;
        if(height <= 0) height = 1;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(width > screenSize.width/2) width = screenSize.width/2;
        if(height > screenSize.height/2) height = screenSize.height/2;
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		return scaledImage;
	}
    /**
     * Rotate a buffered image around its centre point
     *
     * @param image the buffered image to be rotated
     * @param x the x-coordinate of the buffered image
     * @param y the y-coordinate of the buffered image
     * @param theta the rotation of the image in radians
     * @return the rotated bufferedImage
     */
	public static BufferedImage rotateImage(BufferedImage image, int x, int y, double theta){
		BufferedImage rotatedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)rotatedImage.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		double x1 = image.getWidth()/2;
		double y1 = image.getHeight()/2;
		at.rotate(theta, x1, y1);
		g.drawImage(image, at, null);

		return rotatedImage;
	}
    /**
     * Get a copy of a buffered image
     * @param image the image to be copied
     * @return the copied image
     */
    public static BufferedImage getCopy(BufferedImage image){
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        Graphics2D g = copy.createGraphics();
        g.drawImage(image, null, 0,0);
        g.dispose();

        return copy;
    }
}

