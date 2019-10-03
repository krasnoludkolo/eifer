# eifer

Simplify your conditions with _eifer_.

_Eifer_ uses [Either](https://www.vavr.io/vavr-docs/#_either) class from [vavr](https://github.com/vavr-io/vavr/)

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