import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mohan.cameraactivity.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CameraActivity extends Activity {


    Button btnTakePhoto;
    ImageView imgTakenPhoto;
    private static final int CAM_REQUEST = 1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnTakePhoto = (Button) findViewById(R.id.button1);
        imgTakenPhoto = (ImageView) findViewById(R.id.imageview1);

        btnTakePhoto.setOnClickListener(new btnTakePhotoClicker());
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("asdfadsfsd");
        if(requestCode == CAM_REQUEST)
        {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

            imgTakenPhoto.setImageBitmap(thumbnail);
            int[] pixels = new int[thumbnail.getHeight()*thumbnail.getWidth()];

            thumbnail.getPixels( pixels, 0, thumbnail.getWidth(), 0, 0, thumbnail.getWidth(), thumbnail.getHeight());
            Colered[] color = new Colered[pixels.length];
            int length = pixels.length;
            int width = thumbnail.getWidth();
            int height = thumbnail.getHeight();
            for(int i=0; i<pixels.length;i++){
                color[i] = new Colered(getRed(pixels[i]),getBlue(pixels[i]),getGreen(pixels[i]));
            }

            for(int i=0;i<width;i++){
                for(int z=0;z<height;z++) {
                    System.out.print(color[i].red+ " ");
                }
                System.out.println();
            }

            int gap = findGap(color,length,width,height);

            System.out.println("\n\n\n\n\n " + gap);

           // getArrays(color,length,width,height);



        }

    }

    public static int findGap(Colered [] array, int length,int width, int height){
        int greatest = 0;
        int z=0;
        int gap = 0;


        for(int i=0;i<width-1;i++){

            for(z=0;z<height;z++) {
                if((Math.abs(array[i+height].red -  array[i].red) > greatest)){
                    greatest = array[i+height].red -  array[i].red;
                    gap = i;
                }
            }
            System.out.println(i + "  " + array[i*z].red);

        }
        System.out.println("\n\n\n\n" + array[gap].red + "\n\n" + array[gap-height].red + "\n\n" + array[gap + height].red);
        return gap;

    }



    public static int getRed(int color){
        return (color >> 16) & 0xFF;
    }

    public static int getGreen(int color){
        return (color >> 8) & 0xFF;
    }

    public static int getBlue(int color){
        return color & 0xFF;
    }

    public static void getArrays(Colered []array, int length, int width,int height){
        int [] redArray = new int[length];
        int [] blueArray = new int[length];
        int [] greenArray = new int[length];

        for(int i=0;i<length;i++){
            redArray[i] = array[i].red;
            blueArray[i] = array[i].blue;
            greenArray[i] = array[i].green;
        }
        CameraActivity activity = new CameraActivity();

        activity.printArray(redArray, width, height, "red");
        SystemClock.sleep(7000);
        activity.printArray(blueArray, width, height, "blue");
        SystemClock.sleep(7000);
        activity.printArray(greenArray, width, height, "green");
        SystemClock.sleep(7000);


    }

    public  void printArray(int [] array,int width,int height,String string){

            for (int x = 0; x < 10; x++) {
                System.out.print(string + "\n");
            }
            for (int i = 0; i < height; i++) {
                for (int z = 0; z < width; z++) {
                    System.out.print(array[z]+" ");

                }
                System.out.print("\n");
            }
            System.out.print("\n\n\n");



    }


    class btnTakePhotoClicker implements Button.OnClickListener
    {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent cameraintent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraintent, CAM_REQUEST);


        }

    }

    class Colered{
        int red;
        int blue;
        int green;
        Colered( int r, int g, int b){
            this.red = r;
            this.green = g;
            this.blue = b;
        }
    }
}
