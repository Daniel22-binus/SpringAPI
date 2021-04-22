package org.bsim.intern.io.irepository;

import org.bsim.intern.io.entity.UserEntity;
import org.bsim.intern.io.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletsRepository extends JpaRepository<WalletEntity, Long> {

    List<WalletEntity> findAllByUser(UserEntity userEntity);
    WalletEntity findByWalletid(String walletid);


}
