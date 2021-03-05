package BJ_12865;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main2 {

	public static int N, K;// 물품 수
	public static ArrayList<things> t;
	public static int[] isVisit;
	public static int max, cW, cV;
	public static int[][] arr;
	public static int[][] dp;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		K = Integer.parseInt(s[1]);
		t = new ArrayList<>();
		max = -1;
		cW = cV = 0;
		isVisit = new int[N];
		arr = new int[101][2];
		dp = new int[101][100001];

		for (int i = 0; i < N; i++) {
			String[] s1 = br.readLine().split(" ");

			int w = Integer.parseInt(s1[0]);
			int v = Integer.parseInt(s1[1]);
			arr[i][0] = w;
			arr[i][1] = v;
			
		}

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= K; j++) {

				if (arr[i - 1][0] <= j && j + arr[i - 1][0] >= 0) {
					dp[i][j] = Math.max(dp[i - 1][j], (dp[i - 1][j - arr[i - 1][0]]) + arr[i - 1][1]); // 이부분
				} else
					dp[i][j] = dp[i - 1][j];

			}
		}
	
		System.out.println(dp[N][K]);

	}

	public static void print(int N, int K) {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= K; j++) {
				System.out.print(dp[i][j] + " ");
			}
			System.out.println();
		}
	}
}
