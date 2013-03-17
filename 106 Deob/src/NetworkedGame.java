import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.math.BigInteger;

public class NetworkedGame extends GameShell {
	public void init_rsa(BigInteger exp, BigInteger mod) {
		exponent = exp;
		modulus = mod;
	}

	public int ranseed() {
		try {
			String param_ranseed = getParameter("ranseed");
			String sub_ranseed = param_ranseed.substring(0, 10);
			int ranseed = Integer.parseInt(sub_ranseed);
			if (ranseed == 0x3ade68b1) {
				byte uid[] = new byte[4];
				Data.read_fully("uid.dat", uid, 4);
				ranseed = Data.gu32(uid, 0);
			}

			return ranseed;
		} catch (Exception _ex) {
			return 0;
		}
	}

	public void login(String username, String password, boolean reconnecting) {
		if (pd > 0) {
			draw_message(message_table[6], message_table[7]);
			try {
				Thread.sleep(2000L);
			} catch (Exception _ex) {
			}
			draw_message(message_table[8], message_table[9]);
			return;
		}
		try {
			wc = username;
			username = Data.format_string(username, 20);
			xc = password;
			password = Data.format_string(password, 20);
			if (username.trim().length() == 0) {
				draw_message(message_table[0], message_table[1]);
				return;
			}

			if (reconnecting)
				h(message_table[2], message_table[3]);
			else
				draw_message(message_table[6], message_table[7]);
			if (applet_started())
				connection = Connection.open_connection(host, this, port);
			else
				connection = Connection.open_connection(host, null, port);
			connection.se = sc;
			int i1 = connection.g32();
			od = i1;
			System.out.println("Session id: " + i1);
			if (reconnecting)
				connection.begin_packet(19);
			else
				connection.begin_packet(0);
			connection.p16(rc);
			connection.p64(Data.encode_37(username));
			connection.pjag_rsa(password, i1, exponent, modulus);
			connection.p32(ranseed());
			connection.vb();
			connection.g16();
			int response = connection.read();
			System.out.println("Login response: " + response);
			if (response == 0) {
				ad = 0;
				i();
				return;
			}
			if (response == 1) {
				ad = 0;
				e();
				return;
			}
			if (reconnecting) {
				username = "";
				password = "";
				j();
				return;
			}
			if (response == 3) {
				draw_message(message_table[10], message_table[11]);
				return;
			}
			if (response == 4) {
				draw_message(message_table[4], message_table[5]);
				return;
			}
			if (response == 5) {
				draw_message(message_table[16], message_table[17]);
				return;
			}
			if (response == 6) {
				draw_message(message_table[18], message_table[19]);
				return;
			}
			if (response == 7) {
				draw_message(message_table[20], message_table[21]);
				return;
			}
			if (response == 11) {
				draw_message(message_table[22], message_table[23]);
				return;
			}
			if (response == 12) {
				draw_message(message_table[24], message_table[25]);
				return;
			}
			if (response == 13) {
				draw_message(message_table[14], message_table[15]);
				return;
			}
			if (response == 14) {
				draw_message(message_table[8], message_table[9]);
				pd = 1500;
				return;
			} else {
				draw_message(message_table[12], message_table[13]);
				return;
			}
		} catch (Exception exception) {
			System.out.println(String.valueOf(exception));
		}
		if (!applet_started())
			host = uc;
		if (ad > 0) {
			try {
				Thread.sleep(5000L);
			} catch (Exception _ex) {
			}
			ad--;
			login(username, password, reconnecting);
		}
		if (reconnecting) {
			username = "";
			password = "";
			j();
		} else {
			draw_message(message_table[12], message_table[13]);
		}
	}

	public void close_connection() {
		if (connection != null) {
			connection.begin_packet(1);
			connection.write_packet();
		}
		wc = "";
		xc = "";
		j();
	}

	public void reconnect() {
		System.out.println("Lost connection");
		ad = 10;
		login(wc, xc, true);
	}

	public void h(String s1, String s2) {
		Graphics g1 = getGraphics();
		Font font = new Font("Helvetica", 1, 15);
		int i1 = sj();
		int j1 = ck();
		g1.setColor(Color.black);
		g1.fillRect(i1 / 2 - 140, j1 / 2 - 25, 280, 50);
		g1.setColor(Color.white);
		g1.drawRect(i1 / 2 - 140, j1 / 2 - 25, 280, 50);
		mj(g1, s1, font, i1 / 2, j1 / 2 - 10);
		mj(g1, s2, font, i1 / 2, j1 / 2 + 10);
	}

