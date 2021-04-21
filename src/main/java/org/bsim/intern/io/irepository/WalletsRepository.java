package org.bsim.intern.io.irepository;

import org.bsim.intern.io.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletsRepository extends JpaRepository<WalletEntity, Long> {

}
