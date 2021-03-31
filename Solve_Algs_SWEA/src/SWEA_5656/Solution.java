package SWEA_5656;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


class point {
	int x, y;

	public point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Solution {
	// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRQm6qfL0DFAUo

	public static int N, W, H;
	public static int map[][], cmap[][];
	public static int[] n, isVisit;
	public static int isBroke[][];
	public static int[] output;
	public static int outputidx;
	public static int MIN, front, rear;

	public static int[] dx = { -1, 1, 0, 0 };
	public static int[] dy = { 0, 0, 1, -1 };

	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		ArrayList<Integer> ans = new ArrayList<>();
		long pre = System.currentTimeMillis();

		for (int test_case = 1; test_case <= T; test_case++) {
			String[] s1 = br.readLine().split(" ");
			N = Integer.parseInt(s1[0]);
			W = Integer.parseInt(s1[1]);
			H = Integer.parseInt(s1[2]);
			map = new int[H + 1][W + 1];
			cmap = new int[H + 1][W + 1];
			isBroke = new int[H + 1][W + 1];
			MIN = (H+1) * (W+1);
			ps = new point[(W + 1) * (H + 1) * 4 * 9 * 1000];
			n = new int[W + 1];
			isVisit = new int[W + 1];
			output = new int[N + 1];
			front = rear = 0;
			for (int i = 1; i <= H; i++) {
				String[] s2 = br.readLine().split(" ");
				for (int j = 1; j <= W; j++) {
					map[i][j] = Integer.parseInt(s2[j - 1]);
					cmap[i][j] = map[i][j];
				}
			}
			solve();
			ans.add(MIN);
			MIN = Integer.MAX_VALUE;
			isFlag = false;
			clear();
		}
		
		
		long now = System.currentTimeMillis();

		for (int i = 1; i <= ans.size(); i++) {
			System.out.println("#" + i + " " + ans.get(i - 1));
		}

		 System.out.println(now - pre);
	}

	public static void solve() {

		for (int i = 1; i <= W; i++) {
			n[i] = i;
			isVisit[i] = 0;

		}
		outputidx = 0;
		comb(n, isVisit, output, outputidx, 1, W, N);

	}

	public static boolean isFlag = false;

	public static void comb(int[] n, int[] isVisit, int[] output, int outputidx, int start, int end, int rec) {
		if (isFlag == true)
			return;

		if (rec == 0) {

			for (int i = 0; i < N; i++) {
				int flag = findstart(output[i]);
		
			}
			int temp = calc();
			MIN = Math.min(MIN, temp);

			if (MIN == 0) {
				isFlag = true;

				return;
			}
			clear();
		} else {
			for (int i = start; i <= end; i++) {

				output[outputidx] = i;
				outputidx += 1;
				comb(n, isVisit, output, outputidx, start, end, rec - 1);
				outputidx -= 1;

			}
		}
	}

	public static int findstart(int i) {
		int x = i;
		int y = 1;

		while (true) {
			if (y > H)
				break;

			if (cmap[y][x] > 0) {
				break;
			}
			y += 1;
		}
		if (y > H)
			return 1;

		brokeMap(x, y);
		Down();
		return 0;
	}

	public static point[] ps;

	public static void brokeMap(int x, int y) {
		ps[rear++] = new point(x, y);
		while (front != rear) {
			point p = ps[front++];
			int qx = p.x;
			int qy = p.y;
			isBroke[qy][qx] = 1;
			for (int i = 0; i < 4; i++) {
				int tx = qx;
				int ty = qy;
				for (int k = 1; k < cmap[qy][qx]; k++) {
					tx += dx[i];
					ty += dy[i];
					if (isVaild(tx, ty) == true && isBroke[ty][tx] == 0) {
						ps[rear++] = new point(tx, ty);
					}
				}
			}

		}
	}

	public static void Down() {

		int[] isDown = new int[W + 1];

		for (int j = 1; j <= W; j++) {
			for (int i = 1; i <= H; i++) {
				if (isBroke[i][j] == 1) {
					cmap[i][j] = 0;
					isBroke[i][j] = 0;
					isDown[j] += 1;
				}
			}
		}

		for (int i = 1; i <= W; i++) {
			int sidx = -1; // 0 시작
			int eidx = -1; // 0 아닌거
			if (isDown[i] > 0) {
				ArrayList<Integer> list = new ArrayList<>();
				for (int j = H; j >= 1; j--) {

					if (cmap[j][i] != 0) {
						list.add(cmap[j][i]);
					}
				}

				for (int j = 0; j < list.size(); j++) {
					cmap[H - j][i] = list.get(j);
				}
				for (int j = list.size(); j <= H; j++) {
					cmap[H - j][i] = 0;
				}
			}
		}

	}

	public static int calc() {
		int sum = 0;
		for (int i = 1; i <= H; i++) {
			for (int j = 1; j <= W; j++) {
				if (cmap[i][j] > 0)
					sum += 1;
			}
		}
		return sum;

	}

	public static boolean isVaild(int x, int y) {
		if (x < 1 || x > W || y < 1 || y > H)
			return false;
		else
			return true;
	}

	public static void clear() {

		for (int i = 1; i <= H; i++) {
			for (int j = 1; j <= W; j++) {
				cmap[i][j] = map[i][j];
				isBroke[i][j] = 0;
			}
		}
		front = rear = 0;
	}

	public static void printMap() {

		for (int i = 1; i <= H; i++) {
			for (int j = 1; j <= W; j++) {
				System.out.print(cmap[i][j] + " ");
			}
			System.out.println();
		}

	}

	public static void printBroke() {

		for (int i = 1; i <= H; i++) {
			for (int j = 1; j <= W; j++) {
				System.out.print(isBroke[i][j] + " ");
			}
			System.out.println();
		}

	}

	public static void clearBroke() {
		for (int i = 1; i <= H; i++) {
			for (int j = 1; j <= W; j++) {
				isBroke[i][j] = 0;
			}

		}
		front = rear = 0;
	}
}