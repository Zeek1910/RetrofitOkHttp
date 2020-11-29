package zeek1910.com.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AppSettings {

    public static final String SHARED_PREFERENCES_NAME = "app_settings";
    public static final String KEY_FAVORITES = "KEY_FAVORITES";

    private Context context;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static AppSettings instance;

    public static AppSettings getInstance(Context cntx){
        if (instance == null){
            instance = new AppSettings(cntx);
        }
        return instance;
    }

    private AppSettings(Context cntx){
        context = cntx;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
    }

    public List<String> getFavoritesTimeTables(){
        Set<String> data = sharedPreferences.getStringSet(KEY_FAVORITES,new TreeSet<>());
        List<String> stringList = new ArrayList<>();
        stringList.addAll(data);
        return stringList;
    }

    public void addTimeTableToFavorites(String str){
        Set<String> data = sharedPreferences.getStringSet(KEY_FAVORITES,new TreeSet<>());
        data.add(str);
        editor.putStringSet(KEY_FAVORITES,data);
        editor.commit();
    }

    public void removeTimeTableFromFavorites(String str){
        Set<String> data = sharedPreferences.getStringSet(KEY_FAVORITES,new TreeSet<>());
        data.remove(str);
        editor.putStringSet(KEY_FAVORITES,data);
        editor.commit();
    }

    public boolean checkFavorites(String str){
        Set<String> data = sharedPreferences.getStringSet(KEY_FAVORITES,new TreeSet<>());
        if (data.contains(str)){
            return true;
        }
        return false;
    }
}
