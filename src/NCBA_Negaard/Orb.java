/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NCBA_Negaard;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author DrKairos
 */
public class Orb {

    private int x, y, width, height;
    private int dmg, hitboxX, hitboxY;
    private boolean isVisible;
    Image orbpic;
    Shape hitbox;

    public Orb(int a, int b) throws SlickException {
        this.x = a;
        this.y = b;
        this.isVisible = false;
        this.orbpic = new Image("res/orbs/Ninja_22.png");
        this.hitbox = new Rectangle(a, b, 32, 32);
    }
}
/*
* Getters and setters are a common concept in Java. 
* A design guideline in Java, and object oriented
* programming in general, is to encapsulate/isolate
* values as much as possible
* Getters - are methods used to query the value of
* instance variables.
* this.getLocationX();
* Setters - methods that set values for instance
* variables 
*/
