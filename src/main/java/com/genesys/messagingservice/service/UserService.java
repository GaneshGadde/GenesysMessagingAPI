package com.genesys.messagingservice.service;

import com.genesys.messagingservice.entity.User;
import com.genesys.messagingservice.exception.NoUserFoundException;
import com.genesys.messagingservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User get(Long id) throws NoUserFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        else {
            throw new NoUserFoundException();
        }
    }

    public User getByUsername(String username) throws NoUserFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new NoUserFoundException();
        }
    }

    public List<User> getAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users;
    }

    public User add(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User addedUser = userRepository.save(user);
        return addedUser;
    }

    public User update(User user) throws NoUserFoundException {
        Optional<User> userToUpdate = userRepository.findById(user.getId());
        User updatedUser = null;
        if (userToUpdate.isPresent()) {
            if (user.getPassword() != null)
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            updatedUser = userRepository.save(userToUpdate.get().mergeUser(user));
            return updatedUser;
        } else {
            throw new NoUserFoundException();
        }
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}