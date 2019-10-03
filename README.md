# eifer

Simplify your conditions with _eifer_.

Example:

```java
    public Either<ActionError, GameDTO> endGame(EndGameRequestDTO request) {
        return Eifer
                .when(
                        gameCheckers.gameExists(request.getGameId()),
                        gameCheckers.canEndGame(request.getGameId(), request.getUserId())
                ).orAll(
                        gameCheckers.gameExists(request.getGameId()),
                        userCheckers.isAdmin(request.getUserId())

                ).perform(
                        gameService.endGame(request.getGameId())
                );
    }
```