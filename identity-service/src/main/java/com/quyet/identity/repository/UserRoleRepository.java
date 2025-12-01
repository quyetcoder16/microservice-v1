package com.quyet.identity.repository;

import com.quyet.identity.entity.UserRole;
import com.quyet.identity.entity.keys.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {}
