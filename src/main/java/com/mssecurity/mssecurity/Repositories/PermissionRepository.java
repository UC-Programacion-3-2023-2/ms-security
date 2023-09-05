package com.mssecurity.mssecurity.Repositories;

import com.mssecurity.mssecurity.Models.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermissionRepository extends MongoRepository<Permission,String> {
}
