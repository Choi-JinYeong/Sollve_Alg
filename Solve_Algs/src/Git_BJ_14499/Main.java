package Git_BJ_14499;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//https://www.acmicpc.net/problem/14499

/* 1시간 30분 소요
* 
*/

public class Main {

	public static int dice[][];
	// 0 뒤 0
	// 좌 상 우
	// 앞
	// 하

	public static int N, M, X, Y, K;
	public static int map[][];
	public static int Ks[];

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		dice = new int[4][5];
		String[] s1 = br.readLine().split(" ");

		N = Integer.parseInt(s1[0]); // 세로
		M = Integer.parseInt(s1[1]); // 가로
		X = Integer.parseInt(s1[3]) + 1; // x 0 < x < N-1 세로 기준
		Y = Integer.parseInt(s1[2]) + 1; // y 0 < y < M-1 가로 기준
		// 그래서 앞뒤 순서 바꿔줘야돼 우리가 아는대로 하려면
		K = Integer.parseInt(s1[4]); // 명령 갯수

		map = new int[N + 1][M + 1];
		Ks = new int[K];
		for (int i = 1; i <= N; i++) {
			String[] s2 = br.readLine().split(" ");
			for (int j = 1; j <= M; j++) {
				map[i][j] = Integer.parseInt(s2[j - 1]);
			}
		}

		String[] s3 = br.readLine().split(" ");
		for (int i = 0; i < K; i++) {
			Ks[i] = Integer.parseInt(s3[i]);
		}

		//printMap();

		solve();
	}

	public static void move(int K) {

//		public static int dice[][];
//		// 0 뒤 0
//		// 좌 상 우
//		// 앞
//		// 하
//		
		int temp32 = dice[3][2]; // 앞
		int temp22 = dice[2][2]; // 상
		int temp12 = dice[1][2]; // 뒤
		int temp21 = dice[2][1]; // 좌
		int temp23 = dice[2][3]; // 우
		int temp24 = dice[2][4]; // 바닥
		switch (K) {
		case 1: // 동쪽

			dice[2][4] = temp23; // 바닥 = 우
			dice[2][3] = temp22; // 우 = 상
			dice[2][2] = temp21; // 상 = 좌
			dice[2][1] = temp24; // 좌 = 바닥
			// 앞 뒤 그대로

			X += 1;

			break;
		case 2: // 서족
			dice[2][4] = temp21; // 바닥 = 좌
			dice[2][1] = temp22; // 좌 = 상
			dice[2][2] = temp23; // 상 = 우
			dice[2][3] = temp24; // 우 = 바닥
			// 앞 뒤 그대로
			X -= 1;

			break;
		case 3: // 북쪽
			dice[2][4] = temp12;// 바닥 = 뒤
			dice[1][2] = temp22; // 뒤 = 상
			dice[2][2] = temp32; // 상 = 앞
			dice[3][2] = temp24; // 앞 = 바닥
			// 좌 우 그대로
			Y -= 1;

			break;
		case 4: // 남쪽
			dice[2][4] = temp32;// 바닥 = 앞
			dice[3][2] = temp22; // 앞 = 상
			dice[2][2] = temp12; // 상 = 뒤
			dice[1][2] = temp24; // 뒤 = 바닥
			// 좌 우 그대로
			Y += 1;
			break;
		}
		//System.out.println(X + " , " +Y);
		if (map[Y][X] == 0) { // 이동한 칸에 쓰여진 수가 0이면, 주사위 바닥면에 쓰여진 수가 칸에 복사
			map[Y][X] = dice[2][4];

		} else { // 0이아닌경우, 칸에 쓰여진 수가 주사위 바닥면으로 복사, 칸은 0
			dice[2][4] = map[Y][X];
			map[Y][X] = 0;
		}
	}

	public static void solve() {
		for (int i = 0; i < Ks.length; i++) {
			if (isVaild(X, Y, Ks[i]) == true) {
				move(Ks[i]);
				System.out.println(dice[2][2]);
			} else {
			}
		}

	}

	public static boolean isVaild(int tx, int ty, int k) {
		int x = tx;
		int y = ty;

		switch (k) {
		case 1: // 동
			x += 1;
			break;
		case 2: // 서
			x -= 1;
			break;
		case 3: // 북
			y -= 1;
			break;
		case 4: // 남
			y += 1;
			break;

		}
		
		//System.out.println("isVaild : " + x + " , " + y + " . " + k);
		if (x < 1 || x > M || y < 1 || y > N) {
			return false;
		} else {
			return true;
		}
	}

	public static void printMap() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("======================================");
	}
}
