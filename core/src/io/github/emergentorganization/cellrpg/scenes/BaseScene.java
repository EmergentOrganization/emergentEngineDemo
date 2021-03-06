package io.github.emergentorganization.cellrpg.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.emergentorganization.emergent2dcore.PixelonTransmission;


public abstract class BaseScene extends ScreenAdapter {

    protected final PixelonTransmission pt;
    protected final Stage stage;

    public BaseScene(PixelonTransmission pt) {
        this.pt = pt;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Must call Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT) in subclasses before calling super.render()
     */
    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Should the SceneManager stash this scene when navigating to a different scene?
     */
    protected abstract boolean shouldStash();
}
