package com.zjp.core_database

import android.provider.BaseColumns

object ComposeEntry : BaseColumns {
    const val ID = "id"
    const val NAME = "name"
    const val NAME_CN = "nameCN"
    const val DEPRECATED = "deprecated"
    const val FAMILY = "family"
    const val LEVEL = "level"
    const val LINK_WIDGET = "linkWidget"
    const val INFO = "info"
}

object NodeEntry : BaseColumns {
    const val ID = "id"
    const val WIDGET_ID = "widgetId"
    const val NAME = "name"
    const val PRIORITY = "priority"
    const val SUBTITLE = "subtitle"
    const val CODE = "code"

}


object LikeEntry : BaseColumns {
    const val ID = "id"
    const val WIDGET_ID = "widgetId"
}


object CollectEntry : BaseColumns {
    const val ID = "id"
    const val NAME = "name"
    const val NAME_CN = "nameCN"
    const val DEPRECATED = "deprecated"
    const val FAMILY = "family"
    const val LEVEL = "level"
    const val LINK_WIDGET = "linkWidget"
    const val INFO = "info"
    const val IMG = "img"
}

object CollectionNodeEntry : BaseColumns {
    const val ID = "id"
    const val WIDGET_ID = "widgetId"
    const val NAME = "name"
    const val PRIORITY = "priority"
    const val SUBTITLE = "subtitle"
    const val CODE = "code"

}