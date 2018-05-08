package me.youngcoder.quesbot;

import java.io.IOException;

import me.youngcoder.quesbot.core.Rcon;
import me.youngcoder.quesbot.ex.AuthenticationException;

public class rcon {
	
	
	public static String result;
	public static Rcon rcon;
	
	public void rconConnect() throws IOException, AuthenticationException {
		rcon = new Rcon("127.0.0.1", 25566, "12345".getBytes());
	}
	public void sendCommand(String command) throws IOException {
		result = rcon.command(command);
	}
	public String result() {
		return result;
	}
}
