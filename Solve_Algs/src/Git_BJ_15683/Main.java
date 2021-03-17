package Git_BJ_15683;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

//https://www.acmicpc.net/problem/15683

/* 
*  1시간 20분 걸림
*/

class cctv {
	int x;
	int y;
	int n;
	int d;

	public cctv(int x, int y, int n) {
		this.x = x;
		this.y = y;
		this.n = n;
		this.d = 0;
	}
}

public class Main {

	public static int N, M; // 세로 가로
	public static int[][] map, cmap; // map 원본, 카피용

	public static ArrayList<cctv> list;
	public static int[] directions = { 0, 1, 2, 3 }; // 동 서 남 북
	public static int[] combi;
	public static int[] isvisit;
	public static int MAX = 0;
	public static int wall = 0;
	public static int[] currentdirection;
	public static int answermap[][];
	public static ArrayList<ArrayList<Integer>> all_case;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] s1 = br.readLine().split(" ");

		N = Integer.parseInt(s1[0]);
		M = Integer.parseInt(s1[1]);
		map = new int[N + 1][M + 1];
		cmap = new int[N + 1][M + 1];
		answermap = new int[N + 1][M + 1];
		list = new ArrayList<>();
		all_case = new ArrayList<ArrayList<Integer>>();

		for (int i = 1; i <= N; i++) {
			String[] s2 = br.readLine().split(" ");
			for (int j = 1; j <= M; j++) {
				map[i][j] = Integer.parseInt(s2[j - 1]);
				cmap[i][j] = map[i][j];
				if (map[i][j] == 6)
					wall += 1;
				if (map[i][j] >= 1 && map[i][j] <= 5)
					list.add(new cctv(j, i, map[i][j]));
			}
		}
		currentdirection = new int[9];

		for (int i = 0; i < 9; i++) {
			currentdirection[i] = 0;
		}

		doing();
