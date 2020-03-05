package com.bardackx.tiebreaker;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class MultiTypeComparator {

	private static Map<Class<?>, BiFunction<Object, Object, Integer>> BUILT_IN_COMPARATORS;

	static {
		BUILT_IN_COMPARATORS = new HashMap<>();
		//
		BUILT_IN_COMPARATORS.put(Integer.class, (a, b) -> (Integer) a < (Integer) b ? -1 : +1);
		BUILT_IN_COMPARATORS.put(Double.class, (a, b) -> (Double) a < (Double) b ? -1 : +1);
		BUILT_IN_COMPARATORS.put(Float.class, (a, b) -> (Float) a < (Float) b ? -1 : +1);
		BUILT_IN_COMPARATORS.put(Short.class, (a, b) -> (Short) a < (Short) b ? -1 : +1);
		BUILT_IN_COMPARATORS.put(Byte.class, (a, b) -> (Byte) a < (Byte) b ? -1 : +1);
		BUILT_IN_COMPARATORS.put(Long.class, (a, b) -> (Long) a < (Long) b ? -1 : +1);
		BUILT_IN_COMPARATORS.put(BigDecimal.class, (a, b) -> ((BigDecimal) a).compareTo((BigDecimal) b));
		//
		BUILT_IN_COMPARATORS.put(ZonedDateTime.class, (a, b) -> ((ZonedDateTime) a).compareTo((ZonedDateTime) b));
		BUILT_IN_COMPARATORS.put(Instant.class, (a, b) -> ((Instant) a).compareTo((Instant) b));
		BUILT_IN_COMPARATORS.put(LocalDate.class, (a, b) -> ((LocalDate) a).compareTo((LocalDate) b));
		BUILT_IN_COMPARATORS.put(LocalDateTime.class, (a, b) -> ((LocalDateTime) a).compareTo((LocalDateTime) b));
		BUILT_IN_COMPARATORS.put(LocalTime.class, (a, b) -> ((LocalTime) a).compareTo((LocalTime) b));
		BUILT_IN_COMPARATORS.put(OffsetDateTime.class, (a, b) -> ((OffsetDateTime) a).compareTo((OffsetDateTime) b));
		BUILT_IN_COMPARATORS.put(Calendar.class, (a, b) -> ((Calendar) a).compareTo((Calendar) b));
		BUILT_IN_COMPARATORS.put(Date.class, (a, b) -> ((Date) a).compareTo((Date) b));
	}

	private final Map<Class<?>, BiFunction<Object, Object, Integer>> comparators;

	public final BiFunction<Object, Object, Integer> preferLowestBiFunction;
	public final BiFunction<Object, Object, Integer> preferHighestBiFunction;

	public MultiTypeComparator() {
		
		this.comparators = new HashMap<>();
		
		BUILT_IN_COMPARATORS.forEach((k, v) -> comparators.put(k, v));
		preferLowestBiFunction = (a, b) -> {
			
			Class<?> atype = a.getClass();

			if (!atype.equals(b.getClass()))
				throw new RuntimeException(
						"Comparison between " + a.getClass() + " and " + b.getClass() + " is not supported");

			while (!this.comparators.containsKey(atype)) {
				atype = atype.getSuperclass();
				if (atype == null) {
					atype = a.getClass();
					break;
				}
			}

			if (!this.comparators.containsKey(atype))
				throw new RuntimeException("Unsupported type " + a.getClass());

			Integer r = this.comparators.get(atype).apply(a, b);

			return r;
		};
		preferHighestBiFunction = (a, b) -> -this.preferLowestBiFunction.apply(a, b);
	}

	public BiFunction<Object, Object, Integer> getPreferHighestBiFunction() {
		return preferHighestBiFunction;
	}

	public BiFunction<Object, Object, Integer> getPreferLowestBiFunction() {
		return preferLowestBiFunction;
	}

	/**
	 * Comparador en orden ascendente
	 * 
	 * @param type
	 * @param comparator
	 * @return
	 * @return
	 */
	public MultiTypeComparator addCustomComparator(Class<?> type, BiFunction<Object, Object, Integer> comparator) {
		comparators.put(type, comparator);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comparators == null) ? 0 : comparators.hashCode());
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
		MultiTypeComparator other = (MultiTypeComparator) obj;
		if (comparators == null) {
			if (other.comparators != null)
				return false;
		} else if (!comparators.equals(other.comparators))
			return false;
		return true;
	}

}
