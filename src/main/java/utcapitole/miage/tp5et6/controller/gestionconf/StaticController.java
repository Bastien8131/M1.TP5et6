package utcapitole.miage.tp5et6.controller.gestionconf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {

    @RequestMapping("")
    public String index() {
        return "redirect:/gestionconf";
    }

    @RequestMapping("/gestionconf/participants/form/insert")
    public String goToFormParticipants() {
        return "redirect:/gestionconf/participants/insert/insert.html";
    }

    @RequestMapping("/gestionconf/conferences/form")
    public String goToFormConferences() {
        return "redirect:/gestionconf/conference/insert/insert.html";
    }

}
