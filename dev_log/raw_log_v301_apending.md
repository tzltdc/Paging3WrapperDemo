CombinedLoadStates(
    refresh=NotLoading(endOfPaginationReached=false), 
    prepend=NotLoading(endOfPaginationReached=true), 
    append=NotLoading(endOfPaginationReached=false), 

    source=LoadStates(
            refresh=NotLoading(endOfPaginationReached=false), 
            prepend=NotLoading(endOfPaginationReached=false), 
            append=NotLoading(endOfPaginationReached=false)), 
    
    mediator=LoadStates(
            refresh=NotLoading(endOfPaginationReached=false), 
            prepend=NotLoading(endOfPaginationReached=true), 
            append=NotLoading(endOfPaginationReached=false)))
