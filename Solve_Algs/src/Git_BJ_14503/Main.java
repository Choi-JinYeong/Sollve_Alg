package Git_BJ_14503;

import java.io.BufferedReader;


import java.io.InputStreamReader;
// https://www.acmicpc.net/problem/14503

/* 1시간 40분 소요
 * 
 */

class point {
	int x, y;
	int d;

	public point(int x, int y, int d) {
		this.x = x;
		this.y = y;
		this.d = d;
	}
}

public class Main {
	public static point robot;
	public static int[][] isCleaned;
	public static int[][] map;
	public static int N, M;
	public static int[] dx = { 0, 1, 0, -1 };
	public static int[] dy = { -1, 0, 1, 0 };
	public static int cleanCnt;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s1 = br.readLine();

		N = Integer.parseInt(s1.split(" ")[0]);
		M = Integer.parseInt(s1.split(" ")[1]);

		map = new int[N + 1][M + 1];
		isCleaned = new int[N + 1][M + 1];
		String s2 = br.readLine();

		int y = Integer.parseInt(s2.split(" ")[0]);
		int x = Integer.parseInt(s2.split(" ")[1]);
		int d = Integer.parseInt(s2.split(" ")[2]);

		robot = new point(x + 1, y + 1, d);

		for (int i = 1; i <= N; i++) {
			String[] s3 = br.readLine().split(" ");
			for (int j = 1; j <= M; j++) {
				map[i][j] = Integer.parseInt(s3[j - 1]);
				isCleaned[i][j] = 0;
			}
		}
		// System.out.println(robot.x + " , " + robot.y + " / " + robot.d);
		//printMap();
		solve();
		calc();
		System.out.println(cleanCnt);
	}

	public static void solve() {
		cleanCnt = 0;

		while (true) {

			clean();
			boolean isCheckLeftSide = checkLeftSide();
			if (isCheckLeftSide == true) { // 청소 가능
				rotate_1();
				move();

			} else {
				int isCheck4Side = check4Side();
				if (isCheck4Side == 2)// 종료
					break;
				if (isCheck4Side == 1)// 후진후 2번으로 돌아감
					backward();
				if (isCheck4Side == 0)
					rotate_2(); // 그 방향으로 회전하고 걍 돌아감
			}
			// 청소가 불가능해 --> 그 방향으로 회전하고 2번으로 돌아감.
			// 1. 네방향 청소 되었거나 벽 -> 바라보는방향 유지하고 한칸 후진 2번
			// 2. 네방향 청소 + 벽 + 뒤쪽 후진이면 작동 멈춤

		}

	}

	public static void rotate_2() {
		switch (robot.d) {
		case 0: // 북쪽을 바라보고 있으면 왼쪽은 서쪽
			robot.d = 3;
			break;
		case 1: // 동쪽을 바라보고 있으면 왼쪽은 북쪽
			robot.d = 0;
			break;
		case 2: // 남쪽을 바라보고 있으면 왼쪽은 동쪽
			robot.d = 1;
			break;
		case 3: // 서쪽을 바라보고 있으면 왼족은 남쪽
			robot.d = 2;
			break;
		}
	}

	public static void backward() {
		switch (robot.d) {

		case 0: // 북쪽바라보고 있으면 후진은 남쪽으로
			robot.y += 1;
			break;
		case 1: // 동쪽바라보고 있으면 후진은 서쪽으로
			robot.x -= 1;
			break;
		case 2:// 남쪽바라보고 있으면 후진은 북쪽으로
			robot.y -= 1;
			break;
		case 3: // 서쪽바라보고 있으면 후진은 동쪾으로
			robot.x += 1;
			break;
		}
	}

	public static int check4Side() {
		int x = robot.x;
		int y = robot.y;
		int d = robot.d;

		int cnt = 0;
		int cnt2 = 0;
		for (int i = 0; i < 4; i++) {
			int tx = x + dx[i];
			int ty = y + dy[i];

			if (isVaild(tx, ty) == true) {

			} else {
				cnt++;
			}

		}

		// 네 방향 청소나 벽이야
		if (cnt == 4) {
			// 뒤쪽이 벽인지 체크
			int tx = x;
			int ty = y;
			switch (d) {
			case 0: // 북쪽바라보고 있으면 후진은 남쪽으로
				ty += 1;
				break;
			case 1: // 동쪽바라보고 있으면 후진은 서쪽으로
				tx -= 1;
				break;
			case 2:// 남쪽바라보고 있으면 후진은 북쪽으로
				ty -= 1;
				break;
			case 3: // 서쪽바라보고 있으면 후진은 동쪾으로
				tx += 1;
				break;
			}
			if (tx < 1 || tx > M || ty < 1 || ty > N) { // 이것도 벽
				return 2;
			} else {
				if (map[ty][tx] == 1) {// 근데 후진하려고 했더니 벽이야
					return 2; // 종료
				}
				return 1; // 1이면 바라보는 방향 유지한채 한칸 후진하고 2번으로 돌아가
			}
		}
		return 0;
		// 0이면 2번으로 돌아감
	}

	public static boolean isVaild(int x, int y) {

		if (x < 1 || x > M || y < 1 || y > N || map[y][x] == 1 || isCleaned[y][x] == 1)
			return false;
		else
			return true;

	}

	public static void clean() {
		int x = robot.x;
		int y = robot.y;
		map[y][x] = 9;
		isCleaned[y][x] = 1;
		// cleanCnt += 1;
	}

	public static boolean checkLeftSide() {
		int x = robot.x;
		int y = robot.y;
		int d = robot.d;

		switch (d) {
		case 0: // 북쪽을 바라보고 있으면 왼쪽은 서쪽
			x -= 1;
			break;
		case 1: // 동쪽을 바라보고 있으면 왼쪽은 북쪽
			y -= 1;
			break;
		case 2: // 남쪽을 바라보고 있으면 왼쪽은 동쪽
			x += 1;
			break;
		case 3: // 서쪽을 바라보고 있으면 왼족은 남쪽
			y += 1;
			break;
		}

		if (map[y][x] == 0 && isCleaned[y][x] == 0) { // 청소해야하는 상황
			return true;
		}

		return false;

	}

	public static void rotate_1() {
		switch (robot.d) {
		case 0: // 북쪽을 바라보고 있으면 왼쪽은 서쪽
			robot.d = 3;
			break;
		case 1: // 동쪽을 바라보고 있으면 왼쪽은 북쪽
			robot.d = 0;
			break;
		case 2: // 남쪽을 바라보고 있으면 왼쪽은 동쪽
			robot.d = 1;
			break;
		case 3: // 서쪽을 바라보고 있으면 왼족은 남쪽
			robot.d = 2;
			break;
		}
	}

	public static void move() {
		switch (robot.d) {
		case 0: // 북쪽
			robot.y -= 1;
			break;
		case 1: // 동쪽
			robot.x += 1;
			break;
		case 2: // 남쪽
			robot.y += 1;
			break;
		case 3: // 서쪽
			robot.x -= 1;
			break;
		}
	}

	public static void printMap() {
		System.out.println(robot.x + " , " + robot.y + " / " + robot.d);
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				System.out.print(map[i][j] + " ");

			}
			System.out.println("");
		}
		System.out.println("======================================");
	}

	public static void calc() {

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				if (map[i][j] == 9)
					cleanCnt += 1;
			}
		}
	}
}
