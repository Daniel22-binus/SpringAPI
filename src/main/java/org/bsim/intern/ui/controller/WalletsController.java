package org.bsim.intern.ui.controller;

import org.bsim.intern.service.iservice.IWalletsService;
import org.bsim.intern.shared.dto.WalletDTO;
import org.bsim.intern.ui.model.request.WalletRequest;
import org.bsim.intern.ui.model.response.WalletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletsController {

    @Autowired
    IWalletsService walletsService;

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


}
