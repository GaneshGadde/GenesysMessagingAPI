package com.genesys.messagingservice;

import com.genesys.messagingservice.entity.Action;
import com.genesys.messagingservice.entity.Errors;
import com.genesys.messagingservice.entity.Message;
import com.genesys.messagingservice.entity.User;
import com.genesys.messagingservice.exception.NoMessageFoundException;
import com.genesys.messagingservice.exception.NoUserFoundException;
import com.genesys.messagingservice.service.ActionService;
import com.genesys.messagingservice.service.ErrorsService;
import com.genesys.messagingservice.service.MessageService;
import com.genesys.messagingservice.service.OperatorService;
import com.genesys.messagingservice.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class MessagingServiceApplicationTests {
	

	@Autowired
	UserService userService;

	@Autowired
	MessageService messageService;

	@Autowired
	OperatorService operatorService;

	@Autowired
	ActionService actionService;

	@Autowired
	ErrorsService errorsService;

	@Test
	void testOperatorService() throws NoUserFoundException, NoMessageFoundException {
		// create users
		User user1 = new User();
		user1.setUsername("testusername1");
		user1.setEmail("testemail1@gmail.com");
		user1.setPassword("testPassw0rd1");

		User user2 = new User();
		user2.setUsername("testusername2");
		user2.setEmail("testemail2@gmail.com");
		user2.setPassword("testPassw0rd2");

		User user3 = new User();
		user3.setUsername("testusername3");
		user3.setEmail("testemail3@gmail.com");
		user3.setPassword("testPassw0rd3");

		User user4 = new User();
		user4.setUsername("testusername4");
		user4.setEmail("testemail4@gmail.com");
		user4.setPassword("testPassw0rd4");

		User addedUser1 = operatorService.addUser(user1);
		User addedUser2 = operatorService.addUser(user2);
		User addedUser3 = operatorService.addUser(user3);
		User addedUser4 = operatorService.addUser(user4);
		
		// test message
		Message message1 = new Message();
		message1.setReceiverId(addedUser2.getId());
		message1.setSenderId(addedUser1.getId());
		message1.setSenderUsername(addedUser1.getUsername());
		message1.setBody("test body message 1");

		Message message2 = new Message();
		message2.setReceiverId(addedUser1.getId());
		message2.setSenderId(addedUser2.getId());
		message2.setSenderUsername(addedUser2.getUsername());
		message2.setBody("test body message 2");

		Message messageToUpdate = operatorService.sendMessage(message1);
		operatorService.sendMessage(message2);

		Assert.assertEquals(2, messageService.getDialog(addedUser1.getId(), addedUser2.getId()).size());

			// test sending message to non friend user
		Message message3 = new Message();
		message3.setReceiverId(addedUser4.getId());
		message3.setSenderId(addedUser1.getId());
		message3.setSenderUsername(addedUser1.getUsername());
		message3.setBody("test body message 3");

		operatorService.sendMessage(message3);
		Assert.assertEquals(0, messageService.getDialog(addedUser1.getId(), addedUser4.getId()).size());

		// test sending message to a blocked user
		Message message4 = new Message();
		message4.setReceiverId(addedUser3.getId());
		message4.setSenderId(addedUser4.getId());
		message4.setSenderUsername(addedUser4.getUsername());
		message4.setBody("test body message 3");

		operatorService.sendMessage(message4);

		Assert.assertEquals(0, messageService.getDialog(addedUser3.getId(), addedUser4.getId()).size());

			// test message update
		messageToUpdate.setBody("updated");
		messageService.update(messageToUpdate);
		Message updatedMessage = messageService.get(messageToUpdate.getId());

		Assert.assertEquals(updatedMessage.getBody(), messageToUpdate.getBody());

			// test message delete
		Assert.assertEquals(2,messageService.getAllById(addedUser2.getId()).size());
		messageService.delete(updatedMessage);
		Assert.assertEquals(1,messageService.getAllById(addedUser2.getId()).size());

		// test delete user

		User userToDelete = userService.get(addedUser1.getId());

		operatorService.deleteUser(userToDelete);

		Assert.assertEquals(3, userService.getAll().size());

		// test actions

		List<Action> allActions = actionService.getAll();
		List<Action> allActionsOfUser1 = actionService.getAllByUserId(addedUser1.getId());
		List<Action> allSendMessageActionsOfUser1 = actionService.getAllByUserIdAndActionType(addedUser1.getId(), Action.ActionType.SEND_MESSAGE);

		Assert.assertEquals(18, allActions.size());
		Assert.assertEquals(8, allActionsOfUser1.size());
		Assert.assertEquals(2, allSendMessageActionsOfUser1.size());
	}

	@Test
	void testUser() throws NoUserFoundException {
		// test add
		User user1 = new User();
		user1.setUsername("testusername1");
		user1.setEmail("testemail1@gmail.com");
		user1.setPassword("testPassw0rd1");

		User user2 = new User();
		user2.setUsername("testusername2");
		user2.setEmail("testemail2@gmail.com");
		user2.setPassword("testPassw0rd2");

		User addedUser1 = userService.add(user1);
		User addedUser2 = userService.add(user2);

		Assert.assertEquals(user1.getUsername(),userService.get(addedUser1.getId()).getUsername());
		Assert.assertEquals(user2.getUsername(),userService.get(addedUser2.getId()).getUsername());
		Assert.assertEquals(2, userService.getAll().size());

		// test update
		addedUser1.setEmail("changedemail@gmail.com");
		addedUser2.setUsername("changedtestuser");

		User updatedUser1 = userService.update(addedUser1);
		User updatedUser2 = userService.update(addedUser2);

		Assert.assertEquals(updatedUser1.getEmail(), userService.get(addedUser1.getId()).getEmail());
		Assert.assertEquals(updatedUser2.getUsername(), userService.get(addedUser2.getId()).getUsername());

		// test delete
		userService.delete(updatedUser1);
		userService.delete(updatedUser2);

		Assert.assertEquals(0, userService.getAll().size());

		// test errors
		Errors errors = Errors.newError(addedUser1.getId(), addedUser1.getUsername(), "error");
		errorsService.add(errors);

		Assert.assertEquals(1, errorsService.getAll().size());
		Assert.assertEquals(1, errorsService.getAllByUserId(addedUser1.getId()).size());
	}

}
