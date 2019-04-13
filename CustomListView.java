package com.example.sunshine.edocx.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.example.sunshine.edocx.app.R.layout.listview_layout;

public class CustomListView extends ArrayAdapter<String> {
    private String[] images;
    private String[] url;
    private Activity contextt;

    public CustomListView(Activity context,String[] images,String[] url)
    {
        super(context, listview_layout,images);
        this.contextt=context;
        this.images=images;
        this.url=url;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // View r=convertView;
        ViewHolder holder=null;
        if(convertView==null)
        {
            LayoutInflater inflater=contextt.getLayoutInflater();
            convertView=inflater.inflate(listview_layout,null,true);

            holder=new ViewHolder(convertView);
            convertView.setTag(holder);

        }
        else
        {
            holder=(ViewHolder)convertView.getTag();

        }
        try {
            // HttpURLConnection connection = (HttpURLConnection) new URL(url[position]).openConnection();
            //connection.connect();
            //InputStream input = connection.getInputStream();
            //Bitmap bitmap = BitmapFactory.decodeStream(input);
            // Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url[position]).openConnection().getContent());
            //Bitmap bitmap = BitmapFactory.decodeStream(new URL(url[position]).openConnection().getInputStream());
            // holder.img.setImageDrawable(new BitmapDrawable(bitmap));
            holder.txt.setText(images[position]);
            Glide.with(contextt).load(url[position]).into(holder.img);
        }catch(Exception e){
            //System.out.print("inside exception pa");
            Toast.makeText(contextt,"inside exception pa",Toast.LENGTH_LONG).show();
            //startActivity(new Intent(getApplicationContext(), ChangePassword.class));
            holder.txt.setText("Exception");
        }
        //return super.getView(position, convertView, parent);
        return  convertView;
    }
    static  class ViewHolder{
        TextView txt;
        ImageView img;
        ViewHolder(View v)
        {
            txt=v.findViewById(R.id.textView);
            img=v.findViewById(R.id.imageView);

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

          //  uploadImage(plainText);

        } catch (Exception e) {
        }
    }
}