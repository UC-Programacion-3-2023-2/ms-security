package com.mssecurity.mssecurity.Repositories;

import com.mssecurity.mssecurity.Models.RolePermission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RolePermissionRepository extends MongoRepository<RolePermission,String> {
}
