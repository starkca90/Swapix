package net.swapix.swapix;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.api.client.http.GenericUrl;

import net.swapix.oauth.OAuth2Helper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by caseystark on 1/10/15.
 */
public class FragmentSwapix extends Fragment {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private final String TAG = FragmentSwapix.class.getCanonicalName();
    Button butTake;
    Button butUpld;
    ImageView imPrev;
    private Uri fileUri;

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.geo_cam, menu);
        return true;
    }*/

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "GeoCamImages");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("FragmentSwapix", "failed to create image directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE)
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        else if (type == MEDIA_TYPE_VIDEO)
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        else
            return null;

        return mediaFile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_swapix, container, false);

        imPrev = (ImageView) view.findViewById(R.id.ivImagePrev);

        butTake = (Button) view.findViewById(R.id.butTakePic);
        butTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Take Pressed");
                // Create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        butUpld = (Button) view.findViewById(R.id.butUploadPic);
        butUpld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Upload Pressed");
                // TODO: Upload Picture
                Toast.makeText(getActivity(), "Uploading Picture", Toast.LENGTH_SHORT).show();
                if (fileUri != null) {
                    new UploadToServer().execute(fileUri.getPath(), "http://picture.jessestark.com/pictures/upload_media");

                    // Do stuff
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(getActivity(), "Image saved to:\n" +
                        fileUri.getPath(), Toast.LENGTH_SHORT).show();
                imPrev.setImageURI(fileUri);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "User Canceled: Photo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Fail: Photo", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Video captured and saved to fileUri specified in the Intent
                Toast.makeText(getActivity(), "Video saved to:\n" +
                        fileUri.getPath(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "User Canceled: Video", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Fail: Video", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
