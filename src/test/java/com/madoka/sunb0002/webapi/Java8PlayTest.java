package com.madoka.sunb0002.webapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.madoka.sunb0002.common.utils.Validators;

public class Java8PlayTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private List<Integer> arrInt;
	private List<String> arrStr;

	@Before
	public void setUp() {
		arrInt = Arrays.asList(1, 2, 4, 5, 8, 20, 19, 18);
		arrStr = Arrays.asList("Madoka", "Sayaka", "Kyouko", "Mami", "Homura", "Nagisa");
	}

	/**
	 * Int array
	 */
	// @Test
	public void test1() {
		// arr.forEach(x -> logger.info("Hi {}", x));
		MyLambdaFunc1<Integer> printHi = x -> logger.info("Hi--{}", x);
		MyLambdaFunc1<String> printHiStr = x -> logger.info("HiStr--{}", x);
		arrInt.stream().filter(x -> x > 5).sorted().forEach(printHi::operation);

		logger.info("===========");
		Predicate<Integer> bigFilter = val -> (val > 2 && val < 19);
		arrInt.stream().filter(bigFilter).sorted(Comparator.reverseOrder()).forEach(printHi::operation);
		long testCount = arrInt.stream().filter(val -> val > 10).count();
		logger.info("Count of valid numbers={}", testCount);
		boolean isAllPositive = arrInt.stream().allMatch(v -> v > 0);
		logger.info("Are all numbers positive: {}", isAllPositive);

		logger.info("===========");
		Function<Integer, String> mapFunc = val -> val + "www";
		arrInt.stream().map(mapFunc).sorted().forEach(printHiStr::operation);

	}

	/**
	 * String array; Optional;
	 */
	// @Test
	public void test2() {
		logger.info("===========");
		List<String> reverseArrStr = arrStr.stream().map(str -> reverseString(str)).collect(Collectors.toList());
		reverseArrStr.stream().forEachOrdered(s -> logger.info("Reversed grief seed: {}", s));

		logger.info("===========");
		Optional<String> optStr = arrStr.stream().reduce((a, b) -> a + "|" + b);
		// reduce("", ...) with initial value "", will give String instead of
		// Optional<String>
		String str = optStr.orElse("Empty loh.");
		logger.info("Reduced result: {}", str);

		logger.info("===========");
		List<String> filteredList = arrStr.stream().filter(s -> s.startsWith("qb")).collect(Collectors.toList());
		// filteredList=[]
		String processResult = Optional.ofNullable(filteredList).filter(Validators::isNotEmpty).map(x -> x + "kkk")
				.orElse("Empty processResult, no element will be mapped.");
		logger.info("Optional processResult: {}", processResult);
	}

	/**
	 * parallelStream; Int/Double/LongSummaryStatistics; Collect with specific
	 * Collection type.
	 */
	@Test
	public void test3() {
		logger.info("===========");
		arrStr.parallelStream().forEach(logger::info);
		logger.info("===========");
		String joinResult = arrStr.stream().collect(Collectors.joining(","));
		logger.info("Join Result={}", joinResult);

		logger.info("===========");
		IntSummaryStatistics stat = arrInt.stream().mapToInt(x -> x).summaryStatistics();
		logger.info("Int/Double/Long stream special summaryStatistics, sum={}, avg={}, max={}", stat.getSum(),
				stat.getAverage(), stat.getMax());

		logger.info("===========");
		List<String> arrayListStr = arrStr.stream().map(s -> s + "-chan")
				.collect(Collectors.toCollection(ArrayList::new));
		// ArrayList::new constructor actually provides a (Supplier<C>
		// collectionFactory)
		logger.info("This is an ArrayList not just List: {}", arrayListStr instanceof ArrayList);

	}

	private String reverseString(String s) {
		return (new StringBuilder(s)).reverse().toString();
	}

}

@FunctionalInterface
interface MyLambdaFunc1<T> {
	void operation(T a);
}

interface MyLambdaFunc2 {
	Integer operation(Integer a, Integer b);
}

// Java8 Stream Useful Operations:
//
// Intermediate:
// map (mapToInt, flatMap, etc), filter, distinct, sorted, peek, limit, skip,
// parallel, sequential, unordered
//
// Terminal:
// forEach, forEachOrdered, toArray, reduce, collect, min, max, count, anyMatch,
// allMatch, noneMatch, findFirst, findAny, iterator
//
// Short-circuiting:
// anyMatch, allMatch, noneMatch, findFirst, findAny, limit