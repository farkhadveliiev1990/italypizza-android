package com.latitude22.homemdopao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class UtilityMethod {

    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static ProgressDialog showLoading(ProgressDialog progress, Context context){
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

            Toast.makeText(ctx, " Verifique a ligação de internet .Tente novamente mais tarde ", Toast.LENGTH_LONG).show();
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
    public static boolean isStringFirstSpace(String str){
        if(Character.isWhitespace(str.charAt(0))){
            return true;
        }

        return false;
    }

    public static void showToast(String message, Context ctx){
        try{
            Toast toast = null;
            if(ctx!=null)
                toast= Toast.makeText(ctx, Html.fromHtml(message), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
            return;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void showSnackbar(String message, final View v){
        try{
            Snackbar snackbar = Snackbar
                    .make(v, message, Snackbar.LENGTH_LONG);


            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void alertforServerError(final Context ctx, final String index)
    {
        AlertDialog dialogBuilder;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);

        // Setting Dialog Title
        alertDialog.setTitle("Mensagem...");

        // Setting Dialog Message
        alertDialog.setMessage("O webserver não responde, Tente novamente mais tarde");



        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(index.equals("1")){
                    dialog.cancel();
                    Intent i=new Intent(ctx,MyCart.class);
                    ctx.startActivity(i);
                    ((Activity)ctx).finish();
                }
                else
                {
                    dialog.cancel();
                }


            }
        });

        // Setting Negative "NO" Button

        // Showing Alert Message
        alertDialog.setCancelable(false);

        dialogBuilder = alertDialog.create();
        dialogBuilder.show();
    }
    public static void NoInternet(View v){
        final Snackbar snackbar = Snackbar
                .make(v, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        snackbar.show();

    }
    public static void alertforNoData(Context ctx)
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);

        // Setting Dialog Title
        alertDialog.setTitle("Message...");

        // Setting Dialog Message
        alertDialog.setMessage("No Data Found");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Setting Negative "NO" Button

        // Showing Alert Message
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

}
