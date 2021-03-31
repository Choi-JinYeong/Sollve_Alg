package Git_BJ_3190;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	static class Point {
		int x, y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;

		}
	}

	static class Apple {
		int x;
		int y;
		int isUsed;

		Apple(int x, int y, int isUsed) {
			this.x = x;
			this.y = y;
			this.isUsed = 0;
		}
	}

	static class s_cmd {
		int delay; // 시작시간 기준
		char curve;

		s_cmd(int d, char c) {
			this.delay = d;
			this.curve = c;
		}
	}

	public static int N, K;
	public static int map[][];
	public static int cmap[][];
	public static ArrayList<Point> snake;
	public static ArrayList<Apple> apples;
	public static ArrayList<s_cmd> s_cmds;
	public static int CurrentDirection; // 2,4,6,8
	public static int tempx, tempy;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		CurrentDirection = 6;
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());

		snake = new ArrayList<>();
		apples = new ArrayList<>();
		s_cmds = new ArrayList<>();
		for (int i = 0; i < K; i++) {
			String s = br.readLine();
			int x = Integer.parseInt(s.split(" ")[1]);
			int y = Integer.parseInt(s.split(" ")[0]);
			apples.add(new Apple(x, y, 0));

		}

		int t = Integer.parseInt(br.readLine());
		for (int i = 0; i < t; i++) {
			String s = br.readLine();
			int x = Integer.parseInt(s.split(" ")[0]);
			char y = s.split(" ")[1].charAt(0);
			s_cmds.add(new s_cmd(x, y));
		}
		snake.add(new Point(1, 1));
		solve();
		System.out.println(currentTime);
	}

	// 이동 -> 머리만 먼저 앞으로 이동
	// * 사과 없음 : 한칸씩 머리쪽으로 따라서 이동 -> 맨 마지막 칸이 비워져야 함
	// * 사과 있음 : 현재 위치에 추가 ( index 1에 add )
	// 업데이트는 전좌표 값 기준으로 차이를 계산해서 어디 방향으로 움직여야 하는지 도출

	public static int currentTime;
	public static int s_cmd_idx;

	public static void solve() {

		currentTime = 0;
		while (true) {
			currentTime++;
			move();

			boolean check1 = isINmap(snake.get(0).x, snake.get(0).y);
			boolean check2 = isNotComplictWithBody();

			if (check1 == true && check2 == true) {
				boolean check3 = checkApple();
				updateBody(check3);
			} else {
				break;
			}

			//checkSnakeStatus();

			boolean check = checkChangeDirection();
			if (check == true) {
				changeDirection();
			} else {

			}

		}
	}

	public static void changeDirection() {

		// L 이 왼쪽 D가 오른쪽
		switch (CurrentDirection) {
		case 2: // 하
			if (s_cmds.get(s_cmd_idx).curve == 'L') {
				CurrentDirection = 6;

			} else {
				CurrentDirection = 4;
			}

			break;
		case 4: // 좌
			if (s_cmds.get(s_cmd_idx).curve == 'L') {
				CurrentDirection = 2;

			} else {
				CurrentDirection = 8;
			}

			break;
		case 6: // 우
			if (s_cmds.get(s_cmd_idx).curve == 'L') {
				CurrentDirection = 8;

			} else {
				CurrentDirection = 2;
			}

			break;
		case 8: // 상
			if (s_cmds.get(s_cmd_idx).curve == 'L') {
				CurrentDirection = 4;

			} else {
				CurrentDirection = 6;
			}

			break;
		}

	}

	public static boolean checkChangeDirection() {
		for (int i = 0; i < s_cmds.size(); i++) {
			s_cmd s = s_cmds.get(i);
			if (s.delay == currentTime) {
				s_cmd_idx = i;
				return true;
			}

		}
		return false;
	}

	// 업데이트는 전좌표 값 기준으로 차이를 계산해서 어디 방향으로 움직여야 하는지 도출
	public static void move() {
		tempx = snake.get(0).x;
		tempy = snake.get(0).y;
		switch (CurrentDirection) {
		case 2: // 하
			snake.get(0).y++;

			break;
		case 4: // 좌
			snake.get(0).x--;
			break;
		case 6: // 우
			snake.get(0).x++;
			break;
		case 8: // 상
			snake.get(0).y--;
			break;
		}

	}

	public static boolean checkApple() {

		int sx = snake.get(0).x;
		int sy = snake.get(0).y;

		// 사과 있을 경우
		for (int i = 0; i < apples.size(); i++) {
			int ax = apples.get(i).x;
			int ay = apples.get(i).y;
			int isused = apples.get(i).isUsed;
			if (ax == sx && ay == sy && isused == 0) {
				apples.get(i).isUsed = 1;
				return true;
			}
		}
		return false;

	}

	public static void updateBody(boolean check) {

		if (check == true) { // 사과 있음
			snake.add(1, new Point(tempx, tempy));
		} else { // 사과 없음
			if (snake.size() >= 2) {
				snake.add(1, new Point(tempx, tempy));
				snake.remove(snake.size()-1);
			}
		}

	}

	public static void checkSnakeStatus() {

		for (int i = 0; i < snake.size(); i++) {
			//System.out.print(snake.get(i).x + "," + snake.get(i).y + "  ");
		}
		//System.out.println();
		for (int i = 0; i < apples.size(); i++) {
			//System.out.print(apples.get(i).x + "," + apples.get(i).y + " " + apples.get(i).isUsed + " / ");
		}
		//System.out.println();
	}

	public static boolean isINmap(int x, int y) {
		if (x < 1 || x > N || y < 1 || y > N)
			return false;
		else
			return true;
	}

	public static boolean isNotComplictWithBody() {
		
		for (int i = 0; i < snake.size(); i++) {
			for (int j = 0; j < snake.size(); j++) {
				if (i != j)
					if (snake.get(i).x == snake.get(j).x && snake.get(i).y == snake.get(j).y) {
						checkSnakeStatus();
						return false;
					}
						
			}
		}
		return true;
	}
}

