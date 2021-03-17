package Git_BJ_14503;

import java.io.BufferedReader;


import java.io.InputStreamReader;
// https://www.acmicpc.net/problem/14503

/* 1�ð� 40�� �ҿ�
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
			if (isCheckLeftSide == true) { // û�� ����
				rotate_1();
				move();

			} else {
				int isCheck4Side = check4Side();
				if (isCheck4Side == 2)// ����
					break;
				if (isCheck4Side == 1)// ������ 2������ ���ư�
					backward();
				if (isCheck4Side == 0)
					rotate_2(); // �� �������� ȸ���ϰ� �� ���ư�
			}
			// û�Ұ� �Ұ����� --> �� �������� ȸ���ϰ� 2������ ���ư�.
			// 1. �׹��� û�� �Ǿ��ų� �� -> �ٶ󺸴¹��� �����ϰ� ��ĭ ���� 2��
			// 2. �׹��� û�� + �� + ���� �����̸� �۵� ����

		}

	}

	public static void rotate_2() {
		switch (robot.d) {
		case 0: // ������ �ٶ󺸰� ������ ������ ����
			robot.d = 3;
			break;
		case 1: // ������ �ٶ󺸰� ������ ������ ����
			robot.d = 0;
			break;
		case 2: // ������ �ٶ󺸰� ������ ������ ����
			robot.d = 1;
			break;
		case 3: // ������ �ٶ󺸰� ������ ������ ����
			robot.d = 2;
			break;
		}
	}

	public static void backward() {
		switch (robot.d) {

		case 0: // ���ʹٶ󺸰� ������ ������ ��������
			robot.y += 1;
			break;
		case 1: // ���ʹٶ󺸰� ������ ������ ��������
			robot.x -= 1;
			break;
		case 2:// ���ʹٶ󺸰� ������ ������ ��������
			robot.y -= 1;
			break;
		case 3: // ���ʹٶ󺸰� ������ ������ ���U����
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

		// �� ���� û�ҳ� ���̾�
		if (cnt == 4) {
			// ������ ������ üũ
			int tx = x;
			int ty = y;
			switch (d) {
			case 0: // ���ʹٶ󺸰� ������ ������ ��������
				ty += 1;
				break;
			case 1: // ���ʹٶ󺸰� ������ ������ ��������
				tx -= 1;
				break;
			case 2:// ���ʹٶ󺸰� ������ ������ ��������
				ty -= 1;
				break;
			case 3: // ���ʹٶ󺸰� ������ ������ ���U����
				tx += 1;
				break;
			}
			if (tx < 1 || tx > M || ty < 1 || ty > N) { // �̰͵� ��
				return 2;
			} else {
				if (map[ty][tx] == 1) {// �ٵ� �����Ϸ��� �ߴ��� ���̾�
					return 2; // ����
				}
				return 1; // 1�̸� �ٶ󺸴� ���� ������ä ��ĭ �����ϰ� 2������ ���ư�
			}
		}
		return 0;
		// 0�̸� 2������ ���ư�
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
		case 0: // ������ �ٶ󺸰� ������ ������ ����
			x -= 1;
			break;
		case 1: // ������ �ٶ󺸰� ������ ������ ����
			y -= 1;
			break;
		case 2: // ������ �ٶ󺸰� ������ ������ ����
			x += 1;
			break;
		case 3: // ������ �ٶ󺸰� ������ ������ ����
			y += 1;
			break;
		}

		if (map[y][x] == 0 && isCleaned[y][x] == 0) { // û���ؾ��ϴ� ��Ȳ
			return true;
		}

		return false;

	}

	public static void rotate_1() {
		switch (robot.d) {
		case 0: // ������ �ٶ󺸰� ������ ������ ����
			robot.d = 3;
			break;
		case 1: // ������ �ٶ󺸰� ������ ������ ����
			robot.d = 0;
			break;
		case 2: // ������ �ٶ󺸰� ������ ������ ����
			robot.d = 1;
			break;
		case 3: // ������ �ٶ󺸰� ������ ������ ����
			robot.d = 2;
			break;
		}
	}

	public static void move() {
		switch (robot.d) {
		case 0: // ����
			robot.y -= 1;
			break;
		case 1: // ����
			robot.x += 1;
			break;
		case 2: // ����
			robot.y += 1;
			break;
		case 3: // ����
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
