package com.zjp.core_database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL(
            "INSERT INTO compose (`id`,`name`,`nameCN`,`deprecated`,`family`,`level`,`linkWidget`,`info`) VALUES " +
                    "('1','Text','文字组件','0','0','3.0','','用于显示文字的组件。拥有的属性非常多，足够满足你的使用需求,核心样式由modify属性控制。')"
        )
        database.execSQL(
            "INSERT INTO node (`id`,`widgetId`,`name`,`priority`,`subtitle`,`code`) VALUES " +
                    "('1','1','文字的基本样式','1','【color】 \t\t: 文字颜色  \t【Color】\n" +
                    "【fontSize】   \t: 字体大小   \t【TextUnit】\n" +
                    "【fontWeight】 \t: 字重      \t【FontWeight】\n" +
                    "【textAlign】  \t: 文字对齐   \t【TextAlign】\n" +
                    "【fontFamily】 \t: 字体      \t【FontFamily】\n','@Composable\n" +
                    "fun TextCommon() {\n" +
                    "    Text(\n" +
                    "        \"Hello World\",\n" +
                    "        color = Color.Blue,\n" +
                    "        fontSize = 30.sp,\n" +
                    "        fontWeight = FontWeight.Bold,\n" +
                    "        textAlign = TextAlign.Center,\n" +
                    "        modifier = Modifier.width(150.dp),\n" +
                    "        fontFamily = FontFamily.SansSerif\n" +
                    "    )\n" +
                    "}')"
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE Book ADD COLUMN pub_year INTEGER")
    }
}