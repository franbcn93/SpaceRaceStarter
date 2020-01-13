package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class Bonus extends Scrollable {


    private Circle collisionCircle;
    private int value;
    private BonusKind kind;

    // Els estats del joc
    public enum BonusKind {

        NORMAL, SPECIAL

    }

    public Bonus(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        //establim tipus i velocitat del bonus
        calculateKind();

        //fixem la seva posició inicial a la dreta de la pantalla i a un punt aleatori de l'eix Y
        setOrigin(0, Methods.randomFloat(0, Settings.GAME_HEIGHT));

        //creem un cercle per a la colissio
        collisionCircle = new Circle();

    }

    /**
     * TODO Exercici 2 - actualització de l'estat del component
     *
     * @param delta
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        // Actualitzem el cercle de col·lisions
        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);

    }

    /**
     * TODO Exercici 2 - reset de l'estat del bonus
     *
     * @param newX
     */
    @Override
    public void reset(float newX) {
        super.reset(newX);
        calculateKind();
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y =  new Random().nextInt(Settings.GAME_HEIGHT - (int) height);

    }

    /**
     * TODO Exercici 2 - assignació de les textures
     *
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //revisem el tipus de bonus i hi assignarem la textura adient

    }

    /**
     * TODO Exercici 2 - detecció de la colissió spacecraft/bonus
     *
     * @param nau
     * @return
     */
    public boolean collides(Spacecraft nau) {

        if (position.x <= nau.getX() + nau.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan el bonus estigui a la mateixa alçada que la spacecraft
            return (Intersector.overlaps(collisionCircle, nau.getCollisionRect()));
        }
        return false;
    }

    /**
     * TODO Exercici 2 - calcula el tipus de bonus i estableix els parametres especifics del tipus calculat
     */
    private void calculateKind() {
        //crearem un nombre a l'atzar compres entre 0 i 100
        float randomizer = Methods.randomFloat(0, 1);
        //establirem el tipus de bonus

    }

    public BonusKind getKind() {return kind;}

    public int getValue() {return value;}

}