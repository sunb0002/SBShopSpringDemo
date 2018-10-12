/**
 * Dynamic Programming
 */
package com.madoka.sunb0002.webapi;

import com.madoka.sunb0002.common.utils.Validators;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunbo
 *
 */
@Slf4j
public class LongestCommonSequence {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String A = "There is no goddess but Madoka, and Kyouko is her Messenger.";
		String B = "Carry Ma~do~ka's will as your torch, with it destroy the shadows.";
		String C = "Madoka";

		log.info("Longest Common Substring: {}", longestCommonSubstring(A, C));
		log.info("Longest Common Substring: {}", longestCommonSequence(B, C));

	}

	/**
	 * 
	 * @param strA
	 * @param strB
	 * @return Longest common substring
	 */
	public static String longestCommonSubstring(String strA, String strB) {

		if (Validators.isEmpty(strA) || Validators.isEmpty(strB)) {
			return "";
		}

		int maxLength = 0;
		int maxAend = 0;

		// count[i][j] = longest common substring length of (first i char of strA) and
		// first j char of StrB)
		// From the DP result table, we can clear see that count[i][j] = same ?
		// count[i-1][j-1]+1 : 0;
		int[][] count = new int[strA.length()][strB.length()];

		for (int i = 0; i < strA.length(); i++) {
			for (int j = 0; j < strB.length(); j++) {

				char chA = strA.charAt(i);
				char chB = strB.charAt(j);

				if (chA == chB) {
					count[i][j] = (i == 0 || j == 0) ? 1 : count[i - 1][j - 1] + 1;
					// Update maxLength and maxAend
					if (count[i][j] > maxLength) {
						maxLength = count[i][j];
						maxAend = i;
					}
				} else {
					count[i][j] = 0;
				}
			}
		}
		return strA.substring(maxAend - maxLength + 1, maxAend + 1);

	}

	/**
	 * 
	 * @param strA
	 * @param strB
	 * @return Longest common sequence
	 */
	public static String longestCommonSequence(String strA, String strB) {

		if (Validators.isEmpty(strA) || Validators.isEmpty(strB)) {
			return "";
		}

		int[][] count = new int[strA.length()][strB.length()];
		int maxLength = 0, iMax = 0, jMax = 0;

		for (int i = 0; i < strA.length(); i++) {
			for (int j = 0; j < strB.length(); j++) {

				char chA = strA.charAt(i);
				char chB = strB.charAt(j);

				if (chA == chB) {
					count[i][j] = (i == 0 || j == 0) ? 1 : count[i - 1][j - 1] + 1;
					// Record the max value coordinate.
					if (count[i][j] > maxLength) {
						maxLength = count[i][j];
						iMax = i;
						jMax = j;
					}
				} else {
					count[i][j] = Math.max((i > 0) ? count[i - 1][j] : 0, (j > 0) ? count[i][j - 1] : 0);
				}
			}
		}

		return printLCS(count, strA, iMax, jMax);
	}

	/**
	 * Recursively print LCS string.
	 * 
	 * @param countMatrix
	 * @param strA
	 * @param i
	 * @param j
	 * @return
	 */
	private static String printLCS(final int[][] countMatrix, String strA, int i, int j) {

		int iCurrent = countMatrix[i][j];

		if (iCurrent == 1) {
			return strA.charAt(i) + "";
		} else if (i == 0 || j == 0) {
			return "";
		}

		int iPrev = countMatrix[i - 1][j];
		int jPrev = countMatrix[i][j - 1];

		if (iPrev == jPrev) {
			return printLCS(countMatrix, strA, i - 1, j - 1) + strA.charAt(i);
		} else {
			return (iPrev < jPrev) ? printLCS(countMatrix, strA, i, j - 1) : printLCS(countMatrix, strA, i - 1, j);
		}

	}

}
