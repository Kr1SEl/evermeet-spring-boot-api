package com.kr1sel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/people")
public class Main {

    private final PersonRepository personRepository;

    public Main(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Person> getPeople(){
        return personRepository.findAll();
    }

    record NewPersonRequest(
            String name,
            int age,
            String location,
            List<Interest> interests

    ){

    }

    @PostMapping
    public void addPerson(@RequestBody NewPersonRequest request){
        Person person = new Person();
        person.setAge(request.age);
        person.setInterests(request.interests);
        person.setLocation(request.location);
        person.setName(request.name);
        personRepository.save(person);
    }

    @DeleteMapping("{personId}")
    public void deletePerson(@PathVariable("personId") Integer id){
        personRepository.deleteById(id);
    }

    @PutMapping("{personId}")
    public void updatePerson(@PathVariable("personId") Integer id, @RequestBody NewPersonRequest request){
        Person toUpd = personRepository.getReferenceById(id);
        toUpd.setName(request.name);
        toUpd.setAge(request.age);
        toUpd.setLocation(request.location);
        toUpd.setInterests(request.interests);
        personRepository.save(toUpd);
    }
}

