package Repositories;

import Models.RolePermission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RolePermissionRepository extends MongoRepository<RolePermission,String> {
}
