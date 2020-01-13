package cat.xtec.ioc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.screens.GameScreen;
import cat.xtec.ioc.utils.Settings;

public class PuntuacionScreen implements Screen {

    private SpaceRace game;
    private Stage stage;

    public PuntuacionScreen(SpaceRace game, int recordActual, int puntuacio) {
        this.game = game;
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
        stage.addActor(new Image(AssetManager.background));
        cargarLabels(recordActual, puntuacio);
        Gdx.input.setInputProcessor(stage);
    }

    private void cargarLabels(int recordActual, int puntuacio) {
//        Label labelRecord = new Label("Record actual (" + (dificultad == 1 ? "F" : dificultad == 2 ? "M" : "D") + "): " + recordActual, new Label.LabelStyle(AssetManager.font, null));
        Label labelPuntuacion = new Label("Tu puntuacion: " + puntuacio, new Label.LabelStyle(AssetManager.font, null));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = AssetManager.font;
//        TextButton butonClick = new TextButton("Pulsa aqui para volver a jugar", textButtonStyle);
//        butonClick.getLabel().setFontScale(0.25f);
//        butonClick.setPosition(Settings.GAME_WIDTH / 2 - butonClick.getWidth() / 2,
//                Settings.GAME_HEIGHT * 0.75f - butonClick.getHeight() / 2);
//        butonClick.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new GameScreen(game, obtenerDificultatPrefs()));
//            }
//        });

//        Container<Label> contenedorRecord = new Container<Label>(labelRecord);
//        contenedorRecord.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT * 0.25f);

        Container<Label> contenedorPuntuacion = new Container<Label>(labelPuntuacion);
        contenedorPuntuacion.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 2);
        if (puntuacio >= recordActual) {
            contenedorPuntuacion.setTransform(true);
            contenedorPuntuacion.addAction(Actions.repeat(RepeatAction.FOREVER,
                    Actions.sequence(Actions.scaleTo(1.25f, 1.25f, 1),
                            Actions.scaleTo(1, 1, 1))));
        }

//        stage.addActor(contenedorRecord);
        stage.addActor(contenedorPuntuacion);

//        stage.addActor(butonClick);

        Image ajustes = new Image((Drawable) AssetManager.font);
        ajustes.setWidth(16f);
        ajustes.setHeight(16f);

        ajustes.setPosition(Settings.GAME_WIDTH - ajustes.getWidth() - 4, 4f);
        TextButton settings = new TextButton("", textButtonStyle);
        settings.setWidth(ajustes.getWidth());
        settings.setHeight(ajustes.getHeight());
        settings.setPosition(ajustes.getX(), ajustes.getY());
//        settings.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new DificultatScreen(game));
//            }
//        });
        stage.addActor(ajustes);

        stage.addActor(settings);
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
    }

//    private int obtenerDificultatPrefs() {
//        return Gdx.app.getPreferences("preferencias").getInteger("dificultad", -33);
//    }

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
        game.dispose();
    }
}