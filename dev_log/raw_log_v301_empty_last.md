CombinedLoadStates(
    refresh=NotLoading(endOfPaginationReached=true), 
    prepend=NotLoading(endOfPaginationReached=true), 
    append=NotLoading(endOfPaginationReached=true), 

    source=LoadStates(
        refresh=NotLoading(endOfPaginationReached=false), 
        prepend=NotLoading(endOfPaginationReached=true), 
        append=NotLoading(endOfPaginationReached=true)), 

    mediator=LoadStates(
        refresh=NotLoading(endOfPaginationReached=true), 
        prepend=NotLoading(endOfPaginationReached=true), 
        append=NotLoading(endOfPaginationReached=true)))
