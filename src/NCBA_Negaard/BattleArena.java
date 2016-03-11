package NCBA_Negaard;

import org.newdawn.slick.state.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

public class BattleArena extends BasicGameState {

    public static Player player;
    public Itemwin antidote;
    public Enemy Doge;
    public ArrayList<Enemy> enemies = new ArrayList();
    public ArrayList<Item> stuff = new ArrayList();
    public ArrayList<Item1> stuff1 = new ArrayList();
    public ArrayList<Itemwin> stuffwin = new ArrayList();
    private boolean[][] hostiles;
    private static TiledMap grassMap;
    private static AppGameContainer app;
    private static Camera camera;
    public static int counter = 0;
    private static final int SIZE = 64;
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 750;
    public BattleArena(int xSize, int ySize) {
    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {

        gc.setTargetFrameRate(1000);
        gc.setShowFPS(false);

        player = new Player();
        grassMap = new TiledMap("res/d4.tmx");
        camera = new Camera(gc, grassMap);
        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {

            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                
                int tileID = grassMap.getTileId(xAxis, yAxis, 0);

                String value = grassMap.getTileProperty(tileID,
                        "blocked", "false");

                if ("true".equals(value)) {
                }
            }
        }

        hostiles = new boolean[grassMap.getWidth()][grassMap.getHeight()];

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                try {
                if (!Blocked.blocked[xBlock][yBlock]) {
                    if (yBlock % 7 == 0 && xBlock % 15 == 0) {
                        Item1 i = new Item1(xAxis * SIZE, yAxis * SIZE);
//                        stuff.add(i);
                        //stuff1.add(h);
                        hostiles[xAxis][yAxis] = true;
                    }
                }}
                    catch(NullPointerException e){
                }
            }
        }

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked.blocked[xBlock][yBlock]) {
                    if (xBlock % 9 == 0 && yBlock % 25 == 0) {
                        Item1 h = new Item1(xAxis * SIZE, yAxis * SIZE);
                        stuff1.add(h);
                        hostiles[xAxis][yAxis] = true;
                    }
                }
            }
        }

        Doge = new Enemy(100, 100);
        enemies.add(Doge);
        antidote = new Itemwin(3004, 92);
        stuffwin.add(antidote);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {

        camera.centerOn((int) player.x, (int) player.y);
        camera.drawMap();
        camera.translateGraphics();
        player.sprite.draw((int) player.x, (int) player.y);
        g.drawString("Health: " + player.health / 1000, camera.cameraX + 10, camera.cameraY + 10);
        g.drawString("Cats collected: " + (int) (player.speed * 10), camera.cameraX + 10, camera.cameraY + 25);
        g.drawString("time passed: " + counter / 1000, camera.cameraX + 600, camera.cameraY);
        
        //moveenemies();

        for (Item i : stuff) {
            if (i.isvisible) {
                i.currentImage.draw(i.x, i.y);       
            }
        }

        for (Item1 h : stuff1) {
            if (h.isvisible) {
                h.currentImage.draw(h.x, h.y);
                     }
        }

        for (Enemy e : enemies) {
            e.isvisible = true;
            if (e.isvisible) {
                e.currentanime.draw(e.Bx, e.Bx);
            
            }
        }
        antidote.antidote.draw(3004, 92);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {

        counter += delta;
        Input input = gc.getInput();
        float fdelta = delta * player.speed;
        player.setpdelta(fdelta);
        double rightlimit = (grassMap.getWidth() * SIZE) - (SIZE * 0.75);        
        float projectedright = player.x + fdelta + SIZE;
        boolean cangoright = projectedright < rightlimit;
        if (input.isKeyDown(Input.KEY_UP)) {
            player.sprite = player.up;
            float fdsc = (float) (fdelta - (SIZE * .15));
            if (!(isBlocked(player.x, player.y - fdelta) || isBlocked((float) (player.x + SIZE + 1.5), player.y - fdelta))) {
                player.sprite.update(delta);
                player.y -= fdelta;
            }
       
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            player.sprite = player.down;
            if (!isBlocked(player.x, player.y + SIZE + fdelta)
                    || !isBlocked(player.x + SIZE - 1, player.y + SIZE + fdelta)) {
                player.sprite.update(delta);
                player.y += fdelta;
            }

        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            player.sprite = player.left;
            if (!(isBlocked(player.x - fdelta, player.y) || isBlocked(player.x
                    - fdelta, player.y + SIZE - 1))) {
                player.sprite.update(delta);
                player.x -= fdelta;
            }

        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            player.sprite = player.right;
                  if (cangoright
                    && (!(isBlocked(player.x + SIZE + fdelta,
                            player.y) || isBlocked(player.x + SIZE + fdelta, player.y
                            + SIZE - 1)))) {
                player. sprite.update(delta);
                player.x += fdelta;
            } 
                 }

        player.rect.setLocation(player.getplayershitboxX(),
                player.getplayershitboxY());

        for (Item1 h : stuff1) {
            if (player.rect.intersects(h.hitbox)) {
            if (h.isvisible) {
                    player.speed += .2f;
                    h.isvisible = false;
                }
            }
        }

        for (Itemwin w : stuffwin) {

            if (player.rect.intersects(w.hitbox)) {             
                if (w.isvisible) {
                    makevisible();
                    sbg.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                }
            }
        }

        player.health -= counter / 10000;
        if (player.health <= 0) {
            makevisible();
            sbg.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }

    public int getID() {
        return 1;
    }

    public void makevisible() {
        for (Item1 h : stuff1) {
            h.isvisible = true;
        }
        for (Item i : stuff) {
            i.isvisible = true;
        }
    }

    private boolean isBlocked(float tx, float ty) {
        int xBlock = (int) tx / SIZE;
        int yBlock = (int) ty / SIZE;
        return Blocked.blocked[xBlock][yBlock];  
    }
}
