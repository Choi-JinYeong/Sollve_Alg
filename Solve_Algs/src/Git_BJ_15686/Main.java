package Git_BJ_15686;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

//https://www.acmicpc.net/problem/15686

class p {
	int x, y, t;

	public p(int x, int y, int t) {
		this.x = x;
		this.y = y;
		this.t = t;
	}
}

public class Main {

	public static int map[][], isvisit[], cmap[][]; // 0 빈칸 1 집 2 치킨집
	public static int N, M;
	public static int MIN;
	public static int ns[];
	public static ArrayList<p> chicken;
	public static ArrayList<p> house;
	public static int[] MINS;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] s1 = br.readLine().split(" ");

		N = Integer.parseInt(s1[0]);
		M = Integer.parseInt(s1[1]); // 치킨집 개수
		map = new int[N + 1][N + 1];
		cmap = new int[N + 1][N + 1];

		chicken = new ArrayList<p>();
		house = new ArrayList<p>();

		for (int i = 1; i <= N; i++) {
			String[] s2 = br.readLine().split(" ");
			for (int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(s2[j - 1]);

				if (map[i][j] == 1) {
					house.add(new p(j, i, 1));
				}
				if (map[i][j] == 2) {
					chicken.add(new p(j, i, 2));
				}
			}
		}

		solve();
	}

	public static void solve() {
		ns = new int[chicken.size()];
		MIN = Integer.MAX_VALUE;
		isvisit = new int[chicken.size()];
		MINS = new int[house.size()];
		for (int i = 0; i < chicken.size(); i++) { //2차원 위치에 대한 1차원 조합으로 변경하기 위하여 추가
			ns[i] = i;
		}
		for (int j = 0; j < house.size(); j++) { // 각 집 위치에서 최소 거리에 있는 치킨집 거리 저장하려고 쓰는 변수
			MINS[j] = Integer.MAX_VALUE;
		}
		comb(ns, isvisit, 0, ns.length - 1, M); // 치킨집 조합 

		System.out.println(MIN);
	}

	public static void comb(int[] n, int isvisit[], int start, int end, int rec) {
		if (rec == 0) {
			ArrayList<p> selectedChicken = new ArrayList<>();
			for (int i = 0; i < isvisit.length; i++) {
				if (isvisit[i] == 1) {
					selectedChicken.add(chicken.get(i));
				}
			}

			for (int i = 0; i < house.size(); i++) {
				p home = house.get(i);
				for (int j = 0; j < selectedChicken.size(); j++) {
					p store = selectedChicken.get(j);
					int distance = Math.abs(home.x - store.x) + Math.abs(home.y - store.y);
					int nowMIN = MINS[i];
					if (nowMIN > distance)
						MINS[i] = distance;
				}
			}

			int val = 0;
			for (int i = 0; i < MINS.length; i++) { // 각 집위치에서 치킨집 까지 최소 거리 도출
				val += MINS[i];
			} 
			MIN = Math.min(val, MIN); // 최소값 비교
			clear();
		} else {
			for (int i = start; i <= end; i++) {
				if (isvisit[i] == 0) {
					isvisit[i] = 1;
					comb(n, isvisit, i, end, rec - 1);
					isvisit[i] = 0;
				}
			}

		}
	}

	public static void clear() {
		for (int i = 0; i < MINS.length; i++) {
			MINS[i] = Integer.MAX_VALUE;
		}
	}

}
