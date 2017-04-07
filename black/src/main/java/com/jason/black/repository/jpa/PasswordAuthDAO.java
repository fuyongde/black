package com.jason.black.repository.jpa;

import com.jason.black.domain.entity.PasswordAuth;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by fuyongde on 2016/11/12.
 */
public interface PasswordAuthDAO extends PagingAndSortingRepository<PasswordAuth, Integer>, JpaSpecificationExecutor<PasswordAuth> {
    PasswordAuth findByUsername(String username);
}