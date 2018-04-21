package com.deeep.spaceglad;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Client implements Runnable {
	private static final Charset ascii = Charset.forName("ASCII");

	private static final String host = "cveworld.com";
	private static final int port = 4500;

	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
    private Thread thread;

	public enum ClientState {
		STARTING, RUNNING, STOPPED;
	}

	private volatile ClientState state = ClientState.STOPPED;
	private final ArrayList<String> queue = new ArrayList<String>();

	// PUBLIC METHODS
	public synchronized ArrayList<String> getMessageQueue() {
		ArrayList<String> q = new ArrayList<String>(queue);
		queue.clear();
		return q;
	}

	//Added for use in new user function.
	//Spamming of create account button would cause crash if using getMessage Queue
	public ArrayList<String> getQueue() {
		return queue;
	}

	public void sendMessage(String msg) {
		sendMessage(msg.getBytes(ascii));
	}

	public boolean isRunning() {
		return getState() == ClientState.RUNNING;
	}

	public void close() {
        if (isRunning()) {
    		setState(ClientState.STOPPED);
    		sendMessage("\\logout \n");
    		socket.dispose();
            try {
                thread.join();
            } catch (Exception e) {
                // Can't actually error
            }
        }
	}

	// BEGIN PRIVATE METHODS

	public Client(String username, String password) {

		// Connect to server
		setState(ClientState.STARTING);
		SocketHints hints = new SocketHints();
		hints.connectTimeout = 2000;
		try {
			socket = Gdx.net.newClientSocket(Protocol.TCP, host, port, hints);
		} catch (GdxRuntimeException e) {
			setState(ClientState.STOPPED);
			return;
		}
		
		if (!socket.isConnected()) {
			setState(ClientState.STOPPED);
			return;
		}

		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());

		// Send Login Command
		setState(ClientState.RUNNING);
		sendMessage("\\login -cvecypher");
		sendMessage(cypher(username));
		sendMessage(" ");
		sendMessage(cypher(password));
		sendMessage("\n");

		// Wait for valid response
		while (isRunning()) {
			String msg = recvMessage();
			if (msg.equals("")) {
			} else if (msg.equals("Valid\n")) {
                thread = new Thread(this);
                thread.start();
				break;
			} else if (msg.equals("Failed\n")) {
                // TODO show login failed error
				setState(ClientState.STOPPED);
				break;
			} else {
				// Invalid response
                // TODO should we handle this?
			}
		}
	}

	private String recvMessage() {
		String msg = "";
		if (isRunning()) {
			try {
				if (dis.available() > 0) {
					byte c;
					do {
						c = dis.readByte();
						msg += (char) c;
					} while (c != '\n');
				}
			} catch (IOException e) {
				// Can't actually error
            }
		}
		return msg;
	}

	private void sendMessage(byte[] msg) {
		if (isRunning()) {
			try {
				dos.write(msg);
			} catch (IOException e) {
				// Can't actually error
            }
		}
	}

	private synchronized ClientState getState() {
		return state;
	}

	private synchronized void setState(ClientState state) {
		this.state = state;
	}

	private byte[] cypher(String message) {
		byte[] plain = message.getBytes(ascii);
		byte[] encry = new byte[plain.length * 2];
		for (int i = 0; i < plain.length; i++) {
			encry[i * 2 + 0] = (byte) (plain[i] + 62);
			encry[i * 2 + 1] = 0x2E; // TODO actually pretend to encrypt
		}
		return encry;
	}

	@Override
	public void run() {
		while (isRunning()) {
			String msg = recvMessage();
			if (!msg.equals("")) {
				queue.add(msg);
				//System.out.println(msg);
			}
		}
	}


    // TODO where to handle message loop
    // for (String msg : client.getMessageQueue()) { ... }

    // TODO where to send messages
    // Core.client.sendMessage(msg + "\n");
}

