package ru.otus.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(doNotUseGetters = true)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @NonNull
    @NaturalId
    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    public User(String name, @NonNull String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof User)) { return false; }

        User user = (User) o;

        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
