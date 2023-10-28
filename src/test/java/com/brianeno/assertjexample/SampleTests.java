package com.brianeno.assertjexample;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleTests {

  @Test
  void standardAssertionExample() {
    List<Integer> oddNumbers = List.of(1, 2, 3, 4, 5).stream()
        .filter(nr -> nr % 2 != 0)
        .toList();

    assertEquals(3, oddNumbers.size());
    assertEquals(1, oddNumbers.get(0));
    assertEquals(3, oddNumbers.get(1));
  }

  @Test
  void assertJExample() {
    List<Integer> oddNumbers = List.of(1, 2, 3, 4, 5).stream()
        .filter(nr -> nr % 2 != 0)
        .toList();

    assertThat(oddNumbers).containsExactly(1, 3, 5).
        contains(1, Index.atIndex(0)).
        contains(3, Index.atIndex(1)) ;
  }


  @Test
  void testSampleWithString() {
    String title = "Of Mice and Men";
    assertThat(title).isNotNull()
        .as("Test failed for string result")
        .startsWith("Of")
        .contains("Mice")
        .containsOnlyOnce("and")
        .isSubstringOf("Faulkner's Of Mice and Men")
        .containsIgnoringCase("mice")
        .endsWith("Men");
  }


  @Test
  void testSampleWithList() {
    List<String> list = Arrays.asList("Java", "Go", "Python", "Rust");

    assertThat(list)
        .hasSize(4)
        .contains("Java", "Python")
        .contains("Java", Index.atIndex(0))
        .contains("Go", Index.atIndex(1))
        .contains("Python", Index.atIndex(2))
        .doesNotContain("Javascript");
  }

  @Test
  void testSampleWithMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("lastName", "Enochson");

    assertThat(map)
        .hasSize(1)
        .extractingByKey("lastName", as(InstanceOfAssertFactories.STRING))
        .isEqualToIgnoringCase("Enochson")
        .startsWith("Enochson");

    assertThat(map).extracting("lastName")
        .isEqualTo("Enochson");

    Map<String, Object> map2 = new HashMap<>();
    map2.put("numberVal", 34);

    assertThat(map2)
        .hasSize(1)
        .extractingByKey("numberVal", as(InstanceOfAssertFactories.INTEGER))
        .isEqualTo(34);
  }

  @Test
  void testSampleWithException() {
    assertThatThrownBy(() -> divide(1, 0))
        .isInstanceOf(ArithmeticException.class)
        .hasMessageContaining("zero")
        .hasMessage("/ by zero");

    assertThatThrownBy(() -> {
      List<String> list = Arrays.asList("one", "two");
      list.get(2);
    })
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessageContaining("Index 2 out of bounds");

  }

  int divide(int input, int divide) {
    return input / divide;
  }

}
