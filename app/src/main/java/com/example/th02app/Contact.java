package com.example.th02app;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String phone;
    private String group;
    private String gender;
    private boolean isFavorite;
    private String note;

    public Contact(String name, String phone, String group, String gender, boolean isFavorite, String note) {
        this.name = name;
        this.phone = phone;
        this.group = group;
        this.gender = gender;
        this.isFavorite = isFavorite;
        this.note = note != null ? note : "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Lấy chữ cái đại diện từ từ cuối cùng của họ tên (tên gọi).
     * Ví dụ: "Nguyễn Văn An" -> "A", "Trần Thị Bình" -> "B".
     */
    public String getAvatarInitial() {
        if (name == null || name.trim().isEmpty()) {
            return "?";
        }
        String trimmed = name.trim();
        String[] parts = trimmed.split("\\s+");
        String lastWord = parts[parts.length - 1];
        if (!lastWord.isEmpty()) {
            return lastWord.substring(0, 1).toUpperCase();
        }
        return "?";
    }

    /**
     * Chọn màu đại diện dựa trên tên để các avatar có màu sắc sinh động, nhất quán.
     */
    public int getAvatarColorRes() {
        String initial = getAvatarInitial();
        int hash = Math.abs(initial.hashCode() + (name != null ? name.hashCode() : 0));
        int[] colors = new int[]{
                R.color.avatar_teal,
                R.color.avatar_orange,
                R.color.avatar_purple,
                R.color.avatar_blue,
                R.color.avatar_green
        };
        return colors[hash % colors.length];
    }
}