//		System.out.println(MAX + " / " + wall);
		System.out.println(N * M - MAX - wall);
	}

	public static void doing() {

		int idx = 0;
		int cnt = 0;
		while (currentdirection[8] == 0) {
			cnt++;
			if (currentdirection[idx + 0] == 4) {
				currentdirection[idx + 1] += 1;
				currentdirection[idx + 0] = 0;
			}
			if (currentdirection[idx + 1] == 4) {
				currentdirection[idx + 2] += 1;
				currentdirection[idx + 1] = 0;
			}
			if (currentdirection[idx + 2] == 4) {
				currentdirection[idx + 3] += 1;
				currentdirection[idx + 2] = 0;
			}
			if (currentdirection[idx + 3] == 4) {
				currentdirection[idx + 4] += 1;
				currentdirection[idx + 3] = 0;
			}
			if (currentdirection[idx + 4] == 4) {
				currentdirection[idx + 5] += 1;
				currentdirection[idx + 4] = 0;
			}

			if (currentdirection[idx + 5] == 4) {
				currentdirection[idx + 6] += 1;
				currentdirection[idx + 5] = 0;
			}
			if (currentdirection[idx + 6] == 4) {
				currentdirection[idx + 7] += 1;
				currentdirection[idx + 6] = 0;
			}
			if (currentdirection[idx + 7] == 4) {
				currentdirection[idx + 8] += 1;
				currentdirection[idx + 7] = 0;
			}

//			if (cnt < 200) {
//				for (int i = 1; i <= 8; i++) {
//					System.out.print(currentdirection[i] + " ");
//				}
//				System.out.println();
//			}
			solve();
			currentdirection[idx]++;

			if (currentdirection[list.size()] > 0)
				break;
		}
	}

	public static void solve() {

		initialize();
		for (int i = 0; i < list.size(); i++) {
			checkView(i, currentdirection[i]);
			calc();
//			System.out.println("===");
//			printcMap();
//			System.out.println("===");

		}

	}

	public static void calc() {
		int cnt = 0;
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				if (cmap[i][j] == 9 || (cmap[i][j] >= 1 && cmap[i][j] <= 5)) { // 감시가능한 곳 또는 cctv위치
					cnt++;
				}
			}
		}

		MAX = Math.max(cnt, MAX);

	}

	public static void checkView(int i, int d) {

		cctv c = list.get(i);

		int x = c.x;
		int y = c.y;
		switch (c.n) {
		case 1: // 상 (한쪽만)
			x = c.x;
			y = c.y;
			while (true) {
				switch (d) {
				case 0: // 동
					x += 1;
					break;
				case 1: // 서
					x -= 1;
					break;

				case 2:// 남
					y += 1;

					break;
				case 3:// 북
					y -= 1;
					break;
				}

				if (isVaild(x, y) == true) {
					cmap[y][x] = 9;
				} else {
					break;
				}
			}
			break;
		case 2:// 상하 (양쪽)
			x = c.x;
			y = c.y;
			while (true) { // 상
				switch (d) {
				case 0: // 동
					x += 1;
					break;
				case 1: // 서
					x -= 1;
					break;

				case 2:// 남
					y += 1;

					break;
				case 3:// 북
					y -= 1;
					break;
				}

				if (isVaild(x, y) == true) {
					cmap[y][x] = 9;
				} else {
					break;
				}
			}
			x = c.x;
			y = c.y;
			while (true) { // 하
				switch (d) {
				case 0: // 동
					x -= 1;
					break;
				case 1: // 서
					x += 1;
					break;

				case 2:// 남
					y -= 1;

					break;
				case 3:// 북
					y += 1;
					break;
				}

				if (isVaild(x, y) == true) {
					cmap[y][x] = 9;
				} else {
					break;
				}
			}
			break;
		case 3: // 상 우
			x = c.x;
			y = c.y;
			while (true) {
				switch (d) { // 상
				case 0: // 동
					x += 1;
					break;
				case 1: // 서
					x -= 1;
					break;

				case 2:// 남
					y += 1;

					break;
				case 3:// 북
					y -= 1;
					break;
				}

				if (isVaild(x, y) == true) {
					cmap[y][x] = 9;
				} else {
					break;
				}
			}
			x = c.x;
			y = c.y;
			while (true) { // 우
				switch (d) {
				case 0: // 동 - > 남
					y += 1;
					break;
				case 1: // 서 -> 북
					y -= 1;
					break;

				case 2:// 남 - > 서
					x -= 1;

					break;
				case 3:// 북 -> 동
					x += 1;
					break;
				}

				if (isVaild(x, y) == true) {
					cmap[y][x] = 9;
				} else {
					break;
				}
			}
			break;
		case 4: // 상 좌 우
			x = c.x;
			y = c.y;
			while (true) {
				switch (d) { // 상
				case 0: // 동
					x += 1;
					break;
				case 1: // 서
					x -= 1;
					break;

				case 2:// 남
					y += 1;

					break;
				case 3:// 북
					y -= 1;
					break;
				}

				if (isVaild(x, y) == true) {
					cmap[y][x] = 9;
				} else {
					break;
				}
			}
			x = c.x;
			y = c.y;
			while (true) { // 우
				switch (d) {
				case 0: // 동 - > 남
					y += 1;
					break;
				case 1: // 서 -> 북
					y -= 1;
					break;

				case 2:// 남 - > 서
					x -= 1;

					break;
				case 3:// 북 -> 동
					x += 1;
					break;
				}

				if (isVaild(x, y) == true) {
					cmap[y][x] = 9;
				} else {
					break;
				}
			}
			x = c.x;
			y = c.y;
			while (true) { // 좌
				switch (d) {
				case 0: // 동 - > 북
					y -= 1;
					break;
				case 1: // 서 -> 남
					y += 1;
					break;

				case 2:// 남 - > 동
					x += 1;

					break;
				case 3:// 북 -> 서
					x -= 1;
					break;
				}
				if (isVaild(x, y) == true) {
					cmap[y][x] = 9;
				} else {
					break;
				}
			}
			break;
		case 5: // 상하좌우
			x = c.x;
			y = c.y;
			while (true) {
				switch (d) { // 상
				case 0: // 동
					x += 1;
					break;
				case 1: // 서
					x -= 1;
					break;

				case 2:// 남
					y += 1;

					break;
				case 3:// 북
					y -= 1;
					break;
				}

				if (isVaild(x, y) == true) {
					cmap[y][x] = 9;
				} else {
					break;
				}
			}
			x = c.x;
			y = c.y;
			while (true) { // 우
				switch (d) {
				case 0: // 동 - > 남
					y += 1;
					break;
				case 1: // 서 -> 북
					y -= 1;
					break;

				case 2:// 남 - > 서
					x -= 1;

					break;
				case 3:// 북 -> 동
					x += 1;
					break;
				}

				if (isVaild(x, y) == true) {
					cmap[y][x] = 9;
				} else {
					break;
				}
			}
			x = c.x;
			y = c.y;
			while (true) { // 좌
				switch (d) {
				case 0: // 동 - > 북
					y -= 1;
					break;
				case 1: // 서 -> 남
					y += 1;
					break;

				case 2:// 남 - > 동
					x += 1;

					break;
				case 3:// 북 -> 서
					x -= 1;
					break;
				}
				if (isVaild(x, y) == true) {
					cmap[y][x] = 9;
				} else {
					break;
				}
			}
			x = c.x;
			y = c.y;
			while (true) { // 하
				switch (d) {
				case 0: // 동
					x -= 1;
					break;
				case 1: // 서
					x += 1;
					break;
				case 2:// 남
					y -= 1;
					break;
				case 3:// 북
					y += 1;
					break;
				}

				if (isVaild(x, y) == true) {
					cmap[y][x] = 9;
				} else {
					break;
				}
			}
			break;

		}

	}

	public static boolean isVaild(int x, int y) {
		if (x < 1 || x > M || y < 1 || y > N || cmap[y][x] == 6)
			return false;
		else
			return true;
	}

	public static void initialize() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				cmap[i][j] = map[i][j];
			}
		}
	}

	public static void printcMap() {

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				System.out.print(cmap[i][j] + " ");
			}
			System.out.println();
		}

	}

}
