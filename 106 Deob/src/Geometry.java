import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Geometry {
	public Geometry(int i, int k) {
		ih = 1;
		jh = true;
		qh = true;
		rh = false;
		sh = false;
		th = -1;
		wh = false;
		xh = false;
		yh = false;
		zh = false;
		ai = false;
		fi = 4;
		gouraud = 0xbc614e;
		mj = 0xbc614e;
		nj = 180;
		oj = 155;
		pj = 95;
		qj = 256;
		rj = 512;
		sj = 32;
		de(i, k);
		pi = new int[k][1];
		for (int l = 0; l < k; l++)
			pi[l][0] = l;

	}

	public Geometry(int i, int k, boolean flag, boolean flag1, boolean flag2, boolean flag3,
			boolean flag4) {
		ih = 1;
		jh = true;
		qh = true;
		rh = false;
		sh = false;
		th = -1;
		wh = false;
		xh = false;
		yh = false;
		zh = false;
		ai = false;
		fi = 4;
		gouraud = 0xbc614e;
		mj = 0xbc614e;
		nj = 180;
		oj = 155;
		pj = 95;
		qj = 256;
		rj = 512;
		sj = 32;
		wh = flag;
		xh = flag1;
		yh = flag2;
		zh = flag3;
		ai = flag4;
		de(i, k);
	}

	public Geometry(int i, int k, boolean flag) {
		ih = 1;
		jh = true;
		qh = true;
		rh = false;
		sh = false;
		th = -1;
		wh = false;
		xh = false;
		yh = false;
		zh = false;
		ai = false;
		fi = 4;
		gouraud = 0xbc614e;
		mj = 0xbc614e;
		nj = 180;
		oj = 155;
		pj = 95;
		qj = 256;
		rj = 512;
		sj = 32;
		de(i, k);
		pi = new int[k][];
	}

	private void de(int i, int k) {
		vertices_x = new int[i];
		vertices_y = new int[i];
		vertices_z = new int[i];
		tg = new int[i];
		ug = new byte[i];
		face_vertex_count = new int[k];
		face_vertices = new int[k][];
		face_back = new int[k];
		face_front = new int[k];
		face_intensity = new int[k];
		bh = new int[k];
		ah = new int[k];
		if (!ai) {
			og = new int[i];
			pg = new int[i];
			qg = new int[i];
			rg = new int[i];
			sg = new int[i];
		}
		if (!zh) {
			vh = new byte[k];
			uh = new int[k];
		}
		if (wh) {
			li = vertices_x;
			mi = vertices_y;
			ni = vertices_z;
		} else {
			li = new int[i];
			mi = new int[i];
			ni = new int[i];
		}
		if (!yh || !xh) {
			dh = new int[k];
			eh = new int[k];
			fh = new int[k];
		}
		if (!xh) {
			qi = new int[k];
			ri = new int[k];
			si = new int[k];
			ti = new int[k];
			ui = new int[k];
			vi = new int[k];
		}
		model_face_count = 0;
		model_vertex_count = 0;
		hi = i;
		oi = k;
		wi = xi = yi = 0;
		zi = aj = bj = 0;
		cj = dj = ej = 256;
		fj = gj = hj = ij = jj = kj = 256;
		lj = 0;
	}

	public void af() {
		og = new int[model_vertex_count];
		pg = new int[model_vertex_count];
		qg = new int[model_vertex_count];
		rg = new int[model_vertex_count];
		sg = new int[model_vertex_count];
	}

	public void ef() {
		model_face_count = 0;
		model_vertex_count = 0;
	}

	public void se(int i, int k) {
		model_face_count -= i;
		if (model_face_count < 0)
			model_face_count = 0;
		model_vertex_count -= k;
		if (model_vertex_count < 0)
			model_vertex_count = 0;
	}

	public Geometry(byte data[], int position, boolean flag) {
		ih = 1;
		jh = true;
		qh = true;
		rh = false;
		sh = false;
		th = -1;
		wh = false;
		xh = false;
		yh = false;
		zh = false;
		ai = false;
		fi = 4;
		gouraud = 0xbc614e;
		mj = 0xbc614e;
		nj = 180;
		oj = 155;
		pj = 95;
		qj = 256;
		rj = 512;
		sj = 32;
		int vertex_count = Data.gu16(data, position);
		position += 2;
		int face_count = Data.gu16(data, position);
		position += 2;
		de(vertex_count, face_count);
		pi = new int[face_count][1];

		for (int i = 0; i < vertex_count; i++) {
			vertices_x[i] = Data.g16(data, position);
			position += 2;
		}

		for (int i = 0; i < vertex_count; i++) {
			vertices_y[i] = Data.g16(data, position);
			position += 2;
		}

		for (int i = 0; i < vertex_count; i++) {
			vertices_z[i] = Data.g16(data, position);
			position += 2;
		}

		model_vertex_count = vertex_count;
		for (int i = 0; i < face_count; i++)
			face_vertex_count[i] = data[position++] & 0xff;

		for (int i = 0; i < face_count; i++) {
			face_back[i] = Data.g16(data, position);
			position += 2;
			if (face_back[i] == 32767)
				face_back[i] = gouraud;
		}

		for (int i = 0; i < face_count; i++) {
			face_front[i] = Data.g16(data, position);
			position += 2;
			if (face_front[i] == 32767)
				face_front[i] = gouraud;
		}

		for (int i = 0; i < face_count; i++) {
			int b = data[position++] & 0xff;
			if (b == 0)
				face_intensity[i] = 0;
			else
				face_intensity[i] = gouraud;
		}

		for (int i = 0; i < face_count; i++) {
			face_vertices[i] = new int[face_vertex_count[i]];
			for (int j3 = 0; j3 < face_vertex_count[i]; j3++)
				if (vertex_count < 256) {
					face_vertices[i][j3] = data[position++] & 0xff;
				} else {
					face_vertices[i][j3] = Data.gu16(data, position);
					position += 2;
				}
		}

		model_face_count = face_count;
		ih = 1;
	}

	public Geometry(byte abyte0[], int i) {
		ih = 1;
		jh = true;
		qh = true;
		rh = false;
		sh = false;
		th = -1;
		wh = false;
		xh = false;
		yh = false;
		zh = false;
		ai = false;
		fi = 4;
		gouraud = 0xbc614e;
		mj = 0xbc614e;
		nj = 180;
		oj = 155;
		pj = 95;
		qj = 256;
		rj = 512;
		sj = 32;
		tj = abyte0;
		uj = i;
		vj = 0;
		ye(tj);
		int k = ye(tj);
		int l = ye(tj);
		de(k, l);
		pi = new int[l][];
		for (int i3 = 0; i3 < k; i3++) {
			int i1 = ye(tj);
			int j1 = ye(tj);
			int k1 = ye(tj);
			ve(i1, j1, k1);
		}

		for (int j3 = 0; j3 < l; j3++) {
			int l1 = ye(tj);
			int i2 = ye(tj);
			int j2 = ye(tj);
			int k2 = ye(tj);
			rj = ye(tj);
			sj = ye(tj);
			int l2 = ye(tj);
			int ai1[] = new int[l1];
			for (int k3 = 0; k3 < l1; k3++)
				ai1[k3] = ye(tj);

			int ai2[] = new int[k2];
			for (int l3 = 0; l3 < k2; l3++)
				ai2[l3] = ye(tj);

			int i4 = ue(l1, ai1, i2, j2);
			pi[j3] = ai2;
			if (l2 == 0)
				face_intensity[i4] = 0;
			else
				face_intensity[i4] = gouraud;
		}

		ih = 1;
	}

	public Geometry(String s) {
		ih = 1;
		jh = true;
		qh = true;
		rh = false;
		sh = false;
		th = -1;
		wh = false;
		xh = false;
		yh = false;
		zh = false;
		ai = false;
		fi = 4;
		gouraud = 0xbc614e;
		mj = 0xbc614e;
		nj = 180;
		oj = 155;
		pj = 95;
		qj = 256;
		rj = 512;
		sj = 32;
		boolean flag = false;
		boolean flag1 = false;
		byte abyte0[] = null;
		try {
			InputStream inputstream = Data.open_stream(s);
			DataInputStream datainputstream = new DataInputStream(inputstream);
			abyte0 = new byte[3];
			uj = 0;
			vj = 0;
			for (int i = 0; i < 3; i += datainputstream.read(abyte0, i, 3 - i))
				;
			int l = ye(abyte0);
			abyte0 = new byte[l];
			uj = 0;
			vj = 0;
			for (int k = 0; k < l; k += datainputstream.read(abyte0, k, l - k))
				;
			datainputstream.close();
		} catch (IOException _ex) {
			model_vertex_count = 0;
			model_face_count = 0;
			return;
		}
		int i1 = ye(abyte0);
		int j1 = ye(abyte0);
		boolean flag2 = false;
		de(i1, j1);
		pi = new int[j1][];
		for (int k3 = 0; k3 < i1; k3++) {
			int k1 = ye(abyte0);
			int l1 = ye(abyte0);
			int i2 = ye(abyte0);
			ve(k1, l1, i2);
		}

		for (int l3 = 0; l3 < j1; l3++) {
			int j2 = ye(abyte0);
			int k2 = ye(abyte0);
			int l2 = ye(abyte0);
			int i3 = ye(abyte0);
			rj = ye(abyte0);
			sj = ye(abyte0);
			int j3 = ye(abyte0);
			int ai1[] = new int[j2];
			for (int i4 = 0; i4 < j2; i4++)
				ai1[i4] = ye(abyte0);

			int ai2[] = new int[i3];
			for (int j4 = 0; j4 < i3; j4++)
				ai2[j4] = ye(abyte0);

			int k4 = ue(j2, ai1, k2, l2);
			pi[l3] = ai2;
			if (j3 == 0)
				face_intensity[k4] = 0;
			else
				face_intensity[k4] = gouraud;
		}

		ih = 1;
	}

	public Geometry(Geometry ah1[], int i, boolean flag, boolean flag1, boolean flag2, boolean flag3) {
		ih = 1;
		jh = true;
		qh = true;
		rh = false;
		sh = false;
		th = -1;
		wh = false;
		xh = false;
		yh = false;
		zh = false;
		ai = false;
		fi = 4;
		gouraud = 0xbc614e;
		mj = 0xbc614e;
		nj = 180;
		oj = 155;
		pj = 95;
		qj = 256;
		rj = 512;
		sj = 32;
		wh = flag;
		xh = flag1;
		yh = flag2;
		zh = flag3;
		me(ah1, i, false);
	}

	public Geometry(Geometry ah1[], int i) {
		ih = 1;
		jh = true;
		qh = true;
		rh = false;
		sh = false;
		th = -1;
		wh = false;
		xh = false;
		yh = false;
		zh = false;
		ai = false;
		fi = 4;
		gouraud = 0xbc614e;
		mj = 0xbc614e;
		nj = 180;
		oj = 155;
		pj = 95;
		qj = 256;
		rj = 512;
		sj = 32;
		me(ah1, i, true);
	}

	public void me(Geometry ah1[], int i, boolean flag) {
		int k = 0;
		int l = 0;
		for (int i1 = 0; i1 < i; i1++) {
			k += ah1[i1].model_face_count;
			l += ah1[i1].model_vertex_count;
		}

		de(l, k);
		if (flag)
			pi = new int[k][];
		for (int j1 = 0; j1 < i; j1++) {
			Geometry h1 = ah1[j1];
			h1.pe();
			sj = h1.sj;
			rj = h1.rj;
			nj = h1.nj;
			oj = h1.oj;
			pj = h1.pj;
			qj = h1.qj;
			for (int k1 = 0; k1 < h1.model_face_count; k1++) {
				int ai1[] = new int[h1.face_vertex_count[k1]];
				int ai2[] = h1.face_vertices[k1];
				for (int l1 = 0; l1 < h1.face_vertex_count[k1]; l1++)
					ai1[l1] = ve(h1.vertices_x[ai2[l1]], h1.vertices_y[ai2[l1]], h1.vertices_z[ai2[l1]]);

				int i2 = ue(h1.face_vertex_count[k1], ai1, h1.face_back[k1], h1.face_front[k1]);
				face_intensity[i2] = h1.face_intensity[k1];
				bh[i2] = h1.bh[k1];
				ah[i2] = h1.ah[k1];
				if (flag)
					if (i > 1) {
						pi[i2] = new int[h1.pi[k1].length + 1];
						pi[i2][0] = j1;
						for (int j2 = 0; j2 < h1.pi[k1].length; j2++)
							pi[i2][j2 + 1] = h1.pi[k1][j2];

					} else {
						pi[i2] = new int[h1.pi[k1].length];
						for (int k2 = 0; k2 < h1.pi[k1].length; k2++)
							pi[i2][k2] = h1.pi[k1][k2];
					}
			}
		}

		ih = 1;
	}

	public Geometry(int i, int ai1[], int ai2[], int ai3[], int k, int l) {
		this(i, 1);
		model_vertex_count = i;
		for (int i1 = 0; i1 < i; i1++) {
			vertices_x[i1] = ai1[i1];
			vertices_y[i1] = ai2[i1];
			vertices_z[i1] = ai3[i1];
		}

		model_face_count = 1;
		face_vertex_count[0] = i;
		int ai4[] = new int[i];
		for (int j1 = 0; j1 < i; j1++)
			ai4[j1] = j1;

		face_vertices[0] = ai4;
		face_back[0] = k;
		face_front[0] = l;
		ih = 1;
	}

	public int ve(int i, int k, int l) {
		for (int i1 = 0; i1 < model_vertex_count; i1++)
			if (vertices_x[i1] == i && vertices_y[i1] == k && vertices_z[i1] == l)
				return i1;

		if (model_vertex_count >= hi) {
			return -1;
		} else {
			vertices_x[model_vertex_count] = i;
			vertices_y[model_vertex_count] = k;
			vertices_z[model_vertex_count] = l;
			return model_vertex_count++;
		}
	}

	public int set_vertices(int x, int y, int z) {
		if (model_vertex_count >= hi) {
			return -1;
		} else {
			vertices_x[model_vertex_count] = x;
			vertices_y[model_vertex_count] = y;
			vertices_z[model_vertex_count] = z;
			return model_vertex_count++;
		}
	}

	public int ue(int i, int ai1[], int k, int l) {
		if (model_face_count >= oi) {
			return -1;
		} else {
			face_vertex_count[model_face_count] = i;
			face_vertices[model_face_count] = ai1;
			face_back[model_face_count] = k;
			face_front[model_face_count] = l;
			ih = 1;
			return model_face_count++;
		}
	}

	public Geometry[] be(int i, int k, int l, int i1, int j1, int k1, int l1, boolean flag) {
		pe();
		int ai1[] = new int[k1];
		int ai2[] = new int[k1];
		for (int i2 = 0; i2 < k1; i2++) {
			ai1[i2] = 0;
			ai2[i2] = 0;
		}

		for (int j2 = 0; j2 < model_face_count; j2++) {
			int k2 = 0;
			int l2 = 0;
			int j3 = face_vertex_count[j2];
			int ai3[] = face_vertices[j2];
			for (int j4 = 0; j4 < j3; j4++) {
				k2 += vertices_x[ai3[j4]];
				l2 += vertices_z[ai3[j4]];
			}

			int l4 = k2 / (j3 * l) + (l2 / (j3 * i1)) * j1;
			ai1[l4] += j3;
			ai2[l4]++;
		}

		Geometry ah1[] = new Geometry[k1];
		for (int i3 = 0; i3 < k1; i3++) {
			if (ai1[i3] > l1)
				ai1[i3] = l1;
			ah1[i3] = new Geometry(ai1[i3], ai2[i3], true, true, true, flag, true);
			ah1[i3].rj = rj;
			ah1[i3].sj = sj;
		}

		for (int k3 = 0; k3 < model_face_count; k3++) {
			int l3 = 0;
			int k4 = 0;
			int i5 = face_vertex_count[k3];
			int ai4[] = face_vertices[k3];
			for (int j5 = 0; j5 < i5; j5++) {
				l3 += vertices_x[ai4[j5]];
				k4 += vertices_z[ai4[j5]];
			}

			int k5 = l3 / (i5 * l) + (k4 / (i5 * i1)) * j1;
			gf(ah1[k5], ai4, i5, k3);
		}

		for (int i4 = 0; i4 < k1; i4++)
			ah1[i4].af();

		return ah1;
	}

	public void gf(Geometry h1, int ai1[], int i, int k) {
		int ai2[] = new int[i];
		for (int l = 0; l < i; l++) {
			int i1 = ai2[l] = h1.ve(vertices_x[ai1[l]], vertices_y[ai1[l]], vertices_z[ai1[l]]);
			h1.tg[i1] = tg[ai1[l]];
			h1.ug[i1] = ug[ai1[l]];
		}

		int j1 = h1.ue(i, ai2, face_back[k], face_front[k]);
		if (!h1.zh && !zh)
			h1.uh[j1] = uh[k];
		h1.face_intensity[j1] = face_intensity[k];
		h1.bh[j1] = bh[k];
		h1.ah[j1] = ah[k];
	}

	public void ze(boolean flag, int i, int k, int l, int i1, int j1) {
		sj = 256 - i * 4;
		rj = (64 - k) * 16 + 128;
		if (yh)
			return;
		for (int k1 = 0; k1 < model_face_count; k1++)
			if (flag)
				face_intensity[k1] = gouraud;
			else
				face_intensity[k1] = 0;

		nj = l;
		oj = i1;
		pj = j1;
		qj = (int) Math.sqrt(l * l + i1 * i1 + j1 * j1);
		te();
	}

	public void ie(int i, int k, int l, int i1, int j1) {
		sj = 256 - i * 4;
		rj = (64 - k) * 16 + 128;
		if (yh) {
			return;
		} else {
			nj = l;
			oj = i1;
			pj = j1;
			qj = (int) Math.sqrt(l * l + i1 * i1 + j1 * j1);
			te();
			return;
		}
	}

	public void ff(int i, int k, int l) {
		if (yh) {
			return;
		} else {
			nj = i;
			oj = k;
			pj = l;
			qj = (int) Math.sqrt(i * i + k * k + l * l);
			te();
			return;
		}
	}

	public void ee(int i, int k) {
		ug[i] = (byte) k;
	}

	public void cf(int i, int k, int l) {
		zi = zi + i & 0xff;
		aj = aj + k & 0xff;
		bj = bj + l & 0xff;
		df();
		ih = 1;
	}

	public void re(int i, int k, int l) {
		zi = i & 0xff;
		aj = k & 0xff;
		bj = l & 0xff;
		df();
		ih = 1;
	}

	public void ge(int i, int k, int l) {
		wi += i;
		xi += k;
		yi += l;
		df();
		ih = 1;
	}

	public void ne(int i, int k, int l) {
		wi = i;
		xi = k;
		yi = l;
		df();
		ih = 1;
	}

	public int hf() {
		return wi;
	}

	public void ce(int i, int k, int l) {
		cj = i;
		dj = k;
		ej = l;
		df();
		ih = 1;
	}

	public void he(int i, int k, int l, int i1, int j1, int k1) {
		fj = i;
		gj = k;
		hj = l;
		ij = i1;
		jj = j1;
		kj = k1;
		df();
		ih = 1;
	}

	private void df() {
		if (fj != 256 || gj != 256 || hj != 256 || ij != 256 || jj != 256 || kj != 256) {
			lj = 4;
			return;
		}
		if (cj != 256 || dj != 256 || ej != 256) {
			lj = 3;
			return;
		}
		if (zi != 0 || aj != 0 || bj != 0) {
			lj = 2;
			return;
		}
		if (wi != 0 || xi != 0 || yi != 0) {
			lj = 1;
			return;
		} else {
			lj = 0;
			return;
		}
	}

	private void _mthif(int i, int k, int l) {
		for (int i1 = 0; i1 < model_vertex_count; i1++) {
			li[i1] += i;
			mi[i1] += k;
			ni[i1] += l;
		}

	}

	private void le(int i, int k, int l) {
		for (int j3 = 0; j3 < model_vertex_count; j3++) {
			if (l != 0) {
				int i1 = bi[l];
				int l1 = bi[l + 256];
				int k2 = mi[j3] * i1 + li[j3] * l1 >> 15;
				mi[j3] = mi[j3] * l1 - li[j3] * i1 >> 15;
				li[j3] = k2;
			}
			if (i != 0) {
				int j1 = bi[i];
				int i2 = bi[i + 256];
				int l2 = mi[j3] * i2 - ni[j3] * j1 >> 15;
				ni[j3] = mi[j3] * j1 + ni[j3] * i2 >> 15;
				mi[j3] = l2;
			}
			if (k != 0) {
				int k1 = bi[k];
				int j2 = bi[k + 256];
				int i3 = ni[j3] * k1 + li[j3] * j2 >> 15;
				ni[j3] = ni[j3] * j2 - li[j3] * k1 >> 15;
				li[j3] = i3;
			}
		}

	}

	private void je(int i, int k, int l, int i1, int j1, int k1) {
		for (int l1 = 0; l1 < model_vertex_count; l1++) {
			if (i != 0)
				li[l1] += mi[l1] * i >> 8;
			if (k != 0)
				ni[l1] += mi[l1] * k >> 8;
			if (l != 0)
				li[l1] += ni[l1] * l >> 8;
			if (i1 != 0)
				mi[l1] += ni[l1] * i1 >> 8;
			if (j1 != 0)
				ni[l1] += li[l1] * j1 >> 8;
			if (k1 != 0)
				mi[l1] += li[l1] * k1 >> 8;
		}

	}

	private void qe(int i, int k, int l) {
		for (int i1 = 0; i1 < model_vertex_count; i1++) {
			li[i1] = li[i1] * i >> 8;
			mi[i1] = mi[i1] * k >> 8;
			ni[i1] = ni[i1] * l >> 8;
		}

	}

	private void ae() {
		kh = mh = oh = 0xf423f;
		mj = lh = nh = ph = 0xfff0bdc1;
		for (int i = 0; i < model_face_count; i++) {
			int ai1[] = face_vertices[i];
			int l = ai1[0];
			int j1 = face_vertex_count[i];
			int k1;
			int l1 = k1 = li[l];
			int i2;
			int j2 = i2 = mi[l];
			int k2;
			int l2 = k2 = ni[l];
			for (int k = 0; k < j1; k++) {
				int i1 = ai1[k];
				if (li[i1] < k1)
					k1 = li[i1];
				else if (li[i1] > l1)
					l1 = li[i1];
				if (mi[i1] < i2)
					i2 = mi[i1];
				else if (mi[i1] > j2)
					j2 = mi[i1];
				if (ni[i1] < k2)
					k2 = ni[i1];
				else if (ni[i1] > l2)
					l2 = ni[i1];
			}

			if (!xh) {
				qi[i] = k1;
				ri[i] = l1;
				si[i] = i2;
				ti[i] = j2;
				ui[i] = k2;
				vi[i] = l2;
			}
			if (l1 - k1 > mj)
				mj = l1 - k1;
			if (j2 - i2 > mj)
				mj = j2 - i2;
			if (l2 - k2 > mj)
				mj = l2 - k2;
			if (k1 < kh)
				kh = k1;
			if (l1 > lh)
				lh = l1;
			if (i2 < mh)
				mh = i2;
			if (j2 > nh)
				nh = j2;
			if (k2 < oh)
				oh = k2;
			if (l2 > ph)
				ph = l2;
		}
	}

	public void te() {
		if (yh)
			return;
		int i = rj * qj >> 8;
		for (int k = 0; k < model_face_count; k++)
			if (face_intensity[k] != gouraud)
				face_intensity[k] = (dh[k] * nj + eh[k] * oj + fh[k] * pj) / i;

		int ai1[] = new int[model_vertex_count];
		int ai2[] = new int[model_vertex_count];
		int ai3[] = new int[model_vertex_count];
		int ai4[] = new int[model_vertex_count];
		for (int l = 0; l < model_vertex_count; l++) {
			ai1[l] = 0;
			ai2[l] = 0;
			ai3[l] = 0;
			ai4[l] = 0;
		}

		for (int i1 = 0; i1 < model_face_count; i1++)
			if (face_intensity[i1] == gouraud) {
				for (int j1 = 0; j1 < face_vertex_count[i1]; j1++) {
					int l1 = face_vertices[i1][j1];
					ai1[l1] += dh[i1];
					ai2[l1] += eh[i1];
					ai3[l1] += fh[i1];
					ai4[l1]++;
				}
			}

		for (int k1 = 0; k1 < model_vertex_count; k1++)
			if (ai4[k1] > 0)
				tg[k1] = (ai1[k1] * nj + ai2[k1] * oj + ai3[k1] * pj) / (i * ai4[k1]);
	}

	public void we() {
		if (yh && xh)
			return;
		for (int i = 0; i < model_face_count; i++) {
			int ai1[] = face_vertices[i];
			int k = li[ai1[0]];
			int l = mi[ai1[0]];
			int i1 = ni[ai1[0]];
			int j1 = li[ai1[1]] - k;
			int k1 = mi[ai1[1]] - l;
			int l1 = ni[ai1[1]] - i1;
			int i2 = li[ai1[2]] - k;
			int j2 = mi[ai1[2]] - l;
			int k2 = ni[ai1[2]] - i1;
			int l2 = k1 * k2 - j2 * l1;
			int i3 = l1 * i2 - k2 * j1;
			int j3;
			for (j3 = j1 * j2 - i2 * k1; l2 > 8192 || i3 > 8192 || j3 > 8192 || l2 < -8192 || i3 < -8192
					|| j3 < -8192; j3 >>= 1) {
				l2 >>= 1;
				i3 >>= 1;
			}

			int k3 = (int) (256D * Math.sqrt(l2 * l2 + i3 * i3 + j3 * j3));
			if (k3 <= 0)
				k3 = 1;
			dh[i] = (l2 * 0x10000) / k3;
			eh[i] = (i3 * 0x10000) / k3;
			fh[i] = (j3 * 65535) / k3;
			bh[i] = -1;
		}

		te();
	}

	public void zd() {
		if (ih == 2) {
			ih = 0;
			for (int i = 0; i < model_vertex_count; i++) {
				li[i] = vertices_x[i];
				mi[i] = vertices_y[i];
				ni[i] = vertices_z[i];
			}

			kh = mh = oh = 0xff676981;
			mj = lh = nh = ph = 0x98967f;
			return;
		}
		if (ih == 1) {
			ih = 0;
			for (int k = 0; k < model_vertex_count; k++) {
				li[k] = vertices_x[k];
				mi[k] = vertices_y[k];
				ni[k] = vertices_z[k];
			}

			if (lj >= 2)
				le(zi, aj, bj);
			if (lj >= 3)
				qe(cj, dj, ej);
			if (lj >= 4)
				je(fj, gj, hj, ij, jj, kj);
			if (lj >= 1)
				_mthif(wi, xi, yi);
			ae();
			we();
		}
	}

	public void oe(int i, int k, int l, int i1, int j1, int k1, int l1, int i2) {
		zd();
		if (oh > Scene.kp || ph < Scene.jp || kh > Scene.gp || lh < Scene.fp || mh > Scene.ip
				|| nh < Scene.hp) {
			jh = false;
			return;
		}
		jh = true;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		if (k1 != 0) {
			i3 = ci[k1];
			j3 = ci[k1 + 1024];
		}
		if (j1 != 0) {
			i4 = ci[j1];
			j4 = ci[j1 + 1024];
		}
		if (i1 != 0) {
			k3 = ci[i1];
			l3 = ci[i1 + 1024];
		}
		for (int k4 = 0; k4 < model_vertex_count; k4++) {
			int l4 = li[k4] - i;
			int i5 = mi[k4] - k;
			int j5 = ni[k4] - l;
			if (k1 != 0) {
				int j2 = i5 * i3 + l4 * j3 >> 15;
				i5 = i5 * j3 - l4 * i3 >> 15;
				l4 = j2;
			}

			if (j1 != 0) {
				int k2 = j5 * i4 + l4 * j4 >> 15;
				j5 = j5 * j4 - l4 * i4 >> 15;
				l4 = k2;
			}
			if (i1 != 0) {
				int l2 = i5 * l3 - j5 * k3 >> 15;
				j5 = i5 * k3 + j5 * l3 >> 15;
				i5 = l2;
			}
			if (j5 >= i2)
				rg[k4] = (l4 << l1) / j5;
			else
				rg[k4] = l4 << l1;
			if (j5 >= i2)
				sg[k4] = (i5 << l1) / j5;
			else
				sg[k4] = i5 << l1;
			og[k4] = l4;
			pg[k4] = i5;
			qg[k4] = j5;
		}

	}

	public void pe() {
		zd();
		for (int i = 0; i < model_vertex_count; i++) {
			vertices_x[i] = li[i];
			vertices_y[i] = mi[i];
			vertices_z[i] = ni[i];
		}

		wi = xi = yi = 0;
		zi = aj = bj = 0;
		cj = dj = ej = 256;
		fj = gj = hj = ij = jj = kj = 256;
		lj = 0;
	}

	public Geometry xe() {
		Geometry ah1[] = new Geometry[1];
		ah1[0] = this;
		Geometry h1 = new Geometry(ah1, 1);
		h1.hh = hh;
		return h1;
	}

	public Geometry bf(boolean flag, boolean flag1, boolean flag2, boolean flag3) {
		Geometry ah1[] = new Geometry[1];
		ah1[0] = this;
		Geometry h1 = new Geometry(ah1, 1, flag, flag1, flag2, flag3);
		h1.hh = hh;
		return h1;
	}

	public void fe(Geometry h1) {
		zi = h1.zi;
		aj = h1.aj;
		bj = h1.bj;
		wi = h1.wi;
		xi = h1.xi;
		yi = h1.yi;
		df();
		ih = 1;
	}

	public int ye(byte abyte0[]) {
		for (; abyte0[uj] == 10 || abyte0[uj] == 13; uj++)
			;
		int i = ei[abyte0[uj++] & 0xff];
		int k = ei[abyte0[uj++] & 0xff];
		int l = ei[abyte0[uj++] & 0xff];
		int i1 = (i * 4096 + k * 64 + l) - 0x20000;
		if (i1 == 0x1e240)
			i1 = gouraud;
		return i1;
	}

	public int model_vertex_count;
	public int og[];
	public int pg[];
	public int qg[];
	public int rg[];
	public int sg[];
	public int tg[];
	public byte ug[];
	public int model_face_count;
	public int face_vertex_count[];
	public int face_vertices[][];
	public int face_back[];
	public int face_front[];
	public int ah[];
	public int bh[];
	public int face_intensity[];
	public int dh[];
	public int eh[];
	public int fh[];
	public int gh;
	public int hh;
	public int ih;
	public boolean jh;
	public int kh;
	public int lh;
	public int mh;
	public int nh;
	public int oh;
	public int ph;
	public boolean qh;
	public boolean rh;
	public boolean sh;
	public int th;
	public int uh[];
	public byte vh[];
	private boolean wh;
	public boolean xh;
	public boolean yh;
	public boolean zh;
	public boolean ai;
	private static int bi[];
	private static int ci[];
	private static byte di[];
	private static int ei[];
	private int fi;
	private int gouraud;
	public int hi;
	public int vertices_x[];
	public int vertices_y[];
	public int vertices_z[];
	public int li[];
	public int mi[];
	public int ni[];
	private int oi;
	private int pi[][];
	private int qi[];
	private int ri[];
	private int si[];
	private int ti[];
	private int ui[];
	private int vi[];
	private int wi;
	private int xi;
	private int yi;
	private int zi;
	private int aj;
	private int bj;
	private int cj;
	private int dj;
	private int ej;
	private int fj;
	private int gj;
	private int hj;
	private int ij;
	private int jj;
	private int kj;
	private int lj;
	private int mj;
	private int nj;
	private int oj;
	private int pj;
	private int qj;
	protected int rj;
	protected int sj;
	private byte tj[];
	private int uj;
	private int vj;

	static {
		bi = new int[512];
		ci = new int[2048];
		di = new byte[64];
		ei = new int[256];
		for (int i = 0; i < 256; i++) {
			bi[i] = (int) (Math.sin((double) i * 0.02454369D) * 32768D);
			bi[i + 256] = (int) (Math.cos((double) i * 0.02454369D) * 32768D);
		}

		for (int k = 0; k < 1024; k++) {
			ci[k] = (int) (Math.sin((double) k * 0.00613592315D) * 32768D);
			ci[k + 1024] = (int) (Math.cos((double) k * 0.00613592315D) * 32768D);
		}

		for (int l = 0; l < 10; l++)
			di[l] = (byte) (48 + l);

		for (int i1 = 0; i1 < 26; i1++)
			di[i1 + 10] = (byte) (65 + i1);

		for (int j1 = 0; j1 < 26; j1++)
			di[j1 + 36] = (byte) (97 + j1);

		di[62] = -93;
		di[63] = 36;
		for (int k1 = 0; k1 < 10; k1++)
			ei[48 + k1] = k1;

		for (int l1 = 0; l1 < 26; l1++)
			ei[65 + l1] = l1 + 10;

		for (int i2 = 0; i2 < 26; i2++)
			ei[97 + i2] = i2 + 36;

		ei[163] = 62;
		ei[36] = 63;
	}
}