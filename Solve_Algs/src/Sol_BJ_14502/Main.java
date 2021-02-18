package Sol_BJ_14502;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	public static int Y, X;

	// 0 빈칸, 1 벽, 2 바이러스
	public static int map[][], cmap[][];
	public static int isVisit[][], spreadVisit[][];
	// 2차원 배열의 1차원 배열로 변환하여 조합 뽑아내기 위한 변수
	public static ArrayList<point> viruses;
	public static int MAX;
	public static int isUseArray[];
	public static ArrayList<point> zeroList;

	// 상 하 좌 우 탐색
	public static int[] dx = { -1, 1, 0, 0 };
	public static int[] dy = { 0, 0, -1, 1 };

	public static point[] queue;
	public static int front, rear;

	public static point spreadps[];
	public static int combcnt = 0;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s[] = br.readLine().split(" ");
		Y = Integer.parseInt(s[0]);
		X = Integer.parseInt(s[1]);

		spreadps = new point[3];
		map = new int[Y + 1][X + 1];
		cmap = new int[Y + 1][X + 1];
		isVisit = new int[Y + 1][X + 1];
		spreadVisit = new int[Y + 1][X + 1];
		viruses = new ArrayList<>();
		zeroList = new ArrayList<>();
		queue = new point[(Y + 1) * (X + 1) * 4 * 1000];
		for (int i = 1; i <= Y; i++) {
			String ss[] = br.readLine().split(" ");
			for (int j = 1; j <= X; j++) {
				map[i][j] = Integer.parseInt(ss[j - 1]);
				cmap[i][j] = map[i][j];
				if (map[i][j] == 0)
					zeroList.add(new point(j, i));
				if (map[i][j] == 2)
					viruses.add(new point(j, i));
			}
		}
		isUseArray = new int[zeroList.size()];
		long start = System.currentTimeMillis();
		solve();
		long end = System.currentTimeMillis();

	}

	public static void solve() {
		MAX = 0;
		isVisit = new int[Y + 1][X + 1];
		combination(zeroList, isUseArray, 0, 3);
		System.out.println(MAX);
	}

	/**
	 * 바이러스 퍼트림
	 * @param ps 벽 세운 3곳 좌표
	 */
	public static void spreadVirus(point[] ps) {
		clear();
		buildWall(ps);
		//바이러스 위치에서부터 BFS 시작
		for (int i = 0; i < viruses.size(); i++) {
			point vp = viruses.get(i);
			int vx = vp.x;
			int vy = vp.y;
			queue[rear++] = new point(vx, vy);
			spread();
		}
		int cnt = calc();
	}

	/**
	 * 지도에 벽 세우기
	 * @param ps 벽 세운 3곳 좌표
	 */
	public static void buildWall(point[] ps) {
		for (int i = 0; i < ps.length; i++) {
			int x = ps[i].x;
			int y = ps[i].y;
			cmap[y][x] = 1;
		}
	}

	/**
	 * 바이러스 퍼트리기
	 */
	public static void spread() {

		while (front != rear) {
			point p = queue[front++];
			int vx = p.x;
			int vy = p.y;
			spreadVisit[vy][vx] = 1;
			for (int j = 0; j < 4; j++) {
				int vdx = vx + dx[j];
				int vdy = vy + dy[j];
				if (isVaild(vdx, vdy) == 1 && cmap[vdy][vdx] == 0 && spreadVisit[vdy][vdx] == 0) { // 움직임
					spreadVisit[vdy][vdx] = 1; // 방문
					queue[rear++] = new point(vdx, vdy);
					cmap[vdy][vdx] = 2;
				}
			}
		}
	}

	/**
	 * 빈 칸 계산 및 최대값 비교
	 * 
	 * @return 빈칸 갯수 반환
	 */
	public static int calc() {
		int cnt = 0;
		for (int i = 1; i <= Y; i++) {
			for (int j = 1; j <= X; j++) {
				if (cmap[i][j] == 0) {
					cnt++;
				}
			}
		}
		if (MAX < cnt)
			MAX = cnt;
		return cnt;
	}

	/**
	 * 벽 세울 수 있는 위치에 대한 조합
	 * @param cmap 0 x,y 좌표
	 * @param isVisit 방문 여부
	 * @param idx 시작 인덱스 처음부터
	 * @param rec 반복여부 여기선 3
	 */
	public static void combination(ArrayList<point> cmap, int[] isVisit, int idx, int rec) {
		if (rec == 0) {

			int psidx = 0;
			for (int i = 0; i < isVisit.length; i++) {
				if (isVisit[i] == 1) {
					spreadps[psidx] = new point(zeroList.get(i).x, zeroList.get(i).y);
					point p = new point(zeroList.get(i).x, zeroList.get(i).y);
					psidx++;
				}
			}
			spreadVirus(spreadps);
		} else {
			for (int i = idx; i < cmap.size(); i++) {
				if (isVisit[i] == 0) {
					isVisit[i] = 1;
					combination(cmap, isVisit, idx, rec - 1);
					isVisit[i] = 0;
				}

			}
		}
	}

	
	/**
	 * BFS 시 범위 안인지 확인
	 * @param x x값
	 * @param y y값
	 * @return 맞으면 1 틀리면 0
	 */
	public static int isVaild(int x, int y) {
		if (x < 1 || x > X || y < 1 || y > Y)
			return 0;
		else
			return 1;

	}

	/**
	 * 디버깅용
	 */
	public static void printMap() {
		for (int i = 1; i <= Y; i++) {

			for (int j = 1; j <= X; j++) {
				System.out.print(cmap[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * 변수 환경 초기화
	 */
	public static void clear() {
		for (int i = 1; i <= Y; i++) {
			for (int j = 1; j <= X; j++) {
				cmap[i][j] = map[i][j];
				spreadVisit[i][j] = 0;
			}
		}
		front = rear = 0;
	}

	/** 
	 * 좌표 관리 클래스
	 * @author Choi
	 *
	 */
	static class point {
		int x, y;

		public point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public point() {
		}
	}
}
