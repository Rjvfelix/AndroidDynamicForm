package com.example.dynamicform.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.activity.result.ActivityResultLauncher;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.example.dynamicform.models.FormField;
import com.bumptech.glide.Glide;
import java.util.Calendar;

public class FormFieldView {
    public static View createField(Context context, FormField field, ActivityResultLauncher<Intent> imagePickerLauncher) {
        switch (field.getType()) {
            case FormField.TYPE_DATE:
                return createDateField(context, field);
            case FormField.TYPE_IMAGE:
                return createImageField(context, field, imagePickerLauncher);
            case FormField.TYPE_GPS:
                return createGpsField(context, field);
            default:
                return createTextField(context, field);
        }
    }

    private static View createTextField(Context context, FormField field) {
        TextInputLayout textInputLayout = new TextInputLayout(context);
        TextInputEditText editText = new TextInputEditText(context);
        
        textInputLayout.setHint(field.getLabel() + (field.isRequired() ? " *" : ""));
        
        switch (field.getType()) {
            case FormField.TYPE_NUMBER:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case FormField.TYPE_EMAIL:
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case FormField.TYPE_PHONE:
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            default:
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        
        textInputLayout.addView(editText);
        return textInputLayout;
    }

    private static View createDateField(Context context, FormField field) {
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout textInputLayout = new TextInputLayout(context);
        TextInputEditText editText = new TextInputEditText(context);
        editText.setFocusable(false);
        editText.setHint(field.getLabel() + (field.isRequired() ? " *" : ""));

        editText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, year, month, dayOfMonth) -> {
                    String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                    editText.setText(date);
                    field.setValue(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        textInputLayout.addView(editText);
        container.addView(textInputLayout);
        return container;
    }

    private static View createImageField(Context context, FormField field, ActivityResultLauncher<Intent> imagePickerLauncher) {
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);

        Button selectButton = new Button(context);
        selectButton.setText("Select Image");
        
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            400
        ));

        selectButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        if (field.getImagePath() != null) {
            Glide.with(context)
                .load(field.getImagePath())
                .into(imageView);
        }

        container.addView(selectButton);
        container.addView(imageView);
        return container;
    }

    private static View createGpsField(Context context, FormField field) {
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout coordinatesLayout = new TextInputLayout(context);
        TextInputEditText coordinatesText = new TextInputEditText(context);
        coordinatesText.setFocusable(false);
        coordinatesText.setHint("GPS Coordinates");

        Button locationButton = new Button(context);
        locationButton.setText("Get Current Location");

        if (field.getLatitude() != 0 && field.getLongitude() != 0) {
            coordinatesText.setText(
                String.format("Lat: %f, Long: %f", 
                field.getLatitude(), 
                field.getLongitude())
            );
        }

        container.addView(coordinatesLayout);
        container.addView(locationButton);
        return container;
    }
}