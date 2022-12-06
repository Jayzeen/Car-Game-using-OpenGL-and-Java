package renderEngine;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;



public class DisplayManager {
    
    private static int WIDTH = 1760;
    private static int HEIGHT = 1000;
    private static int FPS = 120;
    
    private static long lastFrameTime;
    private static float delta;
    
    
    
    public static void createDisplay(){
    
        
        ContextAttribs attribs = new ContextAttribs(4,4)
        .withForwardCompatible(true)
        .withProfileCore(true);
        
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setResizable(true);
            Display.create(new PixelFormat() , attribs);
            Display.setTitle("Car Game ");
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }
        
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        lastFrameTime  = getCurrentTime();
    
    }
    
    public static void updateDisplay(){
    
        Display.sync(FPS);
        Display.update();
        Long currentFrameTime  = getCurrentTime();
        delta = (currentFrameTime  - lastFrameTime)/1000f;
        lastFrameTime = currentFrameTime;
        
        if (Display.wasResized()) 
        {
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        }
    }
    
    public static float getFrameTimeSeconds(){
        return delta;
    }
    
    public static void closeDisplay(){
    
        Display.destroy();
        
    }
    
    private static long getCurrentTime(){
        return Sys.getTime()*1000/Sys.getTimerResolution();
    }
    
}
