package com.example.demo.Model;

import android.provider.BaseColumns;

public final class Table {
    private Table(){}
    // Data table Categories
    public static class CategoriesTable implements BaseColumns{
        public  static final String table_Name="categories";
        public static final String column_Name="name";

    }
    // Data table Question
    public static class QuestionTable implements BaseColumns{
        public static final String table_Name="questions";
        public static final String column_Question="question";
        public static final String column_Option1="option1";
        public static final String column_Option2="option2";
        public static final String column_Option3="option3";
        public static final String column_Option4="option4";
        public static final String column_Answer="answer";

        public  static final String column_Category_Id="id_categories";
    }


}
