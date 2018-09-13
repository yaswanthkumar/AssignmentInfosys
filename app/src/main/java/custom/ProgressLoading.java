package custom;

import android.app.ProgressDialog;
import android.content.Context;

//show progress bar when ever the sevice call it will show only dialog
public class ProgressLoading {
    private Context context;
    private ProgressDialog progressDialog;

    public ProgressLoading(Context context){
        this.context = context;
        this.progressDialog = new ProgressDialog(context);
    }

    public void onShow(){
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismiss(){
        progressDialog.dismiss();
    }
}