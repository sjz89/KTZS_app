package me.daylight.ktzs.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MediaUtil {
    public static File getFileFromMediaUri(Context context, Uri uri) {
        Log.d("Uri",uri.getPath());
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null,null, null);
        if (cursor == null) {
            img_path = uri.getPath();
        } else {
            try{
                int actual_image_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                img_path = cursor.getString(actual_image_column_index);
                cursor.close();
            }catch (IllegalArgumentException e){
                img_path= Environment.getExternalStorageDirectory().getPath()+"/Pictures"+uri.getPath().substring(10);
            }
        }
        return new File(img_path);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static byte[] getByteFromFile(File file){
        byte[] data;
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            data = new byte[(int)file.length()];
            inputStream.read(data);
            inputStream.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
