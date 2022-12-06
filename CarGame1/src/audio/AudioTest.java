/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audio;

import java.io.IOException;

/**
 *
 * @author janit
 */
public class AudioTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        AudioMaster.init();
        AudioMaster.setListenerData();
        
        int buffer = AudioMaster.loadSound("audio/music.wav");
        Source source = new Source();
        
        char c = ' ';
        while (c != 'q')
        {
            c = (char)System.in.read();
            
            if(c == 'p')
            {
                source.play(buffer);
            }
        }
        
        source.deleteSource();
        AudioMaster.cleanUp();
    }
    
}
