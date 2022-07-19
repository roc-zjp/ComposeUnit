package com.zjp.common.state

sealed interface CommonUiState {
    val isLoading: Boolean
    val errorMessages: List<String?>


    /**
     * There are no posts to render.
     *
     * This could either be because they are still loading or they failed to load, and we are
     * waiting to reload them.
     */
    data class NoData(
        override val isLoading: Boolean,
        override val errorMessages: List<String?>,
        ) : CommonUiState

    /**
     * There are posts to render, as contained in [postsFeed].
     *
     * There is guaranteed to be a [selectedPost], which is one of the posts from [postsFeed].
     */
    data class HasData<T>(
        val data: T,
        override val isLoading: Boolean,
        override val errorMessages: List<String?>,
    ) : CommonUiState
}