package cat.xtec.ioc.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import cat.xtec.ioc.objects.Bonus;
import cat.xtec.ioc.utils.Settings;

public class ScoreManager {

    private int score;

    public ScoreManager() {
        resetScore();
    }

    //TODO Exercici 5.3 - desa el resultat si s'ha superat la puntuació maxima
    public void addBonusPoints(Bonus bonus) {
        this.score += bonus.getValue();
    }

    public void saveScore() {
        if (this.score > this.getSavedScore()) {
            Preferences pref = Gdx.app.getPreferences(Settings.PREFERENCES_FILENAME);
            pref.putInteger("points", this.score);
            pref.flush();
        }
    }


    /**
     * TODO Exercici 5.3 - recupera la puntuacio desada a les "preferences"
     *
     * @return
     */
    public int getSavedScore() {
        Preferences pref = Gdx.app.getPreferences(Settings.PREFERENCES_FILENAME);
        return pref.getInteger("points");
    }


    //posa el contador de punts a 0

    public void resetScore() {
        this.score = 0;
    }

    public int getScore() {
        return this.score;
    }
}