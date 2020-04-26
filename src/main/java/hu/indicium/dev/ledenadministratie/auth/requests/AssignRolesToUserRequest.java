package hu.indicium.dev.ledenadministratie.auth.requests;

import java.util.List;

public class AssignRolesToUserRequest {
    private List<String> roles;

    public AssignRolesToUserRequest(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
