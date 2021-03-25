package Git_BJ_10250;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String s1 = br.readLine();

		int T, H, W, N;

		T = Integer.parseInt(s1);
		ArrayList<String> results = new ArrayList<>();
		int map[][];
		for (int i = 1; i <= T; i++) {
			String s2[] = br.readLine().split(" ");

			H = Integer.parseInt(s2[0]);
			W = Integer.parseInt(s2[1]);
			N = Integer.parseInt(s2[2]);

			int x = N / H + 1; // 호수
			int y = N % H; //
			if (y != 0) {
				//System.out.println("if"+y + " , " + x);
				String sx = "";
				String sy = "";
				sy = "" + y;
				if (x < 10)
					sx += ("0" + x);
				else
					sx += (x + "");

				String result = sy + sx;
				results.add(result);
			}else {
				x = N / H;
				y = H;
				//System.out.println("else "+y + " , " + x);
				String sx = "";
				String sy = "";
				sy = "" + y;
				if (x < 10)
					sx += ("0" + x);
				else
					sx += (x + "");

				String result = sy + sx;
				results.add(result);
			}
		}
		for (int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i));
		}

	}

}

//			if (y != 0) {
//
//				String sx = "";
//				String sy = "";
//				sy = "" + y;
//				if (x < 10)
//					sx += ("0" + x);
//				else
//					sx += (x + "");
//
//				String result = sy + sx;
//				results.add(result);
////TC
////5
////6 12 10
////30 50 72
////5 5 25
////4 5 20
////5 4 20
//			} else { // 나머지가 0이면
//				y = (H % W);
//				x = (N / H);
//
//				if (x != 0) {
//
//					String sx = "";
//					String sy = "";
//					sy = "" + y;
//					if (x < 10)
//						sx += ("0" + x);
//					else
//						sx += (x + "");
//
//					String result = sy + sx;
//					results.add(result);
//				}else { // x == 0 이면
//					x = W;
//					String sx = "";
//					String sy = "";
//					sy = "" + y;
//					if (x < 10)
//						sx += ("0" + x);
//					else
//						sx += (x + "");
//
//					String result = sy + sx;
//					results.add(result);
//				}
//			}
//
//		}
//		for (int i = 0; i < results.size(); i++) {
//			System.out.println(results.get(i));
//		}
