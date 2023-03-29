package com.tirsh.dao;

import com.tirsh.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/newDb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public List<Person> getAll(){
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM person");
            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                people.add(person);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public Person getById(int id){
        return null;
    }

    public Person create(Person person){
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(String.format("INSERT INTO person VALUES (%d, '%s', '%d', '%s')",
                    10, person.getName(), person.getAge(), person.getEmail()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


//        person.setId(++PERSON_COUNTER);
//        people.add(person);
        return null;
    }

    public Person update(int id, Person person){
//        Person byId = getById(id);
//        byId.setName(person.getName());
//        byId.setAge(person.getAge());
//        byId.setEmail(person.getEmail());
        return null;
    }

    public void delete(int id) {
//        people.removeIf(p -> p.getId() == id);
    }
}
