package engineTester;

import audio.AudioMaster;
import audio.Source;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import gui.GuiRenderer;
import gui.GuiTexture;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        
        
        //TERRAIN TEXTURE IMPLEMENTATION = MULTI TEXTURING ****************************************************************
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass3"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("road"));
        
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        
        //***************************************************************************************************************
        
        
        //ModelData data = OBJFileLoader.loadOBJ("tree");
        //RawModel treeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
       
        RawModel rockModel = OBJLoader.loadObjModel("rock", loader);
        


        //FERNS IN THE WORLD********************************************************
        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern2"));
        fernTextureAtlas.setNoOfRows(2);
        
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
        fern.getTexture().setHasTransparency(true);
        fern.getTexture().setUseFakeLighting(true);
        ModelTexture texture2 = fern.getTexture();
        texture2.setShineDamper(25);
        texture2.setReflectivity((float) 0.5);
        
        //ROCK MODEL**************************************
        TexturedModel rock = new TexturedModel(rockModel, new ModelTexture(loader.loadTexture("road")));
        rock.getTexture().setHasTransparency(true);
        ModelTexture texture3 = rock.getTexture();
        texture3.setShineDamper(25);
        texture3.setReflectivity((float) 0.5);
        
        //TREE MODEL*****************************************
        TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));
        ModelTexture texture4 = tree.getTexture();
        texture4.setShineDamper(20);
        texture4.setReflectivity((float)0.5);
        
        TexturedModel tree2 = new TexturedModel(OBJLoader.loadObjModel("tree2", loader), new ModelTexture(loader.loadTexture("tree2")));
        ModelTexture texture5 = tree2.getTexture();
        texture5.setShineDamper(30);
        texture5.setReflectivity((float)0.5);
        
        //TERRAINS *****************************************************************************************
        Terrain[][] terrains;
        terrains = new Terrain[2][2];
        Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightMap");
        Terrain terrain2 = new Terrain(0, 1, loader, texturePack, blendMap, "heightMap");
        Terrain terrain3 = new Terrain(1, 0, loader, texturePack, blendMap, "heightMap");
        Terrain terrain4 = new Terrain(1, 1, loader, texturePack, blendMap, "heightMap");
        
        terrains[0][0] = terrain;
        terrains[1][0] = terrain2;
        terrains[0][1] = terrain3;
        terrains[1][1] = terrain4;
        
        
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        //FOR TERRAIN 1
        for (int i = 0; i < 800; i++) {
            if(i % 5 == 0)
            {
                float x = random.nextFloat()*800;
                float z = random.nextFloat()*800;
                float y = terrain.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 0.9f));
            }
            
             if(i % 10 == 0)
            {
                float x = random.nextFloat()*800;
                float z = random.nextFloat()*800;
                float y = terrain.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(rock, new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 2.0f));
            }
             
             if(i % 20 == 0)
            {
                float x = random.nextFloat()*800;
                float z = random.nextFloat()*800;
                float y = terrain.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(tree2, new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 1.0f));
            }
            if(i % 20 == 0)
            {
                float x = random.nextFloat()*800;
                float z = random.nextFloat()*800;
                float y = terrain.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(tree, new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 3.0f));
            }
             
        }
        
        //FOR TERRAIN 2
        for (int i = 0; i < 800; i++) {
            if(i % 5 == 0)
            {
                float x = random.nextFloat()*800;
                float z = random.nextFloat()*800+800;
                float y = terrain2.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 0.9f));
            }
            
            if(i % 10 == 0)
            {
                float x = random.nextFloat()*800;
                float z = random.nextFloat()*800+800;
                float y = terrain2.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(rock, new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 2.0f));
            }
            
             if(i % 20 == 0)
            {
                float x = random.nextFloat()*800;
                float z = random.nextFloat()*800+800;
                float y = terrain2.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(tree2, new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 1.0f));
            }
            if(i % 20 == 0)
            {
                float x = random.nextFloat()*800;
                float z = random.nextFloat()*800+800;
                float y = terrain2.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(tree, new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 3.0f));
            }
            
        } 
        
        //FOR TERRAIN 3
        for (int i = 0; i < 800; i++) {
            if(i % 5 == 0)
            {
                float x = random.nextFloat()*800+800;
                float z = random.nextFloat()*800;
                float y = terrain3.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 0.9f));
            }
            
            if(i % 10 == 0)
            {
                float x = random.nextFloat()*800+800;
                float z = random.nextFloat()*800;
                float y = terrain3.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(rock, new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 2.0f));
            }
            
             if(i % 20 == 0)
            {
                float x = random.nextFloat()*800+800;
                float z = random.nextFloat()*800;
                float y = terrain3.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(tree2, new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 1.0f));
            }
            if(i % 20 == 0)
            {
                float x = random.nextFloat()*800+800;
                float z = random.nextFloat()*800;
                float y = terrain3.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(tree, new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 3.0f));
            }
            
        }
        
        //FOR TERRAIN 4
        for (int i = 0; i < 800; i++) {
            if(i % 5 == 0)
            {
                float x = random.nextFloat()*800+800;
                float z = random.nextFloat()*800+800;
                float y = terrain4.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 0.9f));
            }
            
            if(i % 10 == 0)
            {
                float x = random.nextFloat()*800+800;
                float z = random.nextFloat()*800+800;
                float y = terrain4.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(rock, new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 2.0f));
            }
            
            if(i % 20 == 0)
            {
                float x = random.nextFloat()*800+800;
                float z = random.nextFloat()*800+800;
                float y = terrain4.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(tree2, new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 1.0f));
            }
            if(i % 20 == 0)
            {
                float x = random.nextFloat()*800+800;
                float z = random.nextFloat()*800+800;
                float y = terrain4.getHeightOfTerrain(x, z);
                
                entities.add(new Entity(tree, new Vector3f(x, y, z), 0, random.nextFloat()+360, 0, 3.0f));
            }
        }
         
       
        //LIGHTING OF THE ENVIRONMENT**********************************************************************
        Light light = new Light(new Vector3f(0, 10000, -7000), new Vector3f(0.4f, 0.4f, 0.4f));      //Sun light
        List<Light> lights = new ArrayList<Light>();
        lights.add(light);
        lights.add(new Light(new Vector3f(200,100,200), new Vector3f((float) 0.137255,(float) 0.556863, (float) 0.137255)));
        lights.add(new Light(new Vector3f(200,100,350), new Vector3f(1, (float) 0.5, 0)));
        
        //entities.add(new Entity(lamp, new Vector3f(185, -4.7f, -293),0 ,0 ,0 ,1));
        
        //PLAYER MODEL ***********************************************************************************
        RawModel playerModel = OBJLoader.loadObjModel("Storm", loader);
        TexturedModel texturedPlayerModel = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("Storm2")));
        ModelTexture texture1 = texturedPlayerModel.getTexture();
        texture1.setShineDamper(10);
        texture1.setReflectivity(1);
        
        Player player  = new Player(texturedPlayerModel, new Vector3f(600, 0, 600), 0, 180, 0, 2);
        
        
        Camera camera = new Camera(player);
        
        //MASTER RENDERER INIT
        MasterRenderer renderer = new MasterRenderer(loader);
        
        
        //GUI RENDERER INIT
        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("jayzeen1"), new Vector2f(0.84f, 0.9f), new Vector2f(0.25f, 0.25f));
        GuiTexture gui2 = new GuiTexture(loader.loadTexture("car"), new Vector2f(0.55f, 0.88f), new Vector2f(0.1f, 0.1f));
        GuiTexture gui3 = new GuiTexture(loader.loadTexture("instructions"), new Vector2f(0.7f, -0.6f), new Vector2f(0.35f, 0.35f));
        guis.add(gui);
        guis.add(gui2);
        guis.add(gui3);
        
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        
        //SOUNDS
        AudioMaster.init();
        AudioMaster.setListenerData();
        
        int buffer = AudioMaster.loadSound("audio/music.wav");
        Source source = new Source();
        
        source.play(buffer);

        //MAIN GAME LOOP************************************************************
        while (!Display.isCloseRequested()) {

            camera.move();
            
            int gridX = (int) (player.getPosition().x / Terrain.SIZE );
            int gridZ = (int) (player.getPosition().z / Terrain.SIZE );
            player.move(terrains[gridZ][gridX]);
            renderer.processEntity(player);

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processTerrain(terrain3);
            renderer.processTerrain(terrain4);
            
            for (Entity entity : entities) {
                renderer.processEntity(entity);
            };
           
            //renderer.processEntity(entity1);
            //renderer.processEntity(raceTrackEntity);
            
            renderer.render(lights, camera);
            
            guiRenderer.render(guis);
            
            DisplayManager.updateDisplay();

        }

        //CLEAN UPS
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}
