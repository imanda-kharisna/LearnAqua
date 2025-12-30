package com.example.learnaqua;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private CircularProgressIndicator progressCircle;
    private TextView tvProgress;
    private int currentWater = 0;
    private final int targetWater = 1200;
    private AppDatabase db;

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Simulasi Deteksi AI
                    Toast.makeText(getContext(), "Minuman terdeteksi! (250ml)", Toast.LENGTH_SHORT).show();
                    addWater(250);
                }
            }
    );

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openCamera();
                } else {
                    Toast.makeText(getContext(), "Izin kamera ditolak", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        db = AppDatabase.getInstance(requireContext());
        progressCircle = view.findViewById(R.id.progressCircle);
        tvProgress = view.findViewById(R.id.tvProgress);
        MaterialButton btnCamera = view.findViewById(R.id.btnCamera);

        refreshData();

        view.findViewById(R.id.btn100).setOnClickListener(v -> addWater(100));
        view.findViewById(R.id.btn200).setOnClickListener(v -> addWater(200));
        view.findViewById(R.id.btn300).setOnClickListener(v -> addWater(300));
        view.findViewById(R.id.btnAddWater).setOnClickListener(v -> showQuickAddDialog());
        
        btnCamera.setOnClickListener(v -> checkCameraPermission());

        return view;
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(takePictureIntent);
    }

    private void refreshData() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        currentWater = db.waterDao().getTotalWaterToday(cal.getTimeInMillis());
        updateUI();
    }

    private void updateUI() {
        int progress = Math.min(currentWater, targetWater);
        progressCircle.setProgress(progress);
        tvProgress.setText(currentWater + " / " + targetWater);
    }

    private void showQuickAddDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_quick_add, null);
        AlertDialog dialog = new AlertDialog.Builder(requireContext()).setView(view).create();
        RecyclerView recycler = view.findViewById(R.id.recyclerQuickMl);
        EditText etCustom = view.findViewById(R.id.etCustomMl);
        Button btnAdd = view.findViewById(R.id.btnAdd);

        recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recycler.setAdapter(new QuickAddAdapter(ml -> {
            addWater(ml);
            dialog.dismiss();
        }));

        btnAdd.setOnClickListener(v -> {
            String text = etCustom.getText().toString();
            if (!text.isEmpty()) {
                addWater(Integer.parseInt(text));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void addWater(int amount) {
        db.waterDao().insert(new WaterLog(amount, System.currentTimeMillis()));
        refreshData();
    }
}
