package com.lsystem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ShapeRenderer sr;
	FractalTree ft;
	OrthographicCamera camera;
	Viewport viewport;
	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		sr = new ShapeRenderer();
		ft = new FractalTree();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render() {
		camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		String tree = ft.setup(8);

		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			camera.zoom += 2;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			camera.zoom -= 2;
		}
		sr.setProjectionMatrix(camera.combined);
		ft.draw(tree, sr);
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}

	public void mouseInput() {
		Gdx.input.getX();
		Gdx.input.getY();

	}
}
