package com.bardackx.tiebreaker.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import com.bardackx.tiebreaker.Tiebreaker;
import com.bardackx.tiebreaker.tests.structures.Person;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class TemporalTests {

	@Test
	public void pickLatest() throws IOException {

		final int x = 7;

		Iterable<Person> csvList = read("temporal.csv");

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.latest(e -> e.getRequestDate()) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.latest(e -> e.getRequestDate().toInstant()) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.latest(e -> e.getRequestDate().toLocalDate()) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.latest(e -> e.getRequestDate().toLocalDateTime()) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.latest(e -> e.getRequestDate().toLocalTime()) //
				.lowest(e -> e.getScore()) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.latest(e -> e.getRequestDate().toOffsetDateTime()) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.latest(e -> toCalendar(e.getRequestDate().toInstant())) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.latest(e -> toCalendar(e.getRequestDate().toInstant()).getTime()) //
				.pick(csvList) //
				.getId());

		Person pick = new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 20) //
				.latest(e -> e.getBirthday()) //
				.lowest(e -> e.getScore()) //
				.pick(csvList);
		assertEquals(13, pick.getId());

	}

	private Calendar toCalendar(Instant instant) {
		ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
		Calendar cal1 = GregorianCalendar.from(zdt);
		return cal1;
	}

	@Test
	public void pickEarliest() throws IOException {

		final int x = 2;

		Iterable<Person> csvList = read("temporal.csv");

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.earliest(e -> e.getRequestDate()) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.earliest(e -> e.getRequestDate().toInstant()) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.earliest(e -> e.getRequestDate().toLocalDate()) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.earliest(e -> e.getRequestDate().toLocalDateTime()) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.earliest(e -> e.getRequestDate().toLocalTime()) //
				.highest(e -> e.getScore()) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.earliest(e -> e.getRequestDate().toOffsetDateTime()) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.earliest(e -> toCalendar(e.getRequestDate().toInstant())) //
				.pick(csvList) //
				.getId());

		assertEquals(x, new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 19) //
				.earliest(e -> toCalendar(e.getRequestDate().toInstant()).getTime()) //
				.pick(csvList) //
				.getId());

		Person pick = new Tiebreaker<Person>() //
				.prefer(e -> e.getAge() == 20) //
				.earliest(e -> e.getBirthday()) //
				.lowest(e -> e.getScore()) //
				.pick(csvList);
		assertEquals(11, pick.getId());
	}

	private List<Person> read(String resource) throws IOException {

		InputStream src = getClass().getClassLoader().getResourceAsStream(resource);

		ObjectReader oReader = new CsvMapper() //
				.registerModule(new MyTemporalJacksonModule()) //
				.readerFor(Person.class) //
				.with(CsvSchema.emptySchema().withHeader());

		MappingIterator<Person> mi = oReader.readValues(src);
		List<Person> list = new ArrayList<>();
		while (mi.hasNext())
			list.add(mi.next());
		return list;
	}
}
