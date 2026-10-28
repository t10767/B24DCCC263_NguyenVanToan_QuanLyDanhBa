package com.example.th02app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalContacts;
    private TextView tvFavoriteContacts;
    private TextView tvEmptyState;
    private TextView tvListHint;
    private RecyclerView rvContacts;

    private final ArrayList<Contact> contactList = new ArrayList<>();
    private ContactAdapter contactAdapter;

    private ActivityResultLauncher<Intent> addContactLauncher;
    private ActivityResultLauncher<Intent> detailContactLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupResultLaunchers();
        initSampleData();
        setupRecyclerView();
        updateStatistics();

        findViewById(R.id.btnOpenAddContact).setOnClickListener(v -> openAddContactScreen());
    }

    private void initViews() {
        tvTotalContacts = findViewById(R.id.tvTotalContacts);
        tvFavoriteContacts = findViewById(R.id.tvFavoriteContacts);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        tvListHint = findViewById(R.id.tvListHint);
        rvContacts = findViewById(R.id.rvContacts);
    }

    private void setupResultLaunchers() {
        // Xử lý khi trở về từ màn hình Thêm liên hệ
        addContactLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Contact newContact;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            newContact = result.getData().getSerializableExtra(AddContactActivity.EXTRA_NEW_CONTACT, Contact.class);
                        } else {
                            newContact = (Contact) result.getData().getSerializableExtra(AddContactActivity.EXTRA_NEW_CONTACT);
                        }

                        if (newContact != null) {
                            contactList.add(newContact);
                            contactAdapter.notifyItemInserted(contactList.size() - 1);
                            rvContacts.scrollToPosition(contactList.size() - 1);
                            updateStatistics();
                        }
                    }
                }
        );

        // Xử lý khi trở về từ màn hình Chi tiết liên hệ
        detailContactLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String action = data.getStringExtra(ContactDetailActivity.EXTRA_ACTION);
                        int position = data.getIntExtra(ContactDetailActivity.EXTRA_CONTACT_POSITION, -1);

                        if (ContactDetailActivity.ACTION_DELETE.equals(action)) {
                            if (position >= 0 && position < contactList.size()) {
                                contactList.remove(position);
                                contactAdapter.notifyItemRemoved(position);
                                contactAdapter.notifyItemRangeChanged(position, contactList.size() - position);
                                updateStatistics();
                                Toast.makeText(this, "Đã xóa liên hệ thành công", Toast.LENGTH_SHORT).show();
                            }
                        } else if (ContactDetailActivity.ACTION_UPDATE.equals(action)) {
                            Contact updatedContact;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                updatedContact = data.getSerializableExtra(ContactDetailActivity.EXTRA_CONTACT, Contact.class);
                            } else {
                                updatedContact = (Contact) data.getSerializableExtra(ContactDetailActivity.EXTRA_CONTACT);
                            }

                            if (updatedContact != null && position >= 0 && position < contactList.size()) {
                                contactList.set(position, updatedContact);
                                contactAdapter.notifyItemChanged(position);
                                updateStatistics();
                            }
                        }
                    }
                }
        );
    }

    /**
     * Dữ liệu mẫu ban đầu theo đúng yêu cầu đề bài
     */
    private void initSampleData() {
        contactList.add(new Contact(
                "Nguyễn Văn An",
                "0901234567",
                "Bạn bè",
                "Nam",
                false,
                ""
        ));

        contactList.add(new Contact(
                "Trần Thị Bình",
                "0912345678",
                "Gia đình",
                "Nữ",
                true,
                "Chị gái, hay gọi buổi tối"
        ));

        contactList.add(new Contact(
                "Lê Minh Châu",
                "0987654321",
                "Công việc",
                "Khác",
                false,
                ""
        ));
    }

    private void setupRecyclerView() {
        contactAdapter = new ContactAdapter(this, contactList, (contact, position) -> {
            Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
            intent.putExtra(ContactDetailActivity.EXTRA_CONTACT, contact);
            intent.putExtra(ContactDetailActivity.EXTRA_CONTACT_POSITION, position);
            detailContactLauncher.launch(intent);
        });

        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(contactAdapter);
    }

    private void openAddContactScreen() {
        ArrayList<String> existingPhones = new ArrayList<>();
        for (Contact c : contactList) {
            existingPhones.add(c.getPhone());
        }

        Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
        intent.putStringArrayListExtra(AddContactActivity.EXTRA_EXISTING_PHONES, existingPhones);
        addContactLauncher.launch(intent);
    }

    /**
     * Cập nhật số liệu thống kê: tổng liên hệ và số liên hệ yêu thích
     */
    private void updateStatistics() {
        int total = contactList.size();
        int favorites = 0;
        for (Contact c : contactList) {
            if (c.isFavorite()) {
                favorites++;
            }
        }

        tvTotalContacts.setText(String.valueOf(total));
        tvFavoriteContacts.setText(String.valueOf(favorites));

        if (total == 0) {
            tvEmptyState.setVisibility(View.VISIBLE);
            tvListHint.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            tvListHint.setVisibility(View.VISIBLE);
        }
    }
}

