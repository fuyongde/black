package com.jason.repository.jdbc;

import com.google.common.collect.Lists;
import com.jason.entity.Region;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fuyongde on 2016/11/12.
 */
@Component
public class RegionJdbcDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Region findById(Integer id) {
        StringBuffer sql = new StringBuffer();
        List<Object> val = Lists.newArrayList();
        sql.append("SELECT * FROM region r WHERE r.id = ?");
        val.add(id);
        return jdbcTemplate.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Region.class), val.toArray());
    }
}

