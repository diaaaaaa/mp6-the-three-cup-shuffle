package Refugeescode.at.mp6thethreecupshuffle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CupEndPoint {

    private String hasCoin ="NO";

    @GetMapping("/cup")
    String get(){
        return hasCoin;
    }

    @PostMapping("/cup")
    String posttoTrue(@RequestBody String coin){
        hasCoin = coin + " in ";
        return hasCoin;
    }
}
