package cat.xtec.ioc.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {

    // Sprite Sheet
    public static Texture sheet, sheetExercici;

    // Nau i fons
    public static TextureRegion caraCuinerStraight, caraCuinerDown, caraCuinerUp, background;

    // Asteroid
    public static TextureRegion[] asteroid;
    public static Animation asteroidAnim;

    // Explosió
    public static TextureRegion[] explosion;
    public static Animation explosionAnim;

    // Sons
    public static Sound explosionSound, gameOver;
    public static Music music;

    //Pause
    public static TextureRegion pause;
    public static TextureRegion pauseButton;

    // TODO Exercici 3 - 4.1 i 4.2
    //Moneda
    public static TextureRegion coins;
    public static TextureRegion coins_blue;
    public static Sound coinSound, coinSound2;

    // Font
    public static BitmapFont font;

    public static void load() {
        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        sheet = new Texture(Gdx.files.internal("sheet2.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // TODO Exercici 1 – Carreguem les textures
        sheetExercici = new Texture(Gdx.files.internal("sheet3.png"));
        sheetExercici.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // TODO Exercici 1 – Sprites del cap del personatge principal
        // Canviem la mida d'amplitud i alçada
        caraCuinerStraight = new TextureRegion(sheetExercici, 0, 150, 60, 25);
        caraCuinerStraight.flip(false, true);

        caraCuinerUp = new TextureRegion(sheetExercici, 0, 225, 60, 30);
        caraCuinerUp.flip(false, true);

        caraCuinerDown = new TextureRegion(sheetExercici, 0, 177, 60, 30);
        caraCuinerDown.flip(false, true);

        // TODO Exercici 3 – Carreguem el texture de la moneda dorada
        coins = new TextureRegion(sheetExercici, 0, 75, 10, 10);
//        coins.flip(false, true);
        // TODO Exercici 3 – Carreguem el texture de la moneda blava
        coins_blue = new TextureRegion(sheetExercici, 10, 75, 10, 10);
//        coins_blue.flip(false, true);

        // TODO Exercici 1 – Carreguem els 16 estats de la pizza, l'anem rotant
        asteroid = new TextureRegion[16];
        for (int i = 0; i < asteroid.length; i++) {

            asteroid[i] = new TextureRegion(sheetExercici, i * 0, 100, 35, 35);
          //  asteroid[i].flip(false, true);

        }

        //TODO Exercici 4
        //Pause
        pause = new TextureRegion(sheet, 111, 0, 13, 16);
        pause.flip(false, true);

        // Creem l'animació de l'asteroid i fem que s'executi contínuament en sentit anti-horari
        asteroidAnim = new Animation(0.05f, asteroid);
        asteroidAnim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);

        // Creem els 16 estats de l'explosió
        explosion = new TextureRegion[16];

        // Carreguem els 16 estats de l'explosió
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                explosion[index++] = new TextureRegion(sheet, j * 64,  i * 64 + 49, 64, 64);
//                explosion[index-1].flip(false, true);
            }
        }

        // Finalment creem l'animació
        explosionAnim = new Animation(0.04f, explosion);

        // Fons de pantalla
        background = new TextureRegion(sheet, 0, 177, 480, 135);
    //    background.flip(false, true);

        /******************************* Sounds *************************************/
        // Explosió
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        gameOver = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.wav"));
        // Música del joc
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setVolume(0.2f);
        music.setLooping(true);

        // TODO Exercici 3 - 4.2
        //CoinSound
        coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));
        coinSound2 = Gdx.audio.newSound(Gdx.files.internal("sounds/laser-shot.wav"));

        /******************************* Text *************************************/
        // Font space
        FileHandle fontFile = Gdx.files.internal("fonts/space2.fnt");
        font = new BitmapFont(fontFile, true);
        font.getData().setScale(0.4f);


    }



    public static void dispose() {

        // Alliberem els recursos gràfics i de audio
        sheet.dispose();
        explosionSound.dispose();
        gameOver.dispose();
        music.dispose();
        // TODO Exercici 3 -4.2
        coinSound.dispose();
        coinSound2.dispose();
        sheetExercici.dispose();


    }
}
