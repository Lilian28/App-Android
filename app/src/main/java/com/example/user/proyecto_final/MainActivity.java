package com.example.user.proyecto_final;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.user.proyecto_final.adapter.PagerAdapter;
import com.theartofdev.edmodo.cropper.CropImage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Noticias"));
        tabLayout.addTab(tabLayout.newTab().setText("Teléfonos"));
        tabLayout.addTab(tabLayout.newTab().setText("Contacto"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Intent intent = new Intent(this, BienvenidaActivity.class);
                startActivityForResult(intent, 0);
                return true;
            case R.id.item2:
                Intent intent2 = new Intent(this, RedesActivity.class);
                startActivityForResult(intent2, 0);
                return true;
            case R.id.item3:
                Intent intent3 = new Intent(this, LugaresActivity.class);
                startActivityForResult(intent3, 0);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Aca llega la imagen importada/creada desde la libreria de edicion de imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Toast.makeText(this, "REQUEST IMAGE " + resultUri.getPath(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Error capturando la imagen :(", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
