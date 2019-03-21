package hu.indicium.dev.lit.group;

import hu.indicium.dev.lit.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "groups")
public class Group {
    @Id
    @SequenceGenerator(name = "group_id_generator", sequenceName = "group_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_id_generator")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "group")
    private Set<GroupMembership> memberships = new HashSet<>();

    protected Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GroupMembership> getMemberships() {
        return memberships;
    }

    public void setMemberships(Set<GroupMembership> memberships) {
        this.memberships = memberships;
    }

    public void addMembership(User user, Date dateStart, Date dateEnd) {
        this.memberships.add(new GroupMembership(dateStart, dateEnd, this, user));
    }
}
