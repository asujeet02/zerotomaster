package com.cg.springsecurity.zero_to_master.controller;

import com.cg.springsecurity.zero_to_master.model.Cards;
import com.cg.springsecurity.zero_to_master.repository.CardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardsController {

    private final CardsRepository cardsRepository;
    @GetMapping("/myCards")
    public List<Cards> getCardsDetails(@RequestParam long id)
    {
        List<Cards> cards = cardsRepository.findByCustomerId(id);
        if(cards!=null)
            return cards;
        else
            return null;
    }
}
