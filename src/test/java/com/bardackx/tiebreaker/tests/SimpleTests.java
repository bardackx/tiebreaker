package com.bardackx.tiebreaker.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;

import com.bardackx.tiebreaker.Tiebreaker;
import com.bardackx.tiebreaker.tests.structures.Person;
import com.bardackx.tiebreaker.tests.structures.PersonList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class SimpleTests {

    @Test
    public void testPick() throws IOException {

        List<Person> list = read("input.yml");
        List<Person> control = read("input.yml");
        assertEquals(control, list);

        Person pick = new Tiebreaker<Person>() //
                .prefer(e -> e.isFemale()) //
                .lowest(e -> e.getAge()) //
                .prefer(e -> e.isCitizen()) //
                .highest(e -> e.getScore()) //
                .highest(e -> e.getFunds()) //
                .pick(list);

        assertEquals(pick.getId(), 6);
        assertEquals(control, list);

        
    }

    @Test
    public void testSort() throws IOException {

        List<Person> reference = read("input.yml");
        List<Person> control = read("input.yml");
        assertEquals(control, reference);

        List<Person> sorted = new Tiebreaker<Person>() //
                .prefer(e -> e.isFemale()) //
                .lowest(e -> e.getAge()) //
                .prefer(e -> e.isCitizen()) //
                .highest(e -> e.getScore()) //
                .highest(e -> e.getFunds()) //
                .sort(reference);

        assertNotNull(sorted);
    }

    @Test
    public void noSideEffects() throws IOException {

        Function<Person, Boolean> a = Person::isFemale;
        Function<Person, Object> b = Person::getAge;
        Function<Person, Boolean> c = Person::isCitizen;
        Function<Person, Object> d = Person::getScore;
        Function<Person, Object> e = Person::getFunds;

        Tiebreaker<Person> reference = new Tiebreaker<Person>() //
                .prefer(a) //
                .lowest(b) //
                .prefer(c) //
                .highest(d) //
                .highest(e);
        Tiebreaker<Person> control = new Tiebreaker<Person>() //
                .prefer(a) //
                .lowest(b) //
                .prefer(c) //
                .highest(d) //
                .highest(e);

        List<Person> referenceInput = read("input.yml");
        List<Person> controlInput = read("input.yml");

        assertEquals(control, reference);
        assertEquals(controlInput, referenceInput);

        Person pick = reference.pick(referenceInput);
        assertEquals(pick.getId(), 6);

        assertEquals(control, reference);
        assertEquals(controlInput, referenceInput);
    }

    private List<Person> read(String string) throws IOException {
        return new ObjectMapper(new YAMLFactory())
                .readValue(getClass().getClassLoader().getResourceAsStream("input.yml"), PersonList.class).getList();
    }
}
