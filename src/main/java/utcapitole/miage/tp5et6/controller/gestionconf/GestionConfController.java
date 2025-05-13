package utcapitole.miage.tp5et6.controller.gestionconf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gestionconf")
public class GestionConfController {

    @GetMapping("/conference")
    public String conference(Model model) {
        return "conference";
    }

    @GetMapping("")
    public String index(Model model) {
        return "index";
    }


}
