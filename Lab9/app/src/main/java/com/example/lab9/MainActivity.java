package com.example.lab9;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCongViec = (ListView) findViewById(R.id.lvDanhSach);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);





        database = new Database(this, "GhiChu.sqlite",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TieuDe VARCHAR(200), NoiDung TEXT)");
        //insert data
       Cursor checkData = database.GetData("SELECT COUNT(*) FROM CongViec");
        checkData.moveToFirst();
        if (checkData.getInt(0) == 0) {
            //insert data
            database.QueryData("INSERT INTO CongViec VALUES(null, 'Mua bim bim', 'Mua 1 hộp bim bim')");
            database.QueryData("INSERT INTO CongViec VALUES(null, 'Mua bánh mì', 'Mua 1 ổ bánh mì')");
        }
        checkData.close();
        
        getDataCongViec();
    }
    
    private void getDataCongViec() {
        // Clear previous data
        arrayCongViec.clear();
        
        // Get new data
        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec");
        while (dataCongViec.moveToNext()){
            int id = dataCongViec.getInt(0);
            String tieude = dataCongViec.getString(1);
            String noidung = dataCongViec.getString(2);
            arrayCongViec.add(new CongViec(id, tieude, noidung));
        }
        dataCongViec.close();
        adapter.notifyDataSetChanged();
    }
    
    public void deleteCongViec(final int id, final String tieuDe) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa công việc " + tieuDe + " không?");
        dialogXoa.setPositiveButton("Có", (dialog, which) -> {
            database.QueryData("DELETE FROM CongViec WHERE Id = " + id);
            getDataCongViec();
        });
        dialogXoa.setNegativeButton("Không", (dialog, which) -> {});
        dialogXoa.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            dialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        EditText edtTieude = dialog.findViewById(R.id.edtTieuDe);
        EditText edtNoiDung = dialog.findViewById(R.id.edtNoiDung);
        Button btnThem = dialog.findViewById(R.id.btnThem);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        btnThem.setOnClickListener(v -> {
            String tieuDe = edtTieude.getText().toString().trim();
            String noiDung = edtNoiDung.getText().toString().trim();

            if (tieuDe.isEmpty() || noiDung.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                database.QueryData("INSERT INTO CongViec VALUES(null, '" + tieuDe + "', '" + noiDung + "')");
                Toast.makeText(MainActivity.this, "Đã thêm công việc", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getDataCongViec();
            }
        });

        btnHuy.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void dialogSua(CongViec congViec) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua_cong_viec);

        EditText edtTieude = dialog.findViewById(R.id.edtTieuDeSua);
        EditText edtNoiDung = dialog.findViewById(R.id.edtNoiDungSua);
        Button btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
        Button btnHuy = dialog.findViewById(R.id.btnHuySua);

        // Pre-populate fields with current values
        edtTieude.setText(congViec.getTieude());
        edtNoiDung.setText(congViec.getNoidung());

        btnXacNhan.setOnClickListener(v -> {
            String tieuDeMoi = edtTieude.getText().toString().trim();
            String noiDungMoi = edtNoiDung.getText().toString().trim();

            if (tieuDeMoi.isEmpty() || noiDungMoi.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                database.QueryData("UPDATE CongViec SET TieuDe = '" + tieuDeMoi + "', NoiDung = '" + noiDungMoi + "' WHERE Id = " + congViec.getId());
                Toast.makeText(MainActivity.this, "Đã cập nhật công việc", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getDataCongViec();
            }
        });

        btnHuy.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}