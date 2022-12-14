
package textures;

/**
 *
 * @author janit
 */
public class ModelTexture {
    
    private int textureID;
    
    private float shineDamper = 1;
    private float reflectivity = 0;
    
    public boolean hasTransparency = false;
    private boolean useFakeLighting = false;
    
    private int noOfRows  = 1;

    public int getNoOfRows() {
        return noOfRows;
    }

    public void setNoOfRows(int noOfRows) {
        this.noOfRows = noOfRows;
    }

    public boolean isUseFakeLighting() {
        return useFakeLighting;
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }

    public boolean isHasTransparency() {
        return hasTransparency;
    }

    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }
    
    public ModelTexture(int iD)
    {
        this.textureID = iD;
    }

    public int getID() {
        return this.textureID;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
    
    
}
