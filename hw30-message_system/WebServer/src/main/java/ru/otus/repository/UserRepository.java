package ru.otus.repository;

import ru.otus.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    void deleteByLogin(String login);
}
