package Git_BJ_16234;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

//https://www.acmicpc.net/problem/16234

public class Main {

	public static int map[][];
	public static int union[][], isvisit[][];
	public static int L, R, N;

	public static int[] dx = { -1, 1, 0, 0 };
	public static int[] dy = { 0, 0, -1, 1 };

	public static int unionCnt;
	public static int cnt;

	public static HashMap<Integer, Integer> sumhash;
	public static HashMap<Integer, Integer> cnthash;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] s1 = br.readLine().split(" ");

		N = Integer.parseInt(s1[0]);
		L = Integer.parseInt(s1[1]);
		R = Integer.parseInt(s1[2]);

		map = new int[N + 1][N + 1];
		union = new int[N + 1][N + 1];
		isvisit = new int[N + 1][N + 1];
		sumhash = new HashMap<>();
		cnthash = new HashMap<>();
		unionCnt = 1;
		cnt = 0;
		for (int i = 1; i <= N; i++) {
			String[] s2 = br.readLine().split(" ");
			for (int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(s2[j - 1]);
			}
		}

		solve();
		
	}

	public static void solve() {
		int flag = 1;
		int tempcnt = 1;
		while (flag == 1) {
			findUnion();
			if (unionCnt == 1) {
				flag = 0;
				break;
			}
			averageEachUnion();
			tempcnt++;
			clear();
		}
		System.out.println((tempcnt - 1));
	}

	public static void averageEachUnion() {
		int[] average = new int[unionCnt + 1];
		for (int i = 1; i < unionCnt; i++) {
			int a = sumhash.getOrDefault(i, -1);
			int b = cnthash.getOrDefault(i, -1);
			int temp = a / b;
			for (int j = 1; j <= N; j++) {
				for (int k = 1; k <= N; k++) {
					if (union[j][k] == i)
						map[j][k] = temp;
				}
			}
		}

	}

	public static void findUnion() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				for (int k = 0; k < dx.length; k++) {
					int mx = j + dx[k];
					int my = i + dy[k];
					if (isVaild(mx, my) == true && Math.abs(map[i][j] - map[my][mx]) >= L
							&& Math.abs(map[i][j] - map[my][mx]) <= R && isvisit[my][mx] == 0) {
						isvisit[i][j] = 1;
						union[i][j] = unionCnt;
						int a = sumhash.getOrDefault(unionCnt, 0);
						sumhash.put(unionCnt, a + map[i][j]);
						int b = cnthash.getOrDefault(unionCnt, 0);
						cnthash.put(unionCnt, b + 1);
						findSameUnion(j, i, unionCnt);
						unionCnt++;
					}
				}

			}
		}
	}

	public static void findSameUnion(int x, int y, int unionCnt) {
		for (int i = 0; i < dx.length; i++) {
			int tx = x + dx[i];
			int ty = y + dy[i];
			if (isVaild(tx, ty) == true && Math.abs(map[y][x] - map[ty][tx]) >= L
					&& Math.abs(map[y][x] - map[ty][tx]) <= R && isvisit[ty][tx] == 0) {
				isvisit[ty][tx] = 1;
				union[ty][tx] = unionCnt;
				int a = sumhash.getOrDefault(unionCnt, 0);
				sumhash.put(unionCnt, a + map[ty][tx]);
				int b = cnthash.getOrDefault(unionCnt, 0);
				cnthash.put(unionCnt, b + 1);
				findSameUnion(tx, ty, unionCnt);
			}
		}
	}

	public static boolean isVaild(int x, int y) {
		if (x < 1 || x > N || y < 1 || y > N) {
			return false;
		} else
			return true;

	}

	public static void printUnion() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				System.out.print(union[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void printMap() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void clear() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				isvisit[i][j] = 0;
				union[i][j] = 0;
			}

		}
		sumhash = new HashMap<>();
		cnthash = new HashMap<>();
		unionCnt = 1;
	}

}
