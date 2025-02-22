import com.nsprod.engine.core.Game;
import com.nsprod.engine.core.Handler;
import com.nsprod.engine.helpers.ID;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameIso extends Game {
    public final int GAMEWIDTH, GAMEHEIGHT;
    private MouseInput mouseInput;
    private  BufferedImage highlightImg;
    public static BufferedImage grassImg;
    public static BufferedImage landImg;
    public static Pair<Integer>  origin = new Pair<>(5, 2);
    public static Pair<Integer> cell = new Pair<>(0,0);
    public static Pair<Integer> world = new Pair<>(0, 0);

    public static final int worldSize = 8;

    public GameIso(int i, int i1, String s) throws IOException {
        super(i, i1, s);

        GAMEWIDTH = i;
        GAMEHEIGHT = i1;



        new Tile(-100 , -100,0,0, ID.Object, load("res/empty.png"));


        highlightImg = load("res/highlight.png");
        grassImg = load("res/grass.png");
        landImg = load("res/land.png");


        mouseInput = new MouseInput();
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);

        createWorld(worldSize);

    }

    private void createWorld(int worldSize) throws IOException
    {
        for(int cellX = 0; cellX < worldSize; cellX++)
            for(int cellY = 0; cellY < worldSize; cellY++)
            {
                Pair<Integer> screen = cellToScreen(cellX, cellY);

                Tile land = new Tile(screen.getX() , screen.getY() , 0,0, ID.Object, load("res/empty.png"));

                Handler.addGameObject(land);
            }
    }

    public static Pair<Integer> cellToScreen(int cellX, int cellY)
    {
        int screenX = (cellX - cellY) * Tile.WIDTH /2 + origin.getX() * Tile.WIDTH;
        int screenY = (cellX + cellY) * Tile.HEIGHT /2 + origin.getY() * Tile.HEIGHT;

        return new Pair<>(screenX, screenY);
    }

    @Override
    public void tick() {
        Handler.tick();

        if(Tile.WIDTH > 0 && Tile.HEIGHT > 0 && mouseInput != null){
            cell.setX(mouseInput.getX() / Tile.WIDTH);
            cell.setY(mouseInput.getY() / Tile.HEIGHT);

            world.setX((cell.getX() - origin.getX()) + (cell.getY() - origin.getY()));
            world.setY((cell.getY() - origin.getY()) - (cell.getX() - origin.getX()));

            quarter();
        }
    }

    // Snaps to the world space not multiples of tile size
    private void quarter()
    {
        int rX = mouseInput.getX() % Tile.WIDTH;
        int rY = mouseInput.getY() % Tile.HEIGHT;

        if ((rX > Tile.WIDTH / 2) && (rY < Tile.HEIGHT / 2))
        {
//            System.out.println("Quarter1");
            if (rX-Tile.WIDTH /2 > 2 * rY){
                world.setY(world.getY()-1);
            }
        }
        else if(rX < Tile.WIDTH / 2 && rY < Tile.HEIGHT / 2 ){
//            System.out.println("Quarter2");
            if(Tile.WIDTH /2 - rX > 2 * rY)
                world.setX(world.getX()-1);
        }
        else if(rX < Tile.WIDTH / 2 && rY > Tile.HEIGHT / 2)
        {
//            System.out.println("Quarter3");
            if(Tile.WIDTH /2 - rX > (Tile.HEIGHT - rY) * 2)
                world.setY(world.getY()+1);
        }
        else if(rX  > Tile.WIDTH / 2 && rY > Tile.HEIGHT / 2)
        {
//            System.out.println("Quarter4");
            if(rX - Tile.WIDTH /2 > (Tile.HEIGHT - rY) * 2)
                world.setX(world.getX()+1);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GAMEWIDTH, GAMEHEIGHT);

        Handler.render(g);
        if(mouseInput != null){
            // Draw STRING INFO
            g.setColor(Color.WHITE);
            g.drawString("Mouse X: " + mouseInput.getX(), 700, 20);
            g.drawString("Mouse Y: " + mouseInput.getY(), 700, 40);
            g.drawString("World X: " + world.getX(), 700, 60);
            g.drawString("World Y: " + world.getY(), 700, 80);

            Pair<Integer> screen = cellToScreen(world.getX(), world.getY());
            g.drawImage(highlightImg, screen.getX(), screen.getY(), Tile.WIDTH, Tile.HEIGHT, null);
        }

    }

    private  BufferedImage load(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    public static void main(String[] args) {

        try {
            new GameIso(800, 600, "IsometricGame");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
