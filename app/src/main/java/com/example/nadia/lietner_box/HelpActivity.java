package com.example.nadia.lietner_box;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by nadia on 12/06/2016.
 */
public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        TextView tv_menue3 = (TextView) findViewById(R.id.tv_appName3);
        tv_menue3.setText(R.string.app_name);
        tv_menue3.setTypeface(custom_font);

        FrameLayout fl_back=(FrameLayout) findViewById(R.id.fl_back_h);
        fl_back.setOnClickListener(this);

        TextView back = (TextView) findViewById(R.id.tv_back_h);
        back.setOnClickListener(this);
        back.setTypeface(font);
        back.setText(R.string.icon_back);

        TextView total_tag=(TextView) findViewById(R.id.tv_whatis_lietner);
        total_tag.setText(R.string.app_total_tag);
        total_tag.setTypeface(custom_font);

        TextView lietner_tag=(TextView) findViewById(R.id.tv_workwith_lietner);
        lietner_tag.setText(R.string.app_lietner_tag);
        lietner_tag.setTypeface(custom_font);

        TextView total_defi1=(TextView) findViewById(R.id.tv_total_defi1);
        total_defi1.setText("جعبه لایتنر یک عدد جعبه است که باهاش می\u200Cتونید n میلیون تا لغت/فرمول/نکته/و… که بخواید، حفظ کنید…");
        total_defi1.setTypeface(custom_font);


        TextView total_defi2=(TextView) findViewById(R.id.tv_total_defi2);
        total_defi2.setText("یک جعبه پنج\u200Cخانه\u200Cای است که برای یادگیری استفاده می\u200Cشود. لایتنر در واقع روش علمی یادگیری است که بر مبنای آن آموخته\u200Cها از حافظهٔ کوتاه\u200Cمدت به حافظهٔ بلندمدت منتقل می\u200Cشوند .");
        total_defi2.setTypeface(custom_font);

        TextView sub_total_defi3=(TextView) findViewById(R.id.tv_sub_total_defi3);
        sub_total_defi3.setText("روش کار: ");
        sub_total_defi3.setTypeface(custom_font);

        TextView total_defi3=(TextView) findViewById(R.id.tv_total_defi3);
        total_defi3.setText("این جعبه شامل 5 خانه است که هر خانه شامل وزنی به ترتیب 1، 2، 4، 8، 15 می باشد. وزن هر خانه به معنای تعدا روزها می باشد.");
        total_defi3.setTypeface(custom_font);

        TextView sub_total_defi4=(TextView) findViewById(R.id.tv_sub_total_defi4);
        sub_total_defi4.setText("روال کار:");
        sub_total_defi4.setTypeface(custom_font);

        TextView total_defi4=(TextView) findViewById(R.id.tv_total_defi4);
        total_defi4.setText("ابتدا هر یک از موارد یادگیری  را به ترتیب بر روی کارت به همراه پاسخ آن می نویسیم و بعد تو خونه ی با وزن 1 قرار میدیم، یک روز بعد با توجه به وزن خونه به اون مراجع می کنیم کارتها را مرور می کنیم در صورتی که سوال و جواب کارتی را بلد بودیم کارت را به خانه بعدی انتقال میدیم در صورتی که بلد نبودیم میزاریم تو خونه اول بمونه و دوباره 1 روز بعد به کارتهای موجود در خونه 1 مراجع می کنیم. اینقدر این کار تکرار میکنیم تا کارت به خونه بعدی منتقل بشه. کارتهای موجود در خانه دوم، دو روز بعد به اون مراجع می کنیم و اگه بلد بودیم به خانه بعدی و اگه نه به خونه ی 1 بر می گرده و این مرحله رو تا خونه ی 15 ادامه می دیم و هربار که لغت یا هر چیزی که روی کارت نوشتیم و قصد حفظ کردنشو داریم بلد نبودیم به خونه ی اول انتقال پیدامی کنه. وقتی که کارت وارد خونه ی 15 شد و پس از مرور همچنان سوال و جواب آن را بلد بودیم به این معنی است که سوال مود نظر وارد حافظه بلند مدت شده و هر گز فراموش نمیشه.");
        total_defi4.setTypeface(custom_font);

        TextView lietner_defi1=(TextView) findViewById(R.id.tv_lietner_defi1);
        lietner_defi1.setText("در این نرم افزار هر گروه حکم یک جعبه را دارد که می\u200Cتوان در آن کارتهای مختلفی را قرار داد.پس از ورود به نرم افزار می\u200Cتوانیم گروه خود را بسازید و سپس پس از ورود به گروه کارتهای مورد نظر خود را در آن وارد کنید.");
        lietner_defi1.setTypeface(custom_font);

        TextView lietner_defi2=(TextView) findViewById(R.id.tv_lietner_defi2);
        lietner_defi2.setText("نکته: پس از ساخت کارتها امکان مرور آنها در روز بعد امکان پذیر می شود.");
        lietner_defi2.setTypeface(custom_font);

        TextView lietner_defi3=(TextView) findViewById(R.id.tv_lietner_defi3);
        lietner_defi3.setText("برای  حذف و یا ویرایش گروه ، با نگه داشتن انگشت خود برروی نام گروه موردنظر دو منو ویرایش و حذف نمایان می شود.");
        lietner_defi3.setTypeface(custom_font);

        TextView lietner_defi4=(TextView) findViewById(R.id.tv_lietner_defi4);
        lietner_defi4.setText("پس از ورود به گروه اطلاعات اولیه ای از تعداد کارتها، تعداکارتهای قابل مرور و... نمایش داده می\u200C شود. به منظور حذف و یا ویرایش کارتها بر روی منو بالای صفحه و سپس نمایش کارتها راانتخاب می کنیم. در صفحه باز شده لیست تمامی کارتهای مربوط به گروه انتخابی نمایش داده می\u200Cشود که با نگه داشتن انگشت خود برروی کارت موردنظر دو منو ویرایش و حذف نمایان می شود.");
        lietner_defi4.setTypeface(custom_font);

        TextView tvbackup=(TextView) findViewById(R.id.tv_backup);
        tvbackup.setText("در صورتی که بخواهیم از اطلاعاتی که تا کنون وارد کردیم پشتیبان بگیریم،گروهی که قرار است از آن پشتیبان بگیریم را نگه می داریم تا لیسی برای ما نمایش داده شود، از لیست نمایش داده شده پشتیبان گیری را انتخاب می کنیم. فایل با اسم گروه مورد نظر در پوشه dolphin_backup ذخیره می شود.");
        tvbackup.setTypeface(custom_font);

        TextView tvupload=(TextView) findViewById(R.id.tv_upload);
        tvupload.setText("در صورتی که قبلا عملیات پشتیبان گیری را انجام دادید،می توانید با انتخاب بارگذاری اطلاعات گروه مورد نظر را به جعبه لایتنر وارد کنید.");
        tvupload.setTypeface(custom_font);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back_h:
                finish();
                break;
            case R.id.fl_back_h:
                finish();
                break;
        }

    }
}
