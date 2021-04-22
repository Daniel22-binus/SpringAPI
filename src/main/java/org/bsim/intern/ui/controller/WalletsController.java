package org.bsim.intern.ui.controller;

import org.bsim.intern.service.iservice.IWalletsService;
import org.bsim.intern.shared.dto.WalletDTO;
import org.bsim.intern.ui.model.request.WalletRequest;
import org.bsim.intern.ui.model.response.WalletResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletsController {

    @Autowired
    IWalletsService walletsService;

    @GetMapping(path = "/{userid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<WalletResponse> getAllWallets(@PathVariable String userid)
    {
        List <WalletDTO> walletsData = walletsService.getAllWalletData(userid);
        return  new ModelMapper().map(walletsData, new TypeToken<List<WalletResponse>>(){}.getType());
    }

    @GetMapping(path = "/{userid}/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public long getTotalBalance(@PathVariable String userid)
    {
        long totalBalance = walletsService.getTotalBalance(userid);
        return totalBalance;
    }

    @PostMapping(path = "/{userid}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public WalletResponse addNewWallet(@PathVariable String userid,
                                       @RequestBody WalletRequest walletRequest)    {
        ModelMapper mapper = new ModelMapper();

        WalletDTO walletDTO = mapper.map(walletRequest, WalletDTO.class);
        WalletDTO createdWallet = walletsService.addNewWalletData(userid, walletDTO);

        return mapper.map(createdWallet, WalletResponse.class);
    }

    @PutMapping(path = "/{userid}/{walletid}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public WalletResponse updateWalletAmount(@PathVariable String userid,
                                              @PathVariable String walletid,
                                              @RequestBody WalletRequest walletRequest)
    {
        ModelMapper mapper = new ModelMapper();

        WalletDTO walletDTO = mapper.map(walletRequest, WalletDTO.class);
        WalletDTO updateWallet = walletsService.updateWalletData(userid, walletid, walletDTO);
        return mapper.map(updateWallet, WalletResponse.class);
    }


}
