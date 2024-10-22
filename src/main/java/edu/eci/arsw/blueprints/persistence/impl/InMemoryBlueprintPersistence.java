/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hcadavid
 */
@Component
@Qualifier("inMemory")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts1=new Point[]{new Point(88, 45),new Point(39, 64),new Point(1254,546),new Point(7,8),new Point(4,2),new Point(11,22)};
        Blueprint bp=new Blueprint("johnconnor", "firstBlueprint",pts1);

        Point[] ptsj=new Point[]{new Point(300, 50),new Point(70, 112),new Point(99, 114),new Point(2145,56)};
        Blueprint bpj=new Blueprint("johnconnor", "anotherBlueprint",ptsj);

        Point[] ts1= new Point[] {new Point(100, 400),
                new Point(300, 400), // Base derecha
                new Point(300, 300), // Techo derecha
                new Point(200, 200), // Pico del techo
                new Point(100, 300)  // Techo izquierda
        };
        Blueprint bp3j=new Blueprint("johnconnor", "anotherBlueprint",ts1);

        Blueprint bp2=new Blueprint("maryweyland", "secondBlueprint",pts1);

        Point[] pts3=new Point[]{new Point(30, 10),new Point(250, 55), new Point(300, 50),new Point(70, 112),new Point(99, 114),new Point(2145,56)};
        Blueprint bp3=new Blueprint("Saray", "thirdBlueprint",pts3);

        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        blueprints.put(new Tuple<>(bpj.getAuthor(),bpj.getName()), bpj);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3j);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3j.getName()), bp3j);
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }
    }


    public Blueprint getBlueprint(String author, String bprintname) {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) {
        Set<Blueprint> set = new HashSet<>();
        for (Tuple<String, String> tuple: blueprints.keySet()){
            if (blueprints.get(tuple).getAuthor().equals(author)){
                Blueprint blueprint = getBlueprint(blueprints.get(tuple).getAuthor(), blueprints.get(tuple).getName());
                set.add(blueprint);
            }
        }
        return set;
    }

    @Override
    public Set<Blueprint> getAllBlueprints()  {
        Set<Blueprint> set = new HashSet<>();
        for (Tuple<String,String> tuple : blueprints.keySet()){
            Blueprint blueprint = getBlueprint(blueprints.get(tuple).getAuthor(), blueprints.get(tuple).getName());
            set.add(blueprint);
        }
        return set;
    }

    @Override
    public void updateBlueprint(String bpautor, String bpname, Point[] points) {
        System.out.println(bpautor+" "+bpname);
        System.out.println(points);
        Blueprint bp1 = null;
        bp1 = getBlueprint(bpautor,bpname);
        System.out.println(bp1.getPoints());
        bp1.setPoints(Arrays.asList(points));
        //saveBlueprint(bp1);
    }

    @Override
    public void deleteBlueprint(String bpname, String bpauthor) {
        blueprints.remove(new Tuple<>(bpname,bpauthor),getBlueprint(bpname,bpauthor));
    }



}
