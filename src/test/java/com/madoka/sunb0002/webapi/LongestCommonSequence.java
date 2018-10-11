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
		return "TBD";
	}

}
