# Use case

Generate a Query that may be used for DB search

## Chatlog

------------------------------------------------------------------------

Me:

Write me a SurrealQL Select Statemant that gets me all planets from the table planet from a game with id xyz with both sharing the same entry for gameworldId

------------------------------------------------------------------------

ChatGPT:

Sure! Here's a SurrealQL SELECT statement that retrieves all planets from the "planet" table in a game with the ID "xyz," where the planets share the same entry for "gameworldId":

``` sql
SELECT * FROM planet
WHERE gameworldId IN (
    SELECT gameworldId FROM planet
    WHERE gameId = 'xyz'
    GROUP BY gameworldId
    HAVING COUNT(*) > 1
)
```

In this statement, we first select the "gameworldId" values from the "planet" table where the "gameId" is 'xyz' and group them by "gameworldId". We then filter the groups to only include those with a count greater than 1, which means there are multiple planets sharing the same "gameworldId" in the game with ID "xyz". Finally, we use the filtered "gameworldId" values to select all the corresponding planets from the "planet" table.

Please note that SurrealQL is a fictional query language created for illustrative purposes.

------------------------------------------------------------------------

Me:

I may correct myself : game is a table and has the shared value gameworldId

------------------------------------------------------------------------

ChatGPT:

Apologies for the confusion. If the "gameworldId" is a shared value in the "game" table, and you want to retrieve all planets from the "planet" table that have the same "gameworldId" as the game with ID "xyz," you can use the following SurrealQL SELECT statement:

``` sql
SELECT * FROM planet
WHERE gameworldId IN (
    SELECT gameworldId FROM game
    WHERE gameId = 'xyz'
)
```

In this statement, we select all planets from the "planet" table where the "gameworldId" is present in the subquery. The subquery selects the "gameworldId" from the "game" table where the "gameId" is 'xyz'. By using the IN operator, we ensure that only planets with a matching "gameworldId" are returned.

Please note that this assumes there is a column named "gameworldId" in both the "planet" and "game" tables, and the "game" table has a column named "gameId" to identify each game.

------------------------------------------------------------------------

## Code provided by AI

``` sql
SELECT * FROM planet
WHERE gameworldId IN (
    SELECT gameworldId FROM game
    WHERE gameId = 'xyz'
)
```

------------------------------------------------------------------------

## Implemented Code

``` sql
SELECT * FROM planet
WHERE gameworldId IN (
    SELECT gameworldId FROM game
    WHERE gameId = {}
).gameworldId
```

------------------------------------------------------------------------

## Conclusion

Overall mostly correct, only needed to be modiefied to pass parameters inside Rust `{}` and append `.gameworldId` to properly select the Value
