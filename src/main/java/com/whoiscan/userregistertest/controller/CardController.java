package com.whoiscan.userregistertest.controller;

import com.whoiscan.userregistertest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.whoiscan.userregistertest.entity.Card;
import com.whoiscan.userregistertest.model.Result;
import com.whoiscan.userregistertest.payload.CardReq;
import com.whoiscan.userregistertest.repository.CardRepository;
import com.whoiscan.userregistertest.security.SignedUser;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/card")
public class CardController {
    @Autowired
    CardRepository cardRepository;

    @PostMapping("/add")
    @ResponseBody
    public Result addCard(@Valid @RequestBody CardReq cardReq, @SignedUser User user){
        Result result=new Result();
        Card newCard=new Card();
        newCard.setNumber(cardReq.getNumber());
        newCard.setBank(cardReq.getBank());
        newCard.setExpireDate(cardReq.getExpireDate());
        newCard.setUserId(user.getId());
        Card savedCard=cardRepository.save(newCard);
        if (savedCard!=null){
            result.setSuccess(true);
            result.setMessage(savedCard.getNumber()+" is successfully added!");
        }else {
            result.setSuccess(false);
            result.setMessage(cardReq.getNumber()+ "is not added");
        }

        return result;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result deleteCard(@PathVariable Integer id){
        Result result=new Result();
        Card card=cardRepository.getOne(id);
        cardRepository.delete(card);
        Optional<Card> optionalCard=cardRepository.findById(id);

        if (!optionalCard.isPresent()){
            result.setSuccess(true);
            result.setMessage(card.getNumber()+ " successfully deleted!");
        }else {
            result.setSuccess(false);
            result.setMessage(card.getNumber()+ "is not deleted");
        }

        return result;
    }
    @GetMapping("/list")
    @ResponseBody
    public List<Card> getCardList(@SignedUser User user){
        return cardRepository.getByUserId(user.getId());
    }
    @GetMapping("/")
     public String getCardListPage(@SignedUser User user,Model model) {
        model.addAttribute("user",user);
        return "card";
    }
}
