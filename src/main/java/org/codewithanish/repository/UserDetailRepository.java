package org.codewithanish.repository;

import org.codewithanish.constant.Provider;
import org.codewithanish.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    Optional<UserDetail> findByUserNameAndProvider(String userName, Provider provider);
}
