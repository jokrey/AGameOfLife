# A Game Of Life

A Desktop Implementation of Conway's famous Game of Life.

### Installing

* Build it yourself - Clone the repository with the 'git clone' command.
(Additionally there are a number of dependencies on the jokrey.utilities.animation package(another repository), and utility-algorithms-java and utility-algorithms-kotlin, so clone that into the path also)
* Download one of the releases (possibly easier)
* Download the android app that uses the same underlying engine. (Search the Google Play Store)

## Features

 * Running the GameOfLife simulation
 * Pausing the simulation
 * Stepping through the simulation
 * Choosing between a solid, dead border and a border cycling logic
 * Choose game board size
 * Choose ticks per second (i.e. the number of simulation steps calculated per second)
 * Store and load a game board (using the efficient bwimg storage algorithm from the utility-algorithms-kotlin rep)
 * clear the entire game board with a single click

## Usage

Click tiles to toggle their 'aliveness'. Use the UI panels on the footer to choose surrounding parameters.
Press Ctrl and use the mouse to move the game board or zoom in on it(out of the box AnimationEngine functionality).

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgement

* The idea and algorithm behind this is obviously not mine. It is Conway's famous [Game Of Life (wiki)](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life).