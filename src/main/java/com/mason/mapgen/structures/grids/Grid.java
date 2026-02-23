package com.mason.mapgen.structures.grids;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.structures.iterators.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;

public class Grid<T> implements Iterable<T>{


    protected final Size size;
    protected final T[][] grid;


    public Grid(T[][] grid){
        throwIfGridInvalid(grid);
        this.grid = grid;
        size = new Size(grid[0].length, grid.length);
    }

    public Grid(Grid<T> grid){
        this.grid = grid.grid;
        this.size = grid.size;
    }

    public static <E> Grid<E> buildGrid(E[][] emptyGrid, Function<Coord, E> builder){
        Grid<E> grid = new Grid<>(emptyGrid);
        for(Coord c : grid.coordIterable()){
            grid.grid[c.y()][c.x()] = builder.apply(c);
        }
        return grid;
    }

    private static <E> void throwIfGridInvalid(E[][] grid){
        if(isGridDegenerate(grid)){
            throw new IllegalArgumentException("The grid is degenerate!");
        }
        if(isGridNonRectangular(grid)){
            throw new IllegalArgumentException("The grid is not rectangular!");
        }
    }

    private static <E> boolean isGridDegenerate(E[][] grid){
        if(grid.length == 0) return true;
        for(E[] row : grid){
            if(row.length == 0) return true;
        }
        return false;
    }

    private static <E> boolean isGridNonRectangular(E[][] grid){
        int cols = grid[0].length;
        for(E[] row : grid){
            if(row.length != cols) return true;
        }
        return false;
    }


    //Accessors
    public Size getSize(){
        return size;
    }

    public T getValue(Coord c){
        return grid[c.y()][c.x()];
    }

    public boolean withinBounds(Coord c){
        return size.withinBounds(c);
    }

    public int numCardinalNeighbours(Coord c){
        int neighbours = 0;
        if(c.x() > 0) neighbours++;
        if(c.y() > 0) neighbours++;
        if(c.x() < size.width()-1) neighbours++;
        if(c.y() < size.height()-1) neighbours++;
        return neighbours;
    }

    public int numNeighbours(Coord c){
        if(size.width() == 1 || size.height() == 1){
            return numNeighboursInFlatGrid(c);
        }
        return numNeighboursInNonTrivialGrid(c);
    }

    private int numNeighboursInFlatGrid(Coord c){
        if(size.width() == 1 && size.height() == 1){
            return 0;
        }
        if(size.width() == 1){
            return (c.y() == 0 || c.y() == size.height()-1) ? 1 : 2;
        }
        if(size.height() == 1){
            return (c.x() == 0 || c.x() == size.width()-1) ? 1 : 2;
        }
        throw new IllegalStateException("Grid not flat!");
    }

    private int numNeighboursInNonTrivialGrid(Coord c){
        int cardinalNeighbours = numCardinalNeighbours(c);
        int numNeighbours = 8;
        if(cardinalNeighbours <= 3) numNeighbours -= 3;
        if(cardinalNeighbours <= 2) numNeighbours -= 2;
        return numNeighbours;
    }


    //Traversal
    @Override
    public Iterator<T> iterator(){
        return new ArrayGridIterator<>(grid);
    }
    
    public Iterable<Coord> coordIterable(){
        return () -> new CoordIterator(size);
    }

    public Iterable<T> row(int y){
        return Arrays.asList(grid[y]);
    }

    public Iterable<T> col(int x){
        return () -> new ColumnIterator<>(grid, x);
    }

    public Iterable<T> cardinalNeighbours(Coord c){
        return () -> NeighbourIterator.overCardinals(this, c);
    }

    public Iterable<T> neighbours(Coord c){
        return () -> NeighbourIterator.overWholeCompass(this, c);
    }

}
