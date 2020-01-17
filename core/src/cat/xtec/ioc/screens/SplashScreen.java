package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;


public class SplashScreen implements Screen {

    private Stage stage;
    private SpaceRace game;

    private Label.LabelStyle textStyle;
    private Label textLbl;


    public SplashScreen(SpaceRace game) {

        this.game = game;

        // Creem la càmera de les dimensions del joc
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        // Posant el paràmetre a true configurem la càmera per a
        // que faci servir el sistema de coordenades Y-Down
        camera.setToOrtho(true);

        // Creem el viewport amb les mateixes dimensions que la càmera
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        // Creem l'stage i assginem el viewport
        stage = new Stage(viewport);

        // Afegim el fons
        stage.addActor(new Image(AssetManager.background));

        // Creem l'estil de l'etiqueta i l'etiqueta
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("SpaceRace", textStyle);

        // Creem el contenidor necessari per aplicar-li les accions
        Container container = new Container(textLbl);
        container.setTransform(true);
        container.center();
        container.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 2);

        // TODO Exercici 2.1 – Inserim el titol i el fem desapareixer amb el addAction
        // Afegim les accions de escalar: primer es fa gran i després torna a l'estat original ininterrompudament
//        container.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(0f, 1f, 2), Actions.scaleTo(1, 0, 2))));
//        stage.addActor(container);
        container.addAction(Actions.repeat(RepeatAction.FOREVER,
        Actions.sequence(Actions.parallel(Actions.scaleTo(1.5f, 1.5f, 1), Actions.alpha(1.0f, 1)), //escala gran i apareix
        Actions.parallel( Actions.scaleTo(1f, 1f, 1), Actions.alpha(0.0f, 1))))); //escala normal i desapareix
        stage.addActor(container);

        //TODO Exercici 2.2 Creem la imatge de la nau i li assignem el moviment en horitzontal
        Image spacecraft = new Image(AssetManager.caraCuinerStraight);
        float y = Settings.GAME_HEIGHT / 2 + textLbl.getHeight();
        spacecraft.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(0.7f,0.7f),
        Actions.moveTo(0 - spacecraft.getWidth(), y),Actions.moveTo(Settings.GAME_WIDTH + spacecraft.getWidth(),
        y,1.4f), Actions.rotateBy(-90),
                Actions.moveTo(Settings.GAME_WIDTH-19,Settings.GAME_HEIGHT+spacecraft.getHeight()),
                Actions.moveTo(Settings.GAME_WIDTH-19, 0-spacecraft.getHeight(), 1.5f),
                Actions.rotateTo(360))));

        stage.addActor(spacecraft);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        stage.draw();
        stage.act(delta);

        // Si es fa clic en la pantalla, canviem la pantalla
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport()));
            dispose();
        }

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
}
