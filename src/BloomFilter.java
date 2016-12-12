import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * A simple implementation of a bloom filter.
 * @author Patric Steiner, Roger Obrist, Sandro Schwager
 */
public class BloomFilter {
	private int n; //expected elements
	private double p; //error probability 
	private int m; // filter size
	private int k; // amount of hash functions;
	private boolean[] array;
	
	/**
	 * Initializes the BloomFilter. Size of filter m and optimal amount of hashfunctions k are automatically calculated
	 * using formulas from wikipedia: https://en.wikipedia.org/wiki/Bloom_filter
	 * @param n expecteed elements
	 * @param p error probability
	 */
	public BloomFilter(int n, double p) {
		this.n = n;
		this.p = p;
		m = (int) -(n * Math.log(p) / Math.pow(Math.log(2), 2)); // formula from https://en.wikipedia.org/wiki/Bloom_filter
		k = (int) -(Math.log(p) / Math.log(2)); // formula from https://en.wikipedia.org/wiki/Bloom_filter
		array = new boolean[m];
	}
	
	/**
	 * Inserts item into this BloomFilter.
	 * @param item item to be inserted
	 */
	public void insert(String item) {
		for (int index : calculateIndices(item)) array[index] = true;
	}
	
	/**
	 * Checks if item is contained in this BloomFilter. Important: may return false positive!
	 * Never returns wrong negative though.
	 * @param item
	 * @return true if item MIGHT be contained, false if item is not contained.
	 */
	public boolean contains(String item) {
		for (int index : calculateIndices(item)) if (!array[index]) return false;
		return true;
	}
	
	/**
	 * Use k hashfunctions to calculate k indices for a given item.
	 * @param item
	 * @return indices the indices that correspond to given item.
	 */
	private int[] calculateIndices(String item) {
		int[] indices = new int[k];
		for (int seed = 0; seed < k; seed++) {
			HashFunction h = Hashing.murmur3_128(seed);
			HashCode c = h.newHasher().putString(item, Charsets.UTF_8).hash();
			int index = Math.abs(c.asInt() % m);
			indices[seed] = index;
		}
		return indices;
	}
}
