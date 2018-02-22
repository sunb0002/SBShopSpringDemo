package com.madoka.sunb0002.webapi;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.madoka.sunb0002.common.utils.Validators;

public class Java8PlayTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private List<Integer> arrInt;
	private List<String> arrStr;

	private Random randGen = new Random();

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
		randGen.ints(-50, 50).limit(10).forEach(x -> System.out.print(x + ","));
		System.out.println("+++" + (randGen.nextDouble() * 10 + 60));

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
	 * String array; Optional; TimeUnit.sleep;
	 * 
	 * @throws InterruptedException
	 */
	// @Test
	public void test2() throws InterruptedException {
		logger.info("===========");
		List<String> reverseArrStr = arrStr.stream().map(str -> reverseString(str)).collect(Collectors.toList());
		reverseArrStr.stream().forEachOrdered(s -> logger.info("Reversed grief seed: {}", s));

		TimeUnit.SECONDS.sleep(2);

		logger.info("===========");
		Optional<String> optStr = arrStr.stream().reduce((a, b) -> a + "|" + b);
		// reduce("", ...) with initial value "" will give String instead of
		// Optional<String>
		String str = optStr.orElse("Empty loh.");
		logger.info("Reduced result: {}", str);
		arrInt.stream().mapToInt(x -> x).max().ifPresent(x -> logger.info("Max is present: {}", x));

		logger.info("===========");
		List<String> filteredList = arrStr.stream().parallel().filter(s -> s.startsWith("qb"))
				.collect(Collectors.toList());
		// filteredList=[]
		String processResult = Optional.ofNullable(filteredList).filter(Validators::isNotEmpty).map(x -> x + "kkk")
				.orElse("Empty processResult, no element will be mapped.");
		logger.info("Optional processResult: {}", processResult);
	}

	/**
	 * parallelStream; Int/Double/LongSummaryStatistics; Collect with specific
	 * Collection type.
	 */
	// @Test
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

	/**
	 * Async/Concurrent in Java8
	 * 
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	// @Test
	public void test4() throws InterruptedException, ExecutionException, TimeoutException {
		logger.info("Main thread starts!");

		Runnable runTask = () -> {
			logger.info("Runnable Task starts!");
		};
		Callable<String> futureTask1 = () -> {
			logger.info("Future Task1 starts!");
			return "~walpurgis night1~";
		};
		Callable<String> futureTask2 = () -> {
			logger.info("Future Task2 starts!");
			return "~walpurgis night2~";
		};

		// Java7 approach
		logger.info("===========");
		ExecutorService exeSvc = Executors.newCachedThreadPool();
		exeSvc.submit(runTask);
		Future<String> futureTaskResult = exeSvc.submit(futureTask1);
		logger.info("Future Task result={}", futureTaskResult.get(5, TimeUnit.SECONDS));

		// InvokeAll with forEach
		logger.info("===========");
		List<Callable<String>> futureTaskList = Arrays.asList(futureTask1, futureTask2);
		exeSvc.invokeAll(futureTaskList).forEach(result -> {
			try {
				System.out.println("Invoke result: " + result.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});

		// Use ScheduledExecutorService/ScheduledFuture
		logger.info("===========");
		ScheduledExecutorService exeSvc2 = Executors.newScheduledThreadPool(5);
		exeSvc2.schedule(runTask, 2, TimeUnit.SECONDS);
		ScheduledFuture<String> scheduledFuture = exeSvc2.schedule(futureTask1, 1, TimeUnit.SECONDS);
		logger.info("ScheduledFuture Task result={}, delay={}", scheduledFuture.get(),
				scheduledFuture.getDelay(TimeUnit.SECONDS));

		exeSvc.awaitTermination(10, TimeUnit.SECONDS);
		exeSvc2.awaitTermination(10, TimeUnit.SECONDS);
		logger.info("Main thread finished!");

	}

	/**
	 * Java8 DateTime
	 */
	@Test
	public void test5() {

		LocalDate localDate = LocalDate.of(2017, 6, 1);
		mytestlog("Check localDate", localDate.getYear(), localDate.getMonthValue(), //
				localDate.isLeapYear(), LocalDate.now().isAfter(localDate));
		LocalTime localTime = LocalTime.of(20, 30, 01);
		LocalDateTime localDT = LocalDateTime.of(localDate, localTime);
		mytestlog("Check localDateTime", localDT, localDT.plusHours(6));

		// Instant brings in timezone
		Instant intant = Instant.now();
		mytestlog("Check instant", intant, LocalDateTime.now());

		// Duration must use same type, LocalTime-LocalDate will throw
		// exception.
		// Period only accept LocalDate, and Period doesn't have "toDays" (so
		// useless).
		Duration duration = Duration.between(LocalDateTime.now(), localDT);
		mytestlog("Check duration/period", duration, duration.toHours(), //
				localDT.minus(duration), Period.between(localDate, LocalDate.now()));

		// Formatter
		DateTimeFormatter someFormat = DateTimeFormatter.ofPattern("yyyy~MM~dd HH=mm=ss");
		String localDTstr = localDT.format(someFormat);
		LocalDateTime localDT2 = LocalDateTime.parse("2018~12~24 01=02=03", someFormat);
		mytestlog("Check formatter", localDTstr, localDT2);

		// WTH
		mytestlog("Check other date system", JapaneseDate.now(), HijrahDate.now());

	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	private String reverseString(String s) {
		return (new StringBuilder(s)).reverse().toString();
	}

	/**
	 * My Java8 approach
	 * 
	 * @param prefix
	 * @param objects
	 */
	private void mytestlog(String prefix, Object... objects) {

		String mid = (String) Optional.ofNullable(objects) //
				.map(Stream::of).orElseGet(Stream::empty) //
				.reduce("", (a, b) -> "{};;;" + b);

		// String mid = "";
		// if (objects != null) {
		// mid = (String) Arrays.stream(objects).reduce("", (a, b) -> "{};;;" +
		// b);
		// }

		logger.info(prefix + ":  " + mid, objects);
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
// SBNote: Stream is mutable, forloop is not. Think of time vs space complexity.
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