	public void create_account(String username, String password) {
		if (pd > 0) {
			draw_message(message_table[6], message_table[7]);
			try {
				Thread.sleep(2000L);
			} catch (Exception _ex) {
			}
			draw_message(message_table[8], message_table[9]);
			return;
		}
		try {
			username = Data.format_string(username, 20);
			password = Data.format_string(password, 20);
			draw_message(message_table[6], message_table[7]);
			if (applet_started())
				connection = Connection.open_connection(host, this, port);
			else
				connection = Connection.open_connection(host, null, port);
			int ssid = connection.g32();
			od = ssid;
			System.out.println("Session id: " + ssid);
			connection.begin_packet(2);
			connection.p16(rc);
			connection.p64(Data.encode_37(username));
			connection.pjag_rsa(password, ssid, exponent, modulus);
			connection.p32(ranseed());
			connection.write_packet();
			connection.g16();
			int response = connection.read();
			connection.close();
			System.out.println("Newplayer response: " + response);
			if (response == 2) {
				x();
				return;
			}
			if (response == 3) {
				draw_message(message_table[14], message_table[15]);
				return;
			}
			if (response == 4) {
				draw_message(message_table[4], message_table[5]);
				return;
			}
			if (response == 5) {
				draw_message(message_table[16], message_table[17]);
				return;
			}
			if (response == 6) {
				draw_message(message_table[18], message_table[19]);
				return;
			}
			if (response == 7) {
				draw_message(message_table[20], message_table[21]);
				return;
			}
			if (response == 11) {
				draw_message(message_table[22], message_table[23]);
				return;
			}
			if (response == 12) {
				draw_message(message_table[24], message_table[25]);
				return;
			}
			if (response == 13) {
				draw_message(message_table[14], message_table[15]);
				return;
			}
			if (response == 14) {
				draw_message(message_table[8], message_table[9]);
				pd = 1500;
				return;
			} else {
				draw_message(message_table[12], message_table[13]);
				return;
			}
		} catch (Exception exception) {
			System.out.println(String.valueOf(exception));
		}

		draw_message(message_table[12], message_table[13]);
	}

	public void track() {
		bd = System.currentTimeMillis();
	}

	public void m() {
		long l = System.currentTimeMillis();
		if (l - bd > 5000L) {
			bd = l;
			connection.begin_packet(5);
			connection.write_packet();
		}
		if (!ab())
			return;
		if (((Buffer) (connection)).xd) {
			reconnect();
			return;
		}
		int i1 = connection.jb(zc);
		if (i1 > 0)
			g(zc[0] & 0xff, i1);
	}

	public void g(int i1, int j1) {
		if (i1 == 8) {
			String s1 = new String(zc, 1, j1 - 1);
			send_server_message(s1);
		}
		if (i1 == 9)
			close_connection();
		if (i1 == 10) {
			logout_fail();
			return;
		}
		if (i1 == 23) {
			flist_length = Data.unsign(zc[1]);
			for (int k1 = 0; k1 < flist_length; k1++) {
				flist_encoded[k1] = Data.gu64(zc, 2 + k1 * 9);
				ed[k1] = Data.unsign(zc[10 + k1 * 9]);
			}

			db();
			return;
		}
		if (i1 == 24) {
			long l1 = Data.gu64(zc, 1);
			int j2 = zc[9] & 0xff;
			for (int k2 = 0; k2 < flist_length; k2++)
				if (flist_encoded[k2] == l1) {
					if (ed[k2] == 0 && j2 != 0)
						send_server_message("@pri@" + Data.decode_37(l1) + " has logged in");
					if (ed[k2] != 0 && j2 == 0)
						send_server_message("@pri@" + Data.decode_37(l1) + " has logged out");
					ed[k2] = j2;
					j1 = 0;
					db();
					return;
				}

			flist_encoded[flist_length] = l1;
			ed[flist_length] = j2;
			flist_length++;
			send_server_message("@pri@" + Data.decode_37(l1) + " has been added to your friends list");
			db();
			return;
		}

		if (i1 == 26) {
			fd = Data.unsign(zc[1]);
			for (int i2 = 0; i2 < fd; i2++)
				gd[i2] = Data.gu64(zc, 2 + i2 * 8);

			return;
		}
		if (i1 == 27) {
			hd = zc[1];
			id = zc[2];
			jd = zc[3];
			kd = zc[4];
			ld = zc[5];
			return;
		}
		if (i1 == 28) {
			long l2 = Data.gu64(zc, 1);
			String s2 = Data.qn(zc, 9, j1 - 9, true);
			send_server_message("@pri@" + Data.decode_37(l2) + ": tells you " + s2);
			return;
		} else {
			q(i1, j1, zc);
			return;
		}
	}

	public void db() {
		boolean flag = true;
		while (flag) {
			flag = false;
			for (int i1 = 0; i1 < flist_length - 1; i1++)
				if (ed[i1] < ed[i1 + 1]) {
					int j1 = ed[i1];
					ed[i1] = ed[i1 + 1];
					ed[i1 + 1] = j1;
					long l1 = flist_encoded[i1];
					flist_encoded[i1] = flist_encoded[i1 + 1];
					flist_encoded[i1 + 1] = l1;
					flag = true;
				}
		}
	}

