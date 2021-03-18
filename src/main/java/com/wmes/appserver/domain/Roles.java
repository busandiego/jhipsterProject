package com.wmes.appserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 50)
    @Id
    @Column(length = 50)
    private String role_name;

    public String getName() {
        return role_name;
    }

    public void setName(String role_name) {
        this.role_name = role_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Roles)) {
            return false;
        }
        return Objects.equals(role_name, ((Roles) o).role_name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(role_name);
    }

    @Override
    public String toString() {
        return role_name;
    }
}
