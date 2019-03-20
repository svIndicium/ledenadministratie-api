package hu.indicium.dev.lit.user;

import hu.indicium.dev.lit.group.GroupMembership;
import hu.indicium.dev.lit.membership.Membership;
import hu.indicium.dev.lit.study.Study;
import hu.indicium.dev.lit.userdata.UserData;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;

    @OneToMany(mappedBy = "user")
    private Set<Membership> memberships = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<GroupMembership> groupMemberships = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private UserData userData;

    @OneToMany(mappedBy = "user")
    private Set<Study> studies = new HashSet<>();

    public User(Long id) {
        this.id = id;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Set<Study> getStudies() {
        return studies;
    }

    public void setStudies(Set<Study> studies) {
        this.studies = studies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(Set<Membership> memberships) {
        this.memberships = memberships;
    }

    public void addMembership(Membership membership) {
        this.memberships.add(membership);
    }

    public Set<GroupMembership> getGroupMemberships() {
        return groupMemberships;
    }

    public void setGroupMemberships(Set<GroupMembership> groupMemberships) {
        this.groupMemberships = groupMemberships;
    }
}
