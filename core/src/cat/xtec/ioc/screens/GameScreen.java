package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.helpers.InputHandler;
import cat.xtec.ioc.helpers.ScoreManager;
import cat.xtec.ioc.objects.Asteroid;
import cat.xtec.ioc.objects.ScrollHandler;
import cat.xtec.ioc.objects.Spacecraft;
import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;


public class GameScreen implements Screen {

    // Els estats del joc

    public enum GameState {

        READY, RUNNING, GAMEOVER, PAUSE

    }

    private GameState currentState;

    // Objectes necessaris
    private Stage stage;
    private Spacecraft spacecraft;
    private ScrollHandler scrollHandler;

    // Encarregats de dibuixar elements per pantalla
    private ShapeRenderer shapeRenderer;
    private Batch batch;

    // Per controlar l'animació de l'explosió
    private float explosionTime = 0;

    // Preparem el textLayout per escriure text
    private GlyphLayout textLayout;

    //TODO 3 -4.2
    //text puntuacio
    private Label.LabelStyle textStyle;
    private String StringPuntuacio;
    private Container container;
    private Label labelPuntuacion;
    private int p;
    private ScoreManager scoreManager;

    //TODO Exercici 5 -5.3
    private Container<Label> contenedorPuntuacion;
    private int puntuacion;
    private int recordActual;

