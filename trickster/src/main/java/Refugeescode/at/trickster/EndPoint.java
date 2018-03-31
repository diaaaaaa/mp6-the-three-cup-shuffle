package Refugeescode.at.trickster;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EndPoint {

    List<String> activeCups = new ArrayList<>();

    @Value("#{'${cupports}'.split(',')}")
    private List<String> cupports;

    @GetMapping
    String page(){
        return "play";
    }
    // activeCups = ["http://localhost:8081","http://localhost:8082","http://localhost:8083"]
    @ModelAttribute("cups")
    List<String> getcups(){
        String url="http://localhost:";
        activeCups.clear();
        cupports.forEach(e->activeCups.add(url+e));
        activeCups.clear();
        cupports.forEach(e->activeCups.add(url+e));
        return activeCups;
    }

    @GetMapping("/play")
    void  get() {
        String url="http://localhost:";
        activeCups.clear();
        cupports.forEach(e->activeCups.add(url+e));
        List<String> collect = activeCups.stream().collect(Collectors.toList());
        Collections.shuffle(collect);
        RestTemplate restTemplate = new RestTemplate();
        activeCups.stream().forEach(e->restTemplate.postForEntity(e+"/cup","NO", String.class));
        restTemplate.postForEntity(collect.get(0)+"/cup","YES",String.class);
    }

    @GetMapping("choice/{portNumber}")
    String post(@PathVariable String portNumber) {
        RestTemplate restTemplate = new RestTemplate();
        String value;
        for (String cup:activeCups) {
            ResponseEntity<String> forEntity = restTemplate.getForEntity(cup + "/cup", String.class);
            if (forEntity.getBody().contains("YES")  && (cup.contains(portNumber))) {
                return  "WIN";
            }
        }
        return "You Loss";
    }

}
