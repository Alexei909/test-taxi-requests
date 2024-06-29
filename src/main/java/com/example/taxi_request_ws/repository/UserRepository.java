package com.example.taxi_request_ws.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.taxi_request_ws.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
