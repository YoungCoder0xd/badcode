package me.youngcoder.quesbot.ex;

import java.io.IOException;

public class MalformedPacketException extends IOException {
	
	public MalformedPacketException(String message) {
		super(message);
	}
	
}
