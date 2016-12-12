package com.appom44.tictactoe.persistence;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by swar8080 on 11/29/2016.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile(new File("C:/Users/swar8080/Downloads/appom44.tictactoe-20161119T212212Z/.appom44.tictactoe/app/src/main/res/raw/ormlite_config.txt"));
    }
}
