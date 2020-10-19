package com.example.surveyandroidsecurity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class UserAppsFragment extends Fragment {
    private String mTitle;
    private int nts=0;



    private List<AppList> installedApps;

    private ArrayList<String> Apps=new ArrayList<>();
    private List<AppList> AppsconCriticidad;
    private AppAdapter installedAppAdapter;
    private AppAdapter installedAppAdapter2;
    private String  char1;
    private String  char2;
    private int Num;
    ImageView imageView;
    Context contexto;
    ListView userInstalledApps;

    public UserAppsFragment() {
    }

    public static UserAppsFragment getInstance(int t) {

        UserAppsFragment sf = new UserAppsFragment();
        sf.nts=t;

       // sf.mTitle = title;
        return sf;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }











    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<AppList> getInstalledApps() {
        System.out.println("installled apps"+nts);
        PackageManager pm = getActivity().getApplicationContext().getPackageManager();
        List<AppList> apps = new ArrayList<AppList>();
        List<PackageInfo> packs =getActivity().getApplicationContext().getPackageManager().getInstalledPackages(0);
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        switch (nts){
            case 0:
                for (int i = 0; i < packs.size(); i++) {
                    PackageInfo p = packs.get(i);
                    if (p.applicationInfo.category==ApplicationInfo.CATEGORY_SOCIAL) {
                        String appName = p.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
                        Drawable icon = p.applicationInfo.loadIcon(getContext().getPackageManager());
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
                        String appName = p.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
                        Drawable icon = p.applicationInfo.loadIcon(getContext().getPackageManager());
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
                        String appName = p.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
                        Drawable icon = p.applicationInfo.loadIcon(getContext().getPackageManager());
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
                        String appName = p.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
                        Drawable icon = p.applicationInfo.loadIcon(getContext().getPackageManager());
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
                        String appName = p.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
                        Drawable icon = p.applicationInfo.loadIcon(getContext().getPackageManager());
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
    public class AppAdapter extends BaseAdapter implements Filterable {
        public LayoutInflater layoutInflater;
        public List<AppList> listStorage;
        private AppAdapter.ValueFilter valueFilter;
        public AppAdapter(Context context, List<AppList> customizedListView) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;
            getFilter();
        }

        @Override
        public Filter getFilter() {
            if (valueFilter == null) {

                valueFilter = new AppAdapter.ValueFilter();
            }
            return valueFilter;
        }
        @Override
        public int getCount() {
            return listStorage.size();
        }
        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }


        private class ValueFilter extends Filter {
            //Invoked in a worker thread to filter the data according to the constraint.
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                System.out.println("EL TEXTO ESCRITO ES PERFORM... "+constraint);
                listStorage = getInstalledApps();
                FilterResults results = new FilterResults();
                if (constraint != null && constraint.length() > 0) {
                    System.out.println("EL TEXTO ESCRITO IF ... "+constraint);
                    ArrayList<AppList> filterList = new ArrayList<AppList>();
                    for (int i = 0; i < listStorage.size(); i++) {
                        System.out.println("EL TEXTO ESCRITO FOR ... "+constraint);
                        if ((listStorage.get(i).getName().toUpperCase())
                                .contains(constraint.toString().toUpperCase())) {
                            AppList contacts = new AppList();
                            contacts.setName(listStorage.get(i).getName());
                            contacts.setIcon(listStorage.get(i).getIcon());
                            contacts.setCriticidad(listStorage.get(i).getCriticidad());
                            contacts.setPackaganame(listStorage.get(i).getPackaganame());
                            contacts.setLock_icon(listStorage.get(i).getLock_icon());
                            filterList.add(contacts);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                    System.out.println("EL TEXTO ESCRITO FILTER RESULT... "+constraint);
                } else {
                    results.count = listStorage.size();
                    results.values = listStorage;
                    System.out.println("EL TEXTO ESCRITO ELSE ... "+constraint);
                }
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                System.out.println("EL TEXTO ESCRITO ANTES PUBLISH ... "+constraint);
                listStorage = (ArrayList<AppList>) results.values;
                System.out.println("EL TEXTO ESCRITO DESPUES PUBLISH ... "+constraint);
                notifyDataSetChanged();
            }
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final AppAdapter.ViewHolder listViewHolder;
            if (convertView == null) {
                listViewHolder = new AppAdapter.ViewHolder();
                convertView = layoutInflater.inflate(R.layout.appinstalled, parent, false);
                listViewHolder.textInListView = (TextView) convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = (ImageView) convertView.findViewById(R.id.app_icon);
                listViewHolder.CriticidadInListView = (TextView) convertView.findViewById(R.id.app_criticidad);
                listViewHolder.imageIconLock = (ImageView) convertView.findViewById(R.id.lock_icon);
             //   listViewHolder.PackageInListView = (TextView) convertView.findViewById(R.id.list_package_name);

                convertView.setTag(listViewHolder);
            } else {
                listViewHolder = (AppAdapter.ViewHolder) convertView.getTag();
            }
            listViewHolder.textInListView.setText(listStorage.get(position).getName());
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
            listViewHolder.imageIconLock.setImageDrawable(listStorage.get(position).getLock_icon());
            listViewHolder.CriticidadInListView.setText(listStorage.get(position).getCriticidad());
//            listViewHolder.PackageInListView.setText(listStorage.get(position).getPackaganame());
            listViewHolder.imageIconLock.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int n=Integer.parseInt( listViewHolder.CriticidadInListView.getText().toString())+1;
                    CharSequence charSequence =n+"";
                    if (n==6) charSequence="1";
                    UpateLists(nts,position ,new AppList(installedApps.get(position).getName(),installedApps.get(position).getIcon(),n+"",installedApps.get(position).getPackaganame(),null));


                    System.out.println("id"+  position+nts);

                    Drawable d = getLockIcon(n-1);

                    listViewHolder.imageIconLock.setImageDrawable(d);


                    listViewHolder.CriticidadInListView.setText((charSequence));
        //            TextView tv = (TextView) tempview.findViewById(R.id.number);
      //              Integer pos = (Integer) holder.btn_plus.getTag(R.integer.btn_plus_pos);

    //                int number = Integer.parseInt(tv.getText().toString()) + 1;
  //                  tv.setText(String.valueOf(number));

//                    MainActivity.modelArrayList.get(pos).setNumber(number);

                }
            } );


           // listViewHolder.imageInListView.setOnClickListener(this);


            return convertView;
        }
        class ViewHolder {
            TextView textInListView;
            ImageView imageInListView;
            TextView CriticidadInListView;
            TextView PackageInListView;
            ImageView imageIconLock;
        }
    }

    /*public  void ActualizarListaApps(String packagename, int criti, int indice, String name, Drawable icon){
        //AppList appList = new AppList(name,icon,"nivel "+ criti,packagename);
        System.out.println(" cusqui feik");
        for ( AppList apl: installedApps ){
            System.out.println(" Cusqui recontra feik");
            if (apl.getPackaganame().equals(packagename)){
                System.out.println(" Cusqui recontra recontra feik");
                AppList appList=new AppList(apl.getName(),apl.icon, "Nivel "+criti,packagename);
                System.out.println(" Cusqui recontra recontra feik Instanciado");
                installedApps.remove(indice);
                System.out.println(" Cusqui recontra recontra feik Removido");
                installedApps.add(appList); //Appsc.add(appList);
                System.out.println(" Cusqui recontra recontra feik Agregado");
                return ;
            }else {
                //Appsc.add(apl);
            }
        }
    }*/

    private Drawable getLockIcon(int i){

        switch (i) {
            case 0:
                return getResources().getDrawable(R.drawable.ic_padlock_open, null);
            case 1:
                return getResources().getDrawable(R.drawable.ic_padlock_close1, null);
            case 2:
                return getResources().getDrawable(R.drawable.ic_padlock_close2, null);
            case 3:
                return getResources().getDrawable(R.drawable.ic_padlock_close3, null);
            case 4:
                return getResources().getDrawable(R.drawable.ic_padlock_close4, null);
        }
        return getResources().getDrawable(R.drawable.ic_padlock_open, null);
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        card_title_tv.setText(mTitle);
        Num=1;
//      AppsconCriticidad=ObtenerListaApps();
        userInstalledApps = (ListView) v.findViewById(R.id.LVApp);
        System.out.println("ky"+userInstalledApps);
        installedApps = getInstalledApps();
      //  AppListActivity.installedAppsUpdate.clear();
       // AppListActivity.installedAppsUpdate=getInstalledApps();
       // installedAppsUpdate = getInstalledApps();
        installedAppAdapter = new AppAdapter(getContext(), installedApps);

        userInstalledApps.setAdapter(installedAppAdapter);
        contexto=getContext();

     //   imageView = v.findViewById(R.id.app_icon);
    /*    imageView.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick( View v) {
                System.out.println("onClick");
                // Do something in response to button click
            }
        });*/


     /*   userInstalledApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final int position= adapterView.getPositionForView(view);



               // userInstalledApps.setSelection(i);

                CharSequence[] cs = new CharSequence[5];
                cs[0] = " Nivel 1";
                cs[1] = " Nivel 2";
                cs[2] = " Nivel 3";
                cs[3] = " Nivel 4";
                cs[4] = " Nivel 5";

                final AppList app= installedApps.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder((getContext()));
                builder.setTitle("Seleccione el nivel de criticidad para la aplicación: ").setItems(cs , new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                which++;
                                Toast.makeText(getContext(), app.getName() + " agregado con criticidad " + which, Toast.LENGTH_SHORT).show();
                                //GuardarListaApp();

                                ActualizarListaApps(app.getPackaganame(),which,i,app.getName(),app.getIcon());
                                installedAppAdapter = new AppAdapter(getContext(), installedApps);
                                userInstalledApps.setAdapter(installedAppAdapter);
                               // userInstalledApps.setSelection(position);


                            }
                        }
                );
             //   builder.show();


              //  userInstalledApps.setAdapter (installedAppAdapter);



            }


        });*/




        return v;
    }


    public void UpateLists(int categoria, int posicion, AppList appList){
        switch (categoria){
            case 0:
                AppListActivity.installedAppsSicial.remove(posicion);
                AppListActivity.installedAppsSicial.add(posicion, appList);
                return;

            case 1:
                AppListActivity.installedAppsEntret.remove(posicion);
                AppListActivity.installedAppsEntret.add(posicion,appList);
                return;

            case 2:
                AppListActivity.installedAppsmultimedia.remove(posicion);
                AppListActivity.installedAppsmultimedia.add(posicion,appList);
                return;
            case 3:
                AppListActivity.installedAppsPoductividad.remove(posicion);
                AppListActivity.installedAppsPoductividad.add(posicion,appList);
                return;
            case 4:
                AppListActivity.installedAppsotros.remove(posicion);
                AppListActivity.installedAppsotros.add(posicion,appList);
                return;




        }


    }


}