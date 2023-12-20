import java.util.Arrays;

public class Gene {
	static String x2;
	static String y2;

	public static void sequenceAlignment(String x, String y, double[][] scoringMatrix) {
	    int n = x.length();
	    int m = y.length();

	    double[][] dp = new double[n + 1][m + 1];

	    for (double[] row : dp) {
	        Arrays.fill(row, Double.NEGATIVE_INFINITY);
	    }

	    dp[0][0] = 0;

	    for (int i = 1; i <= n; i++) {
	        for (int j = 1; j <= m; j++) {
	            char charX = x.charAt(i - 1);
	            char charY = y.charAt(j - 1);

	            double both = dp[i - 1][j - 1] + scoringMatrix[getIndex(charX)][getIndex(charY)];
	            double first = dp[i - 1][j] + scoringMatrix[getIndex(charX)][getIndex('-')];
	            double second = dp[i][j - 1] + scoringMatrix[getIndex('-')][getIndex(charY)];
	            dp[i][j] = Math.max(Math.max(both, first), second);
	        }
	    }

	    // Construct alignment directly
	    StringBuilder alignedX = new StringBuilder();
	    StringBuilder alignedY = new StringBuilder();

	    int i = n;
	    int j = m;

	    while (i > 0 || j > 0) {
	        char charX;
	        char charY;

	        if (i > 0) {
	            charX = x.charAt(i - 1);
	        } else {
	            charX = '-';
	        }

	        if (j > 0) {
	            charY = y.charAt(j - 1);
	        } else {
	            charY = '-';
	        }

	        if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] + scoringMatrix[getIndex(charX)][getIndex(charY)]) {
	            alignedX.insert(0, charX);
	            alignedY.insert(0, charY);
	            i--;
	            j--;
	        } else if (i > 0 && dp[i][j] == dp[i][j - 1] + scoringMatrix[getIndex('-')][getIndex(charY)]) {
	            alignedX.insert(0, '-');
	            alignedY.insert(0, charY);
	            j--;
	        } else {
	            alignedX.insert(0, charX);
	            alignedY.insert(0, '-');
	            i--;
	        }
	    }

	    // Add leading gaps if necessary
	    while (i > 0) {
	        alignedX.insert(0, x.charAt(--i));
	        alignedY.insert(0, '-');
	    }

	    while (j > 0) {
	        alignedX.insert(0, '-');
	        alignedY.insert(0, y.charAt(--j));
	    }

	    // Set the aligned sequences directly to x2 and y2
	    x2 = alignedX.toString();
	    y2 = alignedY.toString();
	}

    private static int getIndex(char character) {
        if (character == 'A') 
            return 0;
        else if (character == 'G') 
        	return 1;
        else if (character == 'T') 
            return 2;
         else if (character == 'C') 
            return 3;
         else    // '-'
            return 4;
        
    }


    public static void main(String[] args) {
        String x = "ATGCC";
        String y = "TACGCA";

        double[][] scoreMatrix = {
                {1, -0.8, -0.2, -2.3, -0.6},
                {-0.8, 1, -1.1, -0.7, -1.5},
                {-0.2, -1.1, 1, -0.5, -0.9},
                {-2.3, -0.7, -0.5, 1, -1},
                {-0.6, -1.5, -0.9, -1, Double.NaN}
        };

        sequenceAlignment(x, y, scoreMatrix);
        System.out.println("Alignment of x: " + x2);
        System.out.println("Alignment of y: " + y2);
    }
}
