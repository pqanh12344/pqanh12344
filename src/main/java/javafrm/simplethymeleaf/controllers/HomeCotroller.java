package javafrm.simplethymeleaf.controllers;

import javafrm.simplethymeleaf.models.Person;
import javafrm.simplethymeleaf.models.PersonNew;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeCotroller {
    private static List<Person> persons = new ArrayList<Person>();

    static{
        persons.add(new Person("Bill","Gates"));
        persons.add(new Person("Steve","Jobs"));
    }

    @Value("${welcome.message}")
    private String messages;

    @Value("${error.message}")
    private String errorMessages;

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("message",messages);
        return "index";
    }

    @GetMapping("/personList")
    public String PersonList(Model model){
        model.addAttribute("persons",persons);
        return "/personList";
    }

    @GetMapping("/addPerson")
    public String ShowPerson(Model model){
        PersonNew personForm = new PersonNew();
        model.addAttribute("personForm",personForm);
        return "addPerson";
    }

    @PostMapping("/addPerson")
    public String savePerson(Model model, @ModelAttribute("personForm") PersonNew personForm){
        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();

        if(firstName != null && firstName.length() > 0 && lastName != null && lastName.length() > 0){
            Person newPerson = new Person(firstName, lastName);
            persons.add(newPerson);
            return "redirect:/personList";
        }

        model.addAttribute("errorMessage", errorMessages);
        return "addPerson";
    }
}
