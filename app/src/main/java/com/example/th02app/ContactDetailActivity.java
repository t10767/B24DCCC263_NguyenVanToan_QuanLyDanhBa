package com.example.th02app;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

public class ContactDetailActivity extends AppCompatActivity {

    public static final String EXTRA_CONTACT = "EXTRA_CONTACT";
    public static final String EXTRA_CONTACT_POSITION = "EXTRA_CONTACT_POSITION";
    public static final String EXTRA_ACTION = "EXTRA_ACTION";

    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_DELETE = "ACTION_DELETE";

    private Contact contact;
    private int contactPosition = -1;

    private TextView tvDetailAvatar;
    private TextView tvDetailName;
    private TextView tvDetailPhone;
    private TextView tvDetailGroup;
    private TextView tvDetailGender;
    private TextView tvDetailNote;
    private CheckBox cbDetailFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        readIntentData();
        initViews();
        displayContactData();
        setupListeners();
        setupBackPressedHandler();
    }

    private void readIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            contactPosition = intent.getIntExtra(EXTRA_CONTACT_POSITION, -1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                contact = intent.getSerializableExtra(EXTRA_CONTACT, Contact.class);
            } else {
                contact = (Contact) intent.getSerializableExtra(EXTRA_CONTACT);
            }
        }
    }

    private void initViews() {
        tvDetailAvatar = findViewById(R.id.tvDetailAvatar);
        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailPhone = findViewById(R.id.tvDetailPhone);
        tvDetailGroup = findViewById(R.id.tvDetailGroup);
        tvDetailGender = findViewById(R.id.tvDetailGender);
        tvDetailNote = findViewById(R.id.tvDetailNote);
        cbDetailFavorite = findViewById(R.id.cbDetailFavorite);
    }

    private void displayContactData() {
        if (contact == null) {
            Toast.makeText(this, "Không tìm thấy thông tin liên hệ", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvDetailAvatar.setText(contact.getAvatarInitial());
        int color = ContextCompat.getColor(this, contact.getAvatarColorRes());
        ViewCompat.setBackgroundTintList(tvDetailAvatar, ColorStateList.valueOf(color));

        tvDetailName.setText(contact.getName());
        tvDetailPhone.setText(contact.getPhone());
        tvDetailGroup.setText(contact.getGroup());
        tvDetailGender.setText(contact.getGender());

        String note = contact.getNote();
        if (TextUtils.isEmpty(note)) {
            tvDetailNote.setText("(Không có ghi chú)");
            tvDetailNote.setTextColor(ContextCompat.getColor(this, R.color.text_hint));
        } else {
            tvDetailNote.setText(note);
            tvDetailNote.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        }

        cbDetailFavorite.setChecked(contact.isFavorite());
    }

    private void setupListeners() {
        // Toolbar back
        ImageButton btnToolbarBack = findViewById(R.id.btnDetailToolbarBack);
        btnToolbarBack.setOnClickListener(v -> returnWithUpdatedData());

        // Nút Gọi điện
        findViewById(R.id.btnCall).setOnClickListener(v -> makePhoneCall());

        // Nút Quay lại
        findViewById(R.id.btnDetailBack).setOnClickListener(v -> returnWithUpdatedData());

        // Nút Xóa liên hệ
        findViewById(R.id.btnDeleteContact).setOnClickListener(v -> confirmDeleteContact());
    }

    private void setupBackPressedHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                returnWithUpdatedData();
            }
        });
    }

    private void makePhoneCall() {
        if (contact == null || TextUtils.isEmpty(contact.getPhone())) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + contact.getPhone()));
        startActivity(dialIntent);
    }

    private void returnWithUpdatedData() {
        if (contact != null) {
            contact.setFavorite(cbDetailFavorite.isChecked());
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_ACTION, ACTION_UPDATE);
            resultIntent.putExtra(EXTRA_CONTACT, contact);
            resultIntent.putExtra(EXTRA_CONTACT_POSITION, contactPosition);
            setResult(RESULT_OK, resultIntent);
        }
        finish();
    }

    private void confirmDeleteContact() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa liên hệ " + (contact != null ? contact.getName() : "") + " không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(EXTRA_ACTION, ACTION_DELETE);
                    resultIntent.putExtra(EXTRA_CONTACT_POSITION, contactPosition);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}

