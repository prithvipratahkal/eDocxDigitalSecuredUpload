package com.example.sunshine.edocx.app;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import it.unisa.dia.gas.jpbc.Element;

import static com.example.sunshine.edocx.app.LoginActivity.email;


public class Leftmenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PICK_IMAGE_REQUEST = 0;
    FirebaseDatabase reff= FirebaseDatabase.getInstance();
    DatabaseReference mDatabase =reff.getReference("userDB");
    Query query;
    String path;
    private StorageReference mStorageRef;
    StorageReference userRef;
    private String realPath;
    //Firebase
    FirebaseStorage storage,dis;
    StorageReference storageReference,disref;
    private ImageView mImageView;
    private ListView listview;
    private StorageReference dir;
    private Context context= Leftmenu.this;
    /*ArrayList<String> images = new ArrayList<String>();
    ArrayList<String> url = new ArrayList<String>();*/
    String images[]=new String[10];
    public static DataSnapshot snap;
    String url[]=new String[10];
    String img;
    int i=0,count;
    //  FileDownloadTask.TaskSnapshot taskSnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leftmenu);
        // setContentView(R.Layout.imageView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        mStorageRef=firebaseStorage.getReference();
        userRef=mStorageRef.child(email);
        mImageView=findViewById(R.id.imageView);
        listview=findViewById(R.id.listview);


       /* listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(Leftmenu.this,"long clicked  "+pos,Toast.LENGTH_LONG).show();
                DownloadDialog dd = new DownloadDialog();
                dd.getShowsDialog();
                return true;
            }
        });*/


        query=mDatabase.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String Url;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snap = snapshot;
                        UserInformation user = snapshot.getValue(UserInformation.class);
                        //String id=snapshot.getKey();
                        count = user.getCount() - 1;
                        //   FileDownloadTask.TaskSnapshot taskSnapshot;
                        for(i=0; count >= i; i++)
                        {
                            Url = snapshot.child("img"+(i+1)).getValue().toString();
                            images[i]="img"+(i+1);
                            url[i]=Url;
                            // Toast.makeText(Leftmenu.this,url[i],Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        CustomListView custom=new CustomListView(Leftmenu.this,images,url);


        // Get the Layout Parameters for ListView Current Item View
        //ViewGroup.LayoutParams params = listview.getLayoutParams();

        // Set the height of the Item View
        //params.height = listview.getDividerHeight();
        //listview.setLayoutParams(params);
        listview.setAdapter(custom);

        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
            }
        });*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Upload File", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
               /* Intent target = FileUtils.createGetContentIntent();
                Intent intent =Intent.createChooser(target,getString())*/
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri =Uri.parse("/storage");
                intent.setDataAndType(uri,"*/*");
                startActivityForResult(intent,0);


            }
        });

        //loadImages();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hv=navigationView.getHeaderView(0);
        TextView tb=(TextView)hv.findViewById(R.id.eid);
        tb.setText(LoginActivity.etemp);
        tb=(TextView)hv.findViewById(R.id.Name);
        String y="Hello ," + email;
        tb.setText(y);
        navigationView.setNavigationItemSelectedListener(this);

        dis = FirebaseStorage.getInstance();
        dir = dis.getReference();
        // StorageReference disref = dir.child("image/_20151220_220346.JPG");         //Toast.makeText(Leftmenu.this,"after connection",Toast.LENGTH_SHORT).show();
        try {
            //final File localFile = File.createTempFile("images", "jpg");
            //Toast.makeText(Leftmenu.this,"after file",Toast.LENGTH_SHORT).show();

            /*disref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(Leftmenu.this,"inside ear",Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    mImageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });*/

            //Toast.makeText(Leftmenu.this,"after String  ",Toast.LENGTH_SHORT).show();
           /* Glide
                    .with(context)
                    .load(url)
                    .into(mImageView);*/
            //Toast.makeText(Leftmenu.this,"after glide  "+disref,Toast.LENGTH_SHORT).show();

        } catch (NullPointerException e ) {
            Toast.makeText(Leftmenu.this,"exception",Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Toast.makeText(Leftmenu.this,"entry point",Toast.LENGTH_SHORT).show();
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();


            realPath = ImageFilePath.getPath(Leftmenu.this, data.getData());
            // Toast.makeText(Leftmenu.this,"path is "+realPath,Toast.LENGTH_SHORT).show();
//                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());

            //Log.i(TAG, "onActivityResult: file path : " + realPath);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] bytarr=stream.toByteArray();
                // Log.d(TAG, String.valueOf(bitmap));

                //ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                //imageView.setImageBitmap(bitmap);
                //imageView.setVisibility(View.VISIBLE);
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                SecureRandom secureRandom = new SecureRandom();
               // byte[] key = new byte[16];
                //secureRandom.nextBytes(key);
                Toast.makeText(Leftmenu.this, "hello", Toast.LENGTH_SHORT).show();
                try {
                   UserInformation u = snap.getValue(UserInformation.class);
                  //  long temp = u.getKey();
                   //byte[] key = Longs.toByteArray(temp);
                    String ke=u.getKey();
                    Toast.makeText(Leftmenu.this, "", Toast.LENGTH_SHORT).show();
                    byte[] key=ke.getBytes("UTF-16LE");
                   // ByteBuffer t = ByteBuffer.allocate(Long.BYTES);
                    //t.putLong(temp);
                    //key = t.array();
                    Toast.makeText(Leftmenu.this,key+"====="+key.length,Toast.LENGTH_LONG).show();
                    /* String string = u.getKey(); */
                  // byte[] ke = u.getKey();
                   // Toast.makeText(this, , Toast.LENGTH_LONG).show();
                    SecretKey secretKey = new SecretKeySpec(key, "AES");
                    byte[] iv = new byte[12]; //NEVER REUSE THIS IV WITH SAME KEY
                    secureRandom.nextBytes(iv);

                    Cipher cipher;
                    cipher = Cipher.getInstance("AES/GCM/NoPadding");
                    GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv); //128 bit auth tag length

                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

                    byte[] cipherText = cipher.doFinal(bytarr);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + cipherText.length);
                    byteBuffer.putInt(iv.length);
                    byteBuffer.put(iv);
                    byteBuffer.put(cipherText);
                    byte[] cipherMessage = byteBuffer.array();
                    String s=new String(cipherMessage,"UTF-8");
                    //Bitmap bitt=BitmapFactory.decodeByteArray(cipherMessage,0,cipherMessage.length);
                    Uri urii=Uri.parse(s);

                    //decryption
                    decrypt(cipherMessage,key);



                    //uploadImage(cipherMessage);

                }catch(Exception e)
                {Toast.makeText(Leftmenu.this,e.toString()+"    EXCEPTION",Toast.LENGTH_LONG).show();

                }

                //uploadImage(uri);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void decrypt(byte[] cipherMessage,byte[] key) {
        try{
            ByteBuffer byteBuffer = ByteBuffer.wrap(cipherMessage);
            int ivLength = byteBuffer.getInt();
            if (ivLength < 12 || ivLength >= 16) { // check input parameter
                throw new IllegalArgumentException("invalid iv length");
            }
            byte[] iv = new byte[ivLength];
            byteBuffer.get(iv);
            byte[] cipherText = new byte[byteBuffer.remaining()];
            byteBuffer.get(cipherText);

            final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKey kk=new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, kk, new GCMParameterSpec(128, iv));

            byte[] plainText = cipher.doFinal(cipherText);
            //String s=new String(plainText,"UTF-8");
            //Bitmap bitt=BitmapFactory.decodeByteArray(cipherMessage,0,cipherMessage.length);
            //Uri urii=Uri.parse(s);

            uploadImage(plainText);

        } catch (Exception e) {
        }
    }

    private void uploadImage(byte[] filePath) {

        if(filePath != null)
        {
            //   Toast.makeText(Leftmenu.this,"after nectbytes",Toast.LENGTH_LONG).show();
            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            final StorageReference ref = storageReference.child(email+"/"+ UUID.randomUUID().toString());
            UploadTask upload= (UploadTask) ref.putBytes(filePath)
                    //ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Log.d(TAG, "onSuccess: uri= "+ uri.toString());
                                    path = uri.toString();
                                    query=mDatabase.orderByChild("email").equalTo(email);
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    UserInformation user = snapshot.getValue(UserInformation.class);
                                                    count = user.getCount();
                                                    count+=1;
                                                    String temp=snapshot.getKey();
                                                    mDatabase.child(temp).child("count").setValue(count);
                                                    mDatabase.child(temp).child("img"+count).setValue(path);

                                                }
                                            }
                                            images[count-1] = "img"+(count);
                                            url[count-1] = path;
                                            CustomListView custom=new CustomListView(Leftmenu.this,images,url);
                                            listview.setAdapter(custom);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                }
                            });
                            progressDialog.dismiss();
                            Toast.makeText(Leftmenu.this, "Uploaded", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Leftmenu.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });



        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.leftmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), Settings.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_slideshow) {
            startActivity(new Intent(getApplicationContext(), Settings.class));

        } else if (id == R.id.nav_send) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


}