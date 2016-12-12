# Bloom_filter

- [x] Programming bloom filter
- [x] Programming bloom filter unit test
- [x] Idea
- [x] Advantages
- [x] Disadvantages
- [ ] Use case with explanation of programm
- [x] How the error probabilty is tested
- [ ] Results

## Idea
Check if a value is contained inside a list of values. The bloom filter returns false if the value can't be part of the list. If it returns true then the value could be part of the list of values but it could also be a "wrong" true.

This is done by getting several hashes of the value and writing true at the position of the hashes into a list. 
If we now want to check if a value is part of the list of values, we also calculate the hashes of the value and check if the list has the value true at the position of the hashes.

### Advantages
This way of checking if a value is inside a list is quite fast, because we don't have to check the values itself at every position in the list.

### Disadvantages
There is a probability that the value might not in the list but the filter still returns true even if the value isn't part of the list.

## Use case with explanation of programm

## How a hashcode is transformed into an index of the array
The hashes are generated with the murmur3 algorithm. It takes a seed which is the current value of a for-loop. 
The generated hash of with the input value is casted to a integer. The absolute value of this integer % the filter size will give the value for the filter.

## How the error probabilty is tested
The bloom filter is filled correct values. After that the list of correct values is taken and all values are edited so the words are incorrect.

After that we iterate over the list and check if the words are inside the bloom filter. If it returns true we count up the false positive value.

At the end we divide the number of false positive values with the number of incorrect values inside the list. This value has to be smaller or equals the error probability.

### Results
