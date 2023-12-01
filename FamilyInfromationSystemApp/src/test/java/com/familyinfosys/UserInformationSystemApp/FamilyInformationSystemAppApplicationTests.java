package com.familyinfosys.UserInformationSystemApp;

import com.app.userinformationsystem.model.User;
import com.app.userinformationsystem.model.UserType;
import com.app.userinformationsystem.repository.UserRepository;
import com.app.userinformationsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.server.ResponseStatusException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FamilyInformationSystemAppApplicationTests {


	@Mock
	private UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(FamilyInformationSystemAppApplicationTests.class);

	@InjectMocks
	private UserService userService;

	@Test
	public void testFindById() {
		Long userId = 1L;
		User user = userRepository.findById(userId).orElse(null);
		logger.info("User: {}", user);
		assertNotNull(user, "User should not be null");
	}

//	@Test
//	public void testFindById() {
//		Long userId = 1L;
//		System.out.println("UserId: " + userId);
//		Optional<User> optionalUser = userRepository.findByUserId(userId);
//		User user = optionalUser.orElse(null);
//		assertNotNull(user, "User should not be null");
//		// Add other assertions based on your data
//	}

	@Test
	public void testGetAllChildUsers() {
		// Mock the UserRepository behavior
		when(userRepository.findByUserType(UserType.CHILD)).thenReturn(Collections.emptyList());

		// Call the method to be tested
		List<User> result = userService.getAllChildUsers();

		// Verify the interactions and assertions
		verify(userRepository, times(1)).findByUserType(UserType.CHILD);
		assertEquals(0, result.size());
	}

	@Test
	public void testGetUserById() {
		// Given
		long userId = 1L;
		User mockUser = new User();  // Create a mock user for testing

		// Mock the behavior of userRepository.findById
		when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

		// When
		Optional<User> result = userService.getUserById(userId);

		// Then
		assertTrue(result.isPresent());  // Check if the result is present
		assertEquals(mockUser, result.get());  // Check if the result matches the mockUser

		// Verify that userRepository.findById was called with the correct argument
		verify(userRepository, times(1)).findById(userId);
	}

//	@Test
//	public void testUpdateUser_UserNotExists() {
//		// Given
//		long userId = 1L;
//		when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//		User updatedUser = new User();  // New user details for update
//
//		// When
//		User result = userService.updateUser(userId, updatedUser);
//
//		// Then
//		assertNull(result);
//		verify(userRepository, times(1)).findById(userId);
//		verify(userRepository, never()).save(any(User.class));
//	}

	@Test
	public void testUpdateUser_UserNotExists() {
		Long userId = 999L; // Use a non-existent ID
		User updatedUser = new User(/* provide updated user details */);

		assertThrows(ResponseStatusException.class, () -> userService.updateUser(userId, updatedUser));
	}

	@Test
	public void testUpdateUser() {
		// Given
		long userId = 1L;
		User existingUser = new User();
		existingUser.setUserId(userId);
		when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

		User updatedUser = new User();  // New user details for update
		updatedUser.setUserId(userId);

		// When
		User result = userService.updateUser(userId, updatedUser);

		// Then
		assertNotNull(result);
		assertEquals(userId, result.getUserId());
		verify(userRepository, times(1)).findById(userId);
		verify(userRepository, times(1)).save(existingUser); // Assuming save returns the updated user
	}

	@Test
	public void testGetUserById_UserExists() {
		// Given
		long userId = 1L;
		User mockUser = new User();
		when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

		// When
		Optional<User> result = userService.getUserById(userId);

		// Then
		assertTrue(result.isPresent());
		assertEquals(mockUser, result.get());
		verify(userRepository, times(1)).findById(userId);
	}

	@Test
	public void testGetUserById_UserNotExists() {
		// Given
		long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// When
		Optional<User> result = userService.getUserById(userId);

		// Then
		assertFalse(result.isPresent());
		verify(userRepository, times(1)).findById(userId);
	}

	@Test
	public void testDeleteUser_Success() {
		// Given
		long userId = 1L;
		User existingUser = new User();
		existingUser.setUserId(userId);

		// Mock the behavior of findById
		when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

		// When
		userService.deleteUser(userId);

		// Then
		// Verify that findById was invoked exactly once with the correct user ID
		verify(userRepository, times(1)).findById(userId);

		// Verify that deleteById was invoked exactly once with the correct user ID
		verify(userRepository, times(1)).deleteById(userId);
	}

	@Test
	public void testDeleteUser_UserNotExists() {
		// Given
		long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// When
		assertThrows(ResponseStatusException.class, () -> userService.deleteUser(userId));

		// Then
		verify(userRepository, times(1)).findById(userId);
		verify(userRepository, never()).deleteById(userId);
	}

}
