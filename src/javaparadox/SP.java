package javaparadox; // Stellaris 3.0.2 special_projects copy on_success to on_fail

import java.io.File;
import java.io.FileWriter; // overwrites o folder
import java.io.IOException;
import java.util.Scanner;

public class SP {

	public static void main(String[] args) throws IOException {
		for (final File f : new File(System.getProperty("user.dir") + "\\special_projects\\i\\").listFiles()) {
			output(f);
		}
	}

	public static void output(final File infile) throws IOException {
		FileWriter fw = new FileWriter(System.getProperty("user.dir") + "\\special_projects\\o\\" + infile.getName());
		Scanner scan = new Scanner(infile);
		int brackets = 0;
		String on_success = "";
		String on_fail = "";
		boolean waitOSequal = false;
		boolean waitOSleftBracket = false;
		boolean waitOFequal = false;
		boolean waitOFleftBracket = false;
		boolean isOSscope = false;
		String oscopy = "";
		boolean isOFscope = false;

		while (scan.hasNextLine()) {
			boolean isfakeCommentScope = false;
			String line = scan.nextLine();

			for (int i = 0; i < line.length(); i++) {
				boolean ose = false;
				boolean osb = false;
				boolean ofe = false;
				boolean ofb = false;

				if (!isfakeCommentScope) {
					switch (line.charAt(i)) {
					case '#':
						isfakeCommentScope = true;
						break;
					case '{':
						if (waitOSleftBracket) {
							isOSscope = true;
						}
						if (waitOFleftBracket) {
							isOFscope = true;
						}
						brackets++;
						break;
					case '}':
						brackets--;
						if (isOSscope && brackets == 1) {
							isOSscope = false;
						}
						if (isOFscope && brackets == 1) {
							isOFscope = false;
							fw.write("{\n\t\tfrom = " + oscopy + "}\n\t");
							oscopy = "";
						}
						if (brackets == 0 && !oscopy.equals("")) { // for SP without on_fail scopes
							fw.write("\n\ton_fail = {\n\t\tfrom = " + oscopy + "}\n\t}\n");
							oscopy = "";
						}
						break;
					case 'o':
						on_success = (brackets == 1) ? "o" : "";
						on_fail = (brackets == 1) ? "o" : "";
						break;
					case 'n':
						on_success = (on_success.equals("o")) ? "on" : "";
						on_fail = (on_fail.equals("o")) ? "on" : "";
						break;
					case '_':
						on_success = (on_success.equals("on")) ? "on_" : "";
						on_fail = (on_fail.equals("on")) ? "on_" : "";
						break;
					case 's':
						if (on_success.equals("on_")) {
							on_success = "on_s";
						} else if (on_success.equals("on_succe")) {
							on_success = "on_succes";
						} else if (on_success.equals("on_succes")) {
							on_success = "on_success";
							ose = true;
						} else {
							on_success = "";
						}
						on_fail = "";
						break;
					case 'u':
						on_success = (on_success.equals("on_s")) ? "on_su" : "";
						on_fail = "";
						break;
					case 'c':
						if (on_success.equals("on_su")) {
							on_success = "on_suc";
						} else if (on_success.equals("on_suc")) {
							on_success = "on_succ";
						} else {
							on_success = "";
						}
						on_fail = "";
						break;
					case 'e':
						on_success = (on_success.equals("on_succ")) ? "on_succe" : "";
						on_fail = "";
						break;
					case 'f':
						on_fail = (on_fail.equals("on_")) ? "on_f" : "";
						on_success = "";
						break;
					case 'a':
						on_fail = (on_fail.equals("on_f")) ? "on_fa" : "";
						on_success = "";
						break;
					case 'i':
						on_fail = (on_fail.equals("on_fa")) ? "on_fai" : "";
						on_success = "";
						break;
					case 'l':
						if (on_fail.equals("on_fai")) {
							on_fail = "on_fail";
							ofe = true;
						} else {
							on_fail = "";
						}
						on_success = "";
						break;
					case ' ':
					case '\t':
					case '\n':
						ose = waitOSequal;
						osb = waitOSleftBracket;
						ofe = waitOFequal;
						ofb = waitOFleftBracket;
						break;
					case '=':
						if (waitOSequal) {
							osb = true;
						}
						if (waitOFequal) {
							ofb = true;
						}
						break;
					}
				}
				waitOSequal = ose;
				waitOSleftBracket = osb;
				waitOFequal = ofe;
				waitOFleftBracket = ofb;

				if (isOSscope) {
					oscopy += line.charAt(i);
				}
				if (!isOFscope) {
					fw.write(line.charAt(i));
				}
			}
			if (!isOFscope) {
				fw.write('\n');
			}
			oscopy += isOSscope ? "\n\t" : "";
		}
		scan.close();
		fw.close();
	}

}
