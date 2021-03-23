package Git_BJ_17144;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//https://www.acmicpc.net/problem/17144

class aircleaner {
	int x1, y1, x2, y2;
}

public class Main {

	public static int Y, X, T;
	public static int map[][];
	public static int smap[][];
	public static int isVisit[][];

	public static int[] dx = { -1, 1, 0, 0 };
	public static int[] dy = { 0, 0, -1, 1 };

	public static aircleaner ac;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] s1 = br.readLine().split(" ");

		Y = Integer.parseInt(s1[0]);
		X = Integer.parseInt(s1[1]);
		T = Integer.parseInt(s1[2]); // T�� �� ������

		map = new int[Y + 1][X + 1];
		smap = new int[Y + 1][X + 1];
		isVisit = new int[Y + 1][X + 1];
		int idx = 0;
		ac = new aircleaner();
		for (int i = 1; i <= Y; i++) {
			String[] s2 = br.readLine().split(" ");
			for (int j = 1; j <= X; j++) {
				map[i][j] = Integer.parseInt(s2[j - 1]);
				smap[i][j] = 0;
				isVisit[i][j] = 0;
				if (map[i][j] == -1) {
					if (idx == 0) {
						ac.x1 = j;
						ac.y1 = i;
						idx += 1;
					} else {
						ac.x2 = j;
						ac.y2 = i;
					}
				}
			}
		}
		solve();
	}

	public static void solve() {
		int NUM = 1;
		while (NUM <= T) {
			spreadMicroDust();
			workAirCleaner();
			clear();
			NUM += 1;
		}
		calc();
	}

	public static void calc() {
		int SUM = 0;
		for (int i = 1; i <= Y; i++) {
			for (int j = 1; j <= X; j++) {
				if (i == ac.y1 && j == ac.x1) {
				} else if (i == ac.y2 && j == ac.x2) {
				} else {
					SUM += map[i][j];
				}
			}
		}
		System.out.println(SUM);
	}

	public static void spreadMicroDust() {
		// ������ �� �������� Ȯ��
		// ������ ���⿡ ����û���Ⱑ �ְų�, ĭ�� ������ �� �������δ� Ȯ���� �Ͼ�� �ʴ´�.
		// Ȯ��Ǵ� ���� X/5�̰� �Ҽ����� ������
		// ���� �̼����� ���� X - X/5 * Ȯ��� ĭ ��

		for (int i = 1; i <= Y; i++) {
			for (int j = 1; j <= X; j++) {
				int spreadCnt = 0;
				int spreadDustPerOne = map[i][j] / 5;
				isVisit[i][j] = 1;
				if (map[i][j] > 0) {
					// smap[i][j] = map[i][j];
					for (int k = 0; k < dx.length; k++) {
						int tx = j + dx[k];
						int ty = i + dy[k];
						if (isVaild(tx, ty) == true && map[ty][tx] != -1 && map[i][j] >= 5) {
							spreadCnt = spreadCnt + 1;
							smap[ty][tx] += spreadDustPerOne;
						}
					}
					if (spreadCnt != 0) {
						smap[i][j] += map[i][j] - spreadDustPerOne * spreadCnt;
					} else {
						smap[i][j] += map[i][j];
					}
				}
			}
		}
	}

	public static void workAirCleaner() {
		// ����û���� �ٶ� ����
		// ���� �ݽð�������� ��ȯ
		// �Ʒ��� �ð�������� ��ȯ
		// �����ȭ? -- > �ٶ� �������� ��ĭ�� �и��°���

		// upside
		int ex = X;
		int ey = ac.y1;
		int temp = smap[1][1];
		for (int i = ey; i > 1; i--) { // �� ����
			smap[i][1] = smap[i-1][1];
		}
		smap[ey][1] = -1;
		for (int i = 0; i < ex; i++) { // �� ����
			smap[1][i] = smap[1][i + 1];
		}
		for (int i = 2; i <= ey; i++) { // �� ����
			smap[i - 1][ex] = smap[i][ex];
		}
		for (int i = ex; i > 2; i--) { // �Ʒ� ����
			smap[ey][i] = smap[ey][i - 1];
		}
		smap[ey][2] = 0;

		// downside
		int sx = ac.x2;
		int sy = ac.y2;
		for (int i = sy + 1; i <= Y; i++) { // �� ����
			smap[i - 1][sx] = smap[i][sx];
		}
		for (int i = 1; i < X; i++) { // �Ʒ� ����
			smap[Y][i] = smap[Y][i + 1];
		}
		for (int i = Y; i > sy; i--) { // �� ����
			smap[i][X] = smap[i - 1][X];
		}
		for (int i = X; i > 2; i--) { // �� ����
			smap[sy][i] = smap[sy][i - 1];
		}
		smap[sy][1] = -1;
		smap[sy][2] = 0;
	}

	public static void printMap() {

		for (int i = 1; i <= Y; i++) {

			for (int j = 1; j <= X; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("========print map ==========");
	}

	public static void printSMap() {

		for (int i = 1; i <= Y; i++) {

			for (int j = 1; j <= X; j++) {
				System.out.print(smap[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("========print smap ==========");
	}

	public static boolean isVaild(int x, int y) {
		if (x < 1 || x > X || y < 1 || y > Y)
			return false;
		else
			return true;
	}

	public static void clear() {
		for (int i = 1; i <= Y; i++) {

			for (int j = 1; j <= X; j++) {
				map[i][j] = smap[i][j];
				smap[i][j] = 0;
			}

		}

	}
}
