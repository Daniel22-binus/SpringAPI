package org.bsim.intern.service.impl;

import org.bsim.intern.io.entity.UserEntity;
import org.bsim.intern.io.entity.WalletEntity;
import org.bsim.intern.io.irepository.UserRepository;
import org.bsim.intern.io.irepository.WalletsRepository;
import org.bsim.intern.service.iservice.IWalletsService;
import org.bsim.intern.shared.dto.UserDTO;
import org.bsim.intern.shared.dto.WalletDTO;
import org.bsim.intern.shared.utils.GenerateRandomPublicId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletsServiceImpl implements IWalletsService {

    @Autowired
    WalletsRepository walletsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GenerateRandomPublicId generateRandomPublicId;

    @Override
    public WalletDTO addNewWalletData(String userid, WalletDTO walletDTO) {

        ModelMapper mapper = new ModelMapper();

        //Generate Wallets Id
        walletDTO.setWalletId(generateRandomPublicId.generateUserId(30));

        //getUser
        UserEntity userData = userRepository.findByUserid(userid);

        //Set User
        walletDTO.setUser(mapper.map(userData, UserDTO.class));

        WalletEntity entity = mapper.map(walletDTO, WalletEntity.class);

        //save Data to database
        WalletEntity storedData = walletsRepository.save(entity);

        return mapper.map(storedData, WalletDTO.class);
    }
}
