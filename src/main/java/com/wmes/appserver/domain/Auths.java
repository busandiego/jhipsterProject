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
@Table(name = "auths")
public class Auths implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 50)
    @Id
    @Column(length = 50)
    private String auth_name;

    public String getName() {
        return auth_name;
    }

    public void setName(String auth_name) {
        this.auth_name = auth_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Auths)) {
            return false;
        }
        return Objects.equals(auth_name, ((Auths) o).auth_name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(auth_name);
    }

    @Override
    public String toString() {
        return auth_name;
    }
}
