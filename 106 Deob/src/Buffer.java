import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Buffer {
	public Buffer(InputStream inputstream) {
		ud = false;
		xd = false;
		yd = "";
		qd = inputstream;
	}

	public Buffer(Socket socket) throws IOException {
		ud = false;
		xd = false;
		yd = "";
		sd = socket;
		qd = socket.getInputStream();
		rd = socket.getOutputStream();
	}

	public Buffer(String s) throws IOException {
		ud = false;
		xd = false;
		yd = "";
		qd = Data.open_stream(s);
	}

	public Buffer(byte buf[]) {
		ud = false;
		xd = false;
		yd = "";
		vf = buf;
		wd = 0;
		ud = true;
	}

	public Buffer(byte buf[], int i) {
		ud = false;
		xd = false;
		yd = "";
		vf = buf;
		wd = i;
		ud = true;
	}

	public Buffer(int i) throws IOException {
		ud = false;
		xd = false;
		yd = "";
		td = new ServerSocket(i);
	}

	public Buffer(String s, int i) throws IOException {
		ud = false;
		xd = false;
		yd = "";
		sd = new Socket(InetAddress.getByName(s), i);
		sd.setSoTimeout(10000);
		qd = sd.getInputStream();
		rd = sd.getOutputStream();
	}

	public void close() {
		if (ud)
			return;
		try {
			if (td != null)
				td.close();
			if (sd != null)
				sd.close();
			if (qd != null)
				qd.close();
			if (rd != null)
				rd.close();
			buffer = null;
			return;
		} catch (IOException _ex) {
			System.out.println("Error closing stream");
		}
	}

	public int read() throws IOException {
		if (vf != null)
			return vf[wd++];
		if (ud)
			return 0;
		else
			return qd.read();
	}

	public int g4() throws IOException {
		return read();
	}

	public int g16() throws IOException {
		int i = g4();
		int j = g4();
		return i * 256 + j;
	}

	public int g32() throws IOException {
		int i = g16();
		int j = g16();
		return i * 0x10000 + j;
	}

	public int available() throws IOException {
		if (ud)
			return 0;
		else
			return qd.available();
	}

	public void yb(int i, byte abyte0[]) throws IOException {
		if (ud)
			return;
		int j = 0;
		int k;
		for (; j < i; j += k)
			if ((k = qd.read(abyte0, j, i - j)) <= 0)
				throw new IOException("EOF");
	}

	public String fc() throws IOException {
		String s = "";
		int i;
		for (i = read(); i == 10 || i == 13; i = read())
			;
		for (; i != 10 && i != 13 && i != -1; i = read())
			s = s + character_map[i];

		return s;
	}

	public void zb() throws IOException {
		for (int i = read(); i != 61 && i != -1; i = read())
			;
	}

	public int gc() throws IOException {
		int i = 0;
		boolean flag = false;
		int j;
		for (j = read(); j < 48 || j > 57;) {
			if (j == 45)
				flag = true;
			j = read();
			if (j == -1)
				throw new IOException("Eof!");
		}

		for (; j >= 48 && j <= 57; j = read())
			i = (i * 10 + j) - 48;

		if (flag)
			i = -i;
		return i;
	}

	public String ac() throws IOException {
		String s = "";
		boolean flag = false;
		int i;
		for (i = read(); i < 32 || i == 44 || i == 59 || i == 61;) {
			i = read();
			if (i == -1)
				throw new IOException("Eof!");
		}

		if (i == 34) {
			flag = true;
			i = read();
		}
		for (; i != -1; i = read()) {
			if (!flag && (i == 44 || i == 61 || i == 59) || flag && i == 34)
				break;
			s = s + character_map[i];
		}

		return s;
	}

	public int lb() throws IOException {
		int i = 0;
		int j;
		for (j = read(); (j < 48 || j > 57) && (j < 97 || j > 102) && (j < 65 || j > 70);) {
			j = read();
			if (j == -1)
				throw new IOException("Eof!");
		}

		do {
			if (j >= 48 && j <= 57)
				i = (i * 16 + j) - 48;
			else if (j >= 97 && j <= 102) {
				i = (i * 16 + j + 10) - 97;
			} else {
				if (j < 65 || j > 70)
					break;
				i = (i * 16 + j + 10) - 65;
			}
			j = read();
		} while (true);
		return i;
	}

	public void xb(byte abyte0[], int i, int j, boolean flag) throws IOException {
	}

	public void begin_packet(int i) {
		if (buffer == null)
			buffer = new byte[40000];
		buffer[2] = (byte) i;
		position = 3;
		ne = 8;
		buffer[3] = 0;
	}

	public void p8(int i) {
		buffer[position++] = (byte) i;
	}

	public void p16(int i) {
		buffer[position++] = (byte) (i >> 8);
		buffer[position++] = (byte) i;
	}

	public void p32(int i) {
		buffer[position++] = (byte) (i >> 24);
		buffer[position++] = (byte) (i >> 16);
		buffer[position++] = (byte) (i >> 8);
		buffer[position++] = (byte) i;
	}

	public void p64(long l) {
		p32((int) (l >> 32));
		p32((int) (l & -1L));
	}

	public void pjag(String s) {
		s.getBytes(0, s.length(), buffer, position);
		position += s.length();
	}

	public void rb(int i, int j) {
		buffer[j++] = (byte) (i >> 8);
		buffer[j++] = (byte) i;
	}

	public void garr(byte src[], int offset, int length) {
		for (int i = 0; i < length; i++)
			buffer[position++] = src[offset + i];
	}

	public void p64_rsa(long l, int i, BigInteger exponent, BigInteger modulus) {
		byte abyte0[] = new byte[15];
		abyte0[0] = (byte) (int) (1.0D + Math.random() * 127D);
		abyte0[1] = (byte) (int) (Math.random() * 256D);
		abyte0[2] = (byte) (int) (Math.random() * 256D);
		Data.p32(abyte0, 3, i);
		Data.p64(abyte0, 7, l);
		BigInteger biginteger2 = new BigInteger(1, abyte0);
		BigInteger biginteger3 = biginteger2.modPow(exponent, modulus);
		byte abyte1[] = biginteger3.toByteArray();
		buffer[position++] = (byte) abyte1.length;
		for (int j = 0; j < abyte1.length; j++)
			buffer[position++] = abyte1[j];
	}

	public void pjag_rsa(String s, int i, BigInteger exponent, BigInteger modulus) {
		byte abyte0[] = s.getBytes();
		int j = abyte0.length;
		byte abyte1[] = new byte[15];
		for (int k = 0; k < j; k += 7) {
			abyte1[0] = (byte) (int) (1.0D + Math.random() * 127D);
			abyte1[1] = (byte) (int) (Math.random() * 256D);
			abyte1[2] = (byte) (int) (Math.random() * 256D);
			abyte1[3] = (byte) (int) (Math.random() * 256D);
			Data.p32(abyte1, 4, i);
			for (int l = 0; l < 7; l++)
				if (k + l < j)
					abyte1[8 + l] = abyte0[k + l];
				else
					abyte1[8 + l] = 32;

			BigInteger biginteger2 = new BigInteger(1, abyte1);
			BigInteger biginteger3 = biginteger2.modPow(exponent, modulus);
			byte abyte2[] = biginteger3.toByteArray();
			buffer[position++] = (byte) abyte2.length;
			for (int i1 = 0; i1 < abyte2.length; i1++)
				buffer[position++] = abyte2[i1];
		}
	}

	public void write_packet() {
		if (ne != 8)
			position++;
		buffer[0] = (byte) ((position - 2) / 256);
		buffer[1] = (byte) (position - 2 & 0xff);
		try {
			xb(buffer, 0, position, true);
			return;
		} catch (IOException ioexception) {
			xd = true;
			yd = ioexception.getMessage();
			return;
		}
	}

	public void hc() {
		if (ne != 8)
			position++;
		buffer[0] = (byte) ((position - 2) / 256);
		buffer[1] = (byte) (position - 2 & 0xff);
		try {
			xb(buffer, 0, position, false);
			return;
		} catch (IOException ioexception) {
			xd = true;
			yd = ioexception.getMessage();
			return;
		}
	}

	public void vb() throws IOException {
		if (ne != 8)
			position++;
		buffer[0] = (byte) ((position - 2) / 256);
		buffer[1] = (byte) (position - 2 & 0xff);
		xb(buffer, 0, position, true);
	}

	public int jb(byte abyte0[]) {
		try {
			re++;
			if (se > 0 && re > se) {
				xd = true;
				yd = "time-out";
				return 0;
			}
			if (qe == 0 && available() >= 2) {
				qe = g16();
				re = 0;
			}
			if (qe > 0 && available() >= qe) {
				yb(qe, abyte0);
				re = 0;
				int i = qe;
				qe = 0;
				return i;
			}
		} catch (IOException ioexception) {
			xd = true;
			yd = ioexception.getMessage();
		}
		return 0;
	}

	protected InputStream qd;
	protected OutputStream rd;
	protected Socket sd;
	protected ServerSocket td;
	protected boolean ud;
	protected byte vf[];
	int wd;
	public boolean xd;
	public String yd;
	final int zd = 61;
	final int ae = 59;
	final int be = 42;
	final int ce = 43;
	final int de = 44;
	final int ee = 45;
	final int fe = 46;
	final int ge = 47;
	final int he = 92;
	final int ie = 32;
	final int je = 124;
	final int ke = 34;
	static char character_map[];
	private static int position = 3;
	private static int ne = 8;
	private static byte buffer[];
	private static int bit_mask[] = { 0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191,
			16383, 32767, 65535, 0x1ffff, 0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff,
			0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff, -1 };
	private int qe;
	public int re;
	public int se;

	static {
		character_map = new char[256];
		for (int i = 0; i < 256; i++)
			character_map[i] = (char) i;

		character_map[61] = '=';
		character_map[59] = ';';
		character_map[42] = '*';
		character_map[43] = '+';
		character_map[44] = ',';
		character_map[45] = '-';
		character_map[46] = '.';
		character_map[47] = '/';
		character_map[92] = '\\';
		character_map[124] = '|';
		character_map[33] = '!';
		character_map[34] = '"';
	}
}