    public GameScreen(Batch prevBatch, Viewport prevViewport) {

        // Iniciem la música
        AssetManager.music.play();

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem l'stage i assginem el viewport
        stage = new Stage(prevViewport, prevBatch);

        batch = stage.getBatch();

        // Creem la nau i la resta d'objectes
        spacecraft = new Spacecraft(Settings.SPACECRAFT_STARTX, Settings.SPACECRAFT_STARTY, Settings.SPACECRAFT_WIDTH, Settings.SPACECRAFT_HEIGHT);
        scrollHandler = new ScrollHandler();

        //TODO Exercici 3 - 4.1
        // Inicialitzem puntuacio
        scoreManager = new ScoreManager();
        StringPuntuacio = "0";
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        labelPuntuacion = new Label(StringPuntuacio, textStyle);
        labelPuntuacion.setText(StringPuntuacio);
        container = new Container(labelPuntuacion);
        container.setTransform(true);
        container.setPosition(Settings.SPACECRAFT_STARTX, 0 + Settings.SPACECRAFT_HEIGHT);
        container.setName("containerpuntuacio");


        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(spacecraft);
        stage.addActor(container);


        // Donem nom a l'Actor
        spacecraft.setName("caraCuinerStraight");

        // Iniciem el GlyphLayout
        textLayout = new GlyphLayout();
        textLayout.setText(AssetManager.font, "Are you\nready?");

        currentState = GameState.READY;

        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));

        //TODO Exercici 4
        //Actor de Pause
        Image pause = new Image (AssetManager.pause);
        pause.setPosition((Settings.GAME_WIDTH) - pause.getWidth() - 5, 5);
        pause.setName(Settings.PAUSESTRING);
        pause.setVisible(true);
        stage.addActor(pause);
    }

    private void drawElements() {

        // Recollim les propietats del Batch de l'Stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Pintem el fons de negre per evitar el "flickering"
        //Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        //Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Definim el color (verd)
        shapeRenderer.setColor(new Color(0, 1, 0, 1));

        // Pintem la nau
        shapeRenderer.rect(spacecraft.getX(), spacecraft.getY(), spacecraft.getWidth(), spacecraft.getHeight());

        // Recollim tots els Asteroid
        ArrayList<Asteroid> pizzes = scrollHandler.getAsteroids();
        Asteroid pizza;

        for (int i = 0; i < pizzes.size(); i++) {

            pizza = pizzes.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(pizza.getX() + pizza.getWidth() / 2, pizza.getY() + pizza.getWidth() / 2, pizza.getWidth() / 2);
        }
        shapeRenderer.end();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Dibuixem tots els actors de l'stage
        stage.draw();

        //TODO Exercici 5 - 5.3
        puntuacion = scrollHandler.getPuntuacion();
//        labelPuntuacion.setText("Puntuacion: " + puntuacion);
        if (puntuacion > recordActual) {
            container.addAction(Actions.repeat(RepeatAction.FOREVER,
                    Actions.sequence(Actions.scaleTo(1.25f, 1.25f, 1),
                            Actions.scaleTo(1, 1, 1))));
        }

        // Depenent de l'estat del joc farem unes accions o unes altres
        switch (currentState) {

            case GAMEOVER:
                updateGameOver(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case READY:
                updateReady();
                break;
            //TODO Exercici 4
            case PAUSE:
                textLayout.setText(AssetManager.font, "Pause");
                updatePause(Settings.PAUSEINT);
                break;

        }

        //drawElements();

    }

    //TODO Exercici 4
    private void updatePause(float delta) {
        stage.act(delta);
        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 2) - textLayout.height / 2);
        batch.end();

    }

    private void updateReady() {

        // TODO Exercici 3 - 4.1
        //Resetejem per a que el coin surti del fons i no de la ultima posicio
        scrollHandler.getCoin().reset(Settings.GAME_WIDTH);
        scrollHandler.getCoinBlue().reset(Settings.GAME_WIDTH);

        // Dibuixem el text al centre de la pantalla
        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 2) - textLayout.height / 2);
        //stage.addActor(textLbl);
        batch.end();

    }

    private void updateRunning(float delta) {
        stage.act(delta);
        if (scrollHandler.collides(spacecraft)) {
            // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
            AssetManager.explosionSound.play();
            AssetManager.music.stop();
            AssetManager.gameOver.play();

            stage.getRoot().findActor("caraCuinerStraight").remove();

            //TODO Exercici 5 - 5.1
            //Quan acaba la partida, depenent la puntuació dona un missatge o un altre
            String puntuacioText = "\n La puntuacio es: " + StringPuntuacio;

            if((Integer.parseInt(StringPuntuacio))<100){
                textLayout.setText(AssetManager.font, "You are a n00b! :'(" + puntuacioText);
            }
            if((Integer.parseInt(StringPuntuacio)>=100 && Integer.parseInt(StringPuntuacio)<150)){
                textLayout.setText(AssetManager.font, "Well done! :'(" + puntuacioText);
            }
            if((Integer.parseInt(StringPuntuacio))>=150){
                textLayout.setText(AssetManager.font, "Oh yeah!! You are a pro! :'(" + puntuacioText);
            }


            //TODO Exercici 5 -5.3
            contenedorPuntuacion = new Container<Label>(labelPuntuacion);
            contenedorPuntuacion.center();
            contenedorPuntuacion.setPosition(Settings.SPACECRAFT_STARTX, 0 + Settings.SPACECRAFT_HEIGHT);
            contenedorPuntuacion.setName("contenedorPuntuacion");
            contenedorPuntuacion.setTransform(true);
            stage.addActor(contenedorPuntuacion);
            contenedorPuntuacion.addAction(Actions.repeat(RepeatAction.FOREVER,
                    Actions.sequence(Actions.scaleTo(1.25f, 1.25f, 1),
                            Actions.scaleTo(1, 1, 1))));

//            // Iniciem el GlyphLayout
//            textLayout = new GlyphLayout();
//            textLayout.setText(AssetManager.font, "Are you\nready?" + StringPuntuacio);


//            PuntuacionScreen puntuacionScreen = new PuntuacionScreen(game, recordActual, puntuacio);

            //TODO Exercici 3 - 4.1
            //Posem el contador a 0 cada partida finalitzada

//            StringPuntuacio = "0";
//            labelPuntuacion.setText(StringPuntuacio);

//            labelPuntuacion.setText((CharSequence) contenedorPuntuacion);


            currentState = GameState.GAMEOVER;
        }

        // TODO Exercici 3 - 4.1 i 4.2
        //Cridem al mètode que toca is capturem la moneda
        //Incrementem en 100 la puntuació
        if (scrollHandler.isCapturedCoinBlue(spacecraft)) {
            puntuacioMoneda(Settings.SCORE_INCREASE_BLAU);
        }
        //Incrementem en 50 la puntuació si capturem la moneda
        if (scrollHandler.isCapturedCoin(spacecraft)) {
            puntuacioMoneda(Settings.SCORE_INCREASE);
        }
    }

    //Donarem uns punts depenent quina moneda sigui
    private void puntuacioMoneda(int punts){
        p = Integer.parseInt(StringPuntuacio);
        p = p + punts;
        StringPuntuacio = Integer.toString(p);
        labelPuntuacion.setText(StringPuntuacio);
        container.addAction(Actions.sequence(Actions.scaleTo(1.5f, 1.5f, 0.3f, Interpolation.linear), Actions.scaleTo(1, 1, 0.3f, Interpolation.linear)));
        stage.addActor(container);
        //Emitim un so quan això passa
//        AssetManager.coinSound2.play();
        if (punts == 100) {
            AssetManager.coinSound2.play();
        }
        else{
            AssetManager.coinSound.play();
        }


    }

    private void updateGameOver(float delta) {
        stage.act(delta);
//        Methods.buildGameOverText(Integer.parseInt(StringPuntuacio));
//        Methods.buildGameReadyText();
        //TODO Exercici 5.2 - reiniciem la puntuacio i actualitzem i esborrem el text del label que la mostra
        StringPuntuacio = "0";
        labelPuntuacion.setText(StringPuntuacio);

        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH - textLayout.width) / 2, (Settings.GAME_HEIGHT - textLayout.height) / 2);
        // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
        batch.draw((TextureRegion) AssetManager.explosionAnim.getKeyFrame(explosionTime, false), (spacecraft.getX() + spacecraft.getWidth() / 2) - 18, spacecraft.getY() + spacecraft.getHeight() / 2 - 18, 44, 44);
        batch.end();

        explosionTime += delta;

    }

    public void reset() {

        // Posem el text d'inici
        textLayout.setText(AssetManager.font, "Are you\nready?");
        // Cridem als restart dels elements.
        spacecraft.reset();
        scrollHandler.reset();

        // Posem l'estat a 'Ready'
        currentState = GameState.READY;

        // Afegim la nau a l'stage
        stage.addActor(spacecraft);

        // Posem a 0 les variables per controlar el temps jugat i l'animació de l'explosió
        explosionTime = 0.0f;

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    public Stage getStage() {
        return stage;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {

        //TODO Exercici 4
        switch (currentState){
            case PAUSE:
                stage.getRoot().findActor(Settings.PAUSESTRING).setVisible(false);
                AssetManager.music.setVolume(0.05f); //Baixem volum
                break;
            case READY:
                stage.getRoot().findActor(Settings.PAUSESTRING).setVisible(false);
                break;
            case RUNNING:
                AssetManager.music.play();
                AssetManager.music.setVolume(0.1f);
                stage.getRoot().findActor(Settings.PAUSESTRING).setVisible(true);
                break;
            case GAMEOVER:
                break;
        }
        this.currentState = currentState;
    }

}