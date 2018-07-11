package risc.iitbbs.intellirisc;




import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class HandleCamera extends SurfaceView implements SurfaceHolder.Callback {

    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;

    public HandleCamera(Context context,SurfaceView mSurfaceView, Camera myCam) {
        super(context);
        this.camera = myCam;
        this.surfaceView = mSurfaceView;
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    /**
     *
     * @param surfaceHolder it holds the view
     *                      and the following function display the live view that is recorded by camera
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Camera.Parameters parameters;
        parameters = camera.getParameters();
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            parameters.set("orientation", "portrait");
            camera.setDisplayOrientation(90);
            parameters.setRotation(90);
        } else {

            parameters.set("orientation", "landscape");
            camera.setDisplayOrientation(0);
            parameters.setRotation(0);
        }
        camera.setParameters(parameters);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param surfaceHolder
     * @param i
     * @param i1
     * @param i2
     * the following function reacts to suface change
     * it stops the preview before changing holding surface and then again
     * start the preview display
     */
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (surfaceHolder.getSurface()==null)
        {
            return;
        }
        try {
            camera.stopPreview();
        }catch (Exception e){

        }
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }catch (IOException e)
        {
            Log.d("Error","error in surface changed"+e.getMessage());
        }

    }

    /**
     *
     * @param surfaceHolder it holds the view
     *                      and following function basically release your camera for uses,for other app when your suface
     *                      get destroyed.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();

    }
}




