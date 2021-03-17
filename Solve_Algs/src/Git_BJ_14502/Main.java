package Git_BJ_14502;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	public static int Y, X;

	// 0 ��ĭ, 1 ��, 2 ���̷���
	public static int map[][], cmap[][];
	public static int isVisit[][], spreadVisit[][];
	// 2���� �迭�� 1���� �迭�� ��ȯ�Ͽ� ���� �̾Ƴ��� ���� ����
	public static ArrayList<point> viruses;
	public static int MAX;
	public static int isUseArray[];
	public static ArrayList<point> zeroList;

	// �� �� �� �� Ž��
	public static int[] dx = { -1, 1, 0, 0 };
	public static int[] dy = { 0, 0, -1, 1 };

	public static point[] queue;
	public static int front, rear;

	public static point spreadps[];
	public static int combcnt = 0;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s[] = br.readLine().split(" ");
		Y = Integer.parseInt(s[0]);
		X = Integer.parseInt(s[1]);

		spreadps = new point[3];
		map = new int[Y + 1][X + 1];
		cmap = new int[Y + 1][X + 1];
		isVisit = new int[Y + 1][X + 1];
		spreadVisit = new int[Y + 1][X + 1];
		viruses = new ArrayList<>();
		zeroList = new ArrayList<>();
		queue = new point[(Y + 1) * (X + 1) * 4 * 1000];
		for (int i = 1; i <= Y; i++) {
			String ss[] = br.readLine().split(" ");
			for (int j = 1; j <= X; j++) {
				map[i][j] = Integer.parseInt(ss[j - 1]);
				cmap[i][j] = map[i][j];
				if (map[i][j] == 0)
					zeroList.add(new point(j, i));
				if (map[i][j] == 2)
					viruses.add(new point(j, i));
			}
		}
		isUseArray = new int[zeroList.size()];
		long start = System.currentTimeMillis();
		solve();
		long end = System.currentTimeMillis();

	}

	public static void solve() {
		MAX = 0;
		isVisit = new int[Y + 1][X + 1];
		combination(zeroList, isUseArray, 0, 3);
		System.out.println(MAX);
	}

	/**
	 * ���̷��� ��Ʈ��
	 * @param ps �� ���� 3�� ��ǥ
	 */
	public static void spreadVirus(point[] ps) {
		clear();
		buildWall(ps);
		//���̷��� ��ġ�������� BFS ����
		for (int i = 0; i < viruses.size(); i++) {
			point vp = viruses.get(i);
			int vx = vp.x;
			int vy = vp.y;
			queue[rear++] = new point(vx, vy);
			spread();
		}
		int cnt = calc();
	}

	/**
	 * ������ �� �����
	 * @param ps �� ���� 3�� ��ǥ
	 */
	public static void buildWall(point[] ps) {
		for (int i = 0; i < ps.length; i++) {
			int x = ps[i].x;
			int y = ps[i].y;
			cmap[y][x] = 1;
		}
	}

	/**
	 * ���̷��� ��Ʈ����
	 */
	public static void spread() {

		while (front != rear) {
			point p = queue[front++];
			int vx = p.x;
			int vy = p.y;
			spreadVisit[vy][vx] = 1;
			for (int j = 0; j < 4; j++) {
				int vdx = vx + dx[j];
				int vdy = vy + dy[j];
				if (isVaild(vdx, vdy) == 1 && cmap[vdy][vdx] == 0 && spreadVisit[vdy][vdx] == 0) { // ������
					spreadVisit[vdy][vdx] = 1; // �湮
					queue[rear++] = new point(vdx, vdy);
					cmap[vdy][vdx] = 2;
				}
			}
		}
	}

	/**
	 * �� ĭ ��� �� �ִ밪 ��
	 * 
	 * @return ��ĭ ���� ��ȯ
	 */
	public static int calc() {
		int cnt = 0;
		for (int i = 1; i <= Y; i++) {
			for (int j = 1; j <= X; j++) {
				if (cmap[i][j] == 0) {
					cnt++;
				}
			}
		}
		if (MAX < cnt)
			MAX = cnt;
		return cnt;
	}

	/**
	 * �� ���� �� �ִ� ��ġ�� ���� ����
	 * @param cmap 0 x,y ��ǥ
	 * @param isVisit �湮 ����
	 * @param idx ���� �ε��� ó������
	 * @param rec �ݺ����� ���⼱ 3
	 */
	public static void combination(ArrayList<point> cmap, int[] isVisit, int idx, int rec) {
		if (rec == 0) {

			int psidx = 0;
			for (int i = 0; i < isVisit.length; i++) {
				if (isVisit[i] == 1) {
					spreadps[psidx] = new point(zeroList.get(i).x, zeroList.get(i).y);
					point p = new point(zeroList.get(i).x, zeroList.get(i).y);
					psidx++;
				}
			}
			spreadVirus(spreadps);
		} else {
			for (int i = idx; i < cmap.size(); i++) {
				if (isVisit[i] == 0) {
					isVisit[i] = 1;
					combination(cmap, isVisit, idx, rec - 1);
					isVisit[i] = 0;
				}

			}
		}
	}

	
	/**
	 * BFS �� ���� ������ Ȯ��
	 * @param x x��
	 * @param y y��
	 * @return ������ 1 Ʋ���� 0
	 */
	public static int isVaild(int x, int y) {
		if (x < 1 || x > X || y < 1 || y > Y)
			return 0;
		else
			return 1;

	}

	/**
	 * ������
	 */
	public static void printMap() {
		for (int i = 1; i <= Y; i++) {

			for (int j = 1; j <= X; j++) {
				System.out.print(cmap[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * ���� ȯ�� �ʱ�ȭ
	 */
	public static void clear() {
		for (int i = 1; i <= Y; i++) {
			for (int j = 1; j <= X; j++) {
				cmap[i][j] = map[i][j];
				spreadVisit[i][j] = 0;
			}
		}
		front = rear = 0;
	}

	/** 
	 * ��ǥ ���� Ŭ����
	 * @author Choi
	 *
	 */
	static class point {
		int x, y;

		public point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public point() {
		}
	}
}
