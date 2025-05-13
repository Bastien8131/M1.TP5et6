package utcapitole.miage.tp5et6.controller.math;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/math")
public class MathController {

    @GetMapping("")
    public String math() {
        return "math/index";
    }

    @GetMapping("/calc")
    public String calc(
            @RequestParam("inf") int inf,
            @RequestParam("sup") int sup,
            Model model) {
        System.out.println(inf + " _ " + sup);
        List<Map<String, Integer>> results = new ArrayList<>();

        for (int i = inf; i <= sup; i++) {
            Map<String, Integer> entry = new HashMap<>();
            entry.put("number", i);
            entry.put("square", i * i);
            results.add(entry);
        }

        model.addAttribute("results", results);
        model.addAttribute("inf", inf);
        model.addAttribute("sup", sup);

        return "math/index";
    }


}
