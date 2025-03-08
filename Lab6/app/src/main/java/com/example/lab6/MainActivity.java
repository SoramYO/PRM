package com.example.lab6;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private Button btnMenu;
    private PopupWindow popupWindow;
    private View popupView;
    private float dX, dY;
    private Button btnChonMau;
    private ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view and set it as the action bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Thêm sự kiện click vào toolbar để mở PopupWindow tùy chỉnh
        toolbar.setOnClickListener(v -> showCustomPopupWindow(v));

        btnMenu = findViewById(R.id.btnMenu);
        // Set a click listener for the button
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });

        btnChonMau = findViewById(R.id.btnChonMau);
        mainLayout = findViewById(R.id.main);

        // Đăng ký menu ngữ cảnh cho nút btnChonMau
        registerForContextMenu(btnChonMau);

            btnChonMau.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showColorMenu(v);
        }
    });

        // Thêm log để kiểm tra đăng ký
        Log.d(TAG, "Context menu registered for btnChonMau");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
        Log.d(TAG, "Context menu created for view: " + v.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    private void showColorMenu(View v) {
    PopupMenu popup = new PopupMenu(this, v);
    MenuInflater inflater = popup.getMenuInflater();
    inflater.inflate(R.menu.context_menu, popup.getMenu());
    
    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.menuDo) {
                mainLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                return true;
            } else if (id == R.id.menuXanhLa) {
                mainLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                return true;
            } else if (id == R.id.menuXanhDuong) {
                mainLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                return true;
            } else if (id == R.id.menuVang) {
                mainLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                return true;
            }
            return false;
        }
    });
    
    popup.show();
}

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, "Item selected: " + item.getTitle());
        int id = item.getItemId();

        if (id == R.id.menuDo) {
            mainLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            return true;
        } else if (id == R.id.menuXanhLa) {
            mainLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            return true;
        } else if (id == R.id.menuXanhDuong) {
            mainLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            return true;
        } else if (id == R.id.menuVang) {
            mainLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            showToast("Share");
            return true;
        } else if (id == R.id.action_search) {
            showToast("Search");
            return true;
        } else if (id == R.id.action_email) {
            showToast("Email");
            return true;
        } else if (id == R.id.action_phone) {
            showToast("Phone");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showToast(String message) {
        Toast.makeText(this, "Bạn đã chọn item " + message, Toast.LENGTH_SHORT).show();
    }

    private void showCustomPopupWindow(View anchorView) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        // Inflate a custom layout for the popup
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_menu_layout, null);

        // Set up the PopupWindow
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.menu_frame));

        // Inflate menu items into TextViews
        TextView tvThem = popupView.findViewById(R.id.tvThem);
        TextView tvSua = popupView.findViewById(R.id.tvSua);
        TextView tvXoa = popupView.findViewById(R.id.tvXoa);

        tvThem.setText("Them");
        tvSua.setText("Sua");
        tvXoa.setText("Xoa");

        // Set click listeners for menu items
        tvThem.setOnClickListener(v -> {
            btnMenu.setText("Menu Them");
            popupWindow.dismiss();
        });
        tvSua.setOnClickListener(v -> {
            btnMenu.setText("Menu Sua");
            popupWindow.dismiss();
        });
        tvXoa.setOnClickListener(v -> {
            btnMenu.setText("Menu Xoa");
            popupWindow.dismiss();
        });

        // Enable dragging
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = popupView.getX() - event.getRawX();
                        dY = popupView.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        popupWindow.update((int) (event.getRawX() + dX), (int) (event.getRawY() + dY), -1, -1, true);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        // Show the popup window below the anchor view
        popupWindow.showAsDropDown(anchorView, 0, 0);
    }

    private void showPopupWindow(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        // Inflate a custom layout for the popup
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_menu_layout, null);

        // Set up the PopupWindow
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.menu_frame));

        // Inflate menu items into TextViews
        TextView tvThem = popupView.findViewById(R.id.tvThem);
        TextView tvSua = popupView.findViewById(R.id.tvSua);
        TextView tvXoa = popupView.findViewById(R.id.tvXoa);

        tvThem.setText("Them");
        tvSua.setText("Sua");
        tvXoa.setText("Xoa");

        // Set click listeners for menu items
        tvThem.setOnClickListener(v -> {
            btnMenu.setText("Menu Them");
            popupWindow.dismiss();
        });
        tvSua.setOnClickListener(v -> {
            btnMenu.setText("Menu Sua");
            popupWindow.dismiss();
        });
        tvXoa.setOnClickListener(v -> {
            btnMenu.setText("Menu Xoa");
            popupWindow.dismiss();
        });

        // Enable dragging
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = popupView.getX() - event.getRawX();
                        dY = popupView.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        popupWindow.update((int) (event.getRawX() + dX), (int) (event.getRawY() + dY), -1, -1, true);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        // Show the popup window below the button
        popupWindow.showAsDropDown(view, 0, 0);
    }
}