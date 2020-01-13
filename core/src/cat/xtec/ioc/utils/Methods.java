package cat.xtec.ioc.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Random;

public class Methods {

    // Mètode que torna un float aleatòri entre un mínim i un màxim
    public static float randomFloat(float min, float max) {
        Random r = new Random();
        return r.nextFloat() * (max - min) + min;

    }

    /**
     * TODO Exercici 5.3 - construeix una cadena de text amb el missatge que
     * es mostrara per pantalla al finalitzar la partida (estat GAME_OVER)
     */
    public static String buildGameOverText(int earnedPoints) {
        String text = "Game Over :'(\n";
        if (earnedPoints < 100) {
            text += "You're a n00b!";
        } else if (earnedPoints >= 100 && earnedPoints < 150) {
            text += "Well done!";
        } else {
            text += "Oh yeah!! You are a pro!";
        }

        return text;
    }

    /**
     * TODO Exercici 5.3 - construeix una cadena de text amb el missatge que es mostrara per pantalla
     * a la pantalla principal del joc (estat READY)
     */
    public static String buildGameReadyText() {
        Preferences pref = Gdx.app.getPreferences(Settings.PREFERENCES_FILENAME);
        int points = pref.getInteger("points");
        String text = "Are you\nready?\n\n\nHigh Score:\n" + points;
        if (points < 100) {
            text += " (N00b)";
        } else if (points >= 100 && points < 150) {
            text += " (Person)";
        } else {
            text += " (Pro)";
        }

        return text;
    }
}