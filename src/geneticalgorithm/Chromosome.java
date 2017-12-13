/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Chromosome {
    
    private int points[][];
    private final int ROW = 4, COLUMN = 5;
    private ArrayList<String> checkpoints = new ArrayList<>();
    protected final int CHROSOMEAMOUNT = 4;
    protected int genCount=0;
    
    public Chromosome(int[][] points){
        this.points = points;
    }
    
    public ArrayList<String> CheckPoints(){
        ArrayList<String> checkpointsMain = new ArrayList<>();
        for(int i=0; i<ROW;i++){
            for(int j=0; j<COLUMN; j++){
                if(points[i][j]==1){
                    checkpointsMain.add(i+"x"+j);
                    genCount++;
                }
            }
        }
        
        return checkpointsMain;
    }
    
    public Stack<String> prepareChromosome(ArrayList<String> checkpointsMain){
        //prepare
        Random rand = new Random();
        Stack<String> chromosomes = new Stack<>();
        chromosomes.clear();
        StringBuilder sb = new StringBuilder();
        String checkPoint;
        //algorithm
        int size = checkpointsMain.size();
        for(int j = 0; j < CHROSOMEAMOUNT; j++){
            checkpoints = (ArrayList<String>) checkpointsMain.clone();
            for(int i = 0; i < size ; i++){
                int randpos = rand.nextInt(checkpoints.size());
                sb.append(checkpoints.get(randpos)+"&");
                checkpoints.remove(randpos);
            }
            //delete last & character
            sb.deleteCharAt(sb.length()-1);
            if(!chromosomes.contains(sb.toString())){
                chromosomes.push(sb.toString());
            }else{
                j--;
            }
            sb.setLength(0);
        }

        return chromosomes;
    }
}
