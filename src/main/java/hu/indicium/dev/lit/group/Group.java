package hu.indicium.dev.lit.group;

import javax.persistence.*;

@Entity
public class Group {
    @Id
    @SequenceGenerator(name = "group_id_generator", sequenceName = "group_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_id_generator")
    private Long id;

    @Column(nullable = false)
    private String name;

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
}
