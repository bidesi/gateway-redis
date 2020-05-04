package in.sabnar.gateway.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sabnar.gateway.bean.Role;
import in.sabnar.gateway.bean.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	Optional<Role> findByName(RoleType roleType);
}