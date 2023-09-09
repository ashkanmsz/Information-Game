
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 * this class is for information of players
 * @author ashka
 *
 */
public class Informations {

	private static String name;
	private static String matches;
	private static String wins;
	private static String looses;
	private static String password;
	private static boolean f = false;
	
	public static ArrayList<String> list = new ArrayList<>();//this is a array list for keeping the host players name 

	

	public Informations() {

	}

	public Informations(String name, String password, String matches, String wins, String looses, boolean f) {
		Informations.name = name;
		Informations.password = password;
		Informations.matches = matches;
		Informations.wins = wins;
		Informations.looses = looses;
		Informations.f = f;
		/**
		*in this section we write our player's information in txt file 
		*/
		if (f == true) {
			try {
				FileWriter fw = new FileWriter("Profile.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
				out.println(name);
				out.println(name+"password : "+password);
				out.println(name+"matches : "+matches);
				out.println(name+"wins : "+wins);
				out.println(name+"looses : "+looses);
				out.println("*************");
				out.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}

	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		Informations.name = name;
	}

	public String getMatches() {
		return matches;
	}

	public String getWins() {
		return wins;
	}

	public String getLooses() {
		return looses;
	}

	public String getPassword() {
		return password;
	}

}
