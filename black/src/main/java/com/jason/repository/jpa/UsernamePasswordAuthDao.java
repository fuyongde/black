package com.jason.repository.jpa;

import com.jason.entity.UsernamePasswordAuth;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by fuyongde on 2016/11/12.
 */
public interface UsernamePasswordAuthDao extends PagingAndSortingRepository<UsernamePasswordAuth, Integer>, JpaSpecificationExecutor<UsernamePasswordAuth> {
    UsernamePasswordAuth findByUsername(String username);
}
