package com.example.th02app;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContactTest {

    @Test
    public void testContactInitialExtraction() {
        Contact c1 = new Contact("Nguyễn Văn An", "0901234567", "Bạn bè", "Nam", false, "");
        assertEquals("A", c1.getAvatarInitial());

        Contact c2 = new Contact("Trần Thị Bình", "0912345678", "Gia đình", "Nữ", true, "Chị gái, hay gọi buổi tối");
        assertEquals("B", c2.getAvatarInitial());

        Contact c3 = new Contact("Lê Minh Châu", "0987654321", "Công việc", "Khác", false, "");
        assertEquals("C", c3.getAvatarInitial());

        Contact c4 = new Contact("David", "0933333333", "Khác", "Nam", false, "");
        assertEquals("D", c4.getAvatarInitial());
    }

    @Test
    public void testPhoneValidationPattern() {
        String validPhone1 = "0901234567";
        String validPhone2 = "0987654321";
        String invalidPhoneLength = "090123456"; // 9 digits
        String invalidPhonePrefix = "1901234567"; // does not start with 0
        String invalidPhoneCharacters = "090123456a";

        assertTrue(validPhone1.matches("^0[0-9]{9}$"));
        assertTrue(validPhone2.matches("^0[0-9]{9}$"));
        assertFalse(invalidPhoneLength.matches("^0[0-9]{9}$"));
        assertFalse(invalidPhonePrefix.matches("^0[0-9]{9}$"));
        assertFalse(invalidPhoneCharacters.matches("^0[0-9]{9}$"));
    }

    @Test
    public void testContactSetters() {
        Contact contact = new Contact("Nguyễn Văn An", "0901234567", "Bạn bè", "Nam", false, "");
        assertFalse(contact.isFavorite());

        contact.setFavorite(true);
        assertTrue(contact.isFavorite());

        contact.setNote("Đã cập nhật ghi chú");
        assertEquals("Đã cập nhật ghi chú", contact.getNote());
    }
}

