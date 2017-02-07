package com.contacts;

import com.contacts.model.Contact;
import com.contacts.model.User;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


/**
 * Created by Alexander Vashurin 07.02.2017
 */
public class MockitoTest {
    @Test
    public void hello() {
        List<Contact> contacts = mock(List.class);
        when(contacts.size()).thenReturn(0);

        User user = mock(User.class);
        when(user.getContacts()).thenReturn(contacts);

        User realUser = new User();
        assertTrue(user.getContacts().size() == realUser.getContacts().size());
    }
}
