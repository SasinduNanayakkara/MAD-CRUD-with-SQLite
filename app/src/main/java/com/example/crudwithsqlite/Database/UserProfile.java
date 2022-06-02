package com.example.crudwithsqlite.Database;

import android.provider.BaseColumns;

public final class UserProfile {
    private UserProfile() {}

    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "UserInfo";
        public static final String COLUMN_1 = "userName";
        public static final String COLUMN_2 = "dateOfBirth";
        public static final String COLUMN_3 = "password";
        public static final String COLUMN_4 = "gender";

    }
}
