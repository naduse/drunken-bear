import java.applet.Applet;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Connection extends Buffer implements Runnable {
	public Connection(InputStream inputstream) {
		super(inputstream);
		closed = true;
	}

	public Connection(Socket socket) throws IOException {
		super(socket);
		closed = true;
	}

	public Connection(String s) throws IOException {
		super(s);
		closed = true;
	}

	public Connection(byte buffer[]) {
		super(buffer);
		closed = true;
	}

	public static Connection open_connection(String host, Applet applet, int port) throws IOException {
		Socket socket;
		if (applet != null)
			socket = new Socket(InetAddress.getByName(applet.getCodeBase().getHost()), port);
		else
			socket = new Socket(InetAddress.getByName(host), port);
		socket.setSoTimeout(30000);
		return new Connection(socket);
	}

	public void close() {
		super.close();
		if (twriter != null) {
			closed = true;
			synchronized (this) {
				notify();
			}

			twriter = null;
		}

		a = null;
	}

	public void xb(byte abyte0[], int i, int j, boolean flag) throws IOException {
		if (super.ud)
			return;
		if (a == null)
			a = new byte[5000];
		synchronized (this) {
			for (int k = 0; k < j; k++) {
				a[c] = abyte0[k + i];
				c = (c + 1) % 5000;
				if (c == (b + 4900) % 5000)
					throw new IOException("buffer overflow");
			}

			if (flag) {
				if (twriter == null) {
					closed = false;
					twriter = new Thread(this);
					twriter.setDaemon(true);
					twriter.setPriority(4);
					twriter.start();
				}

				notify();
			}
		}
	}

	public void a() {
		synchronized (this) {
			if (c == b || a == null)
				return;
			if (twriter == null) {
				closed = false;
				twriter = new Thread(this);
				twriter.setDaemon(true);
				twriter.setPriority(4);
				twriter.start();
			}
			notify();
		}
	}

	public void run() {
		while (twriter != null && !closed) {
			int i;
			int j;
			synchronized (this) {
				if (c == b)
					try {
						wait();
					} catch (InterruptedException _ex) {
					}
				if (twriter == null || closed)
					return;
				j = b;
				if (c >= b)
					i = c - b;
				else
					i = 5000 - b;
			}
			if (i > 0) {
				try {
					super.rd.write(a, j, i);
				} catch (IOException ioexception) {
					super.xd = true;
					super.yd = "Twriter:" + ioexception;
				}

				b = (b + i) % 5000;

				try {
					if (c == b)
						super.rd.flush();
				} catch (IOException ioexception1) {
					super.xd = true;
					super.yd = "Twriter:" + ioexception1;
				}
			}
		}
	}

	private byte a[];
	private int b;
	private int c;
	private Thread twriter;
	private boolean closed;
}