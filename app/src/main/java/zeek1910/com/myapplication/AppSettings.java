package zeek1910.com.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AppSettings {

    public static final String SHARED_PREFERENCES_NAME = "app_settings";
    public static final String KEY_FAVORITES = "KEY_FAVORITES";
    public static final String KEY_LAST_OWNER = "KEY_LAST_OWNER";
    public static final String KEY_DEFAULT_OWNER = "KEY_DEFAULT_OWNER";

    private Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public AppSettings(Context cntx){
        context = cntx;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public List<String> getFavoritesTimeTables(){
        String data = sharedPreferences.getString(KEY_FAVORITES, "");
        if (data.isEmpty()){
            return new ArrayList<String>();
        }
        String[] list = data.split(";");
        return javaArr2Collection(list);
    }

    public void addTimeTableToFavorites(String str){
        String data = sharedPreferences.getString(KEY_FAVORITES, "");
        if (data.isEmpty()){
            data = str;
        }else{
            data+=";"+str;
        }
        editor.putString(KEY_FAVORITES,data);
        editor.apply();
        Log.d("devcpp","fav ->" + data);
    }

    public void removeTimeTableFromFavorites(String str){
        String data = sharedPreferences.getString(KEY_FAVORITES, "");
        if(!data.contains(";")){
            data = data.replace(str,"");
        }
        int index = data.indexOf(str);
        if(index == 0){
            data = data.replace(str+";","");
        }
        if(data.contains(str)){
            data = data.replace(";"+str,"");
        }
        editor.putString(KEY_FAVORITES,data);
        editor.apply();
    }

    public boolean checkFavorites(String str){
        String data = sharedPreferences.getString(KEY_FAVORITES, "");
        if (data.contains(str)){
            return true;
        }
        return false;
    }

    public void setLastOwner(String own){
        editor.putString(KEY_LAST_OWNER, own);
        editor.apply();
    }

    public String getLastOwner(){
        return sharedPreferences.getString(KEY_LAST_OWNER,"");
    }

    public void setDefaultOwner(String own){
        editor.putString(KEY_DEFAULT_OWNER, own);
        editor.apply();
    }

    public String getDefaultOwner(){
        return sharedPreferences.getString(KEY_DEFAULT_OWNER,"");
    }

    private ArrayList<String> javaArr2Collection(String[] array){
        ArrayList<String> stringList = new ArrayList<>();
        for(int i = 0; i< array.length;i++){
            stringList.add(array[i]);
        }
        return stringList;
    }


}
