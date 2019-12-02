package com.example.nadia.lietner_box.lib;

/**
 * Created by nadia on 07/08/2016.
 */
public class  message {
    ///////////////////grp
    static String  nameIsnUniqe="نام گروه تکراری است!";
    static String idIsnUniqe="آی دی یکتا نیست!";
    static String notNullName="نام نمی تواند خالی باشد";
    static String moreThanOneGrpFound="بیشتر از یک گروه با این اسم یا آی دی وجود دارد!";
    ///////////////////card
    static String questionNotUniqe="سوال وارد شده تکراری است";
    static String answerNotUniqe="جواب وارد شده تکراری است";
    static String questionIsNull="سوال نمی تواند خالی باشد";
    static String answerIsNull="جواب نمی تواند خالی باشد";
    //****************grp****************
    public static String nameIsnUniqe_grp(){
        return nameIsnUniqe;
    }

    public static String idIsnUniqe_grp(){
        return idIsnUniqe;
    }

    public static String notNullName_grp(){
        return notNullName;
    }

    public static String moreThanOneGrpFound_grp(){
        return moreThanOneGrpFound;
    }

    //****************crd****************
    public static String idIsnUniqe_crd(){
        return idIsnUniqe;
    }
    public static  String questionNotUniqe_crd(){
        return questionNotUniqe;
    }
    public static  String answerNotUniqe_crd(){
        return answerNotUniqe;
    }
    public static  String questionIsNull_crd(){
        return questionIsNull;
    }
    public static  String answerIsNull_crd(){
        return answerIsNull;
    }



}
