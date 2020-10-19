package com.example.surveyandroidsecurity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

import com.dueeeke.tablayout.CommonTabLayout;
import com.dueeeke.tablayout.listener.CustomTabEntity;
import com.dueeeke.tablayout.listener.OnTabSelectListener;
import com.dueeeke.tablayout.utils.UnreadMsgUtils;
import com.dueeeke.tablayout.widget.MsgView;

import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AppListActivity extends AppCompatActivity {

    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    private String[] mTitles = {"Social", "Entretenimiento", "Multimedia", "Productividad","Otros"};
    private int[] mIconUnselectIds = {
            R.drawable.ic_u_social_icon, R.drawable.ic_u_games_icon,
            R.drawable.ic_u_multimedia_icon, R.drawable.ic_u_office_icon,R.drawable.ic_u_games_icon};
    private int[] mIconSelectIds = {
            R.drawable.ic_social_icon, R.drawable.ic_games_icon,
            R.drawable.ic_multimedia_icon, R.drawable.ic_office_icon, R.drawable.ic_otros_icon};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;
    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout_2;
    private String nombreUsuario;
    public int n=0;
    public String DNI;
    public String nombres;
    public static List<AppList> installedAppsSicial  =new ArrayList<AppList>();
    public static List<AppList> installedAppsEntret  =new ArrayList<AppList>();
    public static List<AppList> installedAppsmultimedia  =new ArrayList<AppList>();
    public static List<AppList> installedAppsPoductividad  =new ArrayList<AppList>();
    public static List<AppList> installedAppsotros  =new ArrayList<AppList>();



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        installedAppsSicial=getInstalledApps(0);
        installedAppsEntret=getInstalledApps(1);
        installedAppsmultimedia=getInstalledApps(2);
        installedAppsPoductividad=getInstalledApps(3);
        installedAppsotros=getInstalledApps(4);

        setContentView(R.layout.activity_common_tab);

        mFragments.add(UserAppsFragment.getInstance( 0));
        mFragments.add(UserAppsFragment.getInstance( 1));
        mFragments.add(UserAppsFragment.getInstance( 2));
        mFragments.add(UserAppsFragment.getInstance( 3));
        mFragments.add(UserAppsFragment.getInstance( 4));








           // mFragments2.add(SimpleCardFragment.getInstance("Switch Fragment " + title));



        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mDecorView = getWindow().getDecorView();

        Display display = getWindowManager().getDefaultDisplay();

        display.getHeight();
        System.out.println("tamaño de pantalla"+display.getHeight());
        mViewPager = ViewFindUtils.find(mDecorView, R.id.vp_2);

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


        mTabLayout_2 = ViewFindUtils.find(mDecorView, R.id.tl_2);




        tl_2();




        //显示未读红点



        //两位数
      //  mTabLayout_2.showMsg(0, 55);
       // mTabLayout_2.setMsgMargin(0, -5, 5);

        //三位数
      //  mTabLayout_2.showMsg(1, 100);
      //  mTabLayout_2.setMsgMargin(1, -5, 5);

        //设置未读消息红点
      //  mTabLayout_2.showDot(2);
        MsgView rtv_2_2 = mTabLayout_2.getMsgView(2);
        if (rtv_2_2 != null) {
            UnreadMsgUtils.setSize(rtv_2_2, dp2px(7.5f));
        }

        //设置未读消息背景
       mTabLayout_2.showMsg(3, 5);
        mTabLayout_2.setMsgMargin(3, 0, 5);
        MsgView rtv_2_3 = mTabLayout_2.getMsgView(3);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }
        System.out.println( "version " +Build.VERSION.RELEASE);
        System.out.println("version"+ getDeviceName());


        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edittext, null);
        final EditText editText1 = dialogView.findViewById(R.id.edit_text);
        final AlertDialog editDialog1 = new AlertDialog.Builder(this)
                .setTitle("Ingrese su DNI")
                .setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        n++;
                        DNI = editText1.getText().toString();
                        if (n==2){


                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            uploadData(DNI,nombres,DNI,Build.VERSION.RELEASE,getDeviceName(),getCPUInfo().get("Hardware"), getMemInfo().get("MemTotal"), ""+getStorageCapacity(), ""+getBatteryCapacity() );
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }).start();




                            n=0;
                        }


                    }
                })
                .create();

        editDialog1.show();

        View dialogView1 = getLayoutInflater().inflate(R.layout.dialog_edittext, null);
        final EditText editText = dialogView1.findViewById(R.id.edit_text);
        final AlertDialog editDialog = new AlertDialog.Builder(this)
                .setTitle("Ingrese su nombre completo")
                .setView(dialogView1)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        n++;
                        nombres = editText.getText().toString();
                        if (n==2){

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        uploadData(DNI,nombres,DNI,Build.VERSION.RELEASE,getDeviceName(),getCPUInfo().get("Hardware"), getMemInfo().get("MemTotal"), ""+getStorageCapacity(), ""+getBatteryCapacity() );
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }).start();


                            n=0;


                        }



                    }
                })
                .create();

        editDialog.show();


        try {
            System.out.println("cpu"+getCPUInfo().get("Hardware"));
            System.out.println("cpu"+getMemInfo().get("MemTotal"));
            System.out.println("cpu"+getStorageCapacity());
            System.out.println("cpu"+getBatteryCapacity());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void sendList(View view){

        switch (mViewPager.getCurrentItem()){

            case 0:
                for ( AppList appList : installedAppsSicial){
                System.out.println("Lista de aplicaciones SOCIAL "+appList.getName() + ""+ appList.getCriticidad());

                }
                break;

            case 1:

            for ( AppList appList : installedAppsEntret){
            System.out.println("Lista de aplicaciones ENTRETENIMIENTO "+appList.getName() + ""+ appList.getCriticidad());


        }
            break;


            case 2:


        for ( AppList appList : installedAppsmultimedia){
            System.out.println("Lista de aplicaciones MULTIMEDIA "+appList.getName() + ""+ appList.getCriticidad());


        }
        break;

            case 3:


        for ( AppList appList : installedAppsPoductividad){
            System.out.println("Lista de aplicaciones PRODUCTIVIDAD "+appList.getName() + ""+ appList.getCriticidad());


        }
        break;



            case 4:
        for ( AppList appList : installedAppsotros){
            System.out.println("Lista de aplicaciones OTROS"+appList.getName() + ""+ appList.getCriticidad());


        }
        break;



        }
       // System.out.println("LISTA DE APLICACIONES "+AppListActivity.installedAppsUpdate);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<AppList> getInstalledApps(int  nts) {

        PackageManager pm = getApplicationContext().getPackageManager();
        List<AppList> apps = new ArrayList<AppList>();
        List<PackageInfo> packs =getApplicationContext().getPackageManager().getInstalledPackages(0);
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        switch (nts){
            case 0:
                for (int i = 0; i < packs.size(); i++) {
                    PackageInfo p = packs.get(i);
                    if (p.applicationInfo.category==ApplicationInfo.CATEGORY_SOCIAL) {
                        String appName = p.applicationInfo.loadLabel(getApplicationContext().getPackageManager()).toString();
                        Drawable icon = p.applicationInfo.loadIcon(getApplicationContext().getPackageManager());
                        String packages = p.applicationInfo.packageName;
                        System.out.println(icon+":  "+packages);
                        apps.add(new AppList(appName,icon,"1",packages, getResources().getDrawable(R.drawable.ic_padlock_open, null)));

                    }
                }

                break;
            case 1:
                for (int i = 0; i < packs.size(); i++) {
                    PackageInfo p = packs.get(i);
                    if (p.applicationInfo.category==ApplicationInfo.CATEGORY_GAME || p.applicationInfo.category==ApplicationInfo.CATEGORY_VIDEO || p.applicationInfo.category==ApplicationInfo.CATEGORY_NEWS ) {
                        String appName = p.applicationInfo.loadLabel(getApplicationContext().getPackageManager()).toString();
                        Drawable icon = p.applicationInfo.loadIcon(getApplicationContext().getPackageManager());
                        String packages = p.applicationInfo.packageName;
                        System.out.println(icon+":  "+packages);
                        apps.add(new AppList(appName,icon,"1",packages,getResources().getDrawable(R.drawable.ic_padlock_open, null)));

                    }
                }

                break;
            case 2:
                for (int i = 0; i < packs.size(); i++) {
                    PackageInfo p = packs.get(i);
                    if (p.applicationInfo.category==ApplicationInfo.CATEGORY_VIDEO || p.applicationInfo.category==ApplicationInfo.CATEGORY_AUDIO || p.applicationInfo.category==ApplicationInfo.CATEGORY_IMAGE) {
                        String appName = p.applicationInfo.loadLabel(getApplicationContext().getPackageManager()).toString();
                        Drawable icon = p.applicationInfo.loadIcon(getApplicationContext().getPackageManager());
                        String packages = p.applicationInfo.packageName;
                        System.out.println(icon+":  "+packages);
                        apps.add(new AppList(appName,icon,"1",packages,getResources().getDrawable(R.drawable.ic_padlock_open, null)));

                    }
                }

                break;
            case 3:
                for (int i = 0; i < packs.size(); i++) {
                    PackageInfo p = packs.get(i);
                    if (p.applicationInfo.category==ApplicationInfo.CATEGORY_PRODUCTIVITY) {
                        String appName = p.applicationInfo.loadLabel(getApplicationContext().getPackageManager()).toString();
                        Drawable icon = p.applicationInfo.loadIcon(getApplicationContext().getPackageManager());
                        String packages = p.applicationInfo.packageName;
                        System.out.println(icon+":  "+packages);
                        apps.add(new AppList(appName,icon,"1",packages,getResources().getDrawable(R.drawable.ic_padlock_open, null)));

                    }
                }

                break;
            case 4:
                for (int i = 0; i < packs.size(); i++) {
                    PackageInfo p = packs.get(i);
                    if ((p.applicationInfo.category==ApplicationInfo.CATEGORY_UNDEFINED && !isSystemPackage(p)) || (p.applicationInfo.category==ApplicationInfo.CATEGORY_MAPS && !isSystemPackage(p))  ) {
                        String appName = p.applicationInfo.loadLabel(getApplicationContext().getPackageManager()).toString();
                        Drawable icon = p.applicationInfo.loadIcon(getApplicationContext().getPackageManager());
                        String packages = p.applicationInfo.packageName;
                        System.out.println(icon+":  "+packages);
                        apps.add(new AppList(appName,icon,"1",packages,getResources().getDrawable(R.drawable.ic_padlock_open, null)));

                    }
                }

                break;







        }









        return apps;
    }


    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }



    public String uploadData(String id, String nombre, String DNI, String version_android, String modelo, String cpu, String ram, String memoria, String bateria) throws IOException {
        String scripurl="https://ciet.pe/guardardataDispositivo.php";
        String resultado="";
        try{
            URL url= new URL(scripurl);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));


            String id1= id;
            String nommbre1= nombre;
            String dni1= DNI;
            String version1= version_android;
            String modelo1= modelo;
            String cpu1= cpu;
            String ram1= ram;
            String memoria1= memoria;
            String bateria1= bateria;

            String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id1, "UTF-8")
                    + "&" + URLEncoder.encode("nombres", "UTF-8") + "=" + URLEncoder.encode(nommbre1, "UTF-8")
                    + "&" + URLEncoder.encode("dni", "UTF-8") + "=" + URLEncoder.encode(dni1 , "UTF-8")
                    + "&" + URLEncoder.encode("version_android", "UTF-8") + "=" + URLEncoder.encode(version1, "UTF-8")
                    + "&" + URLEncoder.encode("modelo", "UTF-8") + "=" + URLEncoder.encode(modelo1, "UTF-8")
                    + "&" + URLEncoder.encode("cpu", "UTF-8") + "=" + URLEncoder.encode(cpu1, "UTF-8")
                    + "&" + URLEncoder.encode("ram", "UTF-8") + "=" + URLEncoder.encode(ram1, "UTF-8")
                    + "&" + URLEncoder.encode("memoriaInterna", "UTF-8") + "=" + URLEncoder.encode(memoria1, "UTF-8")
                    + "&" + URLEncoder.encode("bateria", "UTF-8") + "=" + URLEncoder.encode(bateria1, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();


            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);

            }

            resultado = stringBuilder.toString();




            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        }catch (Exception e){

            System.out.println("4564513132666 gg  "+e+resultado);
        }


        return resultado;
    }

    public static Map<String, String> getCPUInfo () throws IOException {

        BufferedReader br = new BufferedReader (new FileReader("/proc/cpuinfo"));

        String str;

        Map<String, String> output = new HashMap<>();

        while ((str = br.readLine ()) != null) {

            String[] data = str.split (":");

            if (data.length > 1) {

                String key = data[0].trim ().replace (" ", "_");
                if (key.equals ("model_name")) key = "cpu_model";

                output.put (key, data[1].trim ());

            }

        }

        br.close ();

        return output;

    }

    public static long getStorageCapacity(){
        StatFs stat = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        long gb = 1024 * 1024 * 1024;
        long size = stat.getTotalBytes() / gb;
        return size;
    }

    public  double getBatteryCapacity() {
        double battCapacity = 0.0d;
        Object mPowerProfile_ = null;



        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();

        }

        try {
            battCapacity = (Double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile_, "battery.capacity");

        } catch (Exception e) {
            e.printStackTrace();

        }


        return battCapacity;
    }

    public static Map<String, String> getMemInfo () throws IOException {

        BufferedReader br = new BufferedReader (new FileReader("/proc/meminfo"));

        String str;

        Map<String, String> output = new HashMap<>();

        while ((str = br.readLine ()) != null) {

            String[] data = str.split (":");

            if (data.length > 1) {

                String key = data[0].trim ().replace (" ", "_");


                output.put (key, data[1].trim ());

            }

        }

        br.close ();

        return output;

    }


    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }
    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    Random mRandom = new Random();

    private void tl_2() {
        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                System.out.println("xxx"+position);
                mViewPager.setCurrentItem(position);

            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                   // mTabLayout_2.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("scrolled");
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("page selected");
                mTabLayout_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            System.out.println("fragment");
            return mFragments.get(position);

        }
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
