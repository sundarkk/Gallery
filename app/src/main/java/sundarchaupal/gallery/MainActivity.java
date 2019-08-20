package sundarchaupal.gallery;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import java.io.IOException;

import static sundarchaupal.gallery.SQLiteOpenHelper.DBHelper.IMAGE;

public class MainActivity extends AppCompatActivity  {
    private static final String CREATE_IMAGES_TABLE = "Image_table";
    Button btnview,submit,location;
    EditText editText;
    ImageView imageView;
    Bitmap bitmap;
    int SELECT_IMAGE=0;
    SQLiteDatabase sqLiteDatabase;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

  //  private LocationAddressResultReceiver addressResultReceiver;

    private TextView etnumber;

    private Location currentLocation;

    private LocationCallback locationCallback;

    private  static final int CAMERA_REQUEST = 1888;
    private TextView etname,etemail,etcompany;
    String name,city,company,country;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnview = findViewById(R.id.btnview);
        imageView = findViewById(R.id.imageview);
        submit = findViewById(R.id.btn_submit);
        location = findViewById(R.id.location);
        sqLiteDatabase=openOrCreateDatabase("db",  MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_IMAGES_TABLE);


//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 insertImage();
//            }
//        });
location.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String svillage = etname.getText().toString();
        Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("Village", svillage);
        intent.putExtras(bundle1);

        String simagetype = etemail.getText().toString();
        Bundle bundle2 = new Bundle();
        bundle2.putString("ImageType", simagetype);
        intent.putExtras(bundle2);
        intent.putExtra("mimagetype", etemail.toString());

        String saddress = etnumber.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("Address",saddress);
        intent.putExtras(bundle);
        intent.putExtra("maddress",etnumber.toString());
        startActivity(intent);
    }
});
      //  etname=(TextView) findViewById(R.id.villageName);
        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("location");
        etname.setText(s);

       // etemail=(TextView) findViewById(R.id.imageType);
        Bundle b1 = getIntent().getExtras();
        String s1 = b1.getString("imageType");
        etemail.setText(s1);


        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);

            }
        });

    }


//    private void insertImage(byte[] imageBytes) {
//        ContentValues cv = new ContentValues();
//        cv.put(IMAGE, imageBytes);
//        db.insert(IMAGES_TABLE, null, cv);
//    }


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    try
                    {

                        bitmap = MediaStore.Images.Media.getBitmap(MainActivity.this .getContentResolver(), data.getData());
                        imageView.setImageBitmap(bitmap);

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
