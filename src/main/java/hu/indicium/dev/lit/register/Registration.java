package hu.indicium.dev.lit.register;

import javax.persistence.*;

@Entity
public class Registration {
    @Id
    @SequenceGenerator(name = "sign_up_id_generator", sequenceName = "sign_up_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sign_up_id_generator")
    private Long id;

    private String token;

    private String firstName;

    private String lastName;

    private String email;

    public Registration() {
    }

    public Registration(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
