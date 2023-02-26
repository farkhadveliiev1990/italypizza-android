package com.latitude22.homemdopao;

/**
 * Created by Anuved on 11-08-2016.
 */


        import android.app.ProgressDialog;
        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.os.IBinder;
        import android.text.Html;
        import android.view.Gravity;
        import android.view.View;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.ProgressBar;
        import android.widget.Toast;

        import java.util.regex.Pattern;

public class Utility{

    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


    public static ProgressDialog showLoading(ProgressDialog progress,Context context){
        try{

            progress.setMessage("Please Wait...");
            progress.setCancelable(false);
            progress.setIndeterminate(true);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return progress;
    }


    public final static Pattern MOBILE_PATTERN = Pattern.compile(

            // "[0-9]{10}"
            "^[7-9][0-9]{9}$"
    );




    public static boolean checkPhone(String phone)
    {
        return MOBILE_PATTERN.matcher(phone).matches();
    }

	/*public static void showServerError(Context ctx){
		try{
			if(ctx!=null){
				String message = ctx.getResources().getString(R.string.server_error_message);
			    Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void showNetworkError(Context ctx){
		try{
			if(ctx!=null){
				String message = ctx.getResources().getString(R.string.network_error_message);
			    Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
				final AlertDialog adialog=new AlertDialog.Builder(ctx).create();
				adialog.setTitle("Message");
				adialog.setMessage("Internet Error!");
				adialog.setButton("OK",new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
					}
				});
				adialog.show();

			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}*/


    public static void hideLoading(ProgressDialog progress){
        try{
            if(progress!=null){
                if(progress.isShowing()){
                    progress.dismiss();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void showDownloading(ProgressBar progress){
        if(progress!=null){

            progress.setVisibility(View.VISIBLE);

        }
    }

    public static void hideDownloading(ProgressBar progress){
        if(progress!=null){
            progress.setVisibility(View.GONE);
        }
    }

    public static final boolean isInternetOn(Context ctx) {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            //     Toast.makeText(ctx, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(ctx, " Not Connected to internet .Please try again ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    public static boolean isStringNullOrBlank(String str){
        if(str==null){
            return true;
        }
        else if(str.equals("null") || str.equals("")){
            return true;
        }
        return false;
    }
    public static boolean isStringNull(int  id){
        if(id==0){
            return true;
        }

        return false;
    }
    public static void showToast(String message,Context ctx){
        try{
            Toast toast = null;
            if(ctx!=null)
                toast= Toast.makeText(ctx,Html.fromHtml(message), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
            return;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    /*	public static  boolean isStringAlphaNumeric(String str,Context ctx)
    {
        return false;
        Pattern pattern = Pattern.compile("^[a-zA-Z]*$");
        Pattern pattern1 = Pattern.compile("^[0-9]*$");
        int j=0,k=0;
        for(int i=0;i<str.length();i++)
        {
            if(Character.isAlphabetic(str.charAt(i)))
            {
                j=1;
                break;
            }

        }

        for(int l=0;l<str.length();l++)
        {
            if(Character.isDigit(str.charAt(l)))
            {
                k=1;
                break;
            }

        }
        if(j==1 && k==1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }*/
    //method to close keyboard
    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

}
