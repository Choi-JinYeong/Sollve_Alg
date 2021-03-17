package Git_BJ_14888;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

// https://www.acmicpc.net/problem/14888

/* 1시간 소요
*/

public class Main {

	public static int N;

	public static int nums[];

	public static ArrayList<Integer> lists;
	public static int isVisit[], listarr[];
	public static int res[];
	public static int resIdx;
	public static int MAX, MIN;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		String[] s1 = br.readLine().split(" ");
		lists = new ArrayList<>();
		nums = new int[s1.length];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = Integer.parseInt(s1[i]);
		}

		String[] s2 = br.readLine().split(" ");
		int idx = 1;
		for (int i = 0; i < s2.length; i++) {
			int t = Integer.parseInt(s2[i]);
			for (int j = 0; j < t; j++) {
				lists.add(idx);
			}
			idx++;

		}
		isVisit = new int[lists.size()];
		listarr = new int[lists.size()];
		res = new int[lists.size()];
		resIdx = 0;
		MAX = Integer.MIN_VALUE;
		MIN = Integer.MAX_VALUE;
		for (int i = 0; i < lists.size(); i++) {

			isVisit[i] = 0;
			listarr[i] = lists.get(i);
		}

		/* Do Solve */
		comb(listarr, isVisit, res, 0, 0, listarr.length, listarr.length);

		System.out.println(MAX);
		System.out.println(MIN);
	}

	public static void comb(int[] listarr, int[] isVisit, int[] res, int resIdx, int start, int end, int rec) {
		if (rec == 0) {
			// Do something

			int tval = 0;
			if (res[0] == 1) // 더하기
				tval += nums[0] + nums[1];
			if (res[0] == 2) // 빼기
				tval += nums[0] - nums[1];
			if (res[0] == 3) // 곱하기
				tval += nums[0] * nums[1];
			if (res[0] == 4) // 나누기
				tval += nums[0] / nums[1];

			for (int i = 1; i < res.length; i++) {
				if (res[i] == 1) // 더하기
					tval = tval + nums[i + 1];
				if (res[i] == 2) // 빼기
					tval = tval - nums[i + 1];
				if (res[i] == 3) // 곱하기
					tval = tval * nums[i + 1];
				if (res[i] == 4) // 나누기
					tval = tval / nums[i + 1];

			}

			MAX = Math.max(MAX, tval);
			MIN = Math.min(MIN, tval);

			return;
		} else {
			for (int i = start; i < end; i++) {
				if (isVisit[i] == 0) {
					isVisit[i] = 1;
					res[resIdx] = listarr[i];
					comb(listarr, isVisit, res, resIdx + 1, start, end, rec - 1);
					isVisit[i] = 0;
				}
			}
		}
	}

}
