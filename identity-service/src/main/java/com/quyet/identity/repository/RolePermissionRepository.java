package com.quyet.identity.repository;

import com.quyet.identity.entity.RolePermission;
import com.quyet.identity.entity.keys.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {}
