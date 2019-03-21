package hu.indicium.dev.lit.group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByName(String name);

    List<Group> findAll();
}
