package Git_BJ_14889;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

// https://www.acmicpc.net/problem/14889

// 1시간 5분 소요
 
class conn {
	int num1, num2;
	int v;

	public conn(int n, int m, int v) {
		this.num1 = n;
		this.num2 = m;
		this.v = v;
	}
}

public class Main {

	public static int N;
	public static int output[];
	public static int map[][];
	public static ArrayList<conn> c;
	public static conn list[];
	public static int isVisit[], numlist[];
	public static int MIN;
	public static HashMap<String, Integer> hash;
	public static ArrayList<Integer> home;
	public static ArrayList<Integer> away;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		hash = new HashMap<>();
		map = new int[N + 1][N + 1];
		list = new conn[N + 1];
		numlist = new int[N + 1];
		isVisit = new int[N + 1];
		MIN = Integer.MAX_VALUE;
		output = new int[N / 2];
		c = new ArrayList<>();

		for (int i = 1; i <= N; i++) {
			String[] s1 = br.readLine().split(" ");
			for (int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(s1[j - 1]);
				if (i != j)
					c.add(new conn(i, j, map[i][j]));
			}
		}
		for (int i = 1; i <= N; i++) {
			list[i] = c.get(i - 1);
			isVisit[i] = 0;
			numlist[i] = i;
		}
		// printMap();

		solve();

		System.out.println(MIN);
	}



	public static void comb(int list[], int isVisit[], int output[], int outputidx, int start, int end, int rec) {
		if (rec == 0) {
			home = new ArrayList<>();
			away = new ArrayList<>();
			int HOMEVALUE = 0;
			int AWAYVALUE = 0;
			for (int i = 1; i <= N; i++) {
				if (isVisit[i] == 1) {

					home.add(i);
				} else {
					away.add(i);
				}

			}

			for (int i = 0; i < home.size(); i++) {
				for (int j = 0; j < home.size(); j++) {
					if (i != j) {
						int hx = home.get(i);
						int hy = home.get(j);
						int ax = away.get(i);
						int ay = away.get(j);
						HOMEVALUE += map[hy][hx];
						AWAYVALUE += map[ay][ax];
					}
				}
			}
			MIN = Math.min(MIN, Math.abs(HOMEVALUE - AWAYVALUE));
			home = new ArrayList<>();
			away = new ArrayList<>();
			
			return;
		} else {
			for (int i = start; i <= end; i++) {
				if (isVisit[i] == 0) {
					isVisit[i] = 1;
					// 현재 기준값을 넘기면 중복되는 값 없앵ㄹ 수 있음
					// 중복되는거 찾으려면 start 를 넣고
					// 아니면 현재 값을 넣어라
					comb(list, isVisit, output, 0, i, end, rec - 1);
					isVisit[i] = 0;
				}
			}
		}
	}



	public static void solve() {
		// comb(list, isVisit, 1, N, (double) (N / 4));
		comb(numlist, isVisit, output, 0, 1, N, N / 2);
		
	}

	public static void printMap() {
		for (int i = 1; i <= N; i++) {

			for (int j = 1; j <= N; j++) {
				System.out.print(map[i][j] + " ");

			}
			System.out.println();
		}
	}
}
