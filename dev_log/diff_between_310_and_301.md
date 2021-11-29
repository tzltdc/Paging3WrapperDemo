1, For 310, there is no initial state emitted. Hence, the loading state is shown.
2, For 310, at the last of signal, `endOfPaginationReached` will be false for `NotLoading` per official documentation.

https://developer.android.com/jetpack/androidx/releases/paging

endOfPaginationReached is now always false for LoadType.REFRESH for both PagingSource and RemoteMediator
