package com.jason.black.repository.jpa;

import com.jason.black.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by fuyongde on 2016/11/12.
 */
public interface UserDao extends PagingAndSortingRepository<User, String>, JpaSpecificationExecutor<User> {
}
