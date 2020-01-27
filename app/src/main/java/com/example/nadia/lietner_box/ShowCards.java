package com.example.nadia.lietner_box;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nadia.lietner_box.bl.cards_bl;
import com.example.nadia.lietner_box.bl.groups_bl;
import com.example.nadia.lietner_box.models.DaoMaster;
import com.example.nadia.lietner_box.models.DaoSession;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.cardDao;
import com.example.nadia.lietner_box.models.group;
import com.example.nadia.lietner_box.models.groupDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by nadia on 27/06/2016.
 */
public class ShowCards extends AppCompatActivity implements View.OnClickListener {
    String GroupName;
    private ArrayList<String> arrResultQuestion = new ArrayList<String>();
    private ArrayList<String> arrResultAnswer = new ArrayList<String>();
    private ArrayList<Integer> arrResultCardCel = new ArrayList<Integer>();
    public DaoSession daoSession;
    List <card> cards;
    card card_name;
    int grp_id;

    groups_bl grp_BL;
    ListView list;
    cards_bl crd_BL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cards);

        GroupName = getIntent().getStringExtra("GroupName");

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        FrameLayout fl_back=(FrameLayout) findViewById(R.id.flBack);
        fl_back.setOnClickListener(this);

        TextView menu = (TextView) findViewById(R.id.tv_appName);
        menu.setTypeface(custom_font);
        menu.setText(GroupName);

        TextView back = (TextView) findViewById(R.id.tv_back);
        back.setOnClickListener(this);
        back.setTypeface(font);
        back.setText(R.string.icon_back);

        listUpdate();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.flBack:
                finish();
                break;
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        listUpdate();
    }
    private void listUpdate() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");
        list = (ListView) findViewById(R.id.listViewCard);
        grp_BL=new groups_bl(this);
        crd_BL = new cards_bl(this);

        group selectedGrp;
        selectedGrp=grp_BL.getGroup(groupDao.Properties.GroupName.eq(GroupName));
        grp_id=selectedGrp.getId().intValue();
        //find all card related to that grpid
        cards=crd_BL.getCards_2con(cardDao.Properties.GroupId.eq(grp_id),cardDao.Properties.CardCel.notEq(20));
        arrResultQuestion.clear();
        arrResultAnswer.clear();
        arrResultCardCel.clear();
        //get Group Name FRom DB & put it in listVIew*
        for (int i = 0; cards.size() > 0 && cards.size() > i; i++) {
            arrResultQuestion.add(cards.get(i).getQuestion());
            arrResultAnswer.add(cards.get(i).getAnswer());
            arrResultCardCel.add(cards.get(i).getCardCel());
        }
        CustomListAdapterCard adapter = new CustomListAdapterCard(this, arrResultQuestion, arrResultAnswer,arrResultCardCel, custom_font,BNazanin);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
                final String  itemValue=(String) list.getItemAtPosition(position);
                CharSequence items[] = new CharSequence[]{"ویرایش", "حذف"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowCards.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        card_name=crd_BL.getCard(cardDao.Properties.Question.eq(itemValue));
                        switch (which) {
                            case 0:
                                Intent editeCard = new Intent(ShowCards.this, AddNewCardActivity.class);
                                editeCard.putExtra("Group_Id",grp_id);
                                editeCard.putExtra("Card_Id", card_name.getId().intValue());
                                startActivityForResult(editeCard, 1);
                                break;
                            case 1:
                                //delete selected card base on id
                                crd_BL.deleteCrd(card_name);
                                listUpdate();
                                break;
                        }
                    }
                });
                builder.show();            }
        });
    }
}
