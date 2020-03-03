package com.bardackx.tiebreaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Tiebreaker<E> {

	private static final MultiTypeComparator DEFAULT_MULTI_TYPE_COMPARATOR = new MultiTypeComparator();

	private final MultiTypeComparator multiTypeComparator;

	private final List<Function<E, ?>> mappers = new ArrayList<>();
	private final List<BiFunction<Object, Object, Integer>> comparators = new ArrayList<>();

	public Tiebreaker() {
		this(DEFAULT_MULTI_TYPE_COMPARATOR);
	}

	public Tiebreaker(MultiTypeComparator multiTypeComparator) {
		this.multiTypeComparator = multiTypeComparator;
	}

	public Tiebreaker<E> lowest(Function<E, Object> mapper) {
		mappers.add(mapper);
		comparators.add(multiTypeComparator.getPreferLowestBiFunction());
		return this;
	}

	public Tiebreaker<E> highest(Function<E, Object> mapper) {
		mappers.add(mapper);
		comparators.add(multiTypeComparator.getPreferHighestBiFunction());
		return this;
	}

	public Tiebreaker<E> prefer(Function<E, Boolean> mapper) {
		mappers.add(mapper);
		comparators.add((a, b) -> (Boolean) a ? -1 : +1);
		return this;
	}

	public Tiebreaker<E> preferFalse(Function<E, Boolean> mapper) {
		mappers.add(mapper);
		comparators.add((a, b) -> !((Boolean) a) ? -1 : +1);
		return this;
	}

	private List<E> sort(Spliterator<E> spliterator) {
		return StreamSupport //
				.stream(spliterator, false) //
				.map(e -> new TiebreakerEntry<E>(e, mappers.stream().map(f -> f.apply(e)).collect(Collectors.toList()))) //
				.sorted((a, b) -> {
					int n = this.mappers.size();
					for (int i = 0; i < n; i++) {
						Object avi = a.getValue(i);
						Object bvi = b.getValue(i);
						if (!avi.equals(bvi))
							return this.comparators.get(i).apply(avi, bvi);
					}
					return 0;
				}) //
				.map(e -> e.getElement()) //
				.collect(Collectors.toList());
	}

	private E pick(Spliterator<E> spliterator) {
		return StreamSupport //
				.stream(spliterator, false) //
				.map(e -> new TiebreakerEntry<E>(e, mappers.stream().map(f -> f.apply(e)).collect(Collectors.toList()))) //
				.reduce((a, b) -> {
					int n = this.mappers.size();
					for (int i = 0; i < n; i++) {
						Object avi = a.getValue(i);
						Object bvi = b.getValue(i);
						if (!avi.equals(bvi))
							return this.comparators.get(i).apply(avi, bvi) == -1 ? a : b;
					}
					return a;
				}) //
				.get() //
				.getElement();
	}

	public List<E> sort(Iterable<E> input) {
		return sort(input.spliterator());
	}

	public E[] sort(E[] input) {
		return sort(Arrays.spliterator(input)).toArray(Arrays.copyOf(input, input.length));
	}

	public E pick(Iterable<E> input) {
		return pick(input.spliterator());
	}

	public E pick(E[] input) {
		return pick(Arrays.spliterator(input));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comparators == null) ? 0 : comparators.hashCode());
		result = prime * result + ((mappers == null) ? 0 : mappers.hashCode());
		result = prime * result + ((multiTypeComparator == null) ? 0 : multiTypeComparator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tiebreaker<?> other = (Tiebreaker<?>) obj;
		if (comparators == null) {
			if (other.comparators != null)
				return false;
		} else if (!comparators.equals(other.comparators))
			return false;
		if (mappers == null) {
			if (other.mappers != null)
				return false;
		} else if (!mappers.equals(other.mappers))
			return false;
		if (multiTypeComparator == null) {
			if (other.multiTypeComparator != null)
				return false;
		} else if (!multiTypeComparator.equals(other.multiTypeComparator))
			return false;
		return true;
	}

}
