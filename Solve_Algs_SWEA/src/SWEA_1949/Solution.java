package SWEA_1949;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

class po {
	int x;
	int y;
	int n;

	public po(int x, int y, int n) {
		this.x = x;
		this.y = y;

		this.n = n;
	}
}
// 35ºÐ ¼Ò¿ä

public class Solution {
	public static int map[][], cmap[][];
	public static int N, K;
	public static int isVisit[][];
	public static ArrayList<Integer> answer;
	public static ArrayList<po> pos;

	public static int front, rear;
	public static po[] queue;
	public static int routeMAX;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());
		answer = new ArrayList<>();
	

		for (int t = 1; t <= T; t++) {
			String[] s1 = br.readLine().split(" ");
			N = Integer.parseInt(s1[0]);
			K = Integer.parseInt(s1[1]);
			pos = new ArrayList<>();
			map = new int[N + 1][N + 1];
			cmap = new int[N + 1][N + 1];
			isVisit = new int[N + 1][N + 1];
			queue = new po[(N + 1) * (N + 1) * 4 * 1000];
			routeMAX = Integer.MIN_VALUE;
			int MAX = Integer.MIN_VALUE;
			for (int i = 1; i <= N; i++) {
				String[] s2 = br.readLine().split(" ");
				for (int j = 1; j <= N; j++) {
					map[i][j] = Integer.parseInt(s2[j - 1]);
					cmap[i][j] = map[i][j];
					isVisit[i][j] = 0;
					if (MAX < map[i][j])
						MAX = map[i][j];
				}
			}

			for (int i = 1; i <= N; i++) {

				for (int j = 1; j <= N; j++) {
					if (MAX == map[i][j]) {
						// System.out.println(j+ " / " + i);
						pos.add(new po(j, i, MAX));
					}
				}
			}

			solve();
			System.out.println("#" + t + " " + routeMAX);
		}
	}

	public static void solve() {
		for (int k = 1; k <= K; k++) {
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					cmap[i][j] -= k;
					for (int a = 0; a < pos.size(); a++) {
						po p = pos.get(a);
						bfs(p.x, p.y);
					}
					clear();
				}
			}
		}

	}

	public static int[] dx = { -1, 1, 0, 0 };
	public static int[] dy = { 0, 0, -1, 1 };

	public static void bfs(int x, int y) {

		queue[rear++] = new po(x, y, 1);

		while (front != rear) {
			po p = queue[front++];

			routeMAX = Math.max(routeMAX, p.n);

			int px = p.x;
			int py = p.y;

			for (int i = 0; i < 4; i++) {
				int tx = px + dx[i];
				int ty = py + dy[i];
				if (isVaild(px, py) == true) {
					if (isVaild(tx, ty) == true && cmap[py][px] - cmap[ty][tx] >= 1) {
						isVisit[ty][tx] = 1;
						queue[rear++] = new po(tx, ty, p.n + 1);
					}
				}
			}

		}

	}

	public static boolean isVaild(int x, int y) {
		if (x < 1 || x > N || y < 1 || y > N)
			return false;
		else
			return true;
	}

	public static void clear() {

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				isVisit[i][j] = 0;
				cmap[i][j] = map[i][j];
			}
		}
		front = rear = 0;
	}
}



