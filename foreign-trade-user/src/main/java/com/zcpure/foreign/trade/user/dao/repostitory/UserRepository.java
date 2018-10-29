package com.zcpure.foreign.trade.user.dao.repostitory;


import com.zcpure.foreign.trade.user.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ethan
 * @create_time 2018/10/22 11:39
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Boolean existsByName(String name);
}
