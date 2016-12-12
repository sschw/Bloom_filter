import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BloomFilterTest {

	BloomFilter bloomFilter;
	List<String> words;
	double p = 0.1; //error probability, set as you wish
	
	@Before
	public void Setup() throws IOException {
		words = Files.readAllLines(Paths.get("words.txt"));
		bloomFilter = new BloomFilter(words.size(), p);
		for (String word : words) bloomFilter.insert(word);
	}
	
	@Test
	public void testContains() {
		List<String> wordsThatAreNotContained = words.stream().map(w -> w + "trolol").collect(Collectors.toList());
		int falsePositives = 0;
		for (String word : wordsThatAreNotContained) if (bloomFilter.contains(word)) falsePositives++;
		double effectiveErrorProbability = (double)falsePositives / wordsThatAreNotContained.size();
		assertTrue(effectiveErrorProbability <= p);
	}

}
