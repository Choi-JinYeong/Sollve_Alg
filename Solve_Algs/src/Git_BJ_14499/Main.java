package Git_BJ_14499;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//https://www.acmicpc.net/problem/14499

/* 1�ð� 30�� �ҿ�
* 
*/

public class Main {

	public static int dice[][];
	// 0 �� 0
	// �� �� ��
	// ��
	// ��

	public static int N, M, X, Y, K;
	public static int map[][];
	public static int Ks[];

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		dice = new int[4][5];
		String[] s1 = br.readLine().split(" ");

		N = Integer.parseInt(s1[0]); // ����
		M = Integer.parseInt(s1[1]); // ����
		X = Integer.parseInt(s1[3]) + 1; // x 0 < x < N-1 ���� ����
		Y = Integer.parseInt(s1[2]) + 1; // y 0 < y < M-1 ���� ����
		// �׷��� �յ� ���� �ٲ���ߵ� �츮�� �ƴ´�� �Ϸ���
		K = Integer.parseInt(s1[4]); // ��� ����

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
//		// 0 �� 0
//		// �� �� ��
//		// ��
//		// ��
//		
		int temp32 = dice[3][2]; // ��
		int temp22 = dice[2][2]; // ��
		int temp12 = dice[1][2]; // ��
		int temp21 = dice[2][1]; // ��
		int temp23 = dice[2][3]; // ��
		int temp24 = dice[2][4]; // �ٴ�
		switch (K) {
		case 1: // ����

			dice[2][4] = temp23; // �ٴ� = ��
			dice[2][3] = temp22; // �� = ��
			dice[2][2] = temp21; // �� = ��
			dice[2][1] = temp24; // �� = �ٴ�
			// �� �� �״��

			X += 1;

			break;
		case 2: // ����
			dice[2][4] = temp21; // �ٴ� = ��
			dice[2][1] = temp22; // �� = ��
			dice[2][2] = temp23; // �� = ��
			dice[2][3] = temp24; // �� = �ٴ�
			// �� �� �״��
			X -= 1;

			break;
		case 3: // ����
			dice[2][4] = temp12;// �ٴ� = ��
			dice[1][2] = temp22; // �� = ��
			dice[2][2] = temp32; // �� = ��
			dice[3][2] = temp24; // �� = �ٴ�
			// �� �� �״��
			Y -= 1;

			break;
		case 4: // ����
			dice[2][4] = temp32;// �ٴ� = ��
			dice[3][2] = temp22; // �� = ��
			dice[2][2] = temp12; // �� = ��
			dice[1][2] = temp24; // �� = �ٴ�
			// �� �� �״��
			Y += 1;
			break;
		}
		//System.out.println(X + " , " +Y);
		if (map[Y][X] == 0) { // �̵��� ĭ�� ������ ���� 0�̸�, �ֻ��� �ٴڸ鿡 ������ ���� ĭ�� ����
			map[Y][X] = dice[2][4];

		} else { // 0�̾ƴѰ��, ĭ�� ������ ���� �ֻ��� �ٴڸ����� ����, ĭ�� 0
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
		case 1: // ��
			x += 1;
			break;
		case 2: // ��
			x -= 1;
			break;
		case 3: // ��
			y -= 1;
			break;
		case 4: // ��
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
