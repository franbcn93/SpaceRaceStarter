package cat.xtec.ioc.utils;

public class Settings {

    // Mida del joc, s'escalarà segons la necessitat
    public static final int GAME_WIDTH = 240;
    public static final int GAME_HEIGHT = 135;

    // Propietats de la nau
    public static final float SPACECRAFT_VELOCITY = 85;
    public static final int SPACECRAFT_WIDTH = 36;
    public static final int SPACECRAFT_HEIGHT = 20;
    public static final float SPACECRAFT_STARTX = 20;
    public static final float SPACECRAFT_STARTY = GAME_HEIGHT/2 - SPACECRAFT_HEIGHT/2;

    // Rang de valors per canviar la mida de l'asteroide.
    public static final float MAX_ASTEROID = 1.5f;
    public static final float MIN_ASTEROID = 0.5f;

    // Configuració Scrollable
    public static final int ASTEROID_SPEED = -150;
    public static final int ASTEROID_GAP = 75;
    public static final int BG_SPEED = -75;

    // TODO Exercici 3 - 4.1: Propietats per la moneda
    //Valors per la dimensió del coin
    public static final float COIN_DIMENSION = 0.3f;

    public static final int SCORE_INCREASE = 50; // s'incrementa en 100 cada cop que toca una moneda
    public static final int SCORE_SPEED = -175;
    public static final int SCORE_INCREASE_BLAU = 100; // s'incrementa en 50 cada cop que toca una moneda
    public static final int SCORE_SPEED_BLAU = -250;

    //TODO Exercici 4
    //Pause y laser
    public static final String PAUSESTRING = "pause";
    public static final int PAUSEINT = 0;
    public static final int PAUSE_BUTTON_SIZE = 14;

    //TODO Exercici 5.3
    public static final String PREFERENCES_FILENAME = "bestScore";
}
