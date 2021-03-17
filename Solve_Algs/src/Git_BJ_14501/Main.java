package Git_BJ_14501;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Main {

	public static int N;
	public static int[][] schedules;

	public static int MAX;
	public static int isVisit[];

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// BufferedReader br = new BufferedReader(new
		// FileReader("./src/BJ_14501/TestCase_BJ_14501.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		MAX = 0;
		// int T = Integer.parseInt(br.readLine());

		// for (int a = 0; a < T; a++) {
		N = Integer.parseInt(br.readLine());

		schedules = new int[N + 1][2];
		isVisit = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			String[] s = br.readLine().split(" ");
			schedules[i][0] = Integer.parseInt(s[0]);
			schedules[i][1] = Integer.parseInt(s[1]);
			isVisit[i] = 0;
		}

		solve();
		// }

	}

	// 1 -> 3 4 5
	// 1 -> 4 5
	// 1 -> 5
	// 3 -> 4,5,
	// 3 -> 5,
	// 3 ->
	// 3 ->
	// 4 -> 5,
	// 4 ->
	// 4 ->
	// 5 ->
	// 1부터 다 확인 하자
	public static void solve() {

		for (int i = 1; i <= N; i++) {
			// System.out.println("===" + i + " start "+ "===");
			start(i, schedules[i][0], schedules[i][1]);
			for (int j = 1; j <= N; j++) {
				isVisit[j] = 0;
			}
		}
		System.out.println(MAX);
	}

	public static void start(int iabc, int time, int now) {
		int initIdx = iabc;
		int idx = iabc;
		int val = now;
		int times = time;
		
		if (idx <= N) {
			int temptime = idx;
			if (temptime <= N) {
				if (temptime + schedules[temptime][0]-1 <= N && isVisit[temptime] == 0) {
					if (MAX < val)
						MAX = val;
				}
			}
		}
		isVisit[idx] = 1;
		for (int i = idx + time; i <= N; i++) {
			if (i <= N) {
				int ttime = schedules[i][0];
				int temptime = i;
				if (temptime <= N) {
					if (temptime + schedules[temptime][0] - 1 <= N && isVisit[temptime] == 0) {
						// System.out.print(idx + " : " + temptime + "/" + val + " ");
						isVisit[temptime] = 1;
						idx = i;
						val += schedules[idx][1];

						// System.out.println(idx + " : " + temptime + "/" + val + " ");
						if (MAX < val)
							MAX = val;
						start(idx, schedules[idx][0], val);
						val -= schedules[idx][1];
						isVisit[temptime] = 0;

					} else {

						// System.out.println(i + " " + "VAL : " + val);
					}
				}
				//
			}
			// System.out.println("Finish");

		}

	}

	public static int add(int i) {
		//
		int val = schedules[i][1];
		int time = schedules[i][0];

		return val;
	}

}
