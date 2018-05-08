package me.youngcoder.quesbot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import javax.xml.crypto.Data;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import me.youngcoder.quesbot.ex.AuthenticationException;

public class Bot extends TelegramLongPollingBot {
	
	public static HashMap<String, Integer> map = new HashMap<>();
	public static Random r = new Random();
	public boolean b;
	public static rcon rc = new rcon();
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		System.out.println("Соединяюсь с сервером...");
		
		Thread.sleep(500);
		
		try {
			rc.rconConnect();
			System.out.println("Соединение прошло успешно...");
		} catch (AuthenticationException e1) {
			System.out.println("Не удалось связатся с сервером...");
			System.exit(0);
		}
		
		ApiContextInitializer.init();
		
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		
		try {
			telegramBotsApi.registerBot(new Bot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		
		}
	}
	
	@Override
	public String getBotUsername() {
		return "NAME";
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		
		Message message = update.getMessage();
		
		if(message != null && message.hasText()) {
			
			if(message.hasText()) {
				System.out.println("["+"Logger"+"] " + message.getText());
			} else {
				System.out.println("["+"Logger"+"] " + "File or Documen");
			}
			
			if(message.getText().equalsIgnoreCase("/start")) {
				sendMessage(message, "Бот запущен. Команда для выключения: /end ");
				sendMessage(message, "Для отправки команды на сервер в начале ставьте > \nПример: > reload");
			}
			
			if(message.getText().equalsIgnoreCase("/end")) {
				sendMessage(message, "Бот выключен.");
				System.exit(0);
			}
			
			if(message.getText().startsWith("> ")) {
				String user_msg = message.getText().replaceAll("> ", "");
				user_msg.replaceAll("/", "");
				user_msg.replaceAll("cmd ", "");
				try {
					rc.sendCommand(user_msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
				sendMessage(message, "Ответ: "+rc.result());
			}
		} else {
			sendMessage(message, "Извините, но я не понимаю что вы от меня хотите.");
		}
		
	}
	
	@Override
	public String getBotToken() {
		return "TOKEN";
	}
	
	@SuppressWarnings("deprecation")
	private void sendMessage(Message message, String string) {
		
		SendMessage sendMessage = new SendMessage();
		
		sendMessage.setChatId(message.getChatId().toString());
		
		sendMessage.setText(string);
		
		try {
			sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
