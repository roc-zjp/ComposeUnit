package com.zjp.article.repository

class LeaderBlogRepository {

    fun getLeadersBlog(): List<LeaderBlog> {
        return leaderBlogs
    }
}


data class LeaderBlog(val name: String, val blogUrl: String)

val leaderBlogs = arrayListOf<LeaderBlog>(
    LeaderBlog("张风捷特烈", "https://juejin.cn/user/149189281194766"),
    LeaderBlog("恋猫de小郭", "https://juejin.cn/user/817692379985752"),
    LeaderBlog("郭霖", "https://blog.csdn.net/guolin_blog"),
    LeaderBlog("鸿洋", "https://blog.csdn.net/lmj623565791"),
    LeaderBlog("阮一峰", "https://www.ruanyifeng.com/blog/"),
    LeaderBlog("任玉刚", "https://blog.csdn.net/singwhatiwanna"),
    LeaderBlog("刘望舒", "https://liuwangshu.blog.csdn.net/"),
    LeaderBlog("扔物线", "https://rengwuxian.com/"),
)