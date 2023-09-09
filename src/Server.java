import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this is a class that runs the serever
 * 
 * @author ashkan
 *
 */
public class Server {

	private static ArrayList<PrintWriter> writers = new ArrayList<>();// this is a array list of print writers
	private static ArrayList<BufferedReader> bfs = new ArrayList<>();// this is a array list of buffer readers
	private static ArrayList<String> words = new ArrayList<>();// this is array list of words in table

	public static void main(String[] args) throws IOException {

		System.out.println("server is runing . . . . .");
		ServerSocket serversocket = new ServerSocket(9000);// set our server socket

		try {
			while (true) {
				new execution(serversocket.accept());

				System.out.println("client conected :/ ");

			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			serversocket.close();
		}
	}

	/**
	 * 
	 * this is a inner class that extends Thread
	 *
	 */

	private static class execution extends Thread {
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;
		private BufferedReader inf;// this is a reader for reading thats 5 files
		private static int p1 = 0;// the point of player 1
		private static int p2 = 0;// the point of player 2
		// these are files that allow to us to use from their words
		


		public execution(Socket socket) {// first we set our socket
			this.socket = socket;
			start();
		}

		public void run() {
			try {

				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				writers.add(out);
				bfs.add(in);

				int j = 0;
				String a = "";

				while (true) {

					a = in.readLine();
					
					String dir= System.getProperty("user.dir");
					
					File name = new File(dir+"\\src/firstNames.txt");
					File lastName = new File(dir+"\\src/lastNames.txt");
					File country = new File(dir+"\\src/countries.txt");
					File animal = new File(dir+"\\src/animals.txt");
					File fruit = new File(dir+"\\src/fruits.txt");

					if (a.equals("letter")) {
						for (j = 0; j < bfs.size(); j++)// here we get the index of input socket that currently used
							if (bfs.get(j).equals(in))
								break;

						sendLooser("LETTER" + in.readLine(), j);// here we send the massage to clients exept index j
					}
					if (a.equals("name"))
						beIng(a, in, inf, name);// this is a function that indicate this word is in txt file or not

					if (a.equals("last name"))
						beIng(a, in, inf, lastName);

					if (a.equals("country"))
						beIng(a, in, inf, country);

					if (a.equals("animal"))
						beIng(a, in, inf, animal);

					if (a.equals("fruits")) {
						beIng(a, in, inf, fruit);

					}
					if (a.startsWith("time")) {
						String s = a.substring(4);
						sendLooser("time" + s, j);
					}

					if (a.equals("i am winner :D ")) {// this is a massage that first clients send to serever
						for (j = 0; j < bfs.size(); j++)
							if (bfs.get(j).equals(in))
								break;
						sendLooser("winner stopped", j);// we send this massage for secound client
					}
					if (a.equals("points")) {
						String s2 = String.valueOf(point(words, "secound Score"));
						sendLooser("Secound Score" + s2, j);// we send this massage for secound client

						String s1 = String.valueOf(point(words, "Winner Score"));
						sendWinner("Winner Score" + s1, j);// we send this massage for first client
					}
					if (a.equals("new round")) {
						words.clear();
						sendLooser("new round", j);// we send this massage for secound client
					}
					if (a.startsWith("the game is over")) {

						if (a.substring(23, 24).equals("1")) {
							p1 = Integer.parseInt(a.substring(24));
							sendLooser("the game is over", j);// we send this massage for secound client
						}
						if (a.substring(23, 24).equals("2"))
							p2 = Integer.parseInt(a.substring(24));
					}
					if (a.equals("conclusion")) {// this is last level of our program that brake from loop
						conclusion(p1, p2, j);
						bfs.clear();
						writers.clear();
						break;
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
				out.close();
			}

		}
	}

	/**
	 * this is a function that show us inter word is in txt file or not
	 * 
	 * @param a
	 * @param in
	 * @param inf
	 * @param file
	 * @throws Exception
	 */
	public static void beIng(String a, BufferedReader in, BufferedReader inf, File file) throws Exception {
		boolean f = false;
		inf = new BufferedReader(new FileReader(file));
		a = in.readLine();
		while (true) {
			String b = inf.readLine();
			if (a.equals(b)) {
				f = true;
				break;
			}
			if (b == null)
				break;
		}
		if (f == true) {
			// System.out.println("barikalaaaaaa");
			words.add(a);// if the word was being , will be added too array list
		} else {
			// System.out.println("peida nashod");
			words.add("null");// if the word wasn't being , the "null" will be added too array list
		}
		inf.close();

	}

	/**
	 * this function send the massage to secound client
	 * 
	 * @param s
	 * @param j
	 */
	public static void sendLooser(String s, int j) {

		for (int i = 0; i < writers.size(); i++)
			if (i != j) {
				writers.get(i).println(s);
			}
	}

	/**
	 * this function send the massage to first client
	 * 
	 * @param s
	 * @param j
	 */
	public static void sendWinner(String s, int j) {
		for (int i = 0; i < writers.size(); i++)
			if (i == j) {
				writers.get(i).println(s);
			}

	}

	/**
	 * this function send the massage to all clients
	 */
	public static void sendToAll(String s) {
		for (PrintWriter myWriter : writers)
			myWriter.println(s);
	}

	/**
	 * this function get the total points of each player and send the conclusion to
	 * each clients
	 * 
	 * @param p1
	 * @param p2
	 * @param j
	 */
	public static void conclusion(int p1, int p2, int j) {
		if (p1 > p2) {
			sendWinner("conclusion: " + "you win the game", j);
			sendLooser("conclusion: " + "you loose the game", j);
		}
		if (p1 == p2) {
		
			sendToAll("conclusion: " + "you were equal");

		}
		if (p1 < p2) {
			sendWinner("conclusion: " + "you loose the game", j);
			sendLooser("conclusion: " + "you win the game", j);
		}
	}

	/**
	 * this function calculate the points
	 * 
	 * @param words
	 * @param s
	 * @return
	 */
	public static int point(ArrayList<String> words, String s) {
		int a = 0;
		int b = 0;
		for (int i = 0; i < 5; i++) {

			if (words.get(i + 5).equals("null") && words.get(i).equals("null")) {// if both table columns were empty
				a += 0;
				b += 0;
			}
			if (words.get(i).equals(words.get(i + 5)) && !words.get(i + 5).equals("null")// if both table columns were
																							// equal
					&& !words.get(i).equals("null")) {
				a += 5;
				b += 5;
			}
			if (!words.get(i).equals(words.get(i + 5)) && !words.get(i + 5).equals("null")// if both table columns were
																							// fill and different
					&& !words.get(i).equals("null")) {
				a += 10;
				b += 10;
			}
			if (words.get(i).equals("null") && !words.get(i + 5).equals("null")) {// if one table column was empty and
																					// annother was fill
				a += 0;
				b += 20;
			}
			if (words.get(i + 5).equals("null") && !words.get(i).equals("null")) {// if one table column was empty and
																					// annother was fill
				a += 20;
				b += 0;
			}

		}
		if (s.equals("Winner Score"))
			return a;//player 1 point
		else
			return b;//player 2 point
	}

}
