package org.bsim.intern.service.iservice;

import org.bsim.intern.shared.dto.WalletDTO;

public interface IWalletsService {

    WalletDTO addNewWalletData (String userid, WalletDTO walletDTO);

}
