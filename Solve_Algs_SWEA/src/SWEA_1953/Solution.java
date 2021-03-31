package SWEA_1953;
import java.util.ArrayList;
import java.util.Scanner;

class po {
	int x;
	int y;

	public po(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class Solution {

	public static int T, N, M;
	public static int[][] map, po, isVisit;
	public static int sn, sm, L;
	public static po Queue[];
	public static int front, rear;

	// 상 하 좌 우
	public static int[] mx = { 0, 0, -1, 1 };
	public static int[] my = { -1, 1, 0, 0 };
	public static ArrayList<ArrayList<Integer>> list;
	public static String[] lists;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);

		T = Integer.parseInt(sc.nextLine());

		for (int i = 0; i <= 7; i++) {
			list = new <Integer>ArrayList();
		}
//		list.add(1).add(0);
//		list.add(1).add(1);
//		list.add(1).add(2);
//		list.add(1).add(3);
		lists = new String[8];
		lists[1] = "0123";
		lists[2] = "01";
		lists[3] = "23";
		lists[4] = "03";
		lists[5] = "13";
		lists[6] = "12";
		lists[7] = "02";

		for (int test = 1; test <= T; test++) {

			String[] input = sc.nextLine().split(" ");
			N = Integer.parseInt(input[0]);
			M = Integer.parseInt(input[1]);

			Queue = new po[N * M * 4];
			front = 0;

			map = new int[N][M];
			po = new int[N][M];
			isVisit = new int[N][M];
			sn = Integer.parseInt(input[2]);
			sm = Integer.parseInt(input[3]);
			L = Integer.parseInt(input[4]);

			Queue[rear++] = new po(sm, sn);

			for (int i = 0; i < N; i++) {
				String[] input1 = sc.nextLine().split(" ");
				for (int j = 0; j < M; j++) {
					map[i][j] = Integer.parseInt(input1[j]);
				}
			}

			// print(0);
//			System.out.println();
			solve();
//			System.out.println();
//			print(0);
//			System.out.println();
			int a = check();
			System.out.println("#" + test + " " + a);
			front = 0;
			rear = 0;
		}
	}

	public static void solve() {

		for (int i = 0; i < L; i++) {
			// 파이프 -> 이동할 수 있는 곳 계산

			int s = front;
			int e = rear;
			//System.out.println((i + 1) + " : " + s + " / " + e);
			for (int j = s; j < e; j++) {
				if (i == 0) {

					isVisit[sn][sm] = 1;
				} else {
					int x = Queue[front].x;
					int y = Queue[front].y;
					front++;

					if (map[y][x] != 0) {
						String pipe = lists[map[y][x]];
						int pleng = pipe.length();
						int pidx = 0;
						for (int k = 0; k < pleng; k++) {
							int m = Integer.parseInt(pipe.charAt(k) + "");
							int dx = x + mx[m];
							int dy = y + my[m];

							if (isVaild(dx, dy) == true && isConnect(x,y,m,dx,dy)) {
								// 이동 가능
									isVisit[dy][dx] = 1;
									Queue[rear++] = new po(dx, dy);
								}

							
						}
					}
				}
			}

			//System.out.println(i + 1);
			// check();
		}

	}

	public static int check() {
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (isVisit[i][j] == 1) {
					cnt++;
				}
				
			}
		
		}
		return cnt;
	}
	
	public static void isVisitPrint() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				
				System.out.print(isVisit[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void print(int a) {
		if (a == 0) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					System.out.print(map[i][j] + " ");
				}
				System.out.println();
			}
		}
	}

	public static boolean isVaild(int x, int y) {

		if (x < 0 || x > M - 1 || y < 0 || y > N - 1 || isVisit[y][x] == 1 || map[y][x] < 1)
			return false;
		else
			return true;

	}
	public static boolean isConnect(int x,int y, int di, int dx, int dy) {
		String cPipe = lists[map[y][x]];
		String nPipe = lists[map[dy][dx]];
		// cPipe == 01
		// nPipe == 03;
		
		// di == 1 -> 0있나 확인 0이면 1있나 확인
		// di == 2(좌) 면 우 있나 확인, 3 이면 2 있나 확인
		
		if(di == 1) // 아래로 내려가는중
		{
			for(int i = 0 ; i < nPipe.length(); i++) {
				if(nPipe.charAt(i) == '0')
					return true;
			}
		}else if(di == 0){
			for(int i = 0 ; i < nPipe.length(); i++) {
				if(nPipe.charAt(i) == '1')
					return true;
			}
			
		}else if(di == 2) {
			for(int i = 0 ; i < nPipe.length(); i++) {
				if(nPipe.charAt(i) == '3')
					return true;
			}
		}else if(di == 3) {
			for(int i = 0 ; i < nPipe.length(); i++) {
				if(nPipe.charAt(i) == '2')
					return true;
			}
		}
		
		return false;
		
		
		
	}

}
