import com.nsprod.engine.core.Handler;
import com.nsprod.engine.helpers.ID;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class MouseInput extends MouseAdapter{
    private int x;
    private int y;

    private BufferedImage [] tileImages ={GameIso.grassImg, GameIso.landImg};
    int next = 0;
    @Override
    public void mouseClicked(MouseEvent e) {
        Pair<Integer> screen = GameIso.cellToScreen(GameIso.world.getX(), GameIso.world.getY());
        Handler.addGameObject(new Tile(screen.getX(), screen.getY(), 0, 0, ID.Object, tileImages[(next++)%tileImages.length]));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }


    public int getX()
    {
        return x;
    }

    public int getY() {
        return y;
    }


}
