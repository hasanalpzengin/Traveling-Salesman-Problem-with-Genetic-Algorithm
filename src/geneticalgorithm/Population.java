package geneticalgorithm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Population {
    private Stack<String> populationMain = new Stack<>();

    public Population(Stack<String> population) {
        this.populationMain = population;
    }

    public Stack<String> getPopulation() {
        CreatePath.result.append("-------Chromosomes-----\n");
        for(int i=0;i<populationMain.size();i++){
            CreatePath.result.append(populationMain.get(i)+"\n");
        }
        CreatePath.result.append("---------------\n");
        return populationMain;
    }

    public void setPopulation(Stack<String> population) {
        this.populationMain = population;
    }
    
    
    
    protected Stack<Double> getMeasurements(Stack<String> population){
        //prepare
        Stack<Double> lengths = new Stack<>();
        double length = 0;
        int counter = 0;
        CreatePath.result.append("------Fitness Value-----\n");
        while(population.size()>counter){
            length = measurement(population.get(counter));
            lengths.push(length);
            CreatePath.result.append(length+"\n");
            counter++;
        }
        CreatePath.result.append("---------------\n");
        
        return lengths;
    }
    
    protected double measurement(String chromosome){
        double length = 0;
        
            String[] gens = chromosome.split("&");
            for(int j=0; j<gens.length-1; j++){
                int y1 = Integer.parseInt(gens[j].split("x")[0]);
                int x1 = Integer.parseInt(gens[j].split("x")[1]);
                int y2 = Integer.parseInt(gens[j+1].split("x")[0]);
                int x2 = Integer.parseInt(gens[j+1].split("x")[1]);
                
                length += (Double)Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
            }
        
        return length;
    }
    
    protected String crossover(String chromosome1, String chromosome2){
        StringBuilder sb = new StringBuilder();
        String[] genes1 = chromosome1.split("&");
        String[] genes2 = chromosome2.split("&");
        int lenght = genes1.length;
        //select half of genes from first chrosome
        for(int i=0; i<Math.floor(lenght*(70/100)); i++){
            sb.append(genes1[i]+"&");
        }
        //if new Chromosome don't has gen from genes2 append
        for(int j=0; j<genes2.length; j++){
            if(!sb.toString().contains(genes2[j])){
                sb.append(genes2[j]+"&");
            }
        }
        
        sb.deleteCharAt(sb.length()-1);
        
        return sb.toString();
    }
    
    protected String mutation(String chromosome1){
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        
        String[] genes = chromosome1.split("&");
        
        int mutationChance = rand.nextInt(100);
        
        //%1 mutation chance
        if(mutationChance==0){
            int pos1 = rand.nextInt(genes.length);
            int pos2 = pos1;
            //generate 2 rand pos
            do{
                pos2 = rand.nextInt(genes.length);
            }while(pos1 == pos2);
            
            for(int i=0; i<genes.length; i++){
                if(i==pos1){
                    sb.append(genes[pos2]+"&");
                }else if(i==pos2){
                    sb.append(genes[pos1]+"&");
                }else{
                    sb.append(genes[i]+"&");
                }
            }
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        }
        
        
        return chromosome1;
    }
    
    private ArrayList<Double> rouleteWheel(Stack<String> population){
        //init
        ArrayList<Double> measurements = new ArrayList<>();
        ArrayList<Double> wheel = new ArrayList<>();
        //calculate total
        double total = 0;
        for(String chromosome: population){
            total += (double)1/measurement(chromosome);
            measurements.add((double)1/measurement(chromosome));
        }
        
        double degree = 0;

        //wheel creation
        for(double chromosome: measurements){            
            wheel.add(degree);
            degree += (chromosome/total)*100;
        }
        
        
        
        return wheel;
    }
    
    protected String selectFromWheel(Stack<String> population){
        //get wheel
        ArrayList<Double> wheel = rouleteWheel(population);
        String selected="";
        Random rand = new Random();
        
        int random = rand.nextInt(100);
        
        for(int i=0;i<wheel.size()-1;i++){
            if(random>=wheel.get(i) && random<wheel.get(i+1)){
                selected=population.get(i);
            }
        }
        
        return selected;
    }
    
}
