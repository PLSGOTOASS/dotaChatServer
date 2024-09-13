package ru.eblan;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        try (ServerSocket serverSocket = new ServerSocket(1337);
             Socket chatSocket = serverSocket.accept();
             BufferedReader reader = new BufferedReader(new InputStreamReader(chatSocket.getInputStream(), StandardCharsets.UTF_8))) {

            while (true) {
                String quote = reader.readLine();
                quote = quote.trim();

                String chatIp = chatSocket.getInetAddress().getHostAddress();
                boolean isSomethingWrong = false;

                int allChatInput;

                String allChatStringInput = reader.readLine();
                allChatStringInput = allChatStringInput.trim();

                try {
                    allChatInput = Integer.parseInt(allChatStringInput);
                } catch (NumberFormatException e) {
                    isSomethingWrong = true;
                    allChatInput = 1;
                }

                if (isSomethingWrong) {
                    quote = "я даун: " + chatIp;
                }

                StringSelection textToSend = new StringSelection(quote);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(textToSend, null);

                boolean isAllChat = allChatInput == 1;

                int robotDelay = 75; // работает нестабильно ниже 75

                if (isAllChat) {
                    robot.keyPress(KeyEvent.VK_SHIFT);;
                    robot.keyPress(KeyEvent.VK_ENTER);

                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                }
                else {
                    robot.keyPress(KeyEvent.VK_ENTER);

                    robot.keyRelease(KeyEvent.VK_ENTER);
                }

                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);

                robot.delay(robotDelay);

                robot.keyPress(KeyEvent.VK_ENTER);

                robot.keyRelease(KeyEvent.VK_ENTER);


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}