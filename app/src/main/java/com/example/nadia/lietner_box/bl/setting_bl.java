package com.example.nadia.lietner_box.bl;

import android.content.Context;
import android.util.Log;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.date;
import com.example.nadia.lietner_box.models.setting;
import com.example.nadia.lietner_box.models.settings;

import java.util.List;

import de.greenrobot.dao.query.WhereCondition;

public class setting_bl {
    private Context _context;
    public setting_bl(Context context){
        this._context = context;
    }

    public void insertSeting(setting seting) throws CustomException {
        try{
            settings setting_model=new settings(_context);
            setting inserted;
            inserted=setting_model.insertSetting(seting);
            Log.i("statuse value is::",String.valueOf(inserted.getStatus()));
        }catch (Exception ex){
            throw ex;
        }
    }

    public void updateSeting(setting seting) throws CustomException {
        try{
            settings setting_model=new settings(_context);
            setting updated;
            updated=setting_model.updatesetting(seting);
        }catch (Exception ex){
            throw ex;
        }
    }

    public void deleteSeting(setting seting){
        settings setting_model=new settings(_context);
        try{
            setting_model.deleteSetting(seting);
        }catch (Exception ex){
            throw ex;
        }
    }

    public List<setting> readSetting(){
        try{
            settings card_model=new settings(_context);
            List<setting> settings;
            settings=card_model.readSetting();
            return settings;
        }catch(Exception ex){
            throw ex;
        }
    }
}
