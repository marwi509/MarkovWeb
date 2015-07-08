package tk.markov.web;

import markov.lyricsGenerator.LyricsFacade;
import markov.util.HashSetTable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class MarkovController {

    @RequestMapping("/goliath")
    public String generateGoliathSong() {
        LyricsFacade facade = new LyricsFacade(new Random(), new HashSetTable<>());
        facade.addSong(getClass().getResourceAsStream("/lyrics/goliath.txt"));
        return facade.generateSong().replaceAll("\n", "<br/>");
    }

}
