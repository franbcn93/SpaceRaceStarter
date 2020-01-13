package cat.xtec.ioc.objects;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.Random;

import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class ScrollHandler extends Group {

    // Fons de pantalla
    Background bg, bg_back;

    // Asteroides
    int numAsteroids;
    private ArrayList<Asteroid> asteroids;

    // Objecte Random
    Random r;

    private Coin coin;
    private CoinBlue coinBlue;

    //TODO Exercici 5.3
    private int puntuacion;

    public ScrollHandler() {

        // Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        // Afegim els fons al grup
        addActor(bg);
        addActor(bg_back);

        // Creem l'objecte random
        r = new Random();

        // Comencem amb 3 asteroids
       numAsteroids = 3;

        // Creem l'ArrayList
        asteroids = new ArrayList<Asteroid>();

        //TODO Exercici 2.2
        // Definim una mida aleatòria entre el mínim i el màxim
        float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 34;

        // Afegim el primer Asteroid a l'Array i al grup
        Asteroid pizza = new Asteroid(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ASTEROID_SPEED);
        asteroids.add(pizza);
        addActor(pizza);

        // Des del segon fins l'últim asteroide
        for (int i = 1; i < numAsteroids; i++) {
            // Creem la mida al·leatòria
            newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 34;
            // Afegim l'pizza.
            pizza = new Asteroid(asteroids.get(asteroids.size() - 1).getTailX() + Settings.ASTEROID_GAP, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ASTEROID_SPEED);
            // Afegim l'asteroide a l'ArrayList
            asteroids.add(pizza);
            // Afegim l'asteroide al grup d'actors
            addActor(pizza);
        }

        // TODO Exercici 3 -4.1
        //Afegim el coin
        setCoin(new Coin(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT) - Settings.COIN_DIMENSION, 8,8, Settings.SCORE_SPEED ));
        addActor(getCoin());
        //
        setCoinBlue(new CoinBlue(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT) - Settings.COIN_DIMENSION, 8,8, Settings.SCORE_SPEED_BLAU ));
        addActor(getCoinBlue());

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Si algun element està fora de la pantalla, fem un reset de l'element.
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());

        }

        for (int i = 0; i < asteroids.size(); i++) {

            Asteroid asteroid = asteroids.get(i);
            if (asteroid.isLeftOfScreen()) {
                if (i == 0) {
                    asteroid.reset(asteroids.get(asteroids.size() - 1).getTailX() + Settings.ASTEROID_GAP);
                } else {
                    asteroid.reset(asteroids.get(i - 1).getTailX() + Settings.ASTEROID_GAP);
                }
            }
        }

        // TODO Exercici 3 -4.1 Si ha sortit de pantalla, fem reset
        if(getCoin().isLeftOfScreen()){
            getCoin().reset(Settings.GAME_WIDTH);
        }
        //
        if(getCoinBlue().isLeftOfScreen()){
            getCoinBlue().reset(Settings.GAME_WIDTH*6);
        }
    }

    public boolean collides(Spacecraft nau) {

        // Comprovem les col·lisions entre cada asteroid i la nau
        for (Asteroid asteroid : asteroids) {
            if (asteroid.collides(nau)) {
                return true;
            }
        }
        return false;
    }

    // TODO Exercici 3 -4.1 - Per veure si capturem la moneda
    public boolean isCapturedCoin(Spacecraft nau){
        if(getCoin().captureCoin(nau)){
            getCoin().reset(Settings.GAME_WIDTH);
            return  true;
        }
        //Comprovem si
        return false;
    }
    // TODO Exercici 3 -4.1 - Per veure si capturem la moneda blava
    public boolean isCapturedCoinBlue(Spacecraft nau){
        if(getCoinBlue().captureCoinBlue(nau)){
            getCoinBlue().reset(Settings.GAME_WIDTH);
            return  true;
        }
        //Comprovem si
        return false;
    }

    public void reset() {

        // Posem el primer asteroid fora de la pantalla per la dreta
        asteroids.get(0).reset(Settings.GAME_WIDTH);
        // Calculem les noves posicions de la resta d'asteroids.
        for (int i = 1; i < asteroids.size(); i++) {

            asteroids.get(i).reset(asteroids.get(i - 1).getTailX() + Settings.ASTEROID_GAP);

        }
    }

    public ArrayList<Asteroid> getAsteroids() {

        return asteroids;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    public CoinBlue getCoinBlue() {
        return coinBlue;
    }

    public void setCoinBlue(CoinBlue coinBlue) {
        this.coinBlue = coinBlue;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
}