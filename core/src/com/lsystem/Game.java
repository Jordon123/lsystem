package com.lsystem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ShapeRenderer sr;
	FractalTree ft;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		sr = new ShapeRenderer();
		ft = new FractalTree();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		String tree = ft.setup(3);
		ft.draw(tree,sr);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
