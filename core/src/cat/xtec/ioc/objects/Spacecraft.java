package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class Spacecraft extends Actor {

    // Distintes posicions de la caraCuinerStraight, recta, pujant i baixant
    public static final int SPACECRAFT_STRAIGHT = 0;
    public static final int SPACECRAFT_UP = 1;
    public static final int SPACECRAFT_DOWN = 2;

    // Paràmetres de la caraCuinerStraight
    private Vector2 position;
    private int width, height;
    private int direction;

    private Rectangle collisionRect;



    public Spacecraft(float x, float y, int width, int height) {

        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        // Inicialitzem la caraCuinerStraight a l'estat normal
        direction = SPACECRAFT_STRAIGHT;

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        // Per a la gestio de hit
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);


    }



    //TODO Exercici 2.2
    public void act(float delta) {
        super.act(delta);

        // Movem la caraCuinerStraight depenent de la direcció controlant que no surti de la pantalla
        switch (direction) {
            case SPACECRAFT_UP:
                if (this.position.y - Settings.SPACECRAFT_VELOCITY * delta >= 0) {
                    this.position.y -= Settings.SPACECRAFT_VELOCITY * delta;
                }
                break;
            case SPACECRAFT_DOWN:
                if (this.position.y + height + Settings.SPACECRAFT_VELOCITY * delta <= Settings.GAME_HEIGHT) {
                    this.position.y += Settings.SPACECRAFT_VELOCITY * delta;
                }
                break;
            case SPACECRAFT_STRAIGHT:
//                this.position.x = Settings.SPACECRAFT_WIDTH/0.2f;
//                this.position.y = Settings.SPACECRAFT_HEIGHT/0.2f;
                break;
        }

        collisionRect.set(position.x, position.y + 3, width, 10);
        setBounds(position.x, position.y, width, height);


    }

    // Getters dels atributs principals
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Canviem la direcció de la caraCuinerStraight: Puja
    public void goUp() {
        direction = SPACECRAFT_UP;
    }

    // Canviem la direcció de la caraCuinerStraight: Baixa
    public void goDown() {
        direction = SPACECRAFT_DOWN;
    }

    // Posem la caraCuinerStraight al seu estat original
    public void goStraight() {
        direction = SPACECRAFT_STRAIGHT;
    }

    //TODO Exercici 2.2
    // Obtenim el TextureRegion depenent de la posició de la caraCuinerStraight
    public TextureRegion getSpacecraftTexture() {

        switch (direction) {

            case SPACECRAFT_STRAIGHT:
                return AssetManager.caraCuinerStraight;
            case SPACECRAFT_UP:
                return AssetManager.caraCuinerUp;
            case SPACECRAFT_DOWN:
                return AssetManager.caraCuinerDown;
            default:
                return AssetManager.caraCuinerStraight;
        }
    }

    public void reset() {

        // La posem a la posició inicial i a l'estat normal
        position.x = Settings.SPACECRAFT_STARTX;
        position.y = Settings.SPACECRAFT_STARTY;
        direction = SPACECRAFT_STRAIGHT;
        collisionRect = new Rectangle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(getSpacecraftTexture(), position.x, position.y, width, height);
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }
}
