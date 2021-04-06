package com.genesys.messagingservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.genesys.messagingservice.entity.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Message findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<Message> findAllByReceiverIdOrSenderId(Long receiverId, Long senderId);

    List<Message> findAllBySenderIdAndReceiverId(Long senderId, Long receiverId);

    void deleteAllByReceiverId(Long id);
}
