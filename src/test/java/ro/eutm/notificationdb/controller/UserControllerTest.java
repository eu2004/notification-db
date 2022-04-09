package ro.eutm.notificationdb.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ro.eutm.notificationdb.controller.model.NotificationUser;
import ro.eutm.notificationdb.controller.model.NotificationUserCreate;
import ro.eutm.notificationdb.controller.model.NotificationUserUpdate;
import ro.eutm.notificationdb.model.User;
import ro.eutm.notificationdb.service.UserService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testCreate() {
        //given
        when(userService.create(Mockito.any(User.class))).thenReturn(Mockito.mock(User.class));
        when(modelMapper.map(Mockito.any(User.class), eq(NotificationUser.class))).thenReturn(Mockito.mock(NotificationUser.class));

        //when
        userController.create(createNotificationUserCreate());

        //then
        verify(userService, times(1)).create(Mockito.any(User.class));
    }

    @Test
    public void testUpdate() {
        //given
        when(userService.save(Mockito.any(User.class))).thenReturn(Mockito.mock(User.class));
        when(modelMapper.map(Mockito.any(User.class), eq(NotificationUser.class))).thenReturn(Mockito.mock(NotificationUser.class));
        when(userService.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new User()));

        //when
        userController.update(createNotificationUserUpdate());

        //then
        verify(userService, times(1)).save(Mockito.any(User.class));
        verify(userService, times(1)).findById(Mockito.any(Long.class));
    }

    @Test
    public void testGetUserById() {
        //given
        User user = new User();
        user.setDevices(Collections.emptySet());
        when(userService.findById(Mockito.any(Long.class))).thenReturn(Optional.of(user));
        when(modelMapper.map(Mockito.any(User.class), eq(NotificationUser.class))).thenReturn(Mockito.mock(NotificationUser.class));

        //when
        userController.getUserById(Mockito.any(Long.class));

        //then
        verify(userService, times(1)).findById(Mockito.any(Long.class));
    }

    @Test
    public void testGetUserByEmail() {
        //given
        User user = new User();
        user.setDevices(Collections.emptySet());
        String givenEmail = "givenEmail@email.com";
        when(userService.findByEmail(Mockito.any(String.class))).thenReturn(Optional.of(user));
        when(modelMapper.map(Mockito.any(User.class), eq(NotificationUser.class))).thenReturn(Mockito.mock(NotificationUser.class));

        //when
        userController.getUserByEmail(givenEmail);

        //then
        verify(userService, times(1)).findByEmail(Mockito.any(String.class));
    }

    @Test
    public void testDeleteByEmail() {
        //given
        String givenEmail = "givenEmail@email.com";
        when(userService.deleteByEmail(Mockito.any(String.class))).thenReturn(true);

        //when
        userController.deleteByEmail(givenEmail);

        //then
        verify(userService, times(1)).deleteByEmail(Mockito.any(String.class));
    }

    private NotificationUserCreate createNotificationUserCreate() {
        NotificationUserCreate notificationUserCreate = new NotificationUserCreate();
        notificationUserCreate.setAddress("1");
        notificationUserCreate.setEmail("1");
        notificationUserCreate.setCountryCode(1);
        notificationUserCreate.setPhoneNumber(1);
        notificationUserCreate.setDevices(Collections.emptySet());
        return notificationUserCreate;
    }

    private NotificationUserUpdate createNotificationUserUpdate() {
        NotificationUserUpdate notificationUserUpdate = new NotificationUserUpdate();
        notificationUserUpdate.setAddress("1");
        notificationUserUpdate.setEmail("1");
        notificationUserUpdate.setCountryCode(1);
        notificationUserUpdate.setPhoneNumber(1);
        notificationUserUpdate.setDevices(Collections.emptySet());
        return notificationUserUpdate;
    }
}
