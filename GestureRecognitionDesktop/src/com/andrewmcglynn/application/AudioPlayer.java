/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.andrewmcglynn.application;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

/**
 * Class that plays an audio sound
 * 
 */
public class AudioPlayer {
    /**
     * Load and play an audio file
     * @param fileName the file name of the audio file
     */
    public void playAudioFile( String fileName ) {
        File soundFile = new File( fileName );

        try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream( soundFile );
        playAudioStream( audioInputStream );

        } catch ( Exception e ) {
            System.out.println( "Problem with file " + fileName + ":" );
            e.printStackTrace();
        }

    } 

    /**
     * Play an audio stream, already loaded from file
     * @param audioInputStream the audio stream
     */
    public void playAudioStream( AudioInputStream audioInputStream ) {
        AudioFormat audioFormat = audioInputStream.getFormat();
  
        DataLine.Info info = new DataLine.Info( SourceDataLine.class, audioFormat );

        if ( !AudioSystem.isLineSupported( info ) ) {
            return;
        }

        try {
            SourceDataLine dataLine = (SourceDataLine) AudioSystem.getLine( info );

            dataLine.open( audioFormat );

            if( dataLine.isControlSupported( FloatControl.Type.MASTER_GAIN ) ) {
                FloatControl volume = (FloatControl) dataLine.getControl( FloatControl.Type.MASTER_GAIN );
                volume.setValue(6f);
            }

            dataLine.start();
            int bufferSize = (int) audioFormat.getSampleRate() * audioFormat.getFrameSize();
            byte [] buffer = new byte[ bufferSize ];

            try {
                int bytesRead = 0;
                while ( bytesRead >= 0 ) {
                bytesRead = audioInputStream.read( buffer, 0, buffer.length );
                    if ( bytesRead >= 0 ) {
                    int framesWritten = dataLine.write( buffer, 0, bytesRead );

                    }

                } 

            }
            catch ( Exception e ) {
            e.printStackTrace();
            }

            dataLine.drain();
            dataLine.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    } 
}
