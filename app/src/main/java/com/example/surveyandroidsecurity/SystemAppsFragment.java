package com.example.surveyandroidsecurity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
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
public class SystemAppsFragment extends Fragment {
    private String mTitle;
    int nts=0;



    private List<AppList> installedApps;
    private List<AppList> installedAppsUpdate;
    private ArrayList<String> Apps=new ArrayList<>();
    private List<AppList> AppsconCriticidad;
    private AppAdapter installedAppAdapter;
    private AppAdapter installedAppAdapter2;
    private String  char1;
    private String  char2;
    private int Num;
    Context contexto;
    ListView userInstalledApps;

    public SystemAppsFragment() {
    }

    public static SystemAppsFragment getInstance(String title) {

        SystemAppsFragment sf = new SystemAppsFragment();
       // sf.mTitle = title;
        return sf;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }






    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<AppList> getInstalledApps() {
        PackageManager pm = getActivity().getApplicationContext().getPackageManager();
        List<AppList> apps = new ArrayList<AppList>();
        List<PackageInfo> packs =getActivity().getApplicationContext().getPackageManager().getInstalledPackages(0);
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);





                for (int i = 0; i < packs.size(); i++) {
                    PackageInfo p = packs.get(i);
                    if ((isSystemPackage(p))) {


                        String appName = p.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
                        Drawable icon = p.applicationInfo.loadIcon(getContext().getPackageManager());
                        System.out.println("ICON "+icon +"  "+ appName +" "+      p.applicationInfo.category);
                        String packages = p.applicationInfo.packageName;
                        //System.out.println(icon+":  "+packages);
                        apps.add(new AppList(appName,icon,"---",packages));

                    }

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
        public View getView(int position, View convertView, ViewGroup parent) {
            AppAdapter.ViewHolder listViewHolder;
            if (convertView == null) {
                listViewHolder = new AppAdapter.ViewHolder();
                convertView = layoutInflater.inflate(R.layout.appinstalled, parent, false);
                listViewHolder.textInListView = (TextView) convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = (ImageView) convertView.findViewById(R.id.app_icon);
                listViewHolder.CriticidadInListView = (TextView) convertView.findViewById(R.id.app_criticidad);
                listViewHolder.PackageInListView = (TextView) convertView.findViewById(R.id.list_package_name);
                convertView.setTag(listViewHolder);
            } else {
                listViewHolder = (AppAdapter.ViewHolder) convertView.getTag();
            }
            listViewHolder.textInListView.setText(listStorage.get(position).getName());
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
            listViewHolder.CriticidadInListView.setText(listStorage.get(position).getCriticidad());
            listViewHolder.PackageInListView.setText(listStorage.get(position).getPackaganame());
            return convertView;
        }
        class ViewHolder {
            TextView textInListView;
            ImageView imageInListView;
            TextView CriticidadInListView;
            TextView PackageInListView;
        }
    }

    public  void ActualizarListaApps(String packagename, int criti, int indice, String name, Drawable icon){
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
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
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

        installedAppsUpdate = getInstalledApps();
        installedAppAdapter = new AppAdapter(getContext(), installedApps);
        System.out.println("linares .i." + installedApps.size());
        System.out.println("ky"+installedAppAdapter);
        userInstalledApps.setAdapter(installedAppAdapter);
        contexto=getContext();
        userInstalledApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
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
                                installedAppAdapter = new AppAdapter(getContext(), installedApps);
                                ActualizarListaApps(app.getPackaganame(),which,i,app.getName(),app.getIcon());
                                userInstalledApps.setAdapter(installedAppAdapter);

                            }
                        }
                );
                builder.show();
            }
        });

        return v;
    }
}