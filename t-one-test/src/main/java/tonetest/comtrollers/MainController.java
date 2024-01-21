package tonetest.comtrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MainController {
    @GetMapping("/count")
    public Map<Character,Integer> count(@RequestParam String str){
        HashMap<Character,Integer> counts = new HashMap<>();
        for (char x : str.toCharArray()){
            counts.compute(x, (k,v)-> v == null ? 1 : v+1);
        }
        return counts.entrySet().stream()
                .sorted((entry1,entry2)-> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
    }
}
