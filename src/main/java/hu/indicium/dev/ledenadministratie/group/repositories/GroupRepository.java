package hu.indicium.dev.ledenadministratie.group.repositories;

import hu.indicium.dev.ledenadministratie.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByNameIgnoreCase(String name);
}
