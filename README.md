# bnb-nick
This is a Spigot plugin that adds a couple of commands to the server for players to change
their names, visible to other players. This includes names in the player list, or in chat.

## Usage
- Players can run `/nick [nickname]` to set a nickname. Running `/nick` without arguments
removes your nickname.
- `/whois <query>` can be used by players and the console to find a player's UUID,
username, and their currently used nickname.

## Building
To build the plugin, you need Git, Maven, and JDK 1.8 or higher.
```sh
git clone https://github.com/bnbmc-plugins/bnb-nick.git
cd bnb-nick
mvn package
```
A file called `target/bnb-nick-VERSION.jar` should appear. This can be placed inside a
server's plugins folder.
