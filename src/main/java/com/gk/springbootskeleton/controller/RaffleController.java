package com.gk.springbootskeleton.controller;

import com.gk.springbootskeleton.exceptionhandling.ApiErrors;
import com.gk.springbootskeleton.service.RaffleService;
import com.gk.springbootskeleton.service.SubmitResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/raffle")
@RestController
@RequiredArgsConstructor
public class RaffleController {

    private final RaffleService raffleService;

    @GetMapping("/all")
    public Map<String, List<String>> getAllRaffles() {
        return raffleService.getAllRaffles();
    }

    @GetMapping("/{address}")
    public List<String> getCodeListByAddress(@PathVariable String address) {
        return raffleService.getRaffleByAddress(address)
                .orElseThrow(() -> ApiErrors.NOT_FOUND.newEx("Raffle not found"));
    }

    @PostMapping("/{address}/submit")
    public SubmitResult submitRaffle(@PathVariable String address, @RequestBody List<String> codes) {
        return raffleService.submitRaffle(address, codes);
    }
}
