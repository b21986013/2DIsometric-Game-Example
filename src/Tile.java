import com.nsprod.engine.core.GameObject;
import com.nsprod.engine.helpers.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends GameObject {

    private BufferedImage sprite;
    private int scaler = 4;

    public static int WIDTH;
    public static int HEIGHT;
    public Tile(int i, int i1, int i2, int i3, ID id, BufferedImage sprite) {
        super(i, i1, i2, i3, id);
        this.sprite = sprite;
        WIDTH = sprite.getWidth()* scaler;
        HEIGHT = sprite.getHeight() * scaler;



    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, x, y,sprite.getWidth() * scaler ,sprite.getHeight() * scaler,null);
    }
}
