# Test assignment for internship in JavaRush company #
***
## Task ##
***
Finish the app for an administrator of the network RPG. Administrator must be able to edit parameters of characters (players), create and remove characters, and distribute bans.

You need to implement these features:
1. Receive a list of all registered players.
2. Create a new player.
3. Edit the characteristics of an existing player.
4. Remove a player.
5. Get a player by id.
6. Receive a filtered list of players by the passed filters.
7. Get the number of players that match the filters.

## Details ##
***
Create an entity ***Player*** with such fields

| Variable               | Description                                                    |
|:-----------------------|:---------------------------------------------------------------|
| Long id                | Player ID                                                      |
| String name            | Character's name (up to 12 symbols)                            |
| String title           | Character's title (up to 30 symbols)                           |
| Race race              | Character's race                                               |
| Profession profession  | Character's profession                                         |
| Integer experience     | Character's experience (from 0 to 10,000,000)                  |
| Integer level          | Character's level                                              |
| Integer untilNextLevel | Amount of experience user need to gain to reach the next level |
| Date birthday          | Date of registration (from 2000 to 3000 year)                  |
| Boolean banned         | Is player banned or active                                     |

The program must count the current character's level and the experience the user needs to gain to reach the next level.
Before every saving character on the database (if creating a new character or updating characteristics of the existing one).

The formula for the current level
$$ L = {\sqrt{2500 + 200 * exp} \over 100} $$

The formula for experience until the next level
$$ 𝑁 = 50 ∙ (𝑙𝑣𝑙 + 1) ∙ (𝑙𝑣𝑙 + 2) − 𝑒𝑥 $$

## Realisation and tools ##
***
This task is an implementation of the RESTful web service.

The most interesting and challenging part of this project was a multiple columns search by the passed filters.
And I decided to use *Specification* framework from *Spring Data Jpa* for creation dynamic queries.

For characters storage I use *MySQL* database. And, of course, I use such tools as *Maven* and *Tomcat*.