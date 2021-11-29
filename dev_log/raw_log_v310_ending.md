CombinedLoadStates(
    refresh=NotLoading(endOfPaginationReached=false), 
    prepend=NotLoading(endOfPaginationReached=true), 
    append=NotLoading(endOfPaginationReached=true), 

    source=LoadStates(
            refresh=NotLoading(endOfPaginationReached=false), 
            prepend=NotLoading(endOfPaginationReached=false), 
            append=NotLoading(endOfPaginationReached=true)), 

    mediator=LoadStates(
            refresh=NotLoading(endOfPaginationReached=false), 
            prepend=NotLoading(endOfPaginationReached=true), 
            append=NotLoading(endOfPaginationReached=true)))
