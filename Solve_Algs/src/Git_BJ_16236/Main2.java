package Git_BJ_16236;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//https://www.acmicpc.net/problem/16236

class shark {
	int x, y, s;
	int m;
	int eatcnt = 0;

	public shark(int x, int y, int s) {
		this.x = x;
		this.y = y;
		this.s = s;
		m = 0;
	}

	public shark(int x, int y, int s, int m) {
		this.x = x;
		this.y = y;
		this.s = s;
		this.m = m;
	}
}

class feed {
	int x, y, size;
	int distance;

	public feed(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
}

public class Main2 {

	public static int map[][];
	public static int isVisit[][], isvisit[][];
	public static shark queue[];
	public static ArrayList<feed> flist;
	public static int dx[] = { -1, 1, 0, 0 };
	public static int dy[] = { 0, 0, 1, -1 };

	public static shark s;
	public static int time, N, front, rear;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		map = new int[N + 1][N + 1];
		isVisit = new int[N + 1][N + 1];
		isvisit = new int[N + 1][N + 1];
		queue = new shark[(N + 1) * (N + 1) * 4 * 2];
		flist = new ArrayList<>();
		time = 0;
		for (int i = 1; i <= N; i++) {
			String[] s1 = br.readLine().split(" ");
			for (int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(s1[j - 1]);
				if (map[i][j] == 9)
					s = new shark(j, i, 2);
				if (map[i][j] >= 1 && map[i][j] <= 6) {
					flist.add(new feed(j, i, map[i][j]));
				}
				isVisit[i][j] = -1;
			}
		}
		solve();
		System.out.println(time);
	}

	public static void solve() {

		while (true) {
			int a = findEatPossible();
			if (a <= 0)
				break;
			feed f = findNearestFish();
			if (f == null)
				break;
			moveToNearestFish(f);
			clear();
		}
	}

	public static int findEatPossible() {
		int cnt = 0;
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (map[i][j] >= 1 && map[i][j] <= 6 && map[i][j] < s.s) // 이상하긴한데 가는 길도 있는지 확인해야 되는거 아닌가.
					cnt++;

				if (cnt >= 1) {
					return cnt;
				}
			}
		}
		return -1;
	}

	public static feed findNearestFish() {
		feed feed = null;
		s.m = 0;
		queue[rear++] = s;
		isVisit[s.y][s.x] = 0;

		while (front != rear) {
			shark ts = queue[front++];
			int x = ts.x;
			int y = ts.y;
			int cnt = ts.m;
			isVisit[y][x] = cnt;
			isvisit[y][x] = 1;
			for (int i = 0; i < dx.length; i++) {
				int tx = x + dx[i];
				int ty = y + dy[i];
				if (isVaild(tx, ty) == true && isvisit[ty][tx] == 0 && map[ty][tx] <= s.s) {
					try {
						isvisit[ty][tx]= 1; // 넘어가기 전에 체크해줘야됌 이거 때문에 40분 까먹음
						queue[rear++] = new shark(tx, ty, 2, cnt + 1);
					} catch (Exception e) {
						e.printStackTrace();

						printMap();
						return null;
					}
				}
			}
		}

		int cnt = 0;
		ArrayList<feed> tempFeed = new ArrayList<>();
		for (int i = 0; i < flist.size(); i++) {
			feed f = flist.get(i);
			int x = f.x;
			int y = f.y;

			if (f.size < s.s && isVisit[y][x] >= 1) {
				f.distance = isVisit[y][x];
				// 3,2 1,4 3-2 2, 4-2 2 == 4
				// 3,2 4,1 4-3 1, 2-1 2
				tempFeed.add(f);
				cnt++;
			}
		}
		if (tempFeed.size() == 0 || cnt == 0)
			return null;
		else {
			Comparator<feed> comparator = new Comparator<feed>() {

				@Override
				public int compare(feed o1, feed o2) {
					// TODO Auto-generated method stub
					// 많으면 가장 위에 있는 물고기
					if (o1.distance < o2.distance) {
						return -1;
					} else if (o1.distance == o2.distance) {
						if (o1.y < o2.y) { //
							return -1;
						} else if (o1.y == o2.y) {
							if (o1.x < o2.x)
								return -1;
							else if (o1.x == o2.x) {
								return 0;

							} else {
								return 1;
							}

						} else {
							return 1;
						}
					} else {
						return 1;
					}

				}
			};

			Collections.sort(tempFeed, comparator);
			feed = tempFeed.get(0);
		}
		return feed;
	}

	public static void moveToNearestFish(feed ts) {
		map[s.y][s.x] = 0;
		s.x = ts.x;
		s.y = ts.y;
		s.eatcnt = s.eatcnt + 1;
		if (s.eatcnt == s.s) { // 먹은 횟수가 자기 크기 만큼 되면 사이즈 1커지고 먹이 횟수 초기화
			s.eatcnt = 0;
			s.s = s.s + 1;
		}
		map[ts.y][ts.x] = 9;
		for (int i = 0; i < flist.size(); i++) {
			feed f = flist.get(i);
			if (f.x == ts.x && f.y == ts.y) {
				flist.remove(i);
				break;
			}
		}
		time += ts.distance;
	}

	public static boolean isVaild(int x, int y) { // 맵 밖으로 나가는지 확인
		if (x < 1 || x > N || y < 1 || y > N) {
			return false;
		} else
			return true;
	}

	public static void clear() {

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				isVisit[i][j] = -1;
				isvisit[i][j] = 0;
			}
		}
		front = 0;
		rear = 0;
	}

	public static void printVisit() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				System.out.print(isvisit[i][j] + " ");
			}
			System.out.println();
		}
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
