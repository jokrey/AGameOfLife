package jokrey.kotlin.agameoflife

import jokrey.utilities.animation.engine.TickEngine
import jokrey.utilities.animation.pipeline.AnimationObject
import jokrey.utilities.animation.util.AEColor
import jokrey.utilities.animation.util.AEPoint
import jokrey.utilities.animation.util.AESize

open class GameOfLifeEngine(
        gameboard_size: AESize,
        var edges_are_death_mode:Boolean = true): TickEngine() {

    var gameboard  //most ugly kotlin code to date
            = Array(gameboard_size.w.toInt()) {Array(gameboard_size.h.toInt()) {false} }

    fun setGameboardSize(width:Int=getGBWidth(), height:Int=getGBHeight()) {
        if(width>=1 && height>=1) {
            val temp_gameboard = Array(width, { Array(height, { false }) })
            for (x in 0 until width) {
                for (y in 0 until height) {
                    temp_gameboard[x][y] = isAlive(x, y, edges_are_death = true)
                }
            }
            gameboard = temp_gameboard
        }
    }
    fun clearGameboard() {
        for (x in 0 until getGBWidth()) {
            for (y in 0 until getGBHeight()) {
                gameboard[x][y] = false
            }
        }
    }
    fun getGBWidth():Int {
        return gameboard.size
    }
    fun getGBHeight():Int {
        return gameboard[0].size
    }

    override fun getVirtualBoundaries(): AESize {
        return AESize(getGBWidth().toDouble(), getGBHeight().toDouble())
    }

    override fun redoDelayedTicks(): Boolean {
        return false
    }
    override fun getAllObjectsToDraw(): MutableList<AnimationObject> {
        val list = mutableListOf<AnimationObject>()
        val aliveColor = AEColor.BLACK
        val deadColor = AEColor.WHITE
        for(x in 0 until getGBWidth()) {
            for(y in 0 until getGBHeight()) {
                list.add(AnimationObject(x.toDouble(), y.toDouble(), 1.0, 1.0,
                        AnimationObject.RECT, if (gameboard[x][y]) aliveColor else deadColor))
            }
        }
        return list
    }
    override fun initiate() {
        gameboard[4][4]=true
        gameboard[4][5]=true
        gameboard[4][6]=true
    }

    private var mousePos: AEPoint? = null
    override fun locationInputChanged(p: AEPoint?, mousePressed: Boolean) {
        if(mousePressed) {
            if (p != null) {
                if ((mousePos == null || p.x.toInt() != mousePos?.x?.toInt() || p.y.toInt() != mousePos?.y?.toInt()) &&
                        p.x >= 0 && p.x < getGBWidth() && p.y >= 0 && p.y < getGBHeight()) {
                    gameboard[p.x.toInt()][p.y.toInt()] = !gameboard[p.x.toInt()][p.y.toInt()]
                }
            }
            mousePos = p
        }
    }

    public override fun calculateTickImpl() {
        val tempGameboard  //most ugly kotlin code to date
                = Array(getGBWidth()) {Array(getGBHeight()) {false} }
        for(x in 0 until getGBWidth()) {
            for (y in 0 until getGBHeight()) {
                tempGameboard[x][y]=isAliveNextTick(x, y)
            }
        }
        gameboard=tempGameboard
    }
    private fun isAliveNextTick(x:Int, y:Int):Boolean =
            if(!gameboard[x][y])
                countAliveNeighborCells(x, y)==3
            else
                countAliveNeighborCells(x, y) in 2..3
    private fun countAliveNeighborCells(x: Int, y:Int): Int {
        var counter = 0
        if(isAlive(x-1,y-1)) counter++
        if(isAlive(x-1,y)) counter++
        if(isAlive(x-1, y+1)) counter++
        if(isAlive(x, y-1)) counter++
        if(isAlive(x, y+1)) counter++
        if(isAlive(x+1, y-1)) counter++
        if(isAlive(x+1, y)) counter++
        if(isAlive(x+1, y+1)) counter++
        return counter
    }
    private fun isAlive(x: Int, y:Int, edges_are_death: Boolean=edges_are_death_mode): Boolean {
        if(edges_are_death) {
            return x>=0 && x < getGBWidth() && y>=0 && y < getGBHeight() && gameboard[x][y]
        }
        val nx = when {
                    x < 0 -> getGBWidth()-1
                    x >= getGBWidth() -> 0
                    else -> x
                }
        val ny = when {
                    y < 0 -> getGBHeight()-1
                    y >= getGBHeight() -> 0
                    else -> y
                }
        return gameboard[nx][ny]
    }

    var gameOfLifeTicksPerSecond = 3
    override fun getTicksPerSecond(): Int {
        return gameOfLifeTicksPerSecond
    }
}