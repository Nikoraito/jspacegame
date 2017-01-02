package net.nikoraito.jspacegame.server;

import com.badlogic.gdx.backends.headless.*;
import net.nikoraito.jspacegame.JSpaceGame;

public class ServerLauncher {
	public static void main (String[] arg) {
		HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
		new HeadlessApplication(new JSpaceGame(), config);
	}
}