	public void bb(String s1, String s2) {
		s1 = Data.format_string(s1, 20);
		s2 = Data.format_string(s2, 20);
		connection.begin_packet(25);
		connection.pjag_rsa(s1 + s2, od, exponent, modulus);
		connection.write_packet();
	}

	public void fb(int i1, int j1, int k1, int l1, int i2) {
		connection.begin_packet(31);
		connection.p8(i1);
		connection.p8(j1);
		connection.p8(k1);
		connection.p8(l1);
		connection.p8(i2);
		connection.write_packet();
	}

	public void cb(String s1) {
		long l1 = Data.encode_37(s1);
		connection.begin_packet(29);
		connection.p64(l1);
		connection.write_packet();
		for (int i1 = 0; i1 < fd; i1++)
			if (gd[i1] == l1)
				return;

		if (fd >= 50) {
			return;
		} else {
			gd[fd++] = l1;
			return;
		}
	}

	public void r(long l1) {
		connection.begin_packet(30);
		connection.p64(l1);
		connection.write_packet();
		for (int i1 = 0; i1 < fd; i1++)
			if (gd[i1] == l1) {
				fd--;
				for (int j1 = i1; j1 < fd; j1++)
					gd[j1] = gd[j1 + 1];

				return;
			}
	}

	public void add_friend(String username) {
		connection.begin_packet(26);
		connection.p64(Data.encode_37(username));
		connection.write_packet();
	}

	public void remove_friend(long name_37) {
		connection.begin_packet(27);
		connection.p64(name_37);
		connection.write_packet();
		for (int i = 0; i < flist_length; i++) {
			if (flist_encoded[i] != name_37)
				continue;
			flist_length--;
			for (int j1 = i; j1 < flist_length; j1++) {
				flist_encoded[j1] = flist_encoded[j1 + 1];
				ed[j1] = ed[j1 + 1];
			}

			break;
		}

		send_server_message("@pri@" + Data.decode_37(name_37)
				+ " has been removed from your friends list");
	}

	public void send_private_message(long name_37, byte bytes[], int length) {
		connection.begin_packet(28);
		connection.p64(name_37);
		connection.garr(bytes, 0, length);
		connection.write_packet();
	}

	public void t(byte abyte0[], int i1) {
		connection.begin_packet(3);
		connection.garr(abyte0, 0, i1);
		connection.write_packet();
		track();
	}

	public void process_command(String command) {
		connection.begin_packet(7);
		connection.pjag(command);
		connection.write_packet();
		track();
	}

	public void draw_message(String s1, String s2) {
	}

	public void e() {
	}

	public void i() {
	}

	public void j() {
	}

	public void logout_fail() {
	}

	public void x() {
	}

	public void q(int i1, int j1, byte abyte0[]) {
	}

	public void send_server_message(String s1) {
	}

	public boolean ab() {
		return true;
	}

	public NetworkedGame() {
		host = "127.0.0.1";
		uc = "66.28.11.53";
		port = 43594;
		wc = "";
		xc = "";
		zc = new byte[5000];
		flist_encoded = new long[100];
		ed = new int[100];
		gd = new long[50];
	}

	public static String message_table[];
	public static int rc = 1;
	public static int sc;
	public String host;
	public String uc;
	public int port;
	String wc;
	String xc;
	public Connection connection;
	byte zc[];
	int ad;
	long bd;
	public int flist_length;
	public long flist_encoded[];
	public int ed[];
	public int fd;
	public long gd[];
	public int hd;
	public int id;
	public int jd;
	public int kd;
	public int ld;
	public BigInteger exponent;
	public BigInteger modulus;
	public int od;
	public int pd;

	static {
		message_table = new String[50];
		message_table[0] = "You must enter both a username";
		message_table[1] = "and a password - Please try again";
		message_table[2] = "Connection lost! Please wait...";
		message_table[3] = "Attempting to re-establish";
		message_table[4] = "That username is already in use.";
		message_table[5] = "Wait 60 seconds then retry";
		message_table[6] = "Please wait...";
		message_table[7] = "Connecting to server";
		message_table[8] = "Sorry! The server is currently full.";
		message_table[9] = "Please try again later";
		message_table[10] = "Invalid username or password.";
		message_table[11] = "Try again, or create a new account";
		message_table[12] = "Sorry! Unable to connect to server.";
		message_table[13] = "Check your internet settings";
		message_table[14] = "Username already taken.";
		message_table[15] = "Please choose another username";
		message_table[16] = "The client has been updated.";
		message_table[17] = "Please reload this page";
		message_table[18] = "You may only use 1 character at once.";
		message_table[19] = "Your ip-address is already in use";
		message_table[20] = "Login attempts exceeded!";
		message_table[21] = "Please try again in 5 minutes";
		message_table[22] = "Account has been temporarily disabled";
		message_table[23] = "for cheating or abuse";
		message_table[24] = "Account has been permanently disabled";
		message_table[25] = "for cheating or abuse";
	}
}