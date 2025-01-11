package com.cg.springsecurity.zero_to_master.controller;

import com.cg.springsecurity.zero_to_master.model.Loans;
import com.cg.springsecurity.zero_to_master.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoansController {

    private final LoanRepository loanRepository;
    @GetMapping("/myLoans")
    @PostAuthorize("hasRole('USER')")
    public List<Loans> getLoanDetails(@RequestParam long id)
    {
        List<Loans> loans=loanRepository.findByCustomerIdOrderByStartDtDesc(id);
        if(loans!=null)
            return loans;
        else return null;
    }
}
