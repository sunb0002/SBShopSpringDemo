package com.madoka.sunb0002.webapi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaGeneralTest {

	@Test
	public void test1() {
		log.info("===========");

		SomePojo pojo = new SomePojo("Tomoe Mami", 17, false, null);
		assertFalse(pojo.isMale());
		assertTrue(ObjectUtils.isEmpty(pojo.getGuns()));

		try {
			Assert.doesNotContain(pojo.getName(), "head", "Mami's head is lost.");
			Assert.notEmpty(pojo.getGuns(), "Too bad no guns.");
		} catch (Exception e) {
			log.info("This is expected: {}", e.getMessage());
		}

		log.info("Test finished with: {}", pojo.toString());
	}

}

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
class SomePojo {
	private String name;
	private Integer age;
	private boolean isMale;
	private List<String> guns;
}