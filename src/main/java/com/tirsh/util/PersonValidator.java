package com.tirsh.util;

import com.tirsh.dao.PersonDAO;
import com.tirsh.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personDAO.getByEmail(person.getEmail()).isPresent()){
            errors.rejectValue("email", "", "We have user with such email!");
        }

    }
}
