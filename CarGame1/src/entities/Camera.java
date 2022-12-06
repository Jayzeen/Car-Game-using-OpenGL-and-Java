package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author janit
 */
public class Camera {

    private float distanceFromPlayer = 60;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch = 15;
    private float yaw = 10;
    private float roll;

    private Player player;

    public Camera(Player player) {
        this.player = player;
    }

    public void move() {
//        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
//            position.y += 0.2f;
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
//            position.y -= 0.2f;
//        }

        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);

    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    private void calculateCameraPosition(float horizDistance, float vertDistance) {

        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));

        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + vertDistance;
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {

        float zoomLevel = Mouse.getDWheel() * 0.1f;
        distanceFromPlayer -= zoomLevel;
        
        if(distanceFromPlayer > 70)
        {
            distanceFromPlayer = 70;
        }
        if(distanceFromPlayer < 25)
        {
            distanceFromPlayer = 25;
        }

    }

    private void calculatePitch() {
        if (Mouse.isButtonDown(1)) {
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch -= pitchChange;
        }
        
        if(pitch < 10)
        {
            pitch = 10;
        }
        
        if(pitch > 40)
        {
            pitch = 40;
        }
    }

    private void calculateAngleAroundPlayer() {
        if (Mouse.isButtonDown(0)) {
            float angleChange = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }

}
