package com.tirsh.dao;

import com.tirsh.models.Person;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getById(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public Person create(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, age, email) VALUES (?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
        return person;
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=? WHERE id=?", person.getName(), person.getAge(), person.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public void testMultipleUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO person(name, age, email) VALUES (?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
        }
        long after = System.currentTimeMillis();
        System.out.println("Multiple update " + (after - before));
    }

    public void testBatchUpdate() {
        List<Person> people = create1000People();
        long after = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO person(name, age, email) VALUES (?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, people.get(i).getName());
                ps.setInt(2, people.get(i).getAge());
                ps.setString(3, people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });
        long before = System.currentTimeMillis();
        System.out.println("Batch update " + (before - after));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "bot " + i, new Random().nextInt(100), String.format("bot%d@mail.com", i)));
        }
        return people;
    }
}
