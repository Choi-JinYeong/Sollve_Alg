package Sol_BJ_17822;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	/**
	 * N 반지름 // M 개수
	 * N ==> y // M == x
	 * T 명령 갯수
	 */
	public static int N, M, T;
	/**
	 * round : 원판 배열
	 * round[1][1 ~ 4] : 맨 안쪽 1 ~ 4 북 동 서 남 순 
	 * round[2][x] : 그 다음 큰 원판 순
	 * ...
	 * 
	 * check : 인접한 수 찾을 때 위치 체크 용 배열
	 */
	public static int round[][];
	public static int check[][];
	public static int[] x, d, k;

	
	/** 
	 * 인접 위치 찾으려고 만든 배열
	 */
	public static int dx[] = { -1, 1, 0, 0 };
	public static int dy[] = { 0, 0, -1, 1 };

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] s1 = br.readLine().split(" ");

		N = Integer.parseInt(s1[0]);
		M = Integer.parseInt(s1[1]);
		T = Integer.parseInt(s1[2]);

		round = new int[N + 1][M + 1];
		check = new int[N + 1][M + 1];
		for (int i = 1; i <= N; i++) {
			String[] s = br.readLine().split(" ");
			for (int j = 1; j <= M; j++) {
				round[i][j] = Integer.parseInt(s[j - 1]);

			}
		}
		x = new int[T + 1];
		d = new int[T + 1];
		k = new int[T + 1];
		for (int i = 1; i <= T; i++) {
			String s2[] = br.readLine().split(" ");
			x[i] = Integer.parseInt(s2[0]);
			d[i] = Integer.parseInt(s2[1]);
			k[i] = Integer.parseInt(s2[2]);
		}

		solve();
	}

	public static void solve() {

		for (int i = 1; i <= T; i++) {
			int xVal = x[i];
			int kVal = k[i];
			int dVal = d[i];
			calcMultifle(xVal, dVal, kVal);
			int SameCnt = FindNearbySameValues();
			if (SameCnt > 0) {
				DeleteSameValues();
			} else {
				double avg = calcAverage();
				histogramRound(avg);
			}
			clear();
		}
		int result = calcResult();
		System.out.println(result);
	}

	/**
	 * 배수 계산 -> 배수에 맞추어 회전
	 * 회전할 때 반복 회전
	 */
	public static void calcMultifle(int x, int d, int k) {

		int cnt = 1;
		while (true) {
			int val = x * cnt;
			if (val > N)
				break;

			for (int i = 0; i < k; i++) {
				rotate(val, d);
			}
			cnt++;
		}
	}

	/**
	 * 원판 회전
	 * @param x 원판 위치
	 * @param r 시계/반시계 ( r == 1 반시계 r == 0  시계)
	 */
	public static void rotate(int x, int r) {
		if (r == 1) { // 반시계
			// 4 ->3 , 3 -> 2, 2->1, 1->4
			int temp = round[x][1];
			for (int i = 1; i < round[x].length - 1; i++) {
				round[x][i] = round[x][i + 1];
			}
			round[x][round[x].length - 1] = temp;
		} else { // 시계
			// 1 -> 2 , 2 -> 3, 3 -> 4, 4 -> 1
			int temp = round[x][round[x].length - 1];
			for (int i = round[x].length - 1; i >= 1; i--) {
				round[x][i] = round[x][i - 1];
			}
			round[x][1] = temp;
		}
	}

	/**
	 * 인접한 숫자 찾기
	 * 찾은뒤에 바로 원판에 계산하는것이 아니라 위치만 체크해두고 한번에 지움
	 * 인접한 숫자 개수를 리턴해서 분기점으로 활용(평균더하냐, 인접한 숫자 삭제하냐)
	 * @return 인접한 숫자 개수
	 */
	public static int FindNearbySameValues() {
		int cnt = 0;
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				int nowVal = round[i][j];
				for (int k = 0; k < dx.length; k++) {
					int mx = j + dx[k];
					int my = i + dy[k];
					
					// 원판이여서 확인시 넘어가더라도 반대쪽으로 변경
					if (mx > M)
						mx = 1;
					if (mx < 1)
						mx = M;
					if (isVaild(mx, my) && nowVal != 0) {
						int checkVal = round[my][mx];
						if (nowVal == checkVal) {
							check[i][j] = 1;
							check[my][mx] = 1;
							cnt++;
						}
					}

				}
			}
		}
		return cnt;
	}
	/**
	 * 인접한 숫자 삭제
	 */
	public static void DeleteSameValues() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				if (check[i][j] == 1)
					round[i][j] = 0;
			}
		}
	}

	/**
	 * 현재 원판 평균값 구함
	 * @return 원판 평균값
	 */
	public static double calcAverage() {
		double res = 0;
		double cnt = 0;
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				if (round[i][j] != 0) {
					res += round[i][j];
					cnt++;
				}
			}
		}
		return res / cnt;
	}

	/**
	 * 평균보다 낮으면 -1 , 평균보다 높으면 +1
	 * @param avg 현재 원판의 평균값
	 */
	public static void histogramRound(double avg) {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				if (round[i][j] != 0) {
					if (round[i][j] < avg)
						round[i][j]++;
					else if (round[i][j] > avg)
						round[i][j]--;
					else {
					}
				}
			}
		}
	}

	/**
	 * 인접 숫자 찾는 변수 초기화
	 */
	public static void clear() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				check[i][j] = 0;
			}
		}
	}

	/**
	 * 마지막 원판 전체 값 더함
	 * @return 원판 전체 더한 값
	 */
	public static int calcResult() {
		int res = 0;
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				res += round[i][j];
			}
		}

		return res;
	}

	/**
	 * 배열 경계 넘어가는거 체크
	 */
	public static boolean isVaild(int x, int y) {

		if (x < 1 || x > M || y < 1 || y > N)
			return false;
		else
			return true;
	}

	/**
	 * round 현재 상태 확인용
	 */
	public static void debugMap() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				System.out.print(round[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("---roundcheck---");
	}

	/**
	 * check 현재 상태 확인용
	 */
	public static void debugcheck() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				System.out.print(check[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("---debugcheck---");
	}
}
