package com.example.th02app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {

    public static final String EXTRA_EXISTING_PHONES = "EXTRA_EXISTING_PHONES";
    public static final String EXTRA_NEW_CONTACT = "EXTRA_NEW_CONTACT";

    private EditText etName;
    private EditText etPhone;
    private Spinner spGroup;
    private RadioGroup rgGender;
    private CheckBox cbFavorite;
    private EditText etNote;

    private ArrayList<String> existingPhones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_EXISTING_PHONES)) {
            existingPhones = intent.getStringArrayListExtra(EXTRA_EXISTING_PHONES);
            if (existingPhones == null) {
                existingPhones = new ArrayList<>();
            }
        }

        initViews();
        setupSpinner();
        setupListeners();
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        spGroup = findViewById(R.id.spGroup);
        rgGender = findViewById(R.id.rgGender);
        cbFavorite = findViewById(R.id.cbFavorite);
        etNote = findViewById(R.id.etNote);
    }

    private void setupSpinner() {
        // Nhóm: Bạn bè, Gia đình, Công việc, Khác (theo hình 5 mặc định là Bạn bè)
        List<String> groups = Arrays.asList("Bạn bè", "Gia đình", "Công việc", "Khác");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGroup.setAdapter(adapter);
    }

    private void setupListeners() {
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        findViewById(R.id.btnSaveContact).setOnClickListener(v -> handleSaveContact());
    }

    private void handleSaveContact() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String note = etNote.getText().toString().trim();
        String group = spGroup.getSelectedItem() != null ? spGroup.getSelectedItem().toString() : "Bạn bè";

        // Xác định giới tính
        int checkedGenderId = rgGender.getCheckedRadioButtonId();
        String gender = "Nam";
        if (checkedGenderId == R.id.rbFemale) {
            gender = "Nữ";
        } else if (checkedGenderId == R.id.rbOther) {
            gender = "Khác";
        }

        boolean isFavorite = cbFavorite.isChecked();

        // 1. Kiểm tra họ và tên không được để trống
        if (TextUtils.isEmpty(name)) {
            etName.setError("Họ và tên không được để trống");
            etName.requestFocus();
            Toast.makeText(this, "Vui lòng nhập họ và tên", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Kiểm tra số điện thoại: gồm 10 chữ số và bắt đầu bằng số 0
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Số điện thoại không được để trống");
            etPhone.requestFocus();
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!phone.matches("^0[0-9]{9}$")) {
            etPhone.setError("Số điện thoại phải gồm 10 chữ số và bắt đầu bằng số 0");
            etPhone.requestFocus();
            Toast.makeText(this, "Số điện thoại phải gồm 10 chữ số và bắt đầu bằng 0", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Kiểm tra số điện thoại đã tồn tại chưa
        if (isPhoneExists(phone)) {
            etPhone.setError("Số điện thoại này đã tồn tại trong danh bạ");
            etPhone.requestFocus();
            Toast.makeText(this, "Số điện thoại đã tồn tại trong danh bạ!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng liên hệ mới
        Contact newContact = new Contact(name, phone, group, gender, isFavorite, note);

        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_NEW_CONTACT, newContact);
        setResult(RESULT_OK, resultIntent);
        Toast.makeText(this, "Thêm liên hệ thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean isPhoneExists(String phone) {
        for (String p : existingPhones) {
            if (p != null && p.replaceAll("\\s+", "").equals(phone.replaceAll("\\s+", ""))) {
                return true;
            }
        }
        return false;
    }
}

