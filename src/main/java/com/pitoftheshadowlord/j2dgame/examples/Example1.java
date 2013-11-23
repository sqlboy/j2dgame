package com.pitoftheshadowlord.j2dgame.examples;

import com.pitoftheshadowlord.j2dgame.core.Game;
import com.pitoftheshadowlord.j2dgame.core.SceneManager;

public class Example1 {

	public static void main(String[] args) {
		Game game = new Game(400, 400, 1);
		SceneManager sceneManager = SceneManager.get();
		sceneManager.register("menu", ExampleScene.class);
		sceneManager.loadScene("menu");
		
		game.start();
	}
}
