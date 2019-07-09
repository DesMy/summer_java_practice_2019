package Model;

import Controller.GraphDrawer;
import Model.FordFulkerson;
import Model.*;
import Ui.Graphicsview;
import org.junit.Assert;
import org.junit.Test;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FordFulkersonTest {
    private int [][] array;
    private int size;
    private Graph graph = new Graph();
    Graphicsview frame;
    private GraphDrawer graphDrawer = new GraphDrawer(graph, frame);


    public void readFromFile(String path) {
        try
        {
            Scanner sc = new Scanner(new File(path));
            size = sc.nextInt();
            array = new int[size][size];
            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    array[i][j] = sc.nextInt();
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Sorry, File not found!");
        }
        catch (InputMismatchException e)
        {
            System.out.println("Sorry, InputMismatchException");
        }
    }

    @Test
    public void algorithmFirstTest()  {
        String path = "src/main/resources/Test/test1.txt";
        readFromFile(path);
        FordFulkerson fordFulkerson = new FordFulkerson(array, graph, graphDrawer);
        Assert.assertEquals(fordFulkerson.maxFlow(array, 0, array.length - 1), 9);
    }

    @Test
    public void algorithmSecondTest()  {
        String path = "src/main/resources/Test/test2.txt";
        readFromFile(path);
        FordFulkerson fordFulkerson = new FordFulkerson(array, graph, graphDrawer);
        Assert.assertEquals(fordFulkerson.maxFlow(array, 0, array.length - 1), 11);
    }